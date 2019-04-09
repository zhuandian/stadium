package com.zhuandian.stadium;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;
import com.zhuandian.base.BaseActivity;
import com.zhuandian.stadium.adapter.StadiumAdapter;
import com.zhuandian.stadium.business.HelpActivity;
import com.zhuandian.stadium.business.StadiumDetailActivity;
import com.zhuandian.stadium.entity.StadiumEntity;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * desc :首页
 * author：xiedong
 * date：2019/4/9
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.tv_help)
    TextView tvHelp;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void setUpView() {
        initData();
    }

    private void initData() {
        BmobQuery<StadiumEntity> query = new BmobQuery<>();
        query.findObjects(new FindListener<StadiumEntity>() {
            @Override
            public void done(List<StadiumEntity> list, final BmobException e) {
                if (e == null) {
                    StadiumAdapter adapter = new StadiumAdapter(list, MainActivity.this);
                    rvList.setAdapter(adapter);
                    rvList.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    adapter.setReserveClickListener(new StadiumAdapter.OnReserveClickListener() {
                        @Override
                        public void onClick(StadiumEntity stadiumEntity) {
                            if (stadiumEntity.getState() == 1) {
                                Toast.makeText(MainActivity.this, "该场馆已被预定，请联系管理员协调，或者预定其他场馆...", Toast.LENGTH_SHORT).show();
                            } else {
                                Intent intent = new Intent(MainActivity.this, StadiumDetailActivity.class);
                                intent.putExtra("entity", stadiumEntity);
                                startActivity(intent);
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    @OnClick(R.id.tv_help)
    public void onClick() {
        startActivity(new Intent(MainActivity.this, HelpActivity.class));
    }
}
