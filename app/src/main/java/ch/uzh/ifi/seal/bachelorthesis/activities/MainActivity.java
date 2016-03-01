package ch.uzh.ifi.seal.bachelorthesis.activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;

import com.reconinstruments.ui.carousel.CarouselActivity;

import ch.uzh.ifi.seal.bachelorthesis.R;
import ch.uzh.ifi.seal.bachelorthesis.fragments.MenuFragment;

public class MainActivity extends CarouselActivity implements MenuFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.carousel_host);
        getCarousel().setPageMargin(30);
        getCarousel().setContents(new ImageMenuItem("My Issues", R.mipmap.bug, 0),
                new ImageMenuItem("Scan Developers", R.mipmap.scan, 1),
                new ImageMenuItem("Settings", R.mipmap.settings, 2));
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}
