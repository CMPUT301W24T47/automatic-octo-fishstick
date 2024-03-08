package com.example.eventapptest2;

import android.Manifest;
import android.content.pm.PackageManager;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class QRCameraScannerFragmentTest {

    // Mock dependencies
    @Mock
    SurfaceView mockSurfaceView;
    @Mock
    SurfaceHolder mockSurfaceHolder;
    @Mock
    CameraSource mockCameraSource;
    @Mock
    BarcodeDetector mockBarcodeDetector;

    QRCameraScannerFragment fragment;

    @Before
    public void setUp() {
        // create mock objects
        MockitoAnnotations.initMocks(this);

        // creates a instance of fragment
        fragment = new QRCameraScannerFragment();

        // inflates fragment
        FragmentScenario.launchInContainer(QRCameraScannerFragment.class);
    }

    @Test
    public void testSurfaceCreated() throws IOException {
        // test behaviour for view and holder
        when(mockSurfaceView.getHolder()).thenReturn(mockSurfaceHolder);
        when(mockSurfaceHolder.getSurface()).thenReturn(mockSurfaceHolder.getSurface());

        // test behaviour for camera's perms
        when(ActivityCompat.checkSelfPermission(fragment.requireContext(), Manifest.permission.CAMERA)).thenReturn(PackageManager.PERMISSION_GRANTED);

        // place objects in fragment
        fragment.surfaceView = mockSurfaceView;
        fragment.cameraSource = mockCameraSource;

        // Call surfaceCreated method with the mocked SurfaceHolder
        fragment.surfaceView.getHolder().getCallback().surfaceCreated(mockSurfaceHolder);

        // check that camera is started with surface holder
        verify(mockCameraSource).start(mockSurfaceHolder);
    }
}
