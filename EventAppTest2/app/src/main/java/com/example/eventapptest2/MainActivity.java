package com.example.eventapptest2;

import static android.content.ContentValues.TAG;

import android.media.Image;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.api.Distribution;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    // Initialize the Explore ArrayList
   // Event addEventFragmentstore = new Event();
    //firebase
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference usersRef = db.collection("users");
    private final CollectionReference eventsRef = db.collection("ExploreEvents");


    ArrayList<Event> savedEvents= new ArrayList<>();
    ArrayList<Event> createdEvents= new ArrayList<>();
    ArrayList<Event> oldQRList= new ArrayList<>();

    ArrayList<Event> Explore = new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        // Improved user retrieval with error handling
        getUser(deviceId).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                User testuser = task.getResult();
                //will be ugly but gonna have to nest again for global events
                /////////



                //////////
                // Navigate to UserProfileFragment with retrieved user
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.framelayout, new UserProfileFragment(testuser))
                        .commit();


//                Explore.add(new Event(deviceId,"Test", "homepage","City", 1234, null,"karan",null,null));
//                eventsRef.document().set(Explore.get(0).toMap()); // adding test event to database


                BottomNavigationView bottomnav = findViewById(R.id.bottomNavView);
                bottomnav.setSelectedItemId(R.id.UserProfileNav);

                bottomnav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        getData();

                        int itemId = item.getItemId();
                        if (itemId == R.id.OrganizeEventNav) {
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            //System.out.println("testtttttttttt " + testuser.getCreatedEvents());
                            fragmentTransaction.replace(R.id.framelayout, new OrgainzersEventFragment(createdEvents)); //explore is temp
                            fragmentTransaction.commit();
                            bottomnav.getMenu().clear();
                            bottomnav.inflateMenu(R.menu.organizer_nav_menu);
                            bottomnav.postDelayed(() -> bottomnav.setSelectedItemId(R.id.OrginzersEventsnav), 100);
                        } else if (itemId == R.id.UserProfileNav) {
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.framelayout, new UserProfileFragment(testuser));
                            fragmentTransaction.commit();
                        } else if (itemId == R.id.ExploreEventNav) {
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.framelayout, new ExploreFragment(Explore,savedEvents,deviceId));
                            fragmentTransaction.commit();
                        } else if (itemId == R.id.SavedEventNav) {
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.framelayout, new SavedEventsFragment(savedEvents)); // prolly will need not get userid of attendee when they sign in for the attendee list maybe explore event if we wanna care abt it ltr
                            fragmentTransaction.commit();
                        } else if (itemId == R.id.OldQrNav) {
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.framelayout, new OldQrsFragments());
                            fragmentTransaction.commit();
                        } else if (itemId == R.id.AddEventnav) {
                            //FragmentManager fragmentManager = getSupportFragmentManager();
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.framelayout, new AddEventFragment(Explore,testuser,deviceId,createdEvents));
                            fragmentTransaction.commit();
                        } else if (itemId == R.id.OrginzersEventsnav) {
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.framelayout, new OrgainzersEventFragment(createdEvents));
                            fragmentTransaction.commit();
                        } else if (itemId == R.id.GoBacknav) {
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.framelayout, new UserProfileFragment(testuser));
                            fragmentTransaction.commit();
                            bottomnav.getMenu().clear();
                            bottomnav.inflateMenu(R.menu.bottom_nav_menu);
                            bottomnav.postDelayed(() -> bottomnav.setSelectedItemId(R.id.UserProfileNav), 100);
                        } else {
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.framelayout, new UserProfileFragment(testuser));
                            fragmentTransaction.commit();
                        }

                        return true;
                    }
                });


            } else {
                // Handle error gracefully
                Log.e(TAG, "Error retrieving user:", task.getException());
            }


        });



        // Add an event to the Explore ArrayList //add a user profile array list for user sign-up/check-in

    }
    //database messy but makes sense
    //we have multiple collections
    //one for user created saved and old events
    //then we will map each event to get their attendelist ss
    //all user stuff saved on create
    private Task<User> getUser(String deviceId) {
        DocumentReference docRef = usersRef.document(deviceId);
        return docRef.get().continueWith(task -> {
            DocumentSnapshot doc = task.getResult();
            User user = new User(deviceId,"","","","",null, savedEvents,  createdEvents,  oldQRList); // Create empty user with device ID
            if (doc.exists()) {
                // Map Firestore data to User object
                user.setUserName((String) doc.getData().get("userName"));
                user.setUserHomepage((String) doc.getData().get("userHomepage"));
                user.setUserEmail((String) doc.getData().get("userEmail"));
                user.setUserPhoneNumber((String) doc.getData().get("userPhoneNumber"));
                user.setUserProfileImage((Image) doc.getData().get("UserProfileImage"));


                getDataSave(deviceId);
                //maybe redunant but im scared
                savedEvents = user.getSavedEvents();

                getDataCreate(deviceId);
                //maybe redunant but im to scared
                user.setCreatedEvents(createdEvents);
                createdEvents = user.getCreatedEvents();



                user.setOldQRList((ArrayList<Event>) doc.getData().get("oldQRList"));
                oldQRList = user.getOldQRList();

            } else {
                // Create a new empty user document in Firestore
                docRef.set(user).addOnSuccessListener(aVoid -> Log.d(TAG, "User document created"));
            }
            return user;
        });
    }





    private void getDataSave(String id){
        final CollectionReference SavedeventsRef = db.collection("SavedEvents" + id);

        SavedeventsRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    return;
                }

                savedEvents.clear(); // Clear the old list
                for(QueryDocumentSnapshot doc: queryDocumentSnapshots) {
//                    String eventId = doc.getId();
                    if (doc.exists()) {
                        String eventName = (String) doc.getData().get("eventName");
                        String eventLimit = (String) doc.getData().get("eventLimit");
                        String eventLocation = (String) doc.getData().get("eventLocation");
                        String eventDate = (String) doc.getData().get("eventDate");
                        String EventId = (String) doc.getData().get("eventId");
                        String Eventdes = (String) doc.getData().get("eventDescription");
                        //we can get arround theses arrays the same way we did above for the user event lists the quniue id for this should be the QR
                        ArrayList<User> attendelist = (ArrayList<User>) doc.getData().get("attendeeList");
                        ArrayList<User> checkinlist = (ArrayList<User>) doc.getData().get("Checkin-list");
                        String eventImage = (String) doc.getData().get("eventPoster");
                        //System.out.println("Image URL: " + eventImage);
                        // ... (add other event properties based on your Event class)
                        savedEvents.add(new Event(EventId, eventName, eventLocation, eventDate, eventLimit, eventImage, Eventdes, attendelist, checkinlist));
                    }// Assuming "karan" is a placeholder for organizer
                }


            }
        });
    }
    private void getDataCreate(String id){
         final CollectionReference createeventsRef = db.collection("CreateEvents" + id);

        createeventsRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    return;
                }

                createdEvents.clear(); // Clear the old list
                for(QueryDocumentSnapshot doc: queryDocumentSnapshots) {
//                    String eventId = doc.getId();
                    if (doc.exists()) {
                        String eventName = (String) doc.getData().get("eventName");
                        String eventLimit = (String) doc.getData().get("eventLimit");
                        String eventLocation = (String) doc.getData().get("eventLocation");
                        String eventDate = (String) doc.getData().get("eventDate");
                        String EventId = (String) doc.getData().get("eventId");
                        String Eventdes = (String) doc.getData().get("eventDescription");
                        ArrayList<User> attendelist = (ArrayList<User>) doc.getData().get("attendeeList");
                        ArrayList<User> checkinlist = (ArrayList<User>) doc.getData().get("Checkin-list");
                        String eventImage = (String) doc.getData().get("eventPoster");
                        //System.out.println("Image URL: " + eventImage);
                        // ... (add other event properties based on your Event class)
                        createdEvents.add(new Event(EventId, eventName, eventLocation, eventDate, eventLimit, eventImage, Eventdes, attendelist, checkinlist));
                    }// Assuming "karan" is a placeholder for organizer
                }


            }
        });
    }

    private void getData(){


        eventsRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    return;
                }

                Explore.clear(); // Clear the old list
                for(QueryDocumentSnapshot doc: queryDocumentSnapshots) {
//                    String eventId = doc.getId();
                    String eventName = (String) doc.getData().get("eventName");
                    String eventLimit = (String) doc.getData().get("eventLimit");
                    String eventLocation = (String) doc.getData().get("eventLocation");
                    String eventDate = (String) doc.getData().get("eventDate");
                    String EventId = (String) doc.getData().get("eventId");
                    String Eventdes = (String) doc.getData().get("eventDescription");
                    ArrayList<User> attendelist = (ArrayList<User>) doc.getData().get("attendeeList");
                    ArrayList<User> checkinlist = (ArrayList<User>) doc.getData().get("Checkin-list");
                    String eventImage = (String) doc.getData().get("eventPoster");
                    //System.out.println("Image URL: " + eventImage);
                    // ... (add other event properties based on your Event class)
                    Explore.add(new Event(EventId,eventName, eventLocation, eventDate,eventLimit, eventImage, Eventdes,attendelist,checkinlist)); // Assuming "karan" is a placeholder for organizer
                }


            }
        });
    }
}
