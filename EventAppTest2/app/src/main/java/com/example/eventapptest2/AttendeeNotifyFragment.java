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
 * A fragment representing a list of notifications to notify attendees in the event.
 * Fragment displays notifications to attendees using a RecyclerView
 */
public class AttendeeNotifyFragment extends Fragment {
    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private static ArrayList<String> AttendeeList; // List of attendees
    private static String EventImage;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public AttendeeNotifyFragment(ArrayList<String> Attendees,String ei) {
        AttendeeList = Attendees;
        EventImage = ei;

    }

    /**
     * Creates new instance of AttendeeNotifyFragment.
     *
     * @param columnCount Number of columns in the RecyclerView grid.
     * @return A new instance of AttendeeNotifyFragment.
     */
    public static AttendeeNotifyFragment newInstance(int columnCount) {
        AttendeeNotifyFragment fragment = new AttendeeNotifyFragment(AttendeeList,EventImage);
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Called when fragment is created.
     *
     * @param savedInstanceState If the fragment is being re-created from
     * a previous saved state, this is the state.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    /**
     * Creates and returns the view structure of the AttendeeNotifyFragment.
     *
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return The view of the fragment layout.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.attendee_notifications, container, false);
        // Set the adapter for the RecyclerView
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new AttendeeNotifyRecyclerViewAdapter(AttendeeList,EventImage));
        }
        return view;
    }
}