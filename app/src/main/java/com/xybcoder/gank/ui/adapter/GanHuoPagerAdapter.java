package com.xybcoder.gank.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.xybcoder.gank.ui.fragment.GanHuoFragment;

/**
 * Created by xybcoder on 16/3/1.
 * viewpager 适配器
 */
public class GanHuoPagerAdapter extends FragmentStatePagerAdapter {

    String[] title = {"Android","iOS","前端","瞎推荐","拓展资源","App"};

    public GanHuoPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return GanHuoFragment.newInstance(title[position]);
    }

    @Override
    public int getCount() {
        return title.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }

}
