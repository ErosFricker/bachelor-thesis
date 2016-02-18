package ch.uzh.ifi.seal.bachelorthesis.activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;

import ch.uzh.ifi.seal.bachelorthesis.R;
import ch.uzh.ifi.seal.bachelorthesis.fragments.MenuFragment;

public class MainActivity extends FragmentActivity implements MenuFragment.OnFragmentInteractionListener{

    ViewPager mViewPager;
    MenuCollectionAdapter collectionAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        collectionAdapter = new MenuCollectionAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(collectionAdapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    class MenuCollectionAdapter extends FragmentStatePagerAdapter {

        public MenuCollectionAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            MenuFragment fragment = new MenuFragment();
            Bundle args = new Bundle();
            args.putInt(MenuFragment.MENU_POSITION, position);
            fragment.setArguments(args);
            return fragment;

        }

        @Override
        public int getCount() {
            return MenuFragment.NUMBER_OF_MENUES;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return MenuFragment.MenueTitles[position];
        }
    }

}
