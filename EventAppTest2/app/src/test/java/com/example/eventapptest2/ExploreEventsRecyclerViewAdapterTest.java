import com.example.eventapptest2.ExploreEventsRecyclerViewAdapter;
import com.example.eventapptest2.placeholder.PlaceholderContent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
public class ExploreEventsRecyclerViewAdapterTest {

    private ExploreEventsRecyclerViewAdapter adapter;

    @Before
    public void createTestList() {
        // initialize list of placeholders
        List<PlaceholderContent.PlaceholderItem> items = new ArrayList<>();
        items.add(new PlaceholderContent.PlaceholderItem("1", "Event 1"));
        items.add(new PlaceholderContent.PlaceholderItem("2", "Event 2"));

        // test adapter initalized
        adapter = new ExploreEventsRecyclerViewAdapter(items);
    }

    @Test
    public void testAdapterInitialization() {
        // check adapter isn't null
        assertNotNull(adapter);
    }

    @Test
    public void testGetItemCount() {
        // checks correct number of items
        assertEquals(2, adapter.getItemCount());
    }

    @Test
    public void testViewHolder() {
        // makes a ViewHolder
        ExploreEventsRecyclerViewAdapter.ViewHolder viewHolder = adapter.onCreateViewHolder(null, 0);

        // bind data to ViewHolder
        adapter.onBindViewHolder(viewHolder, 0);

        // check that viewholder is bound to data
        assertEquals("1", viewHolder.mIdView.getText().toString());
        assertEquals("Event 1", viewHolder.mContentView.getText().toString());
    }
}
