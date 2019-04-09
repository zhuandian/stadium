package com.zhuandian.stadium.entity;

import cn.bmob.v3.BmobUser;

/**
 * desc :用户实体
 * author：xiedong
 * data：2019/04/09
 */
public class UserEntity extends BmobUser {
    private String nikeName;
    private String userInfo;

    public String getNikeName() {
        return nikeName;
    }

    public void setNikeName(String nikeName) {
        this.nikeName = nikeName;
    }

    public String getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
    }
}
