package com.example.eventapptest2;
import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddEventFragment extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 1;
    ImageButton addImageBtn;
    private ImageView eventImageView;
    private EditText eventNameEditText;
    private EditText eventLocationEditText;
    private EditText eventDateEditText;
    private EditText eventLimitEditText;
    private EditText eventDetailsEditText;

    private Uri imageUri;

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference eventsRef = db.collection("events");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_event, container, false);

        addImageBtn = view.findViewById(R.id.addEventImageButton);
        eventImageView = view.findViewById(R.id.AddEventImage);
        eventNameEditText = view.findViewById(R.id.DescriptionText);
        eventLocationEditText = view.findViewById(R.id.add_location);
        eventDateEditText = view.findViewById(R.id.addEventDate);
        eventLimitEditText = view.findViewById(R.id.add_attendee_limit);
        eventDetailsEditText = view.findViewById(R.id.AddEventDescription);

        addImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });

        Button addButton = view.findViewById(R.id.addeventbutton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEvent();
                clearFields();
            }
        });

        return view;
    }

    private void addEvent() {
        String eventName = eventNameEditText.getText().toString().trim();
        String eventLocation = eventLocationEditText.getText().toString().trim();
        String eventDate = eventDateEditText.getText().toString().trim();
        int eventLimit = Integer.parseInt(eventLimitEditText.getText().toString().trim());


        if (eventLimitEditText.getText().toString().trim().isEmpty()){
            eventLimit = -1;

        }




        String eventDetails = eventDetailsEditText.getText().toString().trim();

        Map<String, Object> event = new HashMap<>();
        event.put("eventName", eventName);
        event.put("eventLocation", eventLocation);
        event.put("eventDate", eventDate);
        event.put("eventLimit", eventLimit);
        event.put("eventDetails", eventDetails);

        // Add image URI to the event data if available
        if (imageUri != null) {
            event.put("eventImage", imageUri.toString());
        }

        eventsRef.document(eventName)
                .set(event)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Success message or further action
                        Log.d(TAG, "Event added successfully");
                        // Show toast or navigate to another screen
                        Toast.makeText(getContext(), "Event added successfully", Toast.LENGTH_SHORT).show();
                        clearFields(); // Clear fields after successful addition
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Failure message or handling
                        Log.e(TAG, "Failed to add event", e);
                        // Show toast or display error message
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

<<<<<<< Updated upstream
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_event, null);
=======

>>>>>>> Stashed changes



    private void clearFields() {
        eventNameEditText.getText().clear();
        eventLocationEditText.getText().clear();
        eventDateEditText.getText().clear();
        eventLimitEditText.getText().clear();
        eventDetailsEditText.getText().clear();
        eventImageView.setImageResource(0);
        imageUri = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            eventImageView.setImageURI(imageUri);
        }
    }
}
