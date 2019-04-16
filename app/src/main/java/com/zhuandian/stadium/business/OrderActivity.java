package com.zhuandian.stadium.business;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.zhuandian.base.BaseActivity;
import com.zhuandian.stadium.MainActivity;
import com.zhuandian.stadium.R;
import com.zhuandian.stadium.adapter.StadiumAdapter;
import com.zhuandian.stadium.entity.StadiumEntity;
import com.zhuandian.stadium.entity.UserEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class OrderActivity extends BaseActivity {


    @BindView(R.id.rv_list)
    RecyclerView rvList;
    private String userId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order;
    }

    @Override
    protected void setUpView() {
        this.setTitle("我的订单");
        UserEntity userEntity = BmobUser.getCurrentUser(UserEntity.class);
        userId = userEntity.getObjectId();
        initData();
    }

    private void initData() {
        BmobQuery<StadiumEntity> query = new BmobQuery<>();
        query.addWhereEqualTo("userId", userId);
        query.findObjects(new FindListener<StadiumEntity>() {
            @Override
            public void done(List<StadiumEntity> list, final BmobException e) {
                if (e == null) {
                    StadiumAdapter adapter = new StadiumAdapter(list, OrderActivity.this);
                    rvList.setAdapter(adapter);
                    rvList.setLayoutManager(new LinearLayoutManager(OrderActivity.this));
                    adapter.setReserveClickListener(new StadiumAdapter.OnReserveClickListener() {
                        @Override
                        public void onClick(StadiumEntity stadiumEntity) {
                            if (stadiumEntity.getState() == 1) {
                                Toast.makeText(OrderActivity.this, "您已经预约过该场馆，无需重复预约...", Toast.LENGTH_SHORT).show();
                            } else {
                                Intent intent = new Intent(OrderActivity.this, StadiumDetailActivity.class);
                                intent.putExtra("entity", stadiumEntity);
                                startActivity(intent);
                            }
                        }
                    });
                }
            }
        });
    }
}
