package com.example.eventapptest2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class QRImageReaderFragmentTest {

    QRImageReaderFragment fragment;

    @Before
    public void createTestFragment() {
        // launch fragment
        FragmentScenario<QRImageReaderFragment> scenario = FragmentScenario.launchInContainer(QRImageReaderFragment.class);
        scenario.onFragment(fragment{
            this.fragment = fragment;
        });
    }

    @Test
    public void testFragmentNotNull() {
        // check fragment isn't null
        assertNotNull(fragment);
    }

    @Test
    public void testCorrectLayoutInflation() {
        View view = fragment.getView();

        // check view isn't null
        assertNotNull(view);

        // check correct layout is inflated
        assertEquals(R.layout.fragment_q_r_image_reader, view.getId());
    }
}
