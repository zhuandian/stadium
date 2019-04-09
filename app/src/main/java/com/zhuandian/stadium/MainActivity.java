package com.zhuandian.stadium;

import com.zhuandian.base.BaseActivity;
import com.zhuandian.stadium.utils.Constant;

public class MainActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void setUpView() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Constant.IS_LOGINED = false;
    }
}
