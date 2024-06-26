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
 * A fragment representing a list of all the images within the app for the Admin View.
 * Fragment displays list of images associated with events and user profiles in a RecyclerView.
 */
public class AdminImageFragment extends Fragment {
    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private static ArrayList<Event> allevents;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public AdminImageFragment(ArrayList<Event> alleventsinput) {
        allevents = alleventsinput;

    }

    public static AdminImageFragment newInstance(int columnCount) {
        AdminImageFragment fragment = new AdminImageFragment(allevents);
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
        View view = inflater.inflate(R.layout.admin_images, container, false);
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            // Set up adapter for RecyclerView
            recyclerView.setAdapter(new AdminImageRecyclerViewAdapter(allevents));
        }
        return view;
    }
}