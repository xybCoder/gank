package com.xybcoder.gank.model.entity;

import java.util.Date;

/**
 * 妹子model
 * 数据格式见：http://gank.io/api/data/all/20/2
 * Created by xybcoder on 2016/3/1.
 */
public class Meizi extends Base {
    public boolean used;
    public String type;//干货类型，如Android，iOS，福利等
    public String url;//链接地址
    public String who;//作者
    public String desc;//干货内容的描述
    public Date createdAt;
    public Date updatedAt;
    public Date publishedAt;

    @Override
    public String toString() {
        return "Meizi{" +
                "who='" + who + '\'' +
                ", url='" + url + '\'' +
                ", type='" + type + '\'' +
                ", desc='" + desc + '\'' +
                ", used=" + used +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", publishedAt=" + publishedAt +
                '}';
    }
}
