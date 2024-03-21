package com.example.eventapptest2;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QRCameraScannerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QRCameraScannerFragment extends Fragment {
    SurfaceView surfaceView;
    TextView QRcodevalue;
    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    private static final int REQUEST_CAMERA_PERMISSION = 201;
    Button open_camera_button;
    String intentData;
    MainActivity mActivityCallback;
    Button generate_qr_button;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public QRCameraScannerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QRScannerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QRCameraScannerFragment newInstance(String param1, String param2) {
        QRCameraScannerFragment fragment = new QRCameraScannerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    /**
     *
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_q_r_camera_scanner, container, false);
        open_camera_button = (Button) rootView.findViewById(R.id.open_camera_button);
        surfaceView = (SurfaceView) rootView.findViewById(R.id.surfaceView);
        mActivityCallback = (MainActivity)getActivity();
        generate_qr_button = (Button) rootView.findViewById(R.id.generate_qr_button);
        generate_qr_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraSource.release();
                mActivityCallback.switchToGenerateQRFragment();
            }
        });
        initialiseDetectorsandSources();
        // Inflate the layout for this fragment
        return rootView;
    }

    /**
     * Builds camera surface to implant onto surface view.
     * initializes qr code detector, which will invoke run() immediately upon detection
     *
     */
    private void initialiseDetectorsandSources(){
        Toast.makeText(getActivity().getApplicationContext(), "Please scan your QR code", Toast.LENGTH_SHORT).show();
        barcodeDetector = new BarcodeDetector.Builder(getActivity().getApplicationContext())
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();
        cameraSource = new CameraSource.Builder(getActivity().getApplicationContext(), barcodeDetector)
                .setRequestedPreviewSize(1920,1080)
                .setAutoFocusEnabled(true)
                .build();


        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceCreated(@NonNull SurfaceHolder holder) {

                try{
                    if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                        cameraSource.start(surfaceView.getHolder());

                    }
                    else{
                        ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);

                    }
                } catch (IOException e) {
                    
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

            }

            /**
             * Stops camera if the surfaceview is removed, for example when the fragment is changed.
             * @param holder The SurfaceHolder whose surface is being destroyed.
             */
            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
                cameraSource.stop();
            }
        });
        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            /**
             * When camera is stopped, display a toast.
             */
            @Override
            public void release() {
                Toast.makeText(getActivity().getApplicationContext(), "QR scanner stopped", Toast.LENGTH_SHORT).show();
            }

            /**
             * listener for QR code, run() is invoked immediately upon scanning.
             * @param detections
             */
            @Override
            public void receiveDetections(@NonNull Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() != 0){
                    open_camera_button.post(new Runnable() {
                        @Override
                        public void run() {
                            open_camera_button.setText("Go to event");
                            intentData = barcodes.valueAt(0).displayValue;
                            generate_qr_button.setText(intentData);
                            open_camera_button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mActivityCallback.switchToGenerateQRFragment();
                                }
                            });
                        }
                    });
                }
            }
        });
    }

    /**
     * When the frame is interrupted, camera is paused
     */
    @Override
    public void onPause(){
        super.onPause();
        cameraSource.release();
    }

    /**
     * invoked upon camera restart
     */
    @Override
    public void onResume(){
        super.onResume();
        initialiseDetectorsandSources();
    }
}