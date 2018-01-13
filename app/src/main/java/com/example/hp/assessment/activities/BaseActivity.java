package com.example.hp.assessment.activities;

import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.hp.assessment.R;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Md. Sifat-Ul Haque on 12/01/2018.
 */

public abstract class BaseActivity<DB extends ViewDataBinding, VM extends ViewModel> extends AppCompatActivity {

    private static final String TAG = "BaseActivity";
    /* Primary toolbar*/
    protected Toolbar toolbar;
    protected ProgressDialog progressDialog;
    protected ActionBar actionBar;
    protected DB mBinding;
    protected VM mViewModel;
    protected CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    protected abstract
    @LayoutRes
    int getLayoutRes();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        performDependencyInjection();
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, getLayoutRes());
        //mViewModel = (VM) ViewModelProviders.of(this, viewModelFactory).get(mViewModel.getClass());
    }

    /**
     * Set toolbar into actionbar.
     */
    protected void setupToolbar(int id) {
        if (toolbar == null) {
            toolbar = findViewById(id);
            toolbar.setTitleTextColor(Color.WHITE);
        }

        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        }
    }

    protected void setupToolbar(int toolbar, String title){
        setupToolbar(toolbar);
        actionBar.setTitle(title);
    }


    /**
     * Initialize the loader for Child class whenever necessary.
     */
    public void initProgressLoader() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
    }

    /**
     * Initialize the loader for Child class whenever necessary.
     */
    public void initProgressLoader(boolean isCancelable) {
        initProgressLoader();
        progressDialog.setCancelable(isCancelable);
    }

    /**
     * Sets whether this dialog is cancelable with the
     */
    protected void setProgressCancelable(boolean isCancelable) {
        if (progressDialog != null) {
            progressDialog.setCancelable(isCancelable);
        }
    }

    /**
     * Show progress dialog.
     *
     * @param message The message show in the progress dialog initially.
     */
    public void showProgressDialog(String message) {
        if (progressDialog == null) {
            initProgressLoader();
        }

        progressDialog.setMessage(message);
        progressDialog.show();
    }

    /**
     * Cancel progress dialog.
     */
    public void cancelProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    public void showSnackBar(String message) {
        Snackbar.make(getWindow().getDecorView(), message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()== android.R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void performDependencyInjection() {
        AndroidInjection.inject(this);
    }

    protected VM provideViewModel(FragmentActivity fragment, Class<VM> modelClass){
        return ViewModelProviders.of(fragment, viewModelFactory).get(modelClass);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cancelProgressDialog();
        mCompositeDisposable.dispose();
    }
}
