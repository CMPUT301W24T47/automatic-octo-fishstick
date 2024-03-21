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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {//implements ExploreEventsRecyclerViewAdapter.OnExploreButtonClickListener {

    // Initialize the Explore ArrayList
   // Event addEventFragmentstore = new Event();
    //firebase
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference usersRef = db.collection("users");



    ArrayList<Event> savedEvents= new ArrayList<>();
    ArrayList<Event> createdEvents= new ArrayList<>();
    ArrayList<Event> oldQRList= new ArrayList<>();

    ArrayList<Event> Explore = new ArrayList<>();
    ArrayList<String> notifyList = new ArrayList<>();
    public int inte = 0;




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

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.framelayout, new UserProfileFragment(testuser,fragmentManager,bottomnav));
                fragmentTransaction.commit();



                bottomnav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {



                        int itemId = item.getItemId();
                        if (itemId == R.id.OrganizeEventNav) {
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            //System.out.println("testtttttttttt " + testuser.getCreatedEvents());
                            fragmentTransaction.replace(R.id.framelayout, new OrgainzersEventFragment(createdEvents,fragmentManager,bottomnav,testuser)); //explore is temp
                            fragmentTransaction.commit();
                            bottomnav.getMenu().clear();
                            bottomnav.inflateMenu(R.menu.organizer_nav_menu);
                            bottomnav.postDelayed(() -> bottomnav.setSelectedItemId(R.id.OrginzersEventsnav), 100);
                        } else if (itemId == R.id.UserProfileNav) {
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.framelayout, new UserProfileFragment(testuser,fragmentManager,bottomnav));
                            fragmentTransaction.commit();
                        } else if (itemId == R.id.ExploreEventNav) {
                            getDataExplore(deviceId); //dk why but pervents glitch when the user of deviceid can see there created events in explore

                            FragmentManager fragmentManager = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.framelayout, new ExploreFragment(Explore,savedEvents,deviceId,fragmentManager,bottomnav,testuser));
                            fragmentTransaction.commit();
                        } else if (itemId == R.id.SavedEventNav) {
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.framelayout, new SavedEventsFragment(savedEvents,fragmentManager,bottomnav,testuser)); // prolly will need not get userid of attendee when they sign in for the attendee list maybe explore event if we wanna care abt it ltr
                            fragmentTransaction.commit();
                        } else if (itemId == R.id.OldQrNav) {
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.framelayout, new OldQrsFragments(oldQRList,deviceId,fragmentManager));
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
                            fragmentTransaction.replace(R.id.framelayout, new OrgainzersEventFragment(createdEvents,fragmentManager,bottomnav,testuser));
                            fragmentTransaction.commit();
                        } else if (itemId == R.id.GoBacknav) {
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.framelayout, new UserProfileFragment(testuser,fragmentManager,bottomnav));
                            fragmentTransaction.commit();
                            bottomnav.getMenu().clear();
                            bottomnav.inflateMenu(R.menu.bottom_nav_menu);
                            bottomnav.postDelayed(() -> bottomnav.setSelectedItemId(R.id.UserProfileNav), 100);
                        } else if (itemId == R.id.GoBacknavDets) {
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.framelayout, new ExploreFragment(Explore,savedEvents,deviceId,fragmentManager,bottomnav,testuser));
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
                        }
                        else if (itemId == R.id.EventMapNav) {
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.framelayout, new MapsFragment());
                            fragmentTransaction.commit();
                        }
                        else if (itemId == R.id.EventAttendeesNav) {
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
                        }
                        else if (itemId == R.id.EventNavGoback) {
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            //System.out.println("testtttttttttt " + testuser.getCreatedEvents());
                            fragmentTransaction.replace(R.id.framelayout, new OrgainzersEventFragment(createdEvents,fragmentManager,bottomnav,testuser)); //explore is temp
                            fragmentTransaction.commit();
                            bottomnav.getMenu().clear();
                            bottomnav.inflateMenu(R.menu.organizer_nav_menu);
                            bottomnav.postDelayed(() -> bottomnav.setSelectedItemId(R.id.OrginzersEventsnav), 100);
                        }
                        else if (itemId == R.id.EventDetailsNav) {
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            //System.out.println("testtttttttttt " + testuser.getCreatedEvents());
                            fragmentTransaction.replace(R.id.framelayout, new OrganizeEventDetsFragment(createdEvents.get(testuser.getLastsaved()),testuser)); //explore is temp
                            fragmentTransaction.commit();
                        }



                        else if (itemId == R.id.AttendeeNotifications) {
                          // getNotifyList(savedEvents.get(testuser.getLastsaved()).getEventid());//takes eventid



                            final CollectionReference NotifyListRef = db.collection("Notify" + savedEvents.get(testuser.getLastsaved()).getEventid());
                            notifyList.clear();
                            NotifyListRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
                                @Override
                                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
                                    if (error != null) {
                                        return ;
                                    }

                                    // savedEvents.clear(); // Clear the old list
                                    for(QueryDocumentSnapshot doc: queryDocumentSnapshots) {
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
                            fragmentTransaction.replace(R.id.framelayout, new AttendeeNotifyFragment(notifyList,savedEvents.get(testuser.getLastsaved()).getEventPoster())); //explore is temp
                            fragmentTransaction.commit();



                                }
                            });


                        }



                        else if (itemId == R.id.AttendeeProfile) {
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.framelayout, new UserProfileFragment(testuser,fragmentManager,bottomnav));
                            fragmentTransaction.commit();
                        }


                        else if (itemId == R.id.AttendeeDetails) {
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            //System.out.println("testtttttttttt " + testuser.getCreatedEvents());
                            fragmentTransaction.replace(R.id.framelayout, new ExploreEventDetsFragment(savedEvents.get(testuser.getLastsaved()),fragmentManager,testuser,bottomnav)); //explore is temp
                            fragmentTransaction.commit();
                        }


                        else if (itemId == R.id.AttendeeGoBack) {
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            //System.out.println("testtttttttttt " + testuser.getCreatedEvents());
                            fragmentTransaction.replace(R.id.framelayout, new SavedEventsFragment(savedEvents,fragmentManager,bottomnav,testuser)); //explore is temp
                            fragmentTransaction.commit();
                            bottomnav.getMenu().clear();
                            bottomnav.inflateMenu(R.menu.bottom_nav_menu);
                            bottomnav.postDelayed(() -> bottomnav.setSelectedItemId(R.id.SavedEventNav), 100);
                        }

                        else if (itemId == R.id.AdminGoBack) {
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            //System.out.println("testtttttttttt " + testuser.getCreatedEvents());
                            fragmentTransaction.replace(R.id.framelayout, new SavedEventsFragment(savedEvents,fragmentManager,bottomnav,testuser)); //explore is temp
                            fragmentTransaction.commit();
                            bottomnav.getMenu().clear();
                            bottomnav.inflateMenu(R.menu.bottom_nav_menu);
                            bottomnav.postDelayed(() -> bottomnav.setSelectedItemId(R.id.SavedEventNav), 100);
                        }


                        else if (itemId == R.id.AdminImages) { ///easyish
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            //System.out.println("testtttttttttt " + testuser.getCreatedEvents());
                            fragmentTransaction.replace(R.id.framelayout, new AdminImageFragment(createdEvents)); // the passed list is temporary
                            fragmentTransaction.commit();
                        }


                        else if (itemId == R.id.AdminEvents) { //  hard              // should take in a list of event and user
                            //delte the events creators event easy // delete all users saved events if event in their saved event hard// delete event attendee list easy
                            //delete event notfications as well easy
                            // will also need to check if event is old then delete the event from the event owners old qr list
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            //System.out.println("testtttttttttt " + testuser.getCreatedEvents());
                            fragmentTransaction.replace(R.id.framelayout, new AdminEventsFragment(createdEvents)); //explore is temp
                            fragmentTransaction.commit();
                        }

                        else if (itemId == R.id.AdminProfiles) { //the most easy casue we never delete a profile realisitcly just set everything to "" except the id
                            ArrayList<User> sad = new ArrayList<>();


                            sad.add(testuser);




                            FragmentManager fragmentManager = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            //System.out.println("testtttttttttt " + testuser.getCreatedEvents());
                            fragmentTransaction.replace(R.id.framelayout, new AdminProfileFragment(sad)); //this is temporary takes an array of users
                            fragmentTransaction.commit();
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
    private Task<User> getUser(String deviceId) {
        DocumentReference docRef = usersRef.document(deviceId);
        return docRef.get().continueWith(task -> {
            DocumentSnapshot doc = task.getResult();
            User user = new User(deviceId,"","","","","", savedEvents,  createdEvents,  oldQRList,""); // Create empty user with device ID
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

    private void getOldQrList(String id){
        final CollectionReference SavedeventsRef = db.collection("OldQrsList" + id);

        SavedeventsRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    return;
                }

                oldQRList.clear(); // Clear the old list
                for(QueryDocumentSnapshot doc: queryDocumentSnapshots) {
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




                        oldQRList.add(new Event(EventId, eventName, eventLocation, eventDate, eventLimit, eventImage, Eventdes, attendelist, checkinlist,qrlur,qrelur,owner,track));
                    }// Assuming "karan" is a placeholder for organizer
                }


            }
        });
    }






    private void getDataSave(String id){
        // create event notfications are only made there so no list needed b/c they will only update notfication list of event like attendeelist
        // this means saved data stores a notfication list



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
                        String ExEid = (String) doc.getId();
                        final CollectionReference eventsRef = db.collection("ExploreEvents");
                        //we get from explore so tracking can update,
                        eventsRef.document(ExEid).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                            @Override
                            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
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
                                if(!isDateBeforeCurrentDate(eventDate)){
                                    savedEvents.add(new Event(EventId, eventName, eventLocation, eventDate, eventLimit, eventImage, Eventdes, attendelist, checkinlist,qrlur,qrelur,owner,track));}
                                else{//remove event from explore events fb do the same with saved events fb remove from created fb and add to oldqrfb
                                    SavedeventsRef.document(EventId).delete();
                                }
                            }
                        });



                    }// Assuming "karan" is a placeholder for organizer
                }


            }
        });
    }
    private void getDataCreate(String id){
         final CollectionReference createeventsRef = db.collection("CreateEvents" + id);
        final CollectionReference OldQreventsRef = db.collection("OldQrsList" + id);

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
                        String EventId = (String) doc.getData().get("eventid");
                        String Eventdes = (String) doc.getData().get("eventDesription");
                        ArrayList<User> attendelist = getAttendeList(EventId);
                        ArrayList<User> checkinlist = (ArrayList<User>) doc.getData().get("Checkin-list"); //not needed anymore
                        String eventImage = (String) doc.getData().get("eventPoster");
                        String qrlur = (String) doc.getData().get("qrUrl");
                        String owner = (String) doc.getData().get("owner");
                        String track = (String) doc.getData().get("tracking");
                        //System.out.println("Image URL: " + eventImage);
                        // ... (add other event properties based on your Event class)
                        String qrelur = (String) doc.getData().get("signINQR");
                        if(!isDateBeforeCurrentDate(eventDate)){
                        createdEvents.add(new Event(EventId, eventName, eventLocation, eventDate, eventLimit, eventImage, Eventdes, attendelist, checkinlist,qrlur,qrelur,owner,track));}
                        else{//remove event from explore events fb do the same with saved events fb remove from created fb and add to oldqrfb
                            final CollectionReference AttendeListRef = db.collection("AttendeeList" + EventId);
                            createeventsRef.document(EventId).delete();
                            for (User attende : attendelist){
                                AttendeListRef.document(attende.getDeviceId()).delete();
                            }
                            attendelist = new ArrayList<User>();
                            OldQreventsRef.document(EventId).set(new Event(EventId, eventName, eventLocation, eventDate, eventLimit, eventImage, Eventdes, attendelist, checkinlist,qrlur,qrelur,owner,track));

                            // clear the attendee list





                        }


                    }// Assuming "karan" is a placeholder for organizer
                }


            }
        });
    }

    private void getDataExplore(String id){
        // when a event is old we save the attendees for next time so ateendes already signed up



         final CollectionReference eventsRef = db.collection("ExploreEvents");

        eventsRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    return;
                }

                Explore.clear(); // Clear the old list
                for(QueryDocumentSnapshot doc: queryDocumentSnapshots) {
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
                    Event test = new Event(EventId,eventName, eventLocation, eventDate,eventLimit, eventImage, Eventdes,attendelist,checkinlist,qrlur,qrelur,owner,track);
                    ArrayList<User> attendeliste = getAttendeList(EventId);
                    //if (EventId != test.getEventid()){
                    int lists = attendeliste.size();

                    try {
                        int limit = Integer.parseInt(eventLimit);

                        if (!isDateBeforeCurrentDate(eventDate)) { //checks if date is current or greater
                            if (lists < limit) { //checks if event limit was reached
                                if (!eventIdExists(savedEvents, createdEvents, EventId)) { // this checks if in saved or created
                                    Explore.add(test);
                                }else{}
                            }else{}
                        } else {
                            //remove event from explore events fb do the same with saved events fb remove from created fb and add to oldqrfb
                            eventsRef.document(EventId).delete();
                        }
                    } catch (NumberFormatException e) {
                        // Handle the case where eventLimit is not a valid integer
                        // For now, you might just want to skip the if-else block
                        // You can add logging or any other action as needed
                        if (!eventIdExists(savedEvents, createdEvents, EventId)) { // this checks if in saved or created
                            Explore.add(test);
                        }
                    }


                }


            }
        });
    }
    private ArrayList<User> getAttendeList(String id){ //takes event id
        /// this should instead fetch the user from the users collection so attendee updates if the user changes images or whatever




        final CollectionReference AttendeListRef = db.collection("AttendeeList" + id); // maybe this still need we could get all device id from the collections documents
        ArrayList<User> attendeeList = new ArrayList<>();
        AttendeListRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    return ;
                }

               // savedEvents.clear(); // Clear the old list
                for(QueryDocumentSnapshot doc: queryDocumentSnapshots) {
//                    String eventId = doc.getId();
                    if (doc.exists()) {
                        String attendeeid = doc.getId();
                        final CollectionReference userref = db.collection("users");
                        //get user from user collection if any changes happened to user
                        userref.document(attendeeid).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                            @Override
                            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                                String UserName = (String) value.get("userName");
                                String UserImage = (String) value.getData().get("userProfileImage");
                                String checkin = (String) doc.getData().get("CheckInCount"); // the doc contains the count
                                attendeeList.add(new User(UserName,UserImage,checkin));

                            }
                        });
                    }// Assuming "karan" is a placeholder for organizer
                }

            }
        });
        return attendeeList;
    }
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
    public static boolean isDateBeforeCurrentDate(String dateString) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();

        try {
            Date date = simpleDateFormat.parse(dateString);
            return date.before(currentDate);
        } catch (ParseException e) {
            e.printStackTrace(); // Handle parsing exception appropriately
            // You might want to return false here since parsing failed
            return false;
        }
    }
    private void getNotifyList(String id){ //takes event id
        final CollectionReference NotifyListRef = db.collection("Notify" + id);

        NotifyListRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    return ;
                }

                // savedEvents.clear(); // Clear the old list
                for(QueryDocumentSnapshot doc: queryDocumentSnapshots) {
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








}
