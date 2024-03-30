package com.example.eventapptest2;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.Date;
import java.util.Calendar;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import io.grpc.Context;
import io.grpc.Internal;

public class AddEventFragment extends DialogFragment {

    private static final int PICK_IMAGE_REQUEST = 1;
    private ArrayList<Event> exploreEvents;
    private Uri selectedImageUri;
    private User EditUser;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference useresfb = db.collection("users");
    private final CollectionReference eventfb = db.collection("ExploreEvents");
    private ArrayList<Event> CreateEvents;

    private CollectionReference eventcreated;
    //public String urlqr;
    public AddEventFragment(ArrayList<Event> exploreEvents, User user, String create,ArrayList<Event> createEvents){//CollectionReference eventref, CollectionReference usersref) {
        this.exploreEvents = exploreEvents;
        this.EditUser = user;
        this.eventcreated = db.collection("CreateEvents" + create);
        this.CreateEvents = createEvents;
//        this.useresfb = usersref;
//        this.eventfb = eventref;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_event, container, false);

        ImageButton addEventImageButton = view.findViewById(R.id.addEventImageButton);
        addEventImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });


        Button confirmButton = view.findViewById(R.id.AddEventconfirmButton);
        EditText eventName = view.findViewById(R.id.DescriptionText);
        EditText location = view.findViewById(R.id.add_location);
        EditText limit = view.findViewById(R.id.add_attendee_limit);
        EditText date = view.findViewById(R.id.addEventDate);
        EditText desc = view.findViewById(R.id.AddEventDescription);
        ImageView addEventImage = view.findViewById(R.id.AddEventImage);

        date.addTextChangedListener(new TextWatcher() {
            private String current = "";
            private String ddmmyyyy = "DDMMYYYY";
            private Calendar cal = Calendar.getInstance();


            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //        https://techprogrammingideas.blogspot.com/2020/05/android-edit-text-to-show-dd-mm-yyyy.html
                if (!s.toString().equals(current)) {
                    String clean = s.toString().replaceAll("[^\\d.]", "");
                    String cleanC = current.replaceAll("[^\\d.]", "");

                    int cl = clean.length();
                    int sel = cl;
                    for (int i = 2; i <= cl && i < 6; i += 2) {
                        sel++;
                    }
                    //Fix for pressing delete next to a forward slash
                    if (clean.equals(cleanC)) sel--;

                    if (clean.length() < 8) {
                        clean = clean + ddmmyyyy.substring(clean.length());
                    } else {
                        //This part makes sure that when we finish entering numbers
                        //the date is correct, fixing it otherwise
                        int day = Integer.parseInt(clean.substring(0, 2));
                        int mon = Integer.parseInt(clean.substring(2, 4));
                        int year = Integer.parseInt(clean.substring(4, 8));

                        if (mon > 12) mon = 12;
                        cal.set(Calendar.MONTH, mon - 1);

                        year = (year < 1900) ? 1900 : (year > 2100) ? 2100 : year;
                        cal.set(Calendar.YEAR, year);
                        // ^ first set year for the line below to work correctly
                        //with leap years - otherwise, date e.g. 29/02/2012
                        //would be automatically corrected to 28/02/2012

                        day = (day > cal.getActualMaximum(Calendar.DATE)) ? cal.getActualMaximum(Calendar.DATE) : day;
                        clean = String.format("%02d%02d%02d", day, mon, year);
                    }

                    clean = String.format("%s/%s/%s", clean.substring(0, 2),
                            clean.substring(2, 4),
                            clean.substring(4, 8));

                    sel = sel < 0 ? 0 : sel;
                    current = clean;
                    date.setText(current);
                    date.setSelection(sel < current.length() ? sel : current.length());


                }
            }


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });



        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // need a loading screen so app dont crash cause things take time to store in db
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Calendar calendar = Calendar.getInstance();
                Date currentDate = calendar.getTime();

                Date date1 = null;
                try {
                    date1 = simpleDateFormat.parse(date.getText().toString());
                    //System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa " + date.getText().toString() + "   >=   " + date1);
                } catch (ParseException e) {
                    e.printStackTrace(); // Handle parsing exception appropriately
                }

                if (date1 != null && date1.compareTo(currentDate) >= 0){

                ArrayList<User> addatendeelist = new ArrayList<>();
                ArrayList<User> checkedinlist = new ArrayList<>();
                String name = eventName.getText().toString();
                String eloc = location.getText().toString();
                String lim = limit.getText().toString();
                String datee = date.getText().toString();
                String desce = desc.getText().toString();
                //Image imagee = addEventImage.get
                //Image
//                final String randomkey = UUID.randomUUID().toString();
//                final StorageReference imageref = FirebaseStorage.getInstance().getReference().child("images/" + randomkey);
//
//
//                imageref.putFile(selectedImageUri)
//                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                            @Override
//                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//
//                            }
//                        });
                // Inside AddEventFragment after selecting an image
                StorageReference storageRefQR = FirebaseStorage.getInstance().getReference().child("QR/" + UUID.randomUUID().toString());
                StorageReference storageRefQR2 = FirebaseStorage.getInstance().getReference().child("QR/" + UUID.randomUUID().toString());

                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                try {


                    //sign in qr
                    BitMatrix bitMatrixi = multiFormatWriter.encode("Karan", BarcodeFormat.QR_CODE, 300, 300);
                    BarcodeEncoder barcodeEncoderi = new BarcodeEncoder();
                    Bitmap bitmapi = barcodeEncoderi.createBitmap(bitMatrixi);
                    ByteArrayOutputStream baosi = new ByteArrayOutputStream();
                    bitmapi.compress(Bitmap.CompressFormat.JPEG, 100, baosi);
                    byte[] datai = baosi.toByteArray();


                    //String urlqr = storageRefQR.getName();
                    storageRefQR.putBytes(datai).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshote) {
                            storageRefQR.getDownloadUrl().addOnSuccessListener(uri -> {
                                String secondqr = uri.toString();

                                int hashe = taskSnapshote.hashCode();
                                String eventid = Integer.toString(hashe);
                                BitMatrix bitMatrix = null;
                                try { // storing the qr that takes u to event detials 
                                    bitMatrix = multiFormatWriter.encode(eventid, BarcodeFormat.QR_CODE, 300, 300);
                                } catch (WriterException e) {
                                    throw new RuntimeException(e);
                                }
                                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                                Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                                byte[] data = baos.toByteArray();

                                storageRefQR2.putBytes(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        storageRefQR2.getDownloadUrl().addOnSuccessListener(urit -> {
                                            String urlqr = urit.toString();
                                            //Log.d(TAG, "aaaaaaaaaaaaaaaaaaaaa"+hashtest);

                                            //String secondqr = testri.toString();


                                            // = storageRefQR.getPath();


                                            if (selectedImageUri != null) {

                                                StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("event_images/" + UUID.randomUUID().toString());
                                                storageRef.putFile(selectedImageUri)
                                                        .addOnSuccessListener(taskSnapshoteeee -> {
                                                            // Image uploaded successfully, get download URL
                                                            storageRef.getDownloadUrl().addOnSuccessListener(uriy -> {
                                                                // this is adding imageURL to the databse --------------------------
                                                                String imageURL = uriy.toString();
                                                                //////////////just a test
//                                        Map<String, Event> eventData = new HashMap<>();
                                                                Event newevent = new Event(eventid, name, eloc, datee, lim, imageURL, desce, addatendeelist, checkedinlist, urlqr, secondqr, EditUser.getDeviceId(),"off");
//                                        eventData.put("event",newevent);
                                                                //lists
                                                                // exploreEvents.add(newevent);
                                                                //CreateEvents.add(newevent);


//                                        int how = eventfb.document().set(newevent).hashCode(); /////////firebase
//                                        //^^get the hash of this and then make it the created events hash
//                                        String howid = Integer.toString(how);
//                                        Event newevente = new Event(howid, name, eloc, datee, lim, imageURL, desce, addatendeelist, checkedinlist,urlqr,secondqr, EditUser.getDeviceId());
                                                                // the unique hash we will use for
                                                                eventfb.document(eventid).set(newevent);
                                                                eventcreated.document(eventid).set(newevent);
                                                                exploreEvents.remove(newevent);

//                                    ArrayList<Event> userevent = EditUser.getCreatedEvents();
//                                    userevent.add(newevent);
//                                    EditUser.setCreatedEvents(userevent);
//                                    useresfb.document(EditUser.getDeviceId()).set(EditUser);

                                                                // Store imageURL along with other event details in Firestore
                                                                // Example: firestore.collection("events").document(eventId).update("eventPoster", imageURL);
                                                            });
                                                        });
                                            } else {
                                                Event newevent = new Event(eventid, name, eloc, datee, lim, null, desce, addatendeelist, checkedinlist, urlqr, secondqr, EditUser.getDeviceId(),"off"); //easier to do null for images
                                                //exploreEvents.add(newevent); --> just faster then db but db clears list so nw, we can add these things for faster UI
                                                eventfb.document(eventid).set(newevent);
                                                eventcreated.document(eventid).set(newevent);
                                                exploreEvents.remove(newevent);

//                        int how = eventfb.document().set(newevent).hashCode(); /////////firebase
//                        //^^get the hash of this and then make it the created events hash
//                        String howid = Integer.toString(how);
//                        Event newevente = new Event(howid, name, eloc, datee, lim, null, desce, addatendeelist, checkedinlist,urlqr,secondqr, EditUser.getDeviceId());
//                        // the unique hash we will use for
//                        eventfb.document(eventid).set(newevente);
//                        eventcreated.document(eventid).set(newevente);
                                            }
                                        });
                                    }
                                });
                            });
                        }
                    });


                    //   imageView.setImageBitmap(bitmap); //storing bitmap

                } catch (WriterException e) {
                    throw new RuntimeException(e);
                }


                ////qr^


                eventName.setText("");
                location.setText("");
                limit.setText("");
                date.setText("");
                desc.setText("");
                //addEventImage.setImageResource();
                Glide.with(addEventImage.getContext())
                        .load("https://firebasestorage.googleapis.com/v0/b/charlie-kim-fans.appspot.com/o/event_images%2Fimage_placeholder.png?alt=media&token=a354681d-7f13-4be8-943f-5b99460661c3")
                        .into(addEventImage);


//
//
//
//
//
//
//                // firebase and Eventlist update for user
//
//
//
//                //user.orginzedevents.add(event)
//
//                // sepereatly update explore event it is a global event make a firebase collection explore events
//
//                // add a list in Events called Array<(User,Boolean Chekin Status)> attendelist; -- just a side note not for code

            }        }
        });


        return view;
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            try {
                // Load the selected image into the ImageView using Glide
                ImageView addEventImage = getView().findViewById(R.id.AddEventImage);
                //Glide.with(requireContext()).load(selectedImageUri).into(addEventImage);
                addEventImage.setImageURI(selectedImageUri);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
