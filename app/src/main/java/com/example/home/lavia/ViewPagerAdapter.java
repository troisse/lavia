package com.example.home.lavia;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private String title[] = {"Products", "Sales"};

    public ViewPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
//                Fragment fragment = new productsTab();
//                Bundle args = new Bundle();
//                args.putInt(fragment.ARG_SECTION_NUMBER, position + 1);
//                fragment.setArguments(args);
//                return fragment;
                Bundle bundle = new Bundle();
                bundle.putInt("pos", position + 1);
                productsTab tabFragment = new productsTab();
                tabFragment.setArguments(bundle);
                return tabFragment;

            case 1:
//                Fragment fragment2 = new DummySectionFragment2();
//                Bundle args = new Bundle();
//                args.putInt(fragment2 .ARG_SECTION_NUMBER, position + 2);
//                fragment2.setArguments(args);
//                return fragment2;
                Bundle bundley = new Bundle();
                bundley.putInt("pos", position + 2);
                salesTab tabFragment2 = new salesTab();
                tabFragment2.setArguments(bundley);
                return tabFragment2;

            default:
                return null;
        }
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
