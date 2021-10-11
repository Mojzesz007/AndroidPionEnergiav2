package com.platform.Helper;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.platform.BuildConfig;
import com.platform.DrawerActivity;
import com.platform.Fragments.FragmentContent;
import com.platform.Interface.NavigationManager;
import com.platform.R;

public class FragmentNavigatorManager implements NavigationManager {
    private static FragmentNavigatorManager mInstance;
    private FragmentManager mFragmentManager;
    private DrawerActivity drawerActivity;

    public static FragmentNavigatorManager getmInstance(DrawerActivity drawerActivity) {
        if (mInstance == null)
            mInstance = new FragmentNavigatorManager();
        mInstance.configure(drawerActivity);
        return mInstance;
    }

    private void configure(DrawerActivity drawerActivity) {
        drawerActivity = drawerActivity;
        mFragmentManager = drawerActivity.getSupportFragmentManager();
    }

    @Override
    public void showFragment(String title) {

        showFragment(FragmentContent.newInstance(title), false);

    }


    public void showFragment(Fragment fragmentContent, boolean b) {
        FragmentManager fm = mFragmentManager;
        FragmentTransaction ft = fm.beginTransaction().replace(R.id.container, fragmentContent);
        ft.addToBackStack(null);
        if (b || !BuildConfig.DEBUG)
            ft.commitAllowingStateLoss();
        else
            ft.commit();
        fm.executePendingTransactions();
    }
}