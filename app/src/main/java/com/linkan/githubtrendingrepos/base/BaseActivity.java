package com.linkan.githubtrendingrepos.base;

import androidx.lifecycle.Observer;
import android.content.Context;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import android.os.Bundle;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.linkan.githubtrendingrepos.utils.NetworkUtils;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;



public abstract class BaseActivity<T extends ViewDataBinding, V extends BaseViewModel>
        extends AppCompatActivity implements HasSupportFragmentInjector{

    public final String TAG = this.getClass().getName();
    private T mViewDataBinding;
    private V mViewModel;


    @Inject
    DispatchingAndroidInjector<Fragment> mFragmentInjector;

    @Inject
    protected Context context;

    /**
     * @return layout resource id
     */
    public abstract @LayoutRes
    int getLayoutId();

    protected abstract androidx.appcompat.widget.Toolbar getSupportedToolbar();

    /**
     * Override for set binding variable
     *
     * @return variable id
     */
    public abstract int getBindingVariable();

    public abstract void initOnCreate(@Nullable Bundle savedInstanceState);


    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return mFragmentInjector;
    }


    public T getViewDataBinding() {
        return mViewDataBinding;
    }

    /**
     * Override for set view model
     *
     * @return view model instance
     */
    public abstract V getViewModel();

    public void performDependencyInjection() {
        AndroidInjection.inject(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        performDependencyInjection();

        super.onCreate(savedInstanceState);
        performDataBinding();

        setSupportActionBar(getSupportedToolbar());
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        initOnCreate(savedInstanceState);

    }


    private void performDataBinding() {
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId());
        this.mViewModel = mViewModel == null ? getViewModel() : mViewModel;
        mViewDataBinding.setVariable(getBindingVariable(), mViewModel);
        mViewDataBinding.executePendingBindings();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public boolean isNetworkConnected() {
        return NetworkUtils.isNetworkConnected(context);
    }


    public void handleError(Throwable throwable) {
//        if (throwable instanceof UnknownHostException)
//            showToast("Network Access Error");
//        else
//            showToast(throwable.getMessage());

    }


    public void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

}
