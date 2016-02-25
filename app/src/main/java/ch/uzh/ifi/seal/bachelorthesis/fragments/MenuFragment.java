package ch.uzh.ifi.seal.bachelorthesis.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import ch.uzh.ifi.seal.bachelorthesis.R;
import ch.uzh.ifi.seal.bachelorthesis.activities.MyIssuesActivity;
import ch.uzh.ifi.seal.bachelorthesis.activities.ScanningActivity;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MenuFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MenuFragment extends Fragment {
    public static final String MENU_POSITION = "Menu_Position";
    private Integer menuPosition;

    public static final int NUMBER_OF_MENUES = 3;
    public static final String[] MenueTitles = {"My Issues", "Scan Developers", "Settings"};


    private OnFragmentInteractionListener mListener;

    public MenuFragment() {
        // Required empty public constructor
    }

    public static MenuFragment newInstance(Integer menuPosition) {
        MenuFragment fragment = new MenuFragment();
        Bundle args = new Bundle();
        args.putInt(MENU_POSITION, menuPosition);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            menuPosition = getArguments().getInt(MENU_POSITION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.pager_item, container, false);
        ImageButton button = (ImageButton)view.findViewById(R.id.imagebutton);
        button.requestFocus();
        switch (menuPosition){
            case 0:
                button.setImageDrawable(getResources().getDrawable(R.drawable.my_issues_button_selector));
                break;
            case 1:
                button.setImageDrawable(getResources().getDrawable(R.drawable.scan_dev_button_selector));
                break;
            case 2:
                button.setImageDrawable(getResources().getDrawable(R.drawable.settings_button_selector));
                break;
            default:
                break;
        }


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (menuPosition) {
                    case 0:
                        showMyIssues();
                        Log.e("ERROR", "Not implemented tab " + menuPosition);
                        break;
                    case 1:
                        startScanning();
                        break;

                    case 2:
                        Log.e("ERROR", "Not implemented tab " + menuPosition);
                        break;

                    default:
                        Log.e("ERROR", "Not implemented tab " + menuPosition);
                        break;
                }
                Log.d("IMAGEBUTTON", "Selected button");
            }
        });
        return view;
    }

    /**
     * Starts the activity for showing the user's own issues
     */
    private void showMyIssues() {
        Intent intent = new Intent(getActivity(), MyIssuesActivity.class);
        startActivity(intent);

    }

    /**
     * Starts the QR Scanning process
     */
    private void startScanning() {

        Intent intent = new Intent(getActivity(), ScanningActivity.class);
        startActivity(intent);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
}
