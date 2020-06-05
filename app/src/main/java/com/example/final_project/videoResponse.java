package com.example.final_project;

import com.google.gson.annotations.SerializedName;


class videoResponse {
    @SerializedName("_id")
    public String _id;
    @SerializedName("feedurl")
    public String feedurl;
    @SerializedName("nickname")
    public String nickname;
    @SerializedName("description")
    public String description;
    @SerializedName("likecount")
    public String likecount;
    @SerializedName("avatar")
    public String avatar;

    @Override
    public String toString(){
        return "Article{" +
                "_id=" + _id +
                ",feedurl=" + feedurl +
                ",nickname=" + nickname +
                ",description=" + description +
                ",likecount=" + likecount +
                ",avatar=" + avatar + "}";
    }

    public String get_id(){return _id;}
    public String get_url(){return feedurl;}
    public String get_nickname(){return nickname;}
    public String get_description(){return description;}
    public String get_like_count(){return likecount;}
    public String get_avatar(){return avatar;}
    public void set_id(String i){this._id = i;}
    public void set_url(String i){this.feedurl = i;}
    public void set_nickname(String i){this.nickname = i;}
    public void set_description(String i){this.description = i;}
    public void set_like_count(String i){this.likecount = i;}
    public void set_avatar(String i){this.avatar = i;}
}
