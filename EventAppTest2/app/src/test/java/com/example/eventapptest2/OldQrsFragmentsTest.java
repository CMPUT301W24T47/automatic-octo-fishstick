import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.eventapptest2.OldQrsFragments;
import com.example.eventapptest2.R;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class OldQrsFragmentsTest {

    @Test
    public void testFragmentInflation() {
        // initialize fragment instance
        OldQrsFragments fragment = new OldQrsFragments();

        // layout get's inflated
        LayoutInflater inflater = LayoutInflater.from(ApplicationProvider.getApplicationContext());
        View view = fragment.onCreateView(inflater, null, null);

        // check that layout gets inflated
        assertNotNull(view);

        // check that layout is as it should be
        assertEquals(R.layout.fragment_old_qrs, fragment.getLayoutId());
    }
}
