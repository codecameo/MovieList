package com.example.hp.assessment.adpters;

import android.databinding.DataBindingUtil;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.hp.assessment.R;
import com.example.hp.assessment.databinding.ItemMovieListBinding;
import com.example.hp.assessment.databinding.viewmodels.MovieModel;
import com.example.hp.assessment.diffutils.MovieListDiff;
import com.example.hp.assessment.utils.ListUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP on 12/18/2017.
 */

public class MovieListAdapter extends BaseRecyclerAdapter<MovieListAdapter.ViewHolder> {
    private static final String TAG = "SubjectSelectionAdapter";
    private List<MovieModel> mMovieLists;
    private OnLoadMoreCallBack mLoadMoreCallBack;
    private List<MovieModel> mTempMovieModels;

    public MovieListAdapter(OnLoadMoreCallBack loadMoreCallBack){
        mMovieLists = new ArrayList<>();
        mTempMovieModels = new ArrayList<>();
        mLoadMoreCallBack = loadMoreCallBack;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemMovieListBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_movie_list, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: "+position);
        holder.bind();
        if (position == getItemCount() - 1){
            if (mLoadMoreCallBack != null)
                mLoadMoreCallBack.onLoadMore();
        }
    }

    @Override
    public int getItemCount() {
        return mMovieLists.size();
    }

    public void revert() {
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(new MovieListDiff(mMovieLists, mTempMovieModels));
        mMovieLists.clear();
        mMovieLists.addAll(mTempMovieModels);
        result.dispatchUpdatesTo(listUpdateCallback);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemMovieListBinding mBinding;
        public ViewHolder(ItemMovieListBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bind() {
            mBinding.setModel(mMovieLists.get(getAdapterPosition()));
        }
    }

    public void setData(ArrayList<MovieModel> movieList){
        if (ListUtils.isEmpty(movieList)) return;
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(new MovieListDiff(mMovieLists, movieList));
        mMovieLists.clear();
        mMovieLists.addAll(movieList);
        mTempMovieModels.clear();
        mTempMovieModels.addAll(mMovieLists);
        result.dispatchUpdatesTo(listUpdateCallback);
    }

    public void addData(ArrayList<MovieModel> movieList) {
        if (ListUtils.isEmpty(movieList)) return;

        mTempMovieModels.clear();
        mTempMovieModels.addAll(mMovieLists);
        mTempMovieModels.addAll(movieList);

        DiffUtil.DiffResult result = DiffUtil.calculateDiff(new MovieListDiff(mMovieLists, mTempMovieModels));

        mMovieLists.addAll(movieList);
        result.dispatchUpdatesTo(listUpdateCallback);
    }

    public interface OnLoadMoreCallBack{
        void onLoadMore();
    }
}
