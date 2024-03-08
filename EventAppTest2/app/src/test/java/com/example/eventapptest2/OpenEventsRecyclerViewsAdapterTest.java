package com.example.eventapptest2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.example.eventapptest2.placeholder.PlaceholderContent;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class OpenEventsRecyclerViewAdapterTest {

    private OpenEventsRecyclerViewAdapter adapter;

    @Before
    public void setUp() {
        // initialize list of placeholder items
        List<PlaceholderContent.PlaceholderItem> items = new ArrayList<>();
        items.add(new PlaceholderContent.PlaceholderItem("1", "Event 1"));
        items.add(new PlaceholderContent.PlaceholderItem("2", "Event 2"));
        items.add(new PlaceholderContent.PlaceholderItem("3", "Event 3"));

        // create an adapter with the test data
        adapter = new OpenEventsRecyclerViewAdapter(items);
    }

    @Test
    public void testAdapterInitialization() {
        // checks adapter isn't null
        assertNotNull(adapter);
    }

    @Test
    public void testItemCount() {
        // checks correct item count
        assertEquals(3, adapter.getItemCount());
    }

    @Test
    public void testViewHolderBinding() {
        // create a ViewHolder
        OpenEventsRecyclerViewAdapter.ViewHolder viewHolder = adapter.onCreateViewHolder(null, 0);

        // data bound to viewHolder
        adapter.onBindViewHolder(viewHolder, 0);

        // checks that data is bound to viewholder
        assertEquals("1", viewHolder.mIdView.getText().toString());
        assertEquals("Event 1", viewHolder.mContentView.getText().toString());
    }
}
