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
 * A simple {@link Fragment} subclass.
 * Use the {@link OrgainzersEventFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrgainzersEventFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_COLUMN_COUNT = "column-count";
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
    private  int mColumnCount = 1;
//    private String mParam1;
//    private String mParam2;
    private static ArrayList<Event> events;
    private static FragmentManager frag;
    private static BottomNavigationView bottomnav;
    private static User inte;

    public OrgainzersEventFragment(ArrayList<Event> createevents, FragmentManager freg, BottomNavigationView bottomNavigationview,User inti) {
        // Required empty public constructor
        this.events = createevents;
        frag = freg;
        bottomnav = bottomNavigationview;
        inte = inti;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment OrgainzersEventFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrgainzersEventFragment newInstance(int columnCount) {
        OrgainzersEventFragment fragment = new OrgainzersEventFragment(events,frag,bottomnav,inte);
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }
//
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
            //mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.open_events_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new OpenEventsRecyclerViewAdapter(events,frag,bottomnav,inte));
        }
        return view;
    }

    }
