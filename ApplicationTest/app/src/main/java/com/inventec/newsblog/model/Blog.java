package com.inventec.newsblog.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.ArrayList;

/**
 * 开源实验室博客
 * Created by Test on 2017/2/13.
 */

@XStreamAlias("item")
public class Blog implements Parcelable {
    @XStreamAlias("title")
    private String title;
    @XStreamAlias("description")
    private String description;
    @XStreamAlias("pubDate")
    private String pubDate;
    @XStreamAlias("link")
    private String link;
    @XStreamAlias("recommend")
    private String recommend;
    @XStreamAlias("image")
    private String image;
    @XStreamAlias("content")
    private String content;
    private String author;

    @XStreamImplicit
    private ArrayList<String> tag = new ArrayList<>();
    @XStreamImplicit
    private ArrayList<String> category = new ArrayList<>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        if (TextUtils.isEmpty(author)) author = "wlyy";
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.pubDate);
        dest.writeString(this.link);
        dest.writeString(this.recommend);
        dest.writeString(this.image);
        dest.writeString(this.content);
        dest.writeString(this.author);
        dest.writeStringList(this.tag);
        dest.writeStringList(this.category);
    }

    public Blog() {
    }

    protected Blog(Parcel in) {
        this.title = in.readString();
        this.description = in.readString();
        this.pubDate = in.readString();
        this.link = in.readString();
        this.recommend = in.readString();
        this.image = in.readString();
        this.content = in.readString();
        this.author = in.readString();
        this.tag = in.createStringArrayList();
        this.category = in.createStringArrayList();
    }

    public static final Parcelable.Creator<Blog> CREATOR = new Parcelable.Creator<Blog>() {

        @Override
        public Blog createFromParcel(Parcel source) {
            return new Blog(source);
        }
        @Override
        public Blog[] newArray(int size) {
            return new Blog[size];
        }
    };

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Blog) {
            Blog data = (Blog) obj;
            if (data.link != null) return data.link.equals(link);
            else return link == null;
        } else {
            return super.equals(obj);
        }
    }

    @Override
    public int hashCode() {
        return link.hashCode();
    }
}
