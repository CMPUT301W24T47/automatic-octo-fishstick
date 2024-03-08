import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.eventapptest2.Event;
import com.example.eventapptest2.ExploreFragment;
import com.example.eventapptest2.ExploreEventsRecyclerViewAdapter;
import com.example.eventapptest2.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

@RunWith(AndroidJUnit4.class)
public class ExploreFragmentTest {

    private ExploreFragment fragment;

    @Mock
    private ArrayList<Event> testEventList;

    @Mock
    private ArrayList<Event> testSavedEventList;

    @Mock
    private FragmentManager testFragmentManager;

    @Mock
    private BottomNavigationView testBottomNavigationView;

    @Before
    public void setUp() {
        testEventList = new ArrayList<>();
        testSavedEventList = new ArrayList<>();
        testFragmentManager = mock(FragmentManager.class);
        testBottomNavigationView = mock(BottomNavigationView.class);
        fragment = new ExploreFragment(testEventList, testSavedEventList, "", testFragmentManager, testBottomNavigationView);
    }

    // checks fragment isn't nul
    @Test
    public void testFragmentNotNull() {
        assertNotNull(fragment);
    }

    // ensures view is inflated, and isn't null
    @Test
    public void testViewInflation() {
        // test LayoutInflater and ViewGroup
        LayoutInflater inflater = LayoutInflater.from(ApplicationProvider.getApplicationContext());
        ViewGroup container = null;
        assertNotNull(fragment.onCreateView(inflater, container, null));
    }

    // ensures layout manager is setup based on count 
    @Test
    public void testRecyclerViewSetup() {
        RecyclerView recyclerView = new RecyclerView(ApplicationProvider.getApplicationContext());
        assertNotNull(recyclerView);
        fragment.onAttach(ApplicationProvider.getApplicationContext());

        // setup RecyclerView with a layout manager
        fragment.setupRecyclerView(recyclerView);
        assertNotNull(recyclerView.getLayoutManager());

        // chekcs that LinearLayoutManager is set by default
        assertEquals(LinearLayoutManager.class, recyclerView.getLayoutManager().getClass());
        fragment = ExploreFragment.newInstance(2);
        fragment.onAttach(ApplicationProvider.getApplicationContext());
        fragment.setupRecyclerView(recyclerView);
        assertEquals(GridLayoutManager.class, recyclerView.getLayoutManager().getClass());
    }

    @Test
    public void testAdapterSetup() {
        // imitate RecyclerView and adapter
        RecyclerView recyclerView = new RecyclerView(ApplicationProvider.getApplicationContext());
        ExploreEventsRecyclerViewAdapter adapter = new ExploreEventsRecyclerViewAdapter(testEventList, testSavedEventList, "", testFragmentManager, testBottomNavigationView);
        assertNotNull(recyclerView);
        assertNotNull(adapter);

        // setup RecyclerView with  adapter
        fragment.onAttach(ApplicationProvider.getApplicationContext());
        fragment.setupRecyclerView(recyclerView);
        recyclerView.setAdapter(adapter);
        assertEquals(adapter, recyclerView.getAdapter());
    }
}
