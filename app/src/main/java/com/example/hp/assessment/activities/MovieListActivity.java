package com.example.hp.assessment.activities;

import android.app.SearchManager;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.example.hp.assessment.R;
import com.example.hp.assessment.adpters.MovieListAdapter;
import com.example.hp.assessment.databinding.ActivityMainBinding;
import com.example.hp.assessment.databinding.viewmodels.MovieModel;
import com.example.hp.assessment.utils.ListUtils;
import com.example.hp.assessment.utils.Utils;
import com.example.hp.assessment.viewmodels.MovieViewModel;
import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView;

import org.reactivestreams.Subscription;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

public class MovieListActivity extends BaseActivity<ActivityMainBinding, MovieViewModel> implements MovieListAdapter.OnLoadMoreCallBack {

    private static final String TAG = "MovieListActivity";
    private static final long SEARCH_DEBOUNCE_TIMEOUT = 500;
    private MovieListAdapter mMovieListAdapter;
    private boolean isLoading;
    private MenuItem mSearchMenu;
    private SearchView mSearchView;
    private CharSequence mQueryString;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = provideViewModel(this, MovieViewModel.class);
        setupToolbar(mBinding.movieListToolbar.getId());
        initVariables();
        initList();
        fetchInitialMovieList();
    }

    private void initVariables() {
        mMovieListAdapter = new MovieListAdapter(this);
    }

    private void initList() {
        mBinding.rvMovieList.setAdapter(mMovieListAdapter);
        mBinding.rvMovieList.setLayoutManager(new GridLayoutManager(this, 2));
    }

    /*
    * Fetch initial movie list. First check from DB. If there is no movie in the DB call Api to fetch new list
    * */
    private void fetchInitialMovieList() {
        mCompositeDisposable.add(mViewModel.getInitMovieList()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Subscription>() {
                    @Override
                    public void accept(Subscription subscription) throws Exception {
                        showProgressDialog(getString(R.string.text_getting_movie_list));
                        setProgressCancelable(false);
                        isLoading = true;
                    }
                }).doAfterTerminate(new Action() {
                    @Override
                    public void run() throws Exception {
                        isLoading = false;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSubscriber<List<MovieModel>>() {
                    @Override
                    public void onNext(List<MovieModel> movieModels) {
                        Log.d(TAG, "onSuccess: " + movieModels.size());
                        cancelProgressDialog();
                        isLoading = false;
                        if (!ListUtils.isEmpty(movieModels)) {
                            mViewModel.incrementCurrentPage();
                            mMovieListAdapter.setData((ArrayList<MovieModel>) movieModels);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {}

                    @Override
                    public void onComplete() {}
                }));
    }

    @Override
    public void onLoadMore() {
        if (!isLoading)
            loadMovieList();
    }

    private void loadMovieList() {
        mCompositeDisposable.add(mViewModel.loadMoreMovieList()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        isLoading = true;
                    }
                })
                .doAfterTerminate(new Action() {
                    @Override
                    public void run() throws Exception {
                        isLoading = false;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableCompletableObserver(){
                    @Override
                    public void onComplete() {}

                    @Override
                    public void onError(Throwable e) {
                        Utils.showToast(MovieListActivity.this, getString(R.string.error_unable_loading));
                    }
                }));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);

        mSearchMenu = menu.findItem(R.id.action_search);

        // Retrieve the SearchView and plug it into SearchManager
        mSearchView = (SearchView) mSearchMenu.getActionView();
        mSearchView.setMaxWidth(4000);
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        AutoCompleteTextView searchTextView = mSearchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        try {
            Field mCursorDrawableRes = TextView.class.getDeclaredField("mCursorDrawableRes");
            mCursorDrawableRes.setAccessible(true);
            mCursorDrawableRes.set(searchTextView, R.drawable.cursor); //This sets the cursor resource ID to 0 or @null which will make it visible on white background
        } catch (Exception e) {
            e.printStackTrace();
        }

        mCompositeDisposable.add(RxSearchView.queryTextChanges(mSearchView)
                .debounce(SEARCH_DEBOUNCE_TIMEOUT, TimeUnit.MILLISECONDS)
                .switchMap(new Function<CharSequence, Observable<ArrayList<MovieModel>>>() {
                    @Override
                    public Observable<ArrayList<MovieModel>> apply(CharSequence charSequence) throws Exception {
                        Log.d(TAG, "apply: " + charSequence);
                        mQueryString = charSequence;
                        if (!TextUtils.isEmpty(charSequence))
                            return mViewModel.searchMovie(charSequence);
                        else
                            return Observable.just(new ArrayList<MovieModel>());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ArrayList<MovieModel>>() {
                    @Override
                    public void onNext(ArrayList<MovieModel> movieModels) {
                        Log.d(TAG, "Search onNext: " + movieModels.size());
                        if (TextUtils.isEmpty(mQueryString)){
                            mMovieListAdapter.revert();
                            mBinding.setEmptyPlaceHolderEnable(false);
                        }else {
                            mMovieListAdapter.setData(movieModels);
                            if (ListUtils.isEmpty(movieModels)){
                                mBinding.setEmptyPlaceHolderEnable(true);
                            } else {
                                mBinding.setEmptyPlaceHolderEnable(false);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {}

                    @Override
                    public void onComplete() {}
                }));
        return true;
    }
}
