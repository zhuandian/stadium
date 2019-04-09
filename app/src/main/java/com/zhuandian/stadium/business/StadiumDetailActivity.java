package com.zhuandian.stadium.business;

import android.support.v7.app.AlertDialog;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhuandian.base.BaseActivity;
import com.zhuandian.stadium.R;
import com.zhuandian.stadium.entity.StadiumEntity;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * desc :体育馆详情页
 * author：xiedong
 * date：2019/4/9
 */
public class StadiumDetailActivity extends BaseActivity {


    @BindView(R.id.iv_stadium_img)
    ImageView ivStadiumImg;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_desc)
    TextView tvDesc;
    @BindView(R.id.tv_reserve)
    TextView tvReserve;
    private StadiumEntity stadiumEntity;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_stadium_detail;
    }

    @Override
    protected void setUpView() {
        stadiumEntity = (StadiumEntity) getIntent().getSerializableExtra("entity");
        tvDesc.setText(String.format("%s\n%s", stadiumEntity.getName(), stadiumEntity.getContent()));
        tvPrice.setText(String.format("%s/小时", stadiumEntity.getPrice()));
        Glide.with(this).load(stadiumEntity.getImgUrl()).into(ivStadiumImg);
    }


    @OnClick(R.id.tv_reserve)
    public void onClick() {
        stadiumEntity.setState(1);
        stadiumEntity.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    new AlertDialog.Builder(StadiumDetailActivity.this)
                            .setTitle("成功")
                            .setMessage("场馆预定成功")
                            .create()
                            .show();
                }
            }
        });
    }
}
