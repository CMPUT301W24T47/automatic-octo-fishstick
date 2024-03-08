package com.example.eventapptest2;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class SavedEventsRecyclerViewAdapterTest {

    private List<Event> events;

    @Before
    public void createTestEvents() {
        // create mock events
        events = new ArrayList<>();
        events.add(new Event("Event 1", "Location 1", "2024-03-08", "https://example.com/image1.jpg"));
        events.add(new Event("Event 2", "Location 2", "2024-03-09", "https://example.com/image2.jpg"));
    }

    @Test
    public void testAdapterInitialization() {
        // create a mock ViewGroup
        ViewGroup parent = Mockito.mock(ViewGroup.class);

        // create adapter instance
        SavedEventsRecyclerViewAdapter adapter = new SavedEventsRecyclerViewAdapter(events);

        // check image exists
        assertThat(adapter, is(notNullValue()));

        // check adapter count is correct
        assertThat(adapter.getItemCount(), equalTo(events.size()));

        // check method doesn't return null
        RecyclerView.ViewHolder viewHolder = adapter.onCreateViewHolder(parent, 0);
        assertThat(viewHolder, is(notNullValue()));
    }

    @Test
    public void testViewBinding() {
        // make activity scenario
        ActivityScenario<MainActivity> activityScenario = ActivityScenario.launch(MainActivity.class);

        //create adapter instance
        SavedEventsRecyclerViewAdapter adapter = new SavedEventsRecyclerViewAdapter(events);

        // create ViewGroup
        ViewGroup parent = Mockito.mock(ViewGroup.class);

        // create ViewHolder bind data
        RecyclerView.ViewHolder viewHolder = adapter.onCreateViewHolder(parent, 0);
        adapter.onBindViewHolder(viewHolder, 0);

        // check viewholder bound to data correctly
        assertThat(viewHolder, instanceOf(SavedEventsRecyclerViewAdapter.ViewHolder.class));
        SavedEventsRecyclerViewAdapter.ViewHolder savedViewHolder = (SavedEventsRecyclerViewAdapter.ViewHolder) viewHolder;
        assertThat(savedViewHolder.ExploreEventName.getText().toString(), equalTo("Event 1"));
        assertThat(savedViewHolder.Eventlocation.getText().toString(), equalTo("Location 1"));
        assertThat(savedViewHolder.Eventdate.getText().toString(), equalTo("2024-03-08"));
    }
}
