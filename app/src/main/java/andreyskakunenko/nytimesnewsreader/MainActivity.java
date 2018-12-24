package andreyskakunenko.nytimesnewsreader;

import android.Manifest;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;
import java.util.Objects;

import andreyskakunenko.nytimesnewsreader.Adapters.ViewPagerTabsAdapter;
import andreyskakunenko.nytimesnewsreader.Retrofit.Common;
import andreyskakunenko.nytimesnewsreader.Tabs.TabEmailed;
import andreyskakunenko.nytimesnewsreader.Tabs.TabFavorites;
import andreyskakunenko.nytimesnewsreader.Tabs.TabShared;
import andreyskakunenko.nytimesnewsreader.Tabs.TabViewed;

public class MainActivity extends AppCompatActivity implements
        TabFavorites.OnFragmentInteractionListener,
        TabViewed.OnFragmentInteractionListener,
        TabEmailed.OnFragmentInteractionListener,
        TabShared.OnFragmentInteractionListener {

    TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(this, "network connection is: "+Common.isNetworkAvailable(this), Toast.LENGTH_LONG).show();

        // Request Internet permission;
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.INTERNET, Manifest.permission.ACCESS_NETWORK_STATE,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<com.karumi.dexter.listener.PermissionRequest> permissions, PermissionToken token) {

                    }
                }).check();


        mTabLayout = findViewById(R.id.tab_layout);
        mTabLayout.addTab(mTabLayout.newTab().setText(getString(R.string.tab_most_emailed)));
        mTabLayout.addTab(mTabLayout.newTab().setText(getString(R.string.tab_most_shared)));
        mTabLayout.addTab(mTabLayout.newTab().setText(getString(R.string.tab_most_viewed)));
        mTabLayout.addTab(mTabLayout.newTab().setText(getString(R.string.tab_favorites)));

        final ViewPager mPager = findViewById(R.id.pager);
        final PagerAdapter mAdapter = new ViewPagerTabsAdapter(getSupportFragmentManager(),mTabLayout.getTabCount());
        mPager.setAdapter(mAdapter);
        if(!Common.isNetworkAvailable(this)) {
            mPager.setCurrentItem(3);
        }

        mPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(mPager.getCurrentItem()==3 || !Common.isNetworkAvailable(getApplicationContext())){
                    Objects.requireNonNull(mPager.getAdapter()).notifyDataSetChanged();
                }
                mPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


}
