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
 * A fragment representing a list of current events for the Admin view.
 * This fragment displays all the current events in a RecyclerView.
 */
public class AdminEventsFragment extends Fragment {
    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1; // Column count for the recycler view

    private static ArrayList<Event> allevents; // List of all the current events

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     *
     * @param alleventsinput List of current events to display
     */
    public AdminEventsFragment(ArrayList<Event> alleventsinput) {
        allevents = alleventsinput;

    }

    /**
     * Create a new instance of AdminEventsFragment with the specified column count.
     *
     * @param columnCount Number of columnts in the RecyclerView layout
     * @return New instance of fragment
     */
    public static AdminEventsFragment newInstance(int columnCount) {
        AdminEventsFragment fragment = new AdminEventsFragment(allevents);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_events, container, false);

        // Set up the RecyclerView
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            // Set adapter with the list of the current events
            recyclerView.setAdapter(new AdminEventsRecyclerViewAdapter(allevents));

        }
        return view;
    }
}