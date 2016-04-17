package com.xybcoder.gank.model;
import com.xybcoder.gank.model.entity.Gank;

import java.util.List;

/**
 * 通用(Android ,ios,前端，拓展资源，休息视频)数据模型
 * Created by xybcoder on 16/3/1.
 */
public class GanHuoData extends BaseData {
    public List<Gank> results;

    @Override
    public String toString() {
        return "GanHuoData{" +
                "results=" + results +
                '}';
    }
}
