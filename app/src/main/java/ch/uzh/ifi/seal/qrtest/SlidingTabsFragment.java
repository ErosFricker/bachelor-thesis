package ch.uzh.ifi.seal.qrtest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;

import ch.uzh.ifi.seal.qrtest.common.logger.Log;
import ch.uzh.ifi.seal.qrtest.common.view.SlidingTabLayout;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SlidingTabsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SlidingTabsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SlidingTabsFragment extends Fragment {

    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SlidingTabsFragment.
     */
    public static SlidingTabsFragment newInstance(String param1, String param2) {
        SlidingTabsFragment fragment = new SlidingTabsFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sliding_tabs, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        viewPager = (ViewPager)view.findViewById(R.id.viewpager);
        viewPager.setAdapter(new MenuPagerAdapter());
        slidingTabLayout = (SlidingTabLayout)view.findViewById(R.id.sliding_tabs);
        slidingTabLayout.setViewPager(viewPager);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }
    private void startScanning() {

        Intent intent = new Intent(getActivity(), ScanningActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }

    class MenuPagerAdapter extends PagerAdapter {
        private static final int NUMBER_OF_MENUES = 3;
        private final String[] MenueTitles = {"My Issues", "Scan Developers", "Settings"};

        @Override
        public int getCount() {
            return NUMBER_OF_MENUES;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return object == view;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return MenueTitles[position];
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View view = getActivity().getLayoutInflater().inflate(R.layout.pager_item, container, false);
            container.addView(view);
            TextView title = (TextView)view.findViewById(R.id.menu_title);
            title.setText(MenueTitles[position]);
            ImageButton button = (ImageButton)view.findViewById(R.id.imagebutton);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch(position){
                        case 0:
                            Log.e("ERROR", "Not implemented tab "+position);
                            break;
                        case 1:
                            startScanning();
                            break;

                        case 2:
                            Log.e("ERROR", "Not implemented tab "+position);
                            break;

                        default:
                            Log.e("ERROR", "Not implemented tab "+position);
                            break;
                    }
                    Log.d("IMAGEBUTTON", "Selected button");
                }
            });
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }
    }
}
