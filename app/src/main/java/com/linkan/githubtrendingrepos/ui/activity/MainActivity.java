package com.linkan.githubtrendingrepos.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.appcompat.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.linkan.githubtrendingrepos.BR;
import com.linkan.githubtrendingrepos.R;
import com.linkan.githubtrendingrepos.ViewModelProviderFactory;
import com.linkan.githubtrendingrepos.base.BaseActivity;
import com.linkan.githubtrendingrepos.base.BaseRecyclerViewAdapter;
import com.linkan.githubtrendingrepos.base.ResourceType;
import com.linkan.githubtrendingrepos.data.model.db.TrendingRepo;
import com.linkan.githubtrendingrepos.data.model.other.Status;
import com.linkan.githubtrendingrepos.databinding.ActivityMainBinding;
import com.linkan.githubtrendingrepos.ui.adapter.TrendingRepoAdapter;


import java.util.List;

import javax.inject.Inject;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> implements View.OnClickListener, BaseRecyclerViewAdapter.BaseRecyclerViewAdapterListener {

    private PopupWindow popupWindow;

    @Inject
    ViewModelProviderFactory mViewModelFactory;


    private MainViewModel mMainViewModel;

    @Inject
    LinearLayoutManager mLayoutManager;

    @Inject
    TrendingRepoAdapter repoAdapter;


    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected Toolbar getSupportedToolbar() {
        return getViewDataBinding().includedAppBar.toolbar;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public MainViewModel getViewModel() {
        mMainViewModel = ViewModelProviders.of(this, mViewModelFactory).get(MainViewModel.class);
        return mMainViewModel;
    }


    private void setCustomTitle() {
        getViewDataBinding().includedAppBar.title.setText(getResources().getString(R.string.trending));
    }

    @Override
    public void initOnCreate(@Nullable Bundle savedInstanceState) {
        repoAdapter.setListener(this);
        setStatusBarTranslucent();
        setTitle("");
        setCustomTitle();
        createCustomOptionMenu();
        //.. To Hide the home back button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
        }

        //   getViewModel().fetchFromCacheOrServer();

        setUpRecyclerView();
        subscribeToLiveData();
        subscribeErrorLiveData();
        getViewDataBinding().includedAppBar.imgOptionMenu.setOnClickListener(this);

    }


    private void subscribeToLiveData() {

        getViewModel().getTrendingRepoLiveData().observe(this, new Observer<ResourceType<List<TrendingRepo>>>() {
            @Override
            public void onChanged(ResourceType<List<TrendingRepo>> listResourceType) {

                switch (listResourceType.status){
                    case LOADING:
                        getViewModel().setIsLoading(true);
                        break;
                    case REFRESH:
                        getViewModel().setRefreshLoader(true);
                        break;
                    case SUCCESS:
                        getViewModel().setRepoObservableList(listResourceType.data);
                        getViewModel().setIsLoading(false);
                        getViewModel().setRefreshLoader(false);
                        break;
                    case ERROR:
                        repoAdapter.setRetryVisibility(true);
                        getViewModel().setIsLoading(false);
                        getViewModel().setRefreshLoader(false);
                        break;
                }
            }
        });

    }


    @SuppressLint("WrongConstant")
    private void setUpRecyclerView() {
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        getViewDataBinding().repoRecyclerView.setLayoutManager(mLayoutManager);
        getViewDataBinding().repoRecyclerView.setItemAnimator(new DefaultItemAnimator());
        getViewDataBinding().repoRecyclerView.setAdapter(repoAdapter);
    }


    private void createCustomOptionMenu() {

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.custom_option_menu, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        popupWindow = new PopupWindow(popupView, width, height, focusable);

        popupView.findViewById(R.id.sort_by_stars).setOnClickListener(this);

        popupView.findViewById(R.id.sort_by_name).setOnClickListener(this);

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // popupWindow.dismiss();
                return true;
            }
        });

    }

    private void setStatusBarTranslucent() {

        //make translucent statusBar on kitkat devices
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }

        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        //make fully Android Transparent Status bar
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

    }


    public static void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_option_menu:
                // show the popup window
                // which view you pass in doesn't matter, it is only used for the window token
                popupWindow.showAtLocation(getViewDataBinding().clRoot, Gravity.TOP | Gravity.END, 25, 200);
                break;
            case R.id.sort_by_stars:
                repoAdapter.sortByStars();
                break;
            case R.id.sort_by_name:
                repoAdapter.sortByName();
                break;
        }
    }

    @Override
    public void onRetryClick() {
        getViewModel().fetchFromCacheOrServer(false);
    }


    private void subscribeErrorLiveData() {

        getViewModel().getErrorLiveData().observe(this, throwable -> {
            super.handleError(throwable);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    repoAdapter.setRetryVisibility(true);
                }
            }, 100);
        });
    }

}
