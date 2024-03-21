package com.example.eventapptest2;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 */
public class AttendeeNotifyFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private static ArrayList<String> AttendeeList;
    private static String EventImage;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public AttendeeNotifyFragment(ArrayList<String> Attendees,String ei) {
        AttendeeList = Attendees;
        EventImage = ei;

    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static AttendeeNotifyFragment newInstance(int columnCount) {
        AttendeeNotifyFragment fragment = new AttendeeNotifyFragment(AttendeeList,EventImage);
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
        View view = inflater.inflate(R.layout.attendee_notifications, container, false);
        //RecyclerView recyclerView =  view.findViewById(R.id.NotificationRecyclerView);
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
            recyclerView.setAdapter(new AttendeeNotifyRecyclerViewAdapter(AttendeeList,EventImage));

        }
        return view;
    }
}