package com.example.eventapptest2;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.media.Image;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.api.Distribution;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {//implements ExploreEventsRecyclerViewAdapter.OnExploreButtonClickListener {

    // Initialize the Explore ArrayList
    // Event addEventFragmentstore = new Event();
    //firebase
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference usersRef = db.collection("users");


    ArrayList<Event> savedEvents = new ArrayList<>();
    ArrayList<Event> createdEvents = new ArrayList<>();
    ArrayList<Event> oldQRList = new ArrayList<>();

    ArrayList<Event> Explore = new ArrayList<>();
    ArrayList<String> notifyList = new ArrayList<>();
    public int inte = 0;

    /**
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
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
//                // Navigate to UserProfileFragment with retrieved user
//                FragmentManager fragmentManager = getSupportFragmentManager();
//                fragmentManager.beginTransaction()
//                        .replace(R.id.framelayout, new UserProfileFragment(testuser,fragmentManager,bottomnav))//we should also pass
//                        // CollectionReference usersRef
//                        // we should init a uid from testuser.getdeviceid() to we can do userRef.document(deviceid).set(editeduser) for editing
//                        .commit();


//                Explore.add(new Event(deviceId,"Test", "homepage","City", 1234, null,"karan",null,null));
//                eventsRef.document().set(Explore.get(0).toMap()); // adding test event to database


                BottomNavigationView bottomnav = findViewById(R.id.bottomNavView);
                bottomnav.setSelectedItemId(R.id.UserProfileNav);


                getDataUser(testuser);


                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.framelayout, new UserProfileFragment(testuser, fragmentManager, bottomnav, Explore));
                fragmentTransaction.commit();


//                for(Event event: savedEvents){
//                    HeadsUpNotify(event.getEventid());
//                }

                bottomnav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
                    /**
                     * Upon selecting an option in the navigation bar below, this will transition
                     * the user to the fragment they requested.
                     * @param item The selected item
                     * @return
                     */
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        int itemId = item.getItemId();
                        if (itemId == R.id.OrganizeEventNav) {
                            getDataCreate(deviceId);
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            //System.out.println("testtttttttttt " + testuser.getCreatedEvents());
                            fragmentTransaction.replace(R.id.framelayout, new OrgainzersEventFragment(createdEvents, fragmentManager, bottomnav, testuser)); //explore is temp
                            fragmentTransaction.commit();
                            bottomnav.getMenu().clear();
                            bottomnav.inflateMenu(R.menu.organizer_nav_menu);
                            bottomnav.postDelayed(() -> bottomnav.setSelectedItemId(R.id.OrginzersEventsnav), 100);
                        } else if (itemId == R.id.UserProfileNav) {

                            getDataUser(testuser);
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.framelayout, new UserProfileFragment(testuser, fragmentManager, bottomnav, Explore));
                            fragmentTransaction.commit();
                        } else if (itemId == R.id.ExploreEventNav) {
                            getDataExplore(deviceId); //dk why but pervents glitch when the user of deviceid can see there created events in explore

                            FragmentManager fragmentManager = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.framelayout, new ExploreFragment(Explore, savedEvents, deviceId, fragmentManager, bottomnav, testuser));
                            fragmentTransaction.commit();
                        } else if (itemId == R.id.SavedEventNav) {
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.framelayout, new SavedEventsFragment(savedEvents, fragmentManager, bottomnav, testuser)); // prolly will need not get userid of attendee when they sign in for the attendee list maybe explore event if we wanna care abt it ltr
                            fragmentTransaction.commit();
                        } else if (itemId == R.id.OldQrNav) {
                            getOldQrList(deviceId);

                            FragmentManager fragmentManager = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.framelayout, new OldQrsFragments(oldQRList, deviceId, fragmentManager));
                            fragmentTransaction.commit();
                        } else if (itemId == R.id.AddEventnav) {
                            //FragmentManager fragmentManager = getSupportFragmentManager();
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.framelayout, new AddEventFragment(Explore, testuser, deviceId, createdEvents));
                            fragmentTransaction.commit();
                        } else if (itemId == R.id.OrginzersEventsnav) {
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.framelayout, new OrgainzersEventFragment(createdEvents, fragmentManager, bottomnav, testuser));
                            fragmentTransaction.commit();
                        } else if (itemId == R.id.GoBacknav) {
                            getDataUser(testuser);
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.framelayout, new UserProfileFragment(testuser, fragmentManager, bottomnav, Explore));
                            fragmentTransaction.commit();
                            bottomnav.getMenu().clear();
                            bottomnav.inflateMenu(R.menu.bottom_nav_menu);
                            bottomnav.postDelayed(() -> bottomnav.setSelectedItemId(R.id.UserProfileNav), 100);
                        } else if (itemId == R.id.GoBacknavDets) {
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.framelayout, new ExploreFragment(Explore, savedEvents, deviceId, fragmentManager, bottomnav, testuser));
                            fragmentTransaction.commit();
                            bottomnav.getMenu().clear();
                            bottomnav.inflateMenu(R.menu.bottom_nav_menu);
                            bottomnav.postDelayed(() -> bottomnav.setSelectedItemId(R.id.ExploreEventNav), 100);
                        }


                        //test so change
                        else if (itemId == R.id.EventNotifyNav) {
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            //System.out.println("testtttttttttt " + testuser.getCreatedEvents());
                            fragmentTransaction.replace(R.id.framelayout, new OragnizersNotificationFragment(createdEvents.get(testuser.getLastsaved()))); //explore is temp
                            fragmentTransaction.commit();
//                            bottomnav.getMenu().clear();
//                            bottomnav.inflateMenu(R.menu.organizer_nav_menu);
//                            bottomnav.postDelayed(() -> bottomnav.setSelectedItemId(R.id.OrginzersEventsnav), 100);
                        } else if (itemId == R.id.EventMapNav) {
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.framelayout, new MapsFragment((createdEvents.get(testuser.getLastsaved())).getAttendeList()));
                            fragmentTransaction.commit();
                        } else if (itemId == R.id.EventAttendeesNav) {
                            /// may wanna add a hidden textview in Main xml so we can add the checkin count
                            // do attendees need a sign-out option??


                            FragmentManager fragmentManager = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            //System.out.println("testtttttttttt " + testuser.getCreatedEvents());
                            fragmentTransaction.replace(R.id.framelayout, new AttendeeListFragment((createdEvents.get(testuser.getLastsaved())).getAttendeList())); //explore is temp
                            // pass in createdEvents.get(testuser.getLastsaved())).getAttendelist() to pass in the attendee list //deal with check-in after making good
                            fragmentTransaction.commit();
//                            bottomnav.getMenu().clear();
//                            bottomnav.inflateMenu(R.menu.organizer_nav_menu);
//                            bottomnav.postDelayed(() -> bottomnav.setSelectedItemId(R.id.OrginzersEventsnav), 100);
                        } else if (itemId == R.id.EventNavGoback) {
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            //System.out.println("testtttttttttt " + testuser.getCreatedEvents());
                            fragmentTransaction.replace(R.id.framelayout, new OrgainzersEventFragment(createdEvents, fragmentManager, bottomnav, testuser)); //explore is temp
                            fragmentTransaction.commit();
                            bottomnav.getMenu().clear();
                            bottomnav.inflateMenu(R.menu.organizer_nav_menu);
                            bottomnav.postDelayed(() -> bottomnav.setSelectedItemId(R.id.OrginzersEventsnav), 100);
                        } else if (itemId == R.id.EventDetailsNav) {
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            //System.out.println("testtttttttttt " + testuser.getCreatedEvents());
                            fragmentTransaction.replace(R.id.framelayout, new OrganizeEventDetsFragment(createdEvents.get(testuser.getLastsaved()), testuser)); //explore is temp
                            fragmentTransaction.commit();
                        } else if (itemId == R.id.AttendeeNotifications) {
                            // getNotifyList(savedEvents.get(testuser.getLastsaved()).getEventid());//takes eventid


                            final CollectionReference NotifyListRef = db.collection("Notify" + savedEvents.get(testuser.getLastsaved()).getEventid());



                            NotifyListRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
                                /**
                                 *
                                 * @param queryDocumentSnapshots The value of the event. {@code null} if there was an error.
                                 * @param error The error if there was error. {@code null} otherwise.
                                 */
                                @Override
                                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
                                    if (error != null) {
                                        return;
                                    }
                                    notifyList.clear();

                                    // savedEvents.clear(); // Clear the old list
                                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
//                    String eventId = doc.getId();
                                        if (doc.exists()) {
                                            String UserName = (String) doc.getData().get("note");
                                            //System.out.println("Image URL: " + eventImage);
                                            // ... (add other event properties based on your Event class)

                                            notifyList.add(UserName);
                                        }// Assuming "karan" is a placeholder for organizer
                                    }


                                    FragmentManager fragmentManager = getSupportFragmentManager();
                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    //System.out.println("testtttttttttt " + testuser.getCreatedEvents());
                                    fragmentTransaction.replace(R.id.framelayout, new AttendeeNotifyFragment(notifyList, savedEvents.get(testuser.getLastsaved()).getEventPoster())); //explore is temp
                                    fragmentTransaction.commit();


                                }
                            });


                        } else if (itemId == R.id.AttendeeProfile) {
                            getDataUser(testuser);
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.framelayout, new UserProfileFragment(testuser, fragmentManager, bottomnav, Explore));
                            fragmentTransaction.commit();
                        } else if (itemId == R.id.AttendeeDetails) {
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            //System.out.println("testtttttttttt " + testuser.getCreatedEvents());
                            fragmentTransaction.replace(R.id.framelayout, new ExploreEventDetsFragment(savedEvents.get(testuser.getLastsaved()), fragmentManager, testuser, bottomnav)); //explore is temp
                            fragmentTransaction.commit();
                        } else if (itemId == R.id.AttendeeGoBack) {
                            getDataSave(deviceId);

                            FragmentManager fragmentManager = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            //System.out.println("testtttttttttt " + testuser.getCreatedEvents());
                            fragmentTransaction.replace(R.id.framelayout, new SavedEventsFragment(savedEvents, fragmentManager, bottomnav, testuser)); //explore is temp
                            fragmentTransaction.commit();
                            bottomnav.getMenu().clear();
                            bottomnav.inflateMenu(R.menu.bottom_nav_menu);
                            bottomnav.postDelayed(() -> bottomnav.setSelectedItemId(R.id.SavedEventNav), 100);
                        } else if (itemId == R.id.AdminGoBack) {
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            //System.out.println("testtttttttttt " + testuser.getCreatedEvents());
                            fragmentTransaction.replace(R.id.framelayout, new SavedEventsFragment(savedEvents, fragmentManager, bottomnav, testuser)); //explore is temp
                            fragmentTransaction.commit();
                            bottomnav.getMenu().clear();
                            bottomnav.inflateMenu(R.menu.bottom_nav_menu);
                            bottomnav.postDelayed(() -> bottomnav.setSelectedItemId(R.id.SavedEventNav), 100);
                        } else if (itemId == R.id.AdminImages) { ///easyish
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            //System.out.println("testtttttttttt " + testuser.getCreatedEvents());
                            fragmentTransaction.replace(R.id.framelayout, new AdminImageFragment(adminGetAllImages())); // the passed list is temporary
                            fragmentTransaction.commit();
                        } else if (itemId == R.id.AdminEvents) { //  hard              // should take in a list of event and user
                            //delte the events creators event easy // delete all users saved events if event in their saved event hard// delete event attendee list easy
                            //delete event notfications as well easy
                            // will also need to check if event is old then delete the event from the event owners old qr list
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            //System.out.println("testtttttttttt " + testuser.getCreatedEvents());
                            fragmentTransaction.replace(R.id.framelayout, new AdminEventsFragment(adminGetAllEvents())); //explore is temp
                            fragmentTransaction.commit();
                        } else if (itemId == R.id.AdminProfiles) { //the most easy casue we never delete a profile realisitcly just set everything to "" except the id
                            getProfileList(new ProfileListCallback() {
                                @Override
                                public void onProfileList(ArrayList<User> profileList) {
                                    // Create a new instance of AdminProfileFragment and pass the profileList to its constructor
                                    AdminProfileFragment adminProfileFragment = new AdminProfileFragment(profileList);

                                    // Perform fragment transaction
                                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                                    fragmentTransaction.replace(R.id.framelayout, adminProfileFragment);
                                    fragmentTransaction.commit();
                                }
                            });
                        }


                        //implement elif fragment change for new menus made from elsewhere


                        //else is bad if we have no click menu
//                        else {
//                            FragmentManager fragmentManager = getSupportFragmentManager();
//                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                            fragmentTransaction.replace(R.id.framelayout, new UserProfileFragment(testuser));
//                            fragmentTransaction.commit();
//                        }

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

    /**
     * Fetches user information based on device ID, this is so that profiles can be searched
     * in the database via device ID. As such, no sign-in is required, as the profile is
     * linked to the device.
     * @param deviceId
     * @return
     */
    private Task<User> getUser(String deviceId) {
        DocumentReference docRef = usersRef.document(deviceId);
        return docRef.get().continueWith(task -> {
            DocumentSnapshot doc = task.getResult();
            User user = new User(deviceId, "", "", "", "", "", savedEvents, createdEvents, oldQRList, "on"); // Create empty user with device ID
            if (doc.exists()) {
                // Map Firestore data to User object
                user.setUserName((String) doc.getData().get("userName"));
                user.setUserHomepage((String) doc.getData().get("userHomepage"));
                user.setUserEmail((String) doc.getData().get("userEmail"));
                user.setUserPhoneNumber((String) doc.getData().get("userPhoneNumber"));
                user.setUserProfileImage((String) doc.getData().get("userProfileImage"));
                user.setTracking((String) doc.getData().get("tracking"));

                getDataSave(deviceId);
                //maybe redunant but im scared
                // savedEvents = user.getSavedEvents();
//                for(Event event: savedEvents){
//                    HeadsUpNotify(event.getEventid());
//                }
                getDataCreate(deviceId);
                //maybe redunant but im to scared
                //user.setCreatedEvents(createdEvents);
                //createdEvents = user.getCreatedEvents();

                getDataExplore(deviceId); // underneath created and saved b/c we should remove all saved and created from the users viewble explore list
                // this means adding to saved events code and add event fragment may need fixes // we can just delete all old by date events from explore in the list and db (cleans up db) b/c we will we can delete by .documen(eventid).delete()
                // filter all old dates from created and put them into oldQRList
                // we dont even need a databse collection for oldQR then
                // remove all events from the list that have reached there limit
                getOldQrList(deviceId);
                //below create events get data b/c creategetdata will add to the database
                //oldQRList = user.getOldQRList();
                //user.setOldQRList(oldQRList);

            } else {
                // Create a new empty user document in Firestore
                docRef.set(user).addOnSuccessListener(aVoid -> Log.d(TAG, "User document created"));
            }
            return user;
        });
    }

    /**
     * Gets information of the current user and stores it locally.
     * @param currentuser
     */
    private void getDataUser(User currentuser) {
        DocumentReference docRef = usersRef.document(currentuser.getDeviceId());

        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot doc, @Nullable FirebaseFirestoreException error) {
                //User user = new User(currentuser.getDeviceId(),"","","","","", savedEvents,  createdEvents,  oldQRList,"on"); // Create empty user with device ID

                currentuser.setUserName((String) doc.get("userName"));
                currentuser.setUserHomepage((String) doc.get("userHomepage"));
                currentuser.setUserEmail((String) doc.get("userEmail"));
                currentuser.setUserPhoneNumber((String) doc.get("userPhoneNumber"));
                currentuser.setUserProfileImage((String) doc.get("userProfileImage"));
                currentuser.setTracking((String) doc.get("tracking"));

//                getDataSave(currentuser.getDeviceId());
//                //maybe redunant but im scared
//                // savedEvents = user.getSavedEvents();
//
//                getDataCreate(currentuser.getDeviceId());
//                //maybe redunant but im to scared
//                //user.setCreatedEvents(createdEvents);
//                //createdEvents = user.getCreatedEvents();
//
//                getDataExplore(currentuser.getDeviceId()); // underneath created and saved b/c we should remove all saved and created from the users viewble explore list
//                // this means adding to saved events code and add event fragment may need fixes // we can just delete all old by date events from explore in the list and db (cleans up db) b/c we will we can delete by .documen(eventid).delete()
//                // filter all old dates from created and put them into oldQRList
//                // we dont even need a databse collection for oldQR then
//                // remove all events from the list that have reached there limit
//                getOldQrList(currentuser.getDeviceId());

            }
        });
    }

    /**
     * This recovers old events, allowing the user to reuse events.
     * @param id
     */
    private void getOldQrList(String id) {
        final CollectionReference SavedeventsRef = db.collection("OldQrsList" + id);

        SavedeventsRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    return;
                }

                oldQRList.clear(); // Clear the old list
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
//                    String eventId = doc.getId();
                    if (doc.exists()) {
                        String eventName = (String) doc.getData().get("eventName");
                        String eventLimit = (String) doc.getData().get("eventLimit");
                        String eventLocation = (String) doc.getData().get("eventLocation");
                        String eventDate = (String) doc.getData().get("eventDate");
                        String EventId = (String) doc.getData().get("eventid");
                        String Eventdes = (String) doc.getData().get("eventDesription");
                        //we can get arround theses arrays the same way we did above for the user event lists the quniue id for this should be the QR
                        ArrayList<User> attendelist = (ArrayList<User>) doc.getData().get("attendeeList");
                        ArrayList<User> checkinlist = (ArrayList<User>) doc.getData().get("Checkin-list");
                        String eventImage = (String) doc.getData().get("eventPoster");
                        //QrUrl
                        String qrlur = (String) doc.getData().get("qrUrl");
                        String qrelur = (String) doc.getData().get("signINQR");
                        String owner = (String) doc.getData().get("owner");
                        String track = (String) doc.getData().get("tracking");

                        //System.out.println("Image URL: " + eventImage);
                        // ... (add other event properties based on your Event class)


                        oldQRList.add(new Event(EventId, eventName, eventLocation, eventDate, eventLimit, eventImage, Eventdes, attendelist, checkinlist, qrlur, qrelur, owner, track));
                    }// Assuming "karan" is a placeholder for organizer
                }


            }
        });
    }

    /**
     * Gets event data from the saved events page
     * @param id
     */
    private void getDataSave(String id) {
        // create event notfications are only made there so no list needed b/c they will only update notfication list of event like attendeelist
        // this means saved data stores a notfication list

       // HeadsUpNotify(id);
        final CollectionReference SavedeventsRef = db.collection("SavedEvents" + id);
        //HeadsUpNotify(id);
        SavedeventsRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    return;
                }

                savedEvents.clear(); // Clear the old list
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
//                    String eventId = doc.getId();
                    if (doc.exists()) {
                        String ExEid = (String) doc.getId();
                        final CollectionReference eventsRef = db.collection("ExploreEvents");
                        //we get from explore so tracking can update,
                        eventsRef.document(ExEid).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                            @Override
                            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                if (value.exists()) { //if user exists
                                    String eventName = (String) value.getData().get("eventName");
                                    String eventLimit = (String) value.getData().get("eventLimit");
                                    String eventLocation = (String) value.getData().get("eventLocation");
                                    String eventDate = (String) value.getData().get("eventDate");
                                    String EventId = (String) value.getData().get("eventid");
                                    String Eventdes = (String) value.getData().get("eventDesription");
                                    //we can get arround theses arrays the same way we did above for the user event lists the quniue id for this should be the QR
                                    ArrayList<User> attendelist = (ArrayList<User>) value.getData().get("attendeeList");
                                    ArrayList<User> checkinlist = (ArrayList<User>) value.getData().get("Checkin-list");
                                    String eventImage = (String) value.getData().get("eventPoster");
                                    //QrUrl
                                    String qrlur = (String) value.getData().get("qrUrl");
                                    String qrelur = (String) value.getData().get("signINQR");
                                    String owner = (String) value.getData().get("owner");
                                    String track = (String) value.getData().get("tracking");
                                    //System.out.println("Image URL: " + eventImage);
                                    // ... (add other event properties based on your Event class)
                                    if (!isDateBeforeCurrentDate(eventDate)) {
                                        savedEvents.add(new Event(EventId, eventName, eventLocation, eventDate, eventLimit, eventImage, Eventdes, attendelist, checkinlist, qrlur, qrelur, owner, track));
                                    } else {//remove event from explore events fb do the same with saved events fb remove from created fb and add to oldqrfb
                                        SavedeventsRef.document(EventId).delete();
                                    }
                                }
                            }
                        });


                    }// Assuming "karan" is a placeholder for organizer
                }


            }
        });

    }

    /**
     * gets event data when creating an event
     * @param id
     */
    private void getDataCreate(String id) {
        final CollectionReference createeventsRef = db.collection("CreateEvents" + id);
        final CollectionReference OldQreventsRef = db.collection("OldQrsList" + id);

        createeventsRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    return;
                }


                createdEvents.clear(); // Clear the old list
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
//                    String eventId = doc.getId();
                    if (doc.exists()) {
                        String eventName = (String) doc.getData().get("eventName");
                        String eventLimit = (String) doc.getData().get("eventLimit");
                        String eventLocation = (String) doc.getData().get("eventLocation");
                        String eventDate = (String) doc.getData().get("eventDate");
                        String EventId = (String) doc.getData().get("eventid");
                        String Eventdes = (String) doc.getData().get("eventDesription");
                        ArrayList<User> attendelist = new ArrayList<>();
                        attendelist.clear();
                        attendelist = getAttendeList(EventId);
                        ArrayList<User> checkinlist = (ArrayList<User>) doc.getData().get("Checkin-list"); //not needed anymore
                        String eventImage = (String) doc.getData().get("eventPoster");
                        String qrlur = (String) doc.getData().get("qrUrl");
                        String owner = (String) doc.getData().get("owner");
                        String track = (String) doc.getData().get("tracking");
                        //System.out.println("Image URL: " + eventImage);
                        // ... (add other event properties based on your Event class)
                        String qrelur = (String) doc.getData().get("signINQR");
                        if (!isDateBeforeCurrentDate(eventDate)) {
                            createdEvents.add(new Event(EventId, eventName, eventLocation, eventDate, eventLimit, eventImage, Eventdes, attendelist, checkinlist, qrlur, qrelur, owner, track));
                        } else {//remove event from explore events fb do the same with saved events fb remove from created fb and add to oldqrfb
                            final CollectionReference AttendeListRef = db.collection("AttendeeList" + EventId);
                            createeventsRef.document(EventId).delete();
                            for (User attende : attendelist) {
                                AttendeListRef.document(attende.getDeviceId()).delete();
                            }
                            attendelist = new ArrayList<User>();
                            OldQreventsRef.document(EventId).set(new Event(EventId, eventName, eventLocation, eventDate, eventLimit, eventImage, Eventdes, attendelist, checkinlist, qrlur, qrelur, owner, track));

                            // clear the attendee list


                        }


                    }// Assuming "karan" is a placeholder for organizer
                }


            }
        });
    }

    /**
     * gets event data from the explore events page
     * @param id
     */
    private void getDataExplore(String id) {
        // when a event is old we save the attendees for next time so ateendes already signed up


        final CollectionReference eventsRef = db.collection("ExploreEvents");

        eventsRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    return;
                }

                Explore.clear(); // Clear the old list
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
//                   String eventId = doc.getId(); //I belive this is a has

                    String eventName = (String) doc.getData().get("eventName");
                    String eventLimit = (String) doc.getData().get("eventLimit");
                    String eventLocation = (String) doc.getData().get("eventLocation");
                    String eventDate = (String) doc.getData().get("eventDate");
                    String EventId = (String) doc.getData().get("eventid");
                    String Eventdes = (String) doc.getData().get("eventDesription");
                    ArrayList<User> attendelist = getAttendeList(EventId); //not needed in saved or explore events we can remove theses to save space
                    ArrayList<User> checkinlist = (ArrayList<User>) doc.getData().get("Checkin-list");
                    String eventImage = (String) doc.getData().get("eventPoster");
                    String qrlur = (String) doc.getData().get("qrUrl");
                    String qrelur = (String) doc.getData().get("signINQR");
                    String owner = (String) doc.getData().get("owner");
                    String track = (String) doc.getData().get("tracking");
                    //System.out.println("Image URL: " + eventImage);
                    // ... (add other event properties based on your Event class)
                    Event test = new Event(EventId, eventName, eventLocation, eventDate, eventLimit, eventImage, Eventdes, attendelist, checkinlist, qrlur, qrelur, owner, track);
                    ArrayList<User> attendeliste = getAttendeList(EventId);
                    //if (EventId != test.getEventid()){
                    int lists = attendeliste.size();

                    try {
                        int limit = Integer.parseInt(eventLimit);

                        if (!isDateBeforeCurrentDate(eventDate)) { //checks if date is current or greater
                            if (lists < limit) { //checks if event limit was reached
                                if (!eventIdExists(savedEvents, createdEvents, EventId)) { // this checks if in saved or created
                                    Explore.add(test);
                                } else {
                                }
                            } else {
                            }
                        } else {
                            //remove event from explore events fb do the same with saved events fb remove from created fb and add to oldqrfb
                            eventsRef.document(EventId).delete();
                        }
                    } catch (NumberFormatException e) {
                        // Handle the case where eventLimit is not a valid integer
                        // For now, you might just want to skip the if-else block
                        // You can add logging or any other action as needed
                        if ((!eventIdExists(savedEvents, createdEvents, EventId))&&(!isDateBeforeCurrentDate(eventDate))) { // this checks if in saved or created
                            Explore.add(test);
                        }
                    }


                }


            }
        });
    }

    /**
     * A list of attendees, for use in events.
     * @param id
     * @return
     */
    private ArrayList<User> getAttendeList(String id) { //takes event id
        /// this should instead fetch the user from the users collection so attendee updates if the user changes images or whatever


        final CollectionReference AttendeListRef = db.collection("AttendeeList" + id); // maybe this still need we could get all device id from the collections documents
        ArrayList<User> attendeeList = new ArrayList<>();
        AttendeListRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    return;
                }
                attendeeList.clear();
                // savedEvents.clear(); // Clear the old list
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
//                    String eventId = doc.getId();
                    if (doc.exists()) {
                        String attendeeid = doc.getId();
                        final CollectionReference userref = db.collection("users");
                        //get user from user collection if any changes happened to user
                        Log.e(TAG, attendeeid);Log.e(TAG, id);

                        userref.document(attendeeid).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                            @Override
                            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {


                                String attendeeDID = (String) value.get("deviceId");

                                String UserName = (String) value.get("userName");
                                String UserImage = (String) value.getData().get("userProfileImage");
                                String checkin = (String) doc.getData().get("CheckInCount"); // the doc contains the count
                                String userLongitude = (String) doc.getData().get("userLongitude");
                                String userLatitude = (String) doc.getData().get("userLatitude");
                                for(User attend: attendeeList){
                                    if(attend.getDeviceId().equals(attendeeDID)){
                                        attendeeList.remove(attend);

                                    }


                                }



                                attendeeList.add(new User(attendeeDID,attendeeDID,UserName, UserImage, checkin, userLongitude, userLatitude));


                            }
                        });
                    }// Assuming "karan" is a placeholder for organizer
                }

            }
        });
        return attendeeList;
    }

    /**
     * checks if event exists
     * @param list1
     * @param list2
     * @param eventId
     * @return
     */
    public static boolean eventIdExists(ArrayList<Event> list1, ArrayList<Event> list2, String eventId) {
        // code is literally O(n+m) n & m are size of lists
        for (Event event : list1) {
            if (event.getEventid().equals(eventId)) { ///////////////////////////////// if anyone doesnt know == is to compare memory location and .equals() is used to compare context
                return true;
            }
        }

        for (Event event : list2) {
            if (event.getEventid().equals(eventId)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks current date, as to not allow creation of events that take place on a date that have
     * already passed
     * @param dateString
     * @return
     */
    public static boolean isDateBeforeCurrentDate(String dateString) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();

        try {
            Date date = simpleDateFormat.parse(dateString);
            return date.before(currentDate);
        } catch (ParseException e) {
            // e.printStackTrace(); // Handle parsing exception appropriately
            // You might want to return false here since parsing failed
            return false;
        }
    }

    private void getNotifyList(String id) { //takes event id
        final CollectionReference NotifyListRef = db.collection("Notify" + id);

        NotifyListRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    return;
                }

                // savedEvents.clear(); // Clear the old list
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
//                    String eventId = doc.getId();
                    if (doc.exists()) {
                        String UserName = (String) doc.getData().get("note");
                        //System.out.println("Image URL: " + eventImage);
                        // ... (add other event properties based on your Event class)

                        notifyList.add(UserName);
                    }// Assuming "karan" is a placeholder for organizer
                }

            }
        });
        //return notifyList;
    }


//    // Show all the users in the app for the admin
//    private ArrayList<User> getAllUsers() {
//        // Fetch the user from the "Users" collection so when users updates there profile it updates the User list as well
//
//        // Go to the
//        final CollectionReference usersRef = db.collection("users");
//        ArrayList<User> userList = new ArrayList<>();
//
//        usersRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
//                if (error != null) {
//                    return;
//                }
//
//                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
//                    if (doc.exists()) {
//                        String deviceId = (String) doc.getData().get("deviceId");
//                        String userName = (String) doc.get("userName");
//                        String userImage = (String) doc.getData().get("userProfileImage");
//                        String userHomepage = (String) doc.getData().get("userHomepage");
//                        String userEmail = (String) doc.getData().get("userEmail");
//                        String userPhoneNumber = (String) doc.getData().get("userPhoneNumber");
//                        User user = new User(deviceId, userName, userImage, userHomepage, userEmail, userPhoneNumber);
//
//
//
//
//                        // If no name show deviceId or if userName is empty string make username == DeviceId
//
//                        // !userNmae.equals("") add to userList
//
//                        // As an admin cant delete yourself so dont add to userList
//                        String myDeviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
//
//                        if((!(myDeviceId.equals(deviceId) || ((Objects.equals(userName, "")) && (Objects.equals(userImage, "")) && (Objects.equals(userPhoneNumber, ""))&& (Objects.equals(userHomepage, "")))))){
//                            userList.add(user);
//                        }
//
////                        if((!(myDeviceId.equals(deviceId)) && (Objects.equals(userName, "")) && (Objects.equals(userImage, "")) && (Objects.equals(userPhoneNumber, ""))&& (Objects.equals(userHomepage, "")))){
////                            userList.add(user);
////                        }
//                    }
//                }
//
//            }
//        });
//        return userList;
//    }


    //only admin cant delete their profile
    public interface ProfileListCallback {
        void onProfileList(ArrayList<User> profileList);
    }

    /**
     * Gets list of user profiles so admin can monitor and delete profiles
     * @param callback
     */
    private void getProfileList(final ProfileListCallback callback) {
        final ArrayList<User> profileList = new ArrayList<>();
        CollectionReference profileListRef = db.collection("users");
        profileListRef.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                if (doc.exists()) {
                                    String myDeviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
                                    String deviceId = (String) doc.getData().get("deviceId");
                                    String userName = (String) doc.getData().get("userName");
                                    String userProfileImage = (String) doc.getData().get("userProfileImage");
                                    String userHomepage = (String) doc.getData().get("userHomepage");
                                    String userPhoneNumber = (String) doc.getData().get("userPhoneNumber");
                                    String userEmail = (String) doc.getData().get("userEmail");
                                    //if(!userName.equals(" ") && !userProfileImage.equals(" ") && !userHomepage.equals(" ") && !userPhoneNumber.equals(" ")) {
                                    if ((!(myDeviceId.equals(deviceId) || ((Objects.equals(userName, "")) && (Objects.equals(userProfileImage, "")) && (Objects.equals(userPhoneNumber, "")) && (Objects.equals(userHomepage, "") && (Objects.equals(userEmail, ""))))))) {
                                        profileList.add(new User(deviceId, userName, userProfileImage, userHomepage, userPhoneNumber, userEmail));
                                    }
                                }
                            }
                            callback.onProfileList(profileList);
                        } else {
                        }
                    }
                });
    }

    /**
     * get list of events so admin can monitor and delete events
     * @return
     */
    // Browse events
    private ArrayList<Event> adminGetAllEvents() {
        ArrayList<Event> eventList = new ArrayList<>();
        final CollectionReference exploreEventRef = db.collection("ExploreEvents");

        exploreEventRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    return;
                }
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                    if (doc.exists()) {
                        String eventDate = (String) doc.getData().get("eventDate");
                        String eventDescription = (String) doc.getData().get("eventDesription");
                        String eventLimit = (String) doc.getData().get("eventLimit");
                        String eventLocation = (String) doc.getData().get("eventLocation");
                        String eventName = (String) doc.getData().get("eventName");
                        String eventPoster = (String) doc.getData().get("eventPoster");
                        String eventID = (String) doc.getData().get("eventid");
                        String eventOwner = (String) doc.getData().get("owner");
                        Event event = new Event(eventDate, eventDescription, eventLimit, eventLocation, eventName, eventPoster, eventID, eventOwner);

                        eventList.add(event);
                    }
                }
            }
        });
        return eventList;
    }

    // Admin view all images
    // View all images set poster to null
    // Need event owner Id
    // Constructor same for user and event image
    // Array<Event>
    // arraylist = allimagelist

    /**
     * get list of event images so that admin can monitor and delete event images
     * @return
     */
    private ArrayList<Event> adminGetAllImages() {
        ArrayList<Event> imageAdminList = new ArrayList<>();
        // Get collection of ExploreEvents and Users
        final CollectionReference exploreEventRef = db.collection("ExploreEvents");
        final CollectionReference usersRef = db.collection("users");

        // Loop through all ExploreEvent documents
        exploreEventRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    return;
                }

                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                    if (doc.exists()) {
                        String eventPoster = (String) doc.getData().get("eventPoster");
                        String eventID = (String) doc.getData().get("eventid");
                        Event event = new Event(eventPoster, "", eventID);

                        if ((!(Objects.equals(eventPoster, "") || Objects.equals(eventPoster, null)))) {
                            imageAdminList.add(event);
                        }
                    }
                }
            }
        });

        // Loop through all user documents
        usersRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    return;
                }

                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                    if (doc.exists()) {
                        String userId = (String) doc.getData().get("deviceId");
                        String userImage = (String) doc.getData().get("userProfileImage");
                        Event event = new Event(userImage, userId, "");

                        if ((!(Objects.equals(userImage, "") || Objects.equals(userImage, null)))) {
                            imageAdminList.add(event);
                        }
                    }
                }

            }
        });
        return imageAdminList;
    }

//    private void HeadsUpNotify(String id) {
//        final CollectionReference NotifyListRef = db.collection("Notify" + id);
//
//        NotifyListRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException e) {
//                if (e != null) {
//                    return;
//                }
//
//                for (DocumentChange dc : snapshots.getDocumentChanges()) {
//                    if(dc.getType() == DocumentChange.Type.ADDED) {
//
//                            Toast.makeText(MainActivity.this, dc.getDocument().getString("note"), Toast.LENGTH_SHORT).show();
//
//
//                            Uri new_defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//                            NotificationCompat.Builder new_builder = new NotificationCompat.Builder(MainActivity.this, "My Notification");
//                            new_builder.setContentTitle("New Report added!");
//                            new_builder.setContentText(dc.getDocument().getString("note"));
//                            new_builder.setSmallIcon(R.drawable.charliekimimage);
//                            new_builder.setSound(new_defaultSoundUri);
//                            new_builder.setAutoCancel(true);
//
//
//                            NotificationManager notificationManager = getSystemService(NotificationManager.class);
//                            if(!(ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED)){
//                                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
//                                    activityResultLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
//                                }
//
//                            } else {
//                                    CharSequence name = getString(R.string.app_name);
//                                    String description = "Example Notification";
//                                    int importance = NotificationManager.IMPORTANCE_DEFAULT;
//                                NotificationChannel channel = null;
//                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                                    channel = new NotificationChannel("test", name, importance);
//                                }
//                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                                    channel.setDescription(description);
//                                }
//                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                                    notificationManager.createNotificationChannel(channel);
//                                }
//
//                                notificationManager.notify(10, new_builder.build());
//
//                            }
//                        }
//                    }
//                }
//
//
//
//
//        });
//
//
//
//    }







}
