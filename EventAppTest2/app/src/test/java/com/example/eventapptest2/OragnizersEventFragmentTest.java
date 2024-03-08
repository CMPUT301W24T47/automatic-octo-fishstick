package com.example.eventapptest2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.eventapptest2.placeholder.PlaceholderContent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class OrgainzersEventFragmentTest {

    private OrgainzersEventFragment fragment;

    @Before
    public void setUp() {
        // make new instance of fragment
        fragment = OrgainzersEventFragment.newInstance(1);
    }

    @Test
    public void testFragmentNotNull() {
        // check that fragment exists
        assertNotNull(fragment);
    }

    @Test
    public void testRecyclerViewInitialization() {
        LayoutInflater inflater = mock(LayoutInflater.class);
        ViewGroup container = mock(ViewGroup.class);
        Bundle savedInstanceState = mock(Bundle.class);
        View view = fragment.onCreateView(inflater, container, savedInstanceState);

        // check that view isn't null
        assertNotNull(view);

        // Verify that the RecyclerView is initialized with the correct layout manager
        // check that recycler view is managed correctly upon initialization
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        assertNotNull(recyclerView);
        if (fragment.mColumnCount <= 1) {
            assertEquals(LinearLayoutManager.class, recyclerView.getLayoutManager().getClass());
        } else {
            assertEquals(GridLayoutManager.class, recyclerView.getLayoutManager().getClass());
        }

        // Verify that the RecyclerView is initialized with the correct adapter
        assertNotNull(recyclerView.getAdapter());
        assertEquals(OpenEventsRecyclerViewAdapter.class, recyclerView.getAdapter().getClass());
        assertEquals(PlaceholderContent.ITEMS.size(), recyclerView.getAdapter().getItemCount());
    }
}
