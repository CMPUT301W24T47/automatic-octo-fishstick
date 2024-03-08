package com.example.eventapptest2;

import static org.junit.Assert.assertNotNull;

import android.view.LayoutInflater;
import android.view.View;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

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
