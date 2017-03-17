package com.inventec.newsblog.model.news;

import java.io.Serializable;
import java.util.List;

/**
 * 新闻条目实体类
 * Created by Test on 2017/3/1.
 */

public class NewsBean implements Serializable {

    private String channelId;// 频道id
    private String channelName;// 频道名称
    private String desc;// 新闻简要描述
    private List<NewsImage> imageurls;// 图片列表
    private String link;// 新闻详情链接
    private String content;// 新闻正文,txt格式
    private String html;//新闻正文,html格式
    private String nid;// 新闻对应的外网id
    private String pubDate;// 发布时间，2016-01-28 11:47:17
    private String source;// 来源网站
    private String title;// 新闻标题

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<NewsImage> getImageurls() {
        return imageurls;
    }

    public void setImageurls(List<NewsImage> imageurls) {
        this.imageurls = imageurls;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("newsBean:").append("{channelId:").append(channelId)
                .append( ",channelName:").append(channelName)
                .append(",title:").append(title)
                .append(",pubDate:" ).append(pubDate)
                .append(",source:").append(source)
                .append(",\ndesc:").append(desc)
                .append(",\nimageurls:").append(imageurls)
                .append(",\nlink:").append(link +"\n")
                .append(",content:").append(content)
                .append(",html:").append(html)
                .append(",nid:").append(nid).append("}\n");

        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof  NewsBean){
            NewsBean data = (NewsBean) obj;
            //通过比较新闻标题或者新闻链接判断两个对象是否相等
            if(data.title.equals(this.title) || (data.link.equals(this.link))){
                return true;
            }
        }else {
            return false;
        }
        return false;
    }
}
