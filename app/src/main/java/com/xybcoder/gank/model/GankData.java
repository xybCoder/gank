package com.xybcoder.gank.model;
import com.google.gson.annotations.SerializedName;
import com.xybcoder.gank.model.entity.Gank;


import java.util.List;

/**
 * All Data数据模型（所有干货List）
 * Created by xybcoder on 2016/3/1
 *
 * 数据格式见：http://gank.io/api/day/2015/08/07
 *
 */
public class GankData extends BaseData{
    public List<String> category;
    public Result results;
    public class Result {
        @SerializedName("Android") public List<Gank> androidList;
        @SerializedName("休息视频") public List<Gank> 休息视频List;
        @SerializedName("iOS") public List<Gank> iOSList;
        @SerializedName("福利") public List<Gank> 妹纸List;
        @SerializedName("拓展资源") public List<Gank> 拓展资源List;
        @SerializedName("瞎推荐") public List<Gank> 瞎推荐List;
        @SerializedName("App") public List<Gank> appList;
        @SerializedName("前端") public List<Gank> 前端List;
    }

    @Override
    public String toString() {
        return "GankData{" +
                "category=" + category +
                ", results=" + results +
                '}';
    }
}
