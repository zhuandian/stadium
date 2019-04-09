package com.zhuandian.stadium.business;

import android.content.Intent;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.zhuandian.base.BaseActivity;
import com.zhuandian.stadium.MainActivity;
import com.zhuandian.stadium.R;
import com.zhuandian.stadium.business.login.LoginActivity;
import com.zhuandian.stadium.utils.Constant;

public class SpalshActivity extends BaseActivity {


    @Override
    protected int getLayoutId() {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return R.layout.activity_spalsh;
    }

    @Override
    protected void setUpView() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SpalshActivity.this, Constant.IS_LOGINED ? MainActivity.class : LoginActivity.class));
                finish();
            }
        }, 2000);
    }
}
