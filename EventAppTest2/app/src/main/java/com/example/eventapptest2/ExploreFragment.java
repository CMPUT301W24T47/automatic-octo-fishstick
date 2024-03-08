package com.example.eventapptest2;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eventapptest2.placeholder.PlaceholderContent;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 */
public class ExploreFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private static ArrayList<Event> events;
    private static ArrayList<Event> sevents;
    private static String id;
    private static FragmentManager frag;
    private static BottomNavigationView bottomnav;

    private static User user;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ExploreFragment(ArrayList<Event> Explore,ArrayList<Event> saveEvents,String did,FragmentManager freg,BottomNavigationView bottomNavigationview, User userr) {
        events = Explore;
        sevents = saveEvents;
        id = did;
        frag = freg;
        bottomnav = bottomNavigationview;
        user = userr;
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ExploreFragment newInstance(int columnCount) {
        ExploreFragment fragment = new ExploreFragment(events,sevents,id,frag,bottomnav,user);
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.explore_events_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }


            //give the list of explore events to the recyler view
            recyclerView.setAdapter(new ExploreEventsRecyclerViewAdapter(events,sevents,id,frag,bottomnav,user));
        }
        return view;
    }
}