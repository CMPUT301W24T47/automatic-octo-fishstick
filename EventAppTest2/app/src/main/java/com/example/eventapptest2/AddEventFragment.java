package com.example.eventapptest2;


        import android.app.AlertDialog;
        import android.app.Dialog;
        import android.content.Context;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.media.Image;
        import android.net.Uri;
        import android.os.Bundle;
        import android.provider.MediaStore;
        import android.text.Editable;
        import android.text.TextUtils;
        import android.text.TextWatcher;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ImageButton;
        import android.widget.ImageView;
        import android.widget.Switch;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.google.firebase.firestore.CollectionReference;
        import com.google.firebase.firestore.EventListener;
        import com.google.firebase.firestore.FirebaseFirestore;
        import com.google.firebase.firestore.FirebaseFirestoreException;
        import com.google.firebase.firestore.QueryDocumentSnapshot;
        import com.google.firebase.firestore.QuerySnapshot;

        import androidx.activity.result.ActivityResult;
        import androidx.activity.result.ActivityResultCallback;
        import androidx.activity.result.ActivityResultLauncher;
        import androidx.activity.result.contract.ActivityResultContracts;
        import androidx.annotation.NonNull;
        import androidx.annotation.Nullable;
        import androidx.fragment.app.DialogFragment;

public class AddEventFragment extends DialogFragment {

    interface AddEventDialogListener {
        void addEvent(Event event);
    }
    private AddEventDialogListener listener;

    // Firebase
    private FirebaseFirestore db;
    private CollectionReference eventColl;

    ActivityResultLauncher<Intent> photoLauncher;

    // Fragment shown to add an event to the list
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_event, null);
    ImageView addImage = view.findViewById(R.id.AddEventImage);


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_event, null);
        // connecting the editText to the xml layout
        EditText addEventName = view.findViewById(R.id.EventNameEdit);
        EditText addLocation = view.findViewById(R.id.add_location);
        EditText addAttnLimit = view.findViewById(R.id.add_attendee_limit);
        ImageButton addImageBttn = view.findViewById(R.id.addEventImageButton);
        EditText addEventDate = view.findViewById(R.id.addEventDate);
        EditText addDescription = view.findViewById(R.id.AddEventDescription);



        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        // set fragment buttons to null until clicked (for add and remove)
        final AlertDialog dialog = builder
                .setView(view)
                .setTitle("Add an Event")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Create QR Code", null)
                .create();


        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {

                registerPhoto();
                addImageBttn.setOnClickListener(view -> pickImage());

                Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String EventName = addEventName.getText().toString().trim();
                        String Location = addLocation.getText().toString().trim();
                        String AttnLimit = addAttnLimit.getText().toString().trim();
                        String EventDate = addEventDate.getText().toString().trim();
                        String Description = addDescription.getText().toString().trim();

                        if (EventName.isEmpty()) {
                            addEventName.setError("Please give a name to your event!");
                        }
                        if (Location.isEmpty()) {
                            addLocation.setError("Please enter the location of your event!");
                        }
                        if(EventDate.isEmpty()) {
                            addEventDate.setError("Please specify when your event is happening!");
                        }

                        else {
                            listener.addEvent(new Event(EventName, Location, EventDate, AttnLimit, addImage, Description));
                            dialog.dismiss();
                        }
                    }
                });
            }
        });

        return dialog;
    }

    private void pickImage() {
        Intent intent = new Intent(MediaStore.ACTION_PICK_IMAGES);
        photoLauncher.launch(intent);
    }
    private void registerPhoto() {
        photoLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        try {
                            Uri imageUri = result.getData().getData();
                            addImage.setImageURI(imageUri);
                        }catch (Exception e) {
                            Toast.makeText(requireContext(), "No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }


}
