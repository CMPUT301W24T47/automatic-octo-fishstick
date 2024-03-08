import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.eventapptest2.Event;
import com.example.eventapptest2.ExploreEventDetsFragment;
import com.example.eventapptest2.R;
import com.squareup.picasso.Picasso;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class ExploreEventDetsFragmentTest {

    // create a mock event 
    @Mock
    private Event testEvent;

    private ExploreEventDetsFragment fragment;
    
    // create view for use in following tests,
    // ensures it's not null
    private View mockView() {
        LayoutInflater inflater = LayoutInflater.from(ApplicationProvider.getApplicationContext());
        ViewGroup container = null;
        Bundle savedInstanceState = null;

        View view = fragment.onCreateView(inflater, container, savedInstanceState);
        assertNotNull(view);

        return view;
    }

    // initialize the mock event with some info
    @Before
    public void createTestEvent() {
        testEvent = new Event("Event Name", "Event Location", "2024-03-08", "Event Description", null);
        fragment = new ExploreEventDetsFragment(testEvent);
    }

    // ensure fragment isn't null
    @Test
    public void testFragmentNotNull() {
        assertNotNull(fragment);
    }

    // ensure view inflates and that it isn't null
    @Test
    public void testViewInflation() {
        LayoutInflater inflater = LayoutInflater.from(ApplicationProvider.getApplicationContext());
        ViewGroup container = null;
        Bundle savedInstanceState = null;

        View view = fragment.onCreateView(inflater, container, savedInstanceState);

        assertNotNull(view);
    }

    // ensure data is actually bound to the textview
    @Test
    public void testEventDataBinding() {
        View view = mockView();
        TextView nameTextView = view.findViewById(R.id.EventDetailNameSignup);
        TextView dateTextView = view.findViewById(R.id.CalenderDateUp);
        TextView locationTextView = view.findViewById(R.id.LocationNameUp);
        TextView descriptionTextView = view.findViewById(R.id.EventDescriptionUp);

        assertEquals("Event Name", nameTextView.getText().toString());
        assertEquals("2024-03-08", dateTextView.getText().toString());
        assertEquals("Event Location", locationTextView.getText().toString());
        assertEquals("Event Description", descriptionTextView.getText().toString());
    }

    // ensure Picasso loads event poster into ImageView
    @Test
    public void testImageLoading() {
        View view = mockView();
        ImageView imageView = view.findViewById(R.id.EventImageSignup);

        verify(Picasso.get()).load(testEvent.getEventPoster()).into(imageView);
    }
}
