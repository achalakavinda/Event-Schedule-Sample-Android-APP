package Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.opensource.schedular.LoginFragment;
import com.opensource.schedular.RegisterFragment;


public class UserLoginPageAdapter extends FragmentPagerAdapter {

    int Number_Of_tabs;

    public UserLoginPageAdapter(FragmentManager fm, int numOfTabs){
        super(fm);
        this.Number_Of_tabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                LoginFragment tab1 = new LoginFragment();
                return tab1;
            case 1:
                RegisterFragment tab2 = new RegisterFragment();
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return Number_Of_tabs;
    }
}
