import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.eventapptest2.OrgainzersEventFragment;
import com.example.eventapptest2.OpenEventsRecyclerViewAdapter;
import com.example.eventapptest2.R;
import com.example.eventapptest2.placeholder.PlaceholderContent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

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
