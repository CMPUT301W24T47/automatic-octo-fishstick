import android.media.Image;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class EventTest {

    private Event event;

    @Before
    public void createTestEvent() {
        // Create an event object with sample data for testing
        testEvent = new Event("Test Event", "Test Location", "2024-03-08", 10, null);
    }

    @Test
    public void testEventInitialization() {
        // check event exists
        assertNotNull(event);

        // check event name
        assertEquals("Test Event", event.getEventName());

        // check event location
        assertEquals("Test Location", event.getEventLocation());

        // check event date
        assertEquals("2024-03-08", event.getEventDate());

        // check event limit
        assertEquals(10, event.getEventLimit());

        // later will check poster, replace null with actual image
        assertEquals(null, event.getEventPoster(null));
    }

    @Test
    public void testSettersGetters() {
        // check setters can change event info
        event.setEventName("Updated Event Name");
        event.setEventLocation("Updated Location");
        event.setEventDate("2024-03-09");
        event.setEventLimit(50);
        Image poster = null; // Replace null with actual image object when available
        event.setEventPoster(poster);

        // check getters can retrieve event info
        assertEquals("Updated Event Name", event.getEventName());
        assertEquals("Updated Location", event.getEventLocation());
        assertEquals("2024-03-09", event.getEventDate());
        assertEquals(50, event.getEventLimit());
        assertEquals(null, event.getEventPoster(null));
    }
}
