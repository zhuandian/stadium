package com.zhuandian.stadium.business;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhuandian.base.BaseActivity;
import com.zhuandian.stadium.R;
import com.zhuandian.stadium.entity.StadiumEntity;
import com.zhuandian.stadium.entity.UserEntity;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
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
    private String dayStr, timeStr;

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
        initDialog();

    }


    private void initDialog() {
        View dialogView = LayoutInflater.from(StadiumDetailActivity.this).inflate(R.layout.layout_dialog, null);
        final TextView day = dialogView.findViewById(R.id.et_day);
        day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog dialog = new DatePickerDialog(StadiumDetailActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        dayStr = String.format("%s年%s月%s日", year, month, dayOfMonth);
                    }
                },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                dialog.show();

            }
        });
        RadioButton rbMorning = dialogView.findViewById(R.id.rb_morning);
        RadioButton rbAfternorr = dialogView.findViewById(R.id.rb_afternooe);
        rbMorning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeStr = "上午";
            }
        });
        rbAfternorr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeStr = "下午";
            }
        });
        new AlertDialog.Builder(StadiumDetailActivity.this)
                .setView(dialogView)
                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int which) {
                        stadiumEntity.setState(1);
                        stadiumEntity.setTime(dayStr + timeStr);
                        stadiumEntity.setUserId(BmobUser.getCurrentUser(UserEntity.class).getObjectId());
                        stadiumEntity.update(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    new AlertDialog.Builder(StadiumDetailActivity.this)
                                            .setTitle("成功")
                                            .setMessage(dayStr + timeStr + "场馆预定成功")
                                            .create()
                                            .show();
                                    dialog.dismiss();
                                }
                            }


                        });
                    }
                })
                .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();


    }
}
