package andreyskakunenko.nytimesnewsreader.Adapters;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import andreyskakunenko.nytimesnewsreader.Tabs.TabEmailed;
import andreyskakunenko.nytimesnewsreader.Tabs.TabFavorites;
import andreyskakunenko.nytimesnewsreader.Tabs.TabShared;
import andreyskakunenko.nytimesnewsreader.Tabs.TabViewed;

public class ViewPagerTabsAdapter extends FragmentStatePagerAdapter {

    private int mNoOfTabs;

    public ViewPagerTabsAdapter(FragmentManager fm, int NumberOfTabs) {
        super(fm);
        this.mNoOfTabs = NumberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){

            case 0:
                return new TabEmailed();
            case 1:
                return new TabShared();
            case 2:
                return new TabViewed();
            case 3:
                return new TabFavorites();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNoOfTabs;
    }

    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
