package cn.hg.common;

import android.content.Context;
import android.content.SharedPreferences;

import com.blankj.utilcode.util.SPUtils;
import com.google.gson.Gson;

public class User {

    private String avatar;
    private String nickname;
    private String token;
    private String username;
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void save() {
        SPUtils.getInstance().put("user", new Gson().toJson(this));
        SPUtils.getInstance().put("token",token);
    }

    public static User getCurrentUser() {
        return new Gson().fromJson(SPUtils.getInstance().getString("user"), User.class);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
