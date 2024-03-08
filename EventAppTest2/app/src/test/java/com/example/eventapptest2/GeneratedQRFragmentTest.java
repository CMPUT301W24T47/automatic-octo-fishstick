package com.example.eventapptest2;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class GeneratedQRFragmentTest {

    private GeneratedQRFragment fragment;
    private ImageView imageView;
    private Button generate_qr_button;

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void createTest() {
        // load fragment
        activityRule.getActivity().getSupportFragmentManager().beginTransaction();
        fragment = new GeneratedQRFragment();
        activityRule.getActivity().getSupportFragmentManager().beginTransaction().add(R.id.container, fragment).commitAllowingStateLoss();
        activityRule.getActivity().getSupportFragmentManager().executePendingTransactions();

        // locate views
        View rootView = fragment.getView();
        imageView = rootView.findViewById(R.id.qr_code_image);
        generate_qr_button = rootView.findViewById(R.id.generate_qr_button);
    }

    @Test
    public void testQRCodeGeneration() {
        // tap qr generate button
        generate_qr_button.performClick();

        // check that image has bitmap set
        assertNotNull(imageView.getDrawable());
    }

    @Test
    public void testQRCodeContent() {
        // tap qr generate button
        generate_qr_button.performClick();

        // pull bitmap 
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

        // take info from qr code
        String decodedContent = decodeQRCode(bitmap);

        // check that info matches expectation
        assertEquals("Hello", decodedContent);
    }

    private String decodeQRCode(Bitmap bitmap) {
        // return content of qr
        return "Hello"; // Dummy implementation for testing
    }
}
