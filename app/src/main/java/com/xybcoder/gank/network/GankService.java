package com.xybcoder.gank.network;
import com.xybcoder.gank.GankConfig;
import com.xybcoder.gank.model.GanHuoData;
import com.xybcoder.gank.model.FunnyData;
import com.xybcoder.gank.model.GankData;
import com.xybcoder.gank.model.MeiziData;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * get data
 * Created by xybcoder on 2016/3/1.
 */
public interface GankService {

    // http://gank.io/api/data/数据类型/请求个数/第几页
    @GET(value = "data/福利/" + GankConfig.MEIZI_SIZE + "/{page}")
    Observable<MeiziData> getMeiziData(@Path("page") int page);
    @GET("data/休息视频/" + GankConfig.MEIZI_SIZE + "/{page}")
    Observable<FunnyData> getFunnyData(@Path("page") int page);

    //请求某天干货数据
    @GET("day/{year}/{month}/{day}")
    Observable<GankData> getDailyData(
            @Path("year") int year,
            @Path("month") int month,
            @Path("day") int day);

     //请求不同类型干货（通用）
    @GET("data/{type}/"+GankConfig.GANK_SIZE+"/{page}")
    Observable<GanHuoData> getGanHuoData(@Path("type") String type, @Path("page") int page);
}
