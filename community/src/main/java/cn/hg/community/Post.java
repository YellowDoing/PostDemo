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
    private Integer comment_num;
    private List<Great> greatList;

    public void setGreat(boolean isGreat) {
        if (isGreat)
            greatList.add(new Great(id, User.getCurrentUser().getId()));
        else {
            int index = -1;
            for (int i = 0; i < greatList.size(); i++) {
                if (User.getCurrentUser().getId().equals(greatList.get(i).user_id)) {
                    index = i;
                }
            }
            greatList.remove(index);
        }

    }

    public boolean isGreat() {
        boolean isGreat = false;
        for (Great great : greatList) {
            if (User.getCurrentUser().getId().equals(great.user_id)) {
                isGreat = true;
            }
        }
        return isGreat;
    }

    public List<Great> getGreatList() {
        return greatList;
    }

    public void setGreatList(List<Great> greatList) {
        this.greatList = greatList;
    }


    public Integer getComment_num() {
        return comment_num;
    }

    public void setComment_num(Integer comment_num) {
        this.comment_num = comment_num;
    }


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
        return new Gson().fromJson(media_attachment, new TypeToken<ArrayList<String>>() {
        }.getType());
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

    private static class Great {
        private Integer post_id;
        private Integer user_id;

        public Great(Integer post_id, Integer user_id) {
            this.post_id = post_id;
            this.user_id = user_id;
        }

        public Integer getPost_id() {
            return post_id;
        }

        public void setPost_id(Integer post_id) {
            this.post_id = post_id;
        }

        public Integer getUser_id() {
            return user_id;
        }

        public void setUser_id(Integer user_id) {
            this.user_id = user_id;
        }
    }


    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", media_attachment='" + media_attachment + '\'' +
                ", type=" + type +
                ", user_id=" + user_id +
                ", creator=" + creator +
                ", create_time='" + create_time + '\'' +
                ", update_time='" + update_time + '\'' +
                ", comment_num=" + comment_num +
                ", greatList=" + greatList +
                '}';
    }
}
