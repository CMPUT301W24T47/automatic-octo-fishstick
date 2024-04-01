package com.example.eventapptest2;

import static androidx.core.content.ContextCompat.getSystemService;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.location.LocationListenerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class QRCameraScannerFragment extends Fragment implements LocationListener{
    SurfaceView surfaceView;
    LocationManager locationManager;
    Location location;
    TextView QRcodevalue;
    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    double latitude;
    double longitude;
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
    private User user;
    private Event event;
    FragmentManager frag;
    BottomNavigationView bottomnav;
    boolean getdet;
    ArrayList<Event> explore;

    public QRCameraScannerFragment(Event evnte, User param, FragmentManager fragi, BottomNavigationView bottomnavi, boolean getdete) {
        //needs user to add user to list
        user = param;
        event = evnte;
        frag = fragi;
        bottomnav = bottomnavi;
        getdet = getdete;
        // Required empty public constructor
    }

    public QRCameraScannerFragment(Event evnte, User param, FragmentManager fragi, BottomNavigationView bottomnavi, boolean getdete, ArrayList<Event> Explore) {
        //needs user to add user to list
        user = param;
        event = evnte;
        frag = fragi;
        bottomnav = bottomnavi;
        getdet = getdete;
        explore = Explore;
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     *
     * @return A new instance of fragment QRScannerFragment.
     */
    // TODO: Rename and change types and number of parameters
//    public static QRCameraScannerFragment newInstance(String param1, String param2) {
//        QRCameraScannerFragment fragment = new QRCameraScannerFragment(param1,param2);
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            user = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_q_r_camera_scanner, container, false);
        mActivityCallback = (MainActivity) getActivity();
        if (ContextCompat.checkSelfPermission(getContext(),Manifest.permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(),new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            },100);
        }



        initialiseDetectorsandSources();
        // Inflate the layout for this fragment
        return rootView;
    }

    private void initialiseDetectorsandSources() {
        Toast.makeText(getActivity().getApplicationContext(), "Please scan your QR code", Toast.LENGTH_SHORT).show();
        barcodeDetector = new BarcodeDetector.Builder(getActivity().getApplicationContext())
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();
        cameraSource = new CameraSource.Builder(getActivity().getApplicationContext(), barcodeDetector)
                .setRequestedPreviewSize(1920, 1080)
                .setAutoFocusEnabled(true)
                .build();


        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder holder) {

                try {
                    if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(surfaceView.getHolder());

                    } else {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);

                    }
                } catch (IOException e) {

                    throw new RuntimeException(e);
                }
            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
                cameraSource.stop();
            }
        });
        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
                Toast.makeText(getActivity().getApplicationContext(), "QR scanner stopped", Toast.LENGTH_SHORT).show();
            }

            @Override

            public void receiveDetections(@NonNull Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() != 0) {
                    surfaceView.post(new Runnable() {
                        @Override
                        public void run() {

                            if (!getdet) {
                                getLocation();
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                                //get longitude and latitude
                                //mActivityCallback = (MainActivity) getActivity();
                            //this is what happens after qr code scanned so like we can switch fragments here
                            final FirebaseFirestore db = FirebaseFirestore.getInstance();

                            DocumentReference docRef = db.collection("AttendeeList" + event.getEventid()).document(user.getDeviceId());

                            // Get the document snapshot


                            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        if (document.exists()) {
                                            // Perform the count increment and update
                                            // ...

                                            // Cleanup after successful update
                                            String intValueStr = document.getString("CheckInCount");
                                            int intValue = Integer.parseInt(intValueStr);

                                            // Increment the integer value by 1
                                            intValue+= 1;
                                            docRef.update("CheckInCount", String.valueOf(intValue));
                                            docRef.update("userLatitude", String.valueOf(latitude));
                                            docRef.update("userLongitude", String.valueOf(longitude));

                                            //event.getAttendeList().clear();



                                            cameraSource.stop();
                                            cameraSource.release();
                                            barcodeDetector.release();
                                            //event.getAttendeList().notify();

                                            FragmentTransaction fragmentTransaction = frag.beginTransaction();
                                            //System.out.println("testtttttttttt " + testuser.getCreatedEvents());
                                            
                                            //this isnt the actual exploreeventsdets its actualy savedeventdets
                                            fragmentTransaction.replace(R.id.framelayout, new ExploreEventDetsFragment(event,frag,user,bottomnav)); 
                                            fragmentTransaction.commit();


                                            // Switch fragments
                                            // ...
                                        } else {
                                            // Handle the case where the document doesn't exist
                                        }
                                    } else {
                                        // Handle errors
                                    }
                                }

                            });}
                            else{// open

                                ArrayList<Event> saveevent = new ArrayList<>();
                                intentData = barcodes.valueAt(0).displayValue; // this should store event id
                                for(Event event: explore){
                                    if (event.getEventid().equals(intentData)){
                                        saveevent.add(event);
                                    }

                                }
                                if(saveevent.isEmpty()){
                                    cameraSource.stop();
                                    cameraSource.release();
                                    barcodeDetector.release();
                                    FragmentTransaction fragmentTransaction = frag.beginTransaction();
                                    //System.out.println("testtttttttttt " + testuser.getCreatedEvents());

                                    //this isnt the actual exploreeventsdets its actualy savedeventdets
                                    fragmentTransaction.replace(R.id.framelayout, new UserProfileFragment(user,frag,bottomnav,explore));
                                    fragmentTransaction.commit();



                                    Toast.makeText(getActivity().getApplicationContext(), "Event is Full, Expired,you already signed-Up, or You own the event", Toast.LENGTH_LONG).show();

                                    //print message
                                }
                                else{
                                    cameraSource.stop();
                                    cameraSource.release();
                                    barcodeDetector.release();
                                    //event.getAttendeList().notify();





                                    FragmentTransaction fragmentTransaction = frag.beginTransaction();
                                    //System.out.println("testtttttttttt " + testuser.getCreatedEvents());

                                    //this isnt the actual exploreeventsdets its actualy savedeventdets
                                    fragmentTransaction.replace(R.id.framelayout, new ACTUALExploreEventDetsFragment(saveevent.get(0),frag,user.getDeviceId(),user,bottomnav,explore));
                                    fragmentTransaction.commit();


                                }


                            }



//                            cameraSource.stop();
//                            cameraSource.release();





                        }
                    });
                }
            }
        });
    }

    @Override
    public void onPause(){
        super.onPause();
        cameraSource.release();
    }
    @Override
    public void onResume(){
        super.onResume();
        initialiseDetectorsandSources();
    }
    @SuppressLint("MissingPermission")
    private void getLocation(){
        try{
            locationManager = (LocationManager) getActivity().getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,5,QRCameraScannerFragment.this);
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
    }

    @Override
    public void onLocationChanged(@NonNull List<Location> locations) {
        LocationListener.super.onLocationChanged(locations);
    }

    @Override
    public void onFlushComplete(int requestCode) {
        LocationListener.super.onFlushComplete(requestCode);
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        LocationListener.super.onProviderEnabled(provider);
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        LocationListener.super.onProviderDisabled(provider);
    }
}

