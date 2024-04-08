package com.example.eventapptest2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.type.LatLng;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OldQrsFragments#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapsFragment extends Fragment implements OnMapReadyCallback {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
    GoogleMap gMap;
    ArrayList<User> attendeeList;
    FrameLayout map_frame;
    //LatLng UofA;
    com.google.android.gms.maps.model.LatLng UofA;
    LatLng.Builder mapBuilder;
    SupportMapFragment mapFragment;

    /**
     * Creates a map fragment with the attendee list as a param
     * @param attendeList
     */
    public MapsFragment(ArrayList<User> attendeList) {
        // Required empty public constructor
        this.attendeeList = attendeList;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OldQrsFragments.
     */
    // TODO: Rename and change types and number of parameters
//    public static OldQrsFragments newInstance(String param1, String param2) {
//        OldQrsFragments fragment = new OldQrsFragments();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }

    /**
     * Creates Map view
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
        View rootView = inflater.inflate(R.layout.maps_fragment, container, false);
        map_frame = rootView.findViewById(R.id.mapsFragment);

        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapsFragment);
        mapFragment.getMapAsync(this);

        // Inflate the layout for this fragment
        return rootView;
    }

    /**
     * Creates a marker for every user in attendee list, and zooms in the map automatically.
     * @param googleMap
     */
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.gMap = googleMap;
        this.gMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        float zoomLevel = 17.0f;
        com.google.android.gms.maps.model.LatLng attendeelatlng = new com.google.android.gms.maps.model.LatLng(51.848637, -0.55462);
        for(User Attendee: attendeeList){
            //if attende lat lang = a dont do anything
            if (!(Attendee.getUserLongitude().equals("a"))) {
            com.google.android.gms.maps.model.LatLng userLatLng = new com.google.android.gms.maps.model.LatLng(Double.parseDouble(Attendee.getUserLatitude()),Double.parseDouble(Attendee.getUserLongitude()));
            this.gMap.addMarker(new MarkerOptions().position(userLatLng)).setTitle(Attendee.getUserName());
            attendeelatlng = userLatLng;}
        }
        this.gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(attendeelatlng,zoomLevel));
    }
}