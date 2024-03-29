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

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.UUID;

import io.grpc.Context;

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

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // need a loading screen so app dont crash cause things take time to store in db


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


                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                try {
                    BitMatrix bitMatrix = multiFormatWriter.encode("Karan", BarcodeFormat.QR_CODE,300,300);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] data = baos.toByteArray();
                    storageRefQR.putBytes(data);


                    String urlqr = storageRefQR.getName();

                    BitMatrix bitMatrixi = multiFormatWriter.encode("Karan", BarcodeFormat.QR_CODE,300,300);
                    BarcodeEncoder barcodeEncoderi = new BarcodeEncoder();
                    Bitmap bitmapi = barcodeEncoderi.createBitmap(bitMatrixi);
                    ByteArrayOutputStream baosi = new ByteArrayOutputStream();
                    bitmapi.compress(Bitmap.CompressFormat.JPEG, 100, baosi);
                    byte[] datai = baosi.toByteArray();
                    storageRefQR.putBytes(datai);


                    String secondqr = storageRefQR.getPath();


                    if (selectedImageUri != null) {

                        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("event_images/" + UUID.randomUUID().toString());
                        storageRef.putFile(selectedImageUri)
                                .addOnSuccessListener(taskSnapshot -> {
                                    // Image uploaded successfully, get download URL
                                    storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                                        // this is adding imageURL to the databse --------------------------
                                        String imageURL = uri.toString();
                                        //////////////just a test
                                        Event newevent = new Event(EditUser.getDeviceId(), name, eloc, datee, lim, imageURL, desce, addatendeelist, checkedinlist,urlqr,secondqr);
                                        //lists
                                        // exploreEvents.add(newevent);
                                        //CreateEvents.add(newevent);


                                        eventfb.add(newevent); /////////firebase
                                        //^^get the hash of this and then make it the created events hash



                                        eventcreated.add(newevent);


//                                    ArrayList<Event> userevent = EditUser.getCreatedEvents();
//                                    userevent.add(newevent);
//                                    EditUser.setCreatedEvents(userevent);
//                                    useresfb.document(EditUser.getDeviceId()).set(EditUser);

                                        // Store imageURL along with other event details in Firestore
                                        // Example: firestore.collection("events").document(eventId).update("eventPoster", imageURL);
                                    });
                                });
                    }
                    else {
                        Event newevent = new Event(EditUser.getDeviceId(), name, eloc, datee, lim, null, desce, addatendeelist, checkedinlist,urlqr,secondqr);
                        exploreEvents.add(newevent);
                        eventfb.add(newevent); /////////firebase
                        eventcreated.add(newevent);
                    }

                    //   imageView.setImageBitmap(bitmap); //storing bitmap

                }catch (WriterException e){
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
                        .load("https://firebasestorage.googleapis.com/v0/b/charlie-kim-fans.appspot.com/o/event_images%2Fjujutsu-kaisen-4k-geto-suguru-6i4yc80acocfj7go.jpg?alt=media&token=d5390dbc-f352-47ac-aade-950b1cee5466")
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

            }
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
