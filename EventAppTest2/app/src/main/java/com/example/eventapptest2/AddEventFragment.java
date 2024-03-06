package com.example.eventapptest2;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;

import static android.app.Activity.RESULT_OK;

import java.util.ArrayList;

public class AddEventFragment extends DialogFragment {

    private static final int PICK_IMAGE_REQUEST = 1;
    private ArrayList<Event> exploreEvents;
    private Uri selectedImageUri;
    private User EditUser;

    public AddEventFragment(ArrayList<Event> exploreEvents, User user) {
        this.exploreEvents = exploreEvents;
        this.EditUser = user;
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
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // firebase and Eventlist update for user



                //user.orginzedevents.add(event)

                // sepereatly update explore event it is a global event make a firebase collection explore events

                // add a list in Events called Array<(User,Boolean Chekin Status)> attendelist; -- just a side note not for code

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
