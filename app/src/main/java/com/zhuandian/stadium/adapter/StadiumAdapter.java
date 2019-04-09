package com.zhuandian.stadium.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhuandian.base.BaseAdapter;
import com.zhuandian.base.BaseViewHolder;
import com.zhuandian.stadium.R;
import com.zhuandian.stadium.entity.StadiumEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * desc :
 * author：xiedong
 * date：2019/4/9
 */
public class StadiumAdapter extends BaseAdapter<StadiumEntity, BaseViewHolder> {
    @BindView(R.id.iv_stadium_img)
    ImageView ivStadiumImg;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_reserve)
    TextView tvReserve;

    public void setReserveClickListener(OnReserveClickListener reserveClickListener) {
        this.reserveClickListener = reserveClickListener;
    }

    private OnReserveClickListener reserveClickListener;


    public StadiumAdapter(List<StadiumEntity> mDatas, Context context) {
        super(mDatas, context);
    }

    @Override
    protected void converData(BaseViewHolder myViewHolder, final StadiumEntity stadiumEntity, int position) {
        ButterKnife.bind(this, myViewHolder.itemView);
        tvName.setText(stadiumEntity.getName());
        Glide.with(myViewHolder.itemView.getContext()).load(stadiumEntity.getImgUrl()).into(ivStadiumImg);
        tvContent.setText(stadiumEntity.getContent());
        tvPrice.setText(String.format("%s元/小时", stadiumEntity.getPrice()));
        tvTime.setText(stadiumEntity.getCreatedAt());
        tvReserve.setBackgroundColor(Color.parseColor(stadiumEntity.getState() == 1 ? "#999999" : "#318bc7"));
        tvReserve.setText(stadiumEntity.getState() == 1 ? "已被订" : "预定");
        tvReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (reserveClickListener != null) {
                    reserveClickListener.onClick(stadiumEntity);
                }
            }
        });
    }

    @Override
    public int getItemLayoutId() {
        return R.layout.item_stadium;
    }


    public interface OnReserveClickListener {
        void onClick(StadiumEntity stadiumEntity);
    }
}
