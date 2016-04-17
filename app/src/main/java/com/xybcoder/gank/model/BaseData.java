package com.xybcoder.gank.model;

import java.io.Serializable;

/**
 * Created by xybcoder on 2016/3/1.
 */
public class BaseData implements Serializable{
    public boolean error;

    @Override
    public String toString() {
        return "BaseData{" +
                "error=" + error +
                '}';
    }
}
