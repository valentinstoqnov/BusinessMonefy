package org.elsys.valiolucho.businessmonefy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by valen on 18.4.2016 г..
 */
public class ChartsSwipeAdapter extends FragmentStatePagerAdapter {

    public ChartsSwipeAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fr = new ChartsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("count", position + 1);
        fr.setArguments(bundle);

        return fr;
    }

    @Override
    public int getCount() {
        return 5;
    }
}
