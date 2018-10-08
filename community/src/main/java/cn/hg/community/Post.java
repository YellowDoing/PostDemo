package cn.hg.community;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.hg.common.User;

public class Post implements Serializable {

    private Integer id;
    private String content;
    private String media_attachment;
    private Integer type;
    private Integer user_id;
    private User creator;
    private String create_time;
    private String update_time;

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }


    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ArrayList<String> getMedia_attachment() {
        return new Gson().fromJson(media_attachment,new TypeToken<ArrayList<String>>(){}.getType());
    }

    public void setMedia_attachment(List<String> media_attachment) {
        this.media_attachment = new Gson().toJson(media_attachment);
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
