package com.example.eventapptest2;


import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;

import android.app.AlertDialog;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


/**
 * This class updates and shows Users profile when app opens
 * A simple {@link Fragment} subclass.
 * Use the {@link
 * UserProfileFragment#
 * newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserProfileFragment extends Fragment{
    private ImageView profilePic;
    private TextView textOnProfilePic;
    private User user;
    private Button editProfileButton;
    private TextView userNameTextView;
    private EditText userHomePageTextView;
    private EditText userEmailTextView;
    private EditText userPhoneNumTextView;
    // Geolocation switch
    private Switch geolocation;

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    // CollectionReference will store the collection of Users
    //  - Which then each user will store different information
    private final CollectionReference usersRef = db.collection("users");

    private final ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData()!= null){
                    Uri picture = result.getData().getData();

                    //getting successful image
                    assert picture != null;
                    StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("profile_images/" + UUID.randomUUID().toString());
                    storageRef.putFile(picture) .addOnSuccessListener(taskSnapshot -> {
                        // Image uploaded successfully, get download URL
                        storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                            //  adding imageURL to the db-
                            String imageURL = uri.toString();
                        });
                    });

                    profilePic.setVisibility(View.VISIBLE);
                    user.setUserProfileImage(picture.toString());
                    profilePic.setImageURI(picture);


                    //profilePic.setImageURI(Uri.parse(user.getUserProfileImage()));
                    /*updates image user uses in firebase*/
                    textOnProfilePic.setText(null);
                    usersRef.document(user.getUserName()).set(user);

                }

            });

    /**
     * Handles the returned EditUserProfileActivity after the user has edited thier profile
     */
    // Declares instance of the ActivityResultLauncher
    // Handles the result of starting the EditUserProfileActivity
    // RegisterForActivity
    //  - Register activity launcher for starting current activity and for receiving updated information from the EditUserProfileClass activity
    // if condition
    //  - checks if result is 'RESULT_OK' (if its successful) and the data returned is not null
    //  - if result is successful and EditProfileActivity contains data (not null) the UserProfile retrieves
    //  updated information sent back from the EditUserProfileAcitivty
    private final ActivityResultLauncher<Intent> editProfileLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result ->{
                if (result.getResultCode() == getActivity().RESULT_OK && result.getData() != null){
                    // Get updated User information from EditUserProfileActivity
                    //  - Keys from the EditUserProfileActivity to retrieve the updated information
                    String updatedUserName = result.getData().getStringExtra("updatedUserName");
                    String updatedUserHomepage = result.getData().getStringExtra("updatedUserHomepage");
                    String updatedUserEmail = result.getData().getStringExtra("updatedUserEmail");
                    String updatedUserPhoneNum = result.getData().getStringExtra("updatedUserPhoneNum");

                    // Sets the updated User information and shows on the user profile screen
                    userNameTextView.setText(updatedUserName);
                    userHomePageTextView.setText(updatedUserHomepage);
                    userEmailTextView.setText(updatedUserEmail);
                    userPhoneNumTextView.setText(updatedUserPhoneNum);

                    DocumentReference userDocRef = usersRef.document(user.getUserName());
                    Map<String, Object> updatedUserInfo = new HashMap<>();
                    updatedUserInfo.put("userName", updatedUserName);
                    updatedUserInfo.put("userHomepage", updatedUserHomepage);
                    updatedUserInfo.put("userEmail", updatedUserEmail);
                    updatedUserInfo.put("userPhoneNumber", updatedUserPhoneNum);

                    userDocRef.update(updatedUserInfo)
                            .addOnSuccessListener(aVoid -> Log.d(TAG, "User information updated successfully"))
                            .addOnFailureListener(e -> Log.e(TAG, "Error updating user information", e));
                }
            }
    );

    public UserProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Testing purposes
     * @return
     */
    public User CreateUser(){
        User user = new User("abdimare25", "thatdudeinblue","aamare@gmail.com"
                ,"7807088263", null, null, null,null);
        return user;
    }

    /**
     * Shows the users information
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
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_user_profile, container, false);
        user = CreateUser();

        profilePic = v.findViewById(R.id.userImage);
        textOnProfilePic = v.findViewById(R.id.textOnImage);
        Button replaceButton = v.findViewById(R.id.replaceImageButton);
        Button deleteButton = v.findViewById(R.id.deleteImageButton);

        Drawable originalPic = profilePic.getDrawable();
        generateImage(originalPic);

        usersRef.document(user.getUserName()).set(user);
//
        deleteButton.setOnClickListener(v1 -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Do you want to delete?");
            builder.setTitle("Alert");
            builder.setCancelable(false);

            builder.setPositiveButton("Yes", (dialog, which)-> {

//                  user.setUserProfileImage(originalPic.toString());
//                    profilePic.setImageDrawable(originalPic);
                //needed to delete image
                //usersRef.document(user.setUserProfileImage())
                setImageDel(originalPic);
                //usersRef.document(user.getUserName()).set(user);
                dialog.dismiss();
            });

            builder.setNegativeButton("No", (dialog, which)-> dialog.cancel());
            AlertDialog alert = builder.create();
            alert.show();
        });
        // replace images
        replaceButton.setOnClickListener(v12 -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Do you want to replace?");
            builder.setTitle("Alert");
            builder.setCancelable(false);

            builder.setPositiveButton("Yes", (dialog, which)-> {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                launcher.launch(intent);
                dialog.dismiss();
            });

            builder.setNegativeButton("No", (dialog, which)-> dialog.cancel());
            AlertDialog alert = builder.create();
            alert.show();
        });


        // Edit users profile
        editProfileButton = v.findViewById(R.id.editUsernameButton);
        // Inputed user information
        userNameTextView = v.findViewById(R.id.usernameTextViewProfile);
        userHomePageTextView = v.findViewById(R.id.homepageEditText);
        userEmailTextView = v.findViewById(R.id.emailEditText);
        userPhoneNumTextView = v.findViewById(R.id.phoneEditText);

        // Store user information
        // Testing profile information
        // Set the text of all user information
        // Sets the and shows the UserProfile information
        userNameTextView.setText(user.getUserName());
        userHomePageTextView.setText(user.getUserHomepage());
        userEmailTextView.setText(user.getUserEmail());
        userPhoneNumTextView.setText(user.getUserPhoneNumber());


        // Handles what occurs when the edit Button is clicked
        editProfileButton.setOnClickListener(v1 -> {
                // Get text of user information so we can pass it to the EditProfileActivity
                String userName = userNameTextView.getText().toString();
                String userHomepage = userHomePageTextView.getText().toString();
                String userEmail = userEmailTextView.getText().toString();
                String userPhoneNum = userPhoneNumTextView.getText().toString();

                // Intent created to show the new screen of the EditUserProfile
                // Go from current activity to EditProfileActivity
                Intent intent = new Intent(getActivity(), EditUserProfileActivity.class);

                // Carry the current information of the user and show it on the EditUserProfile Activity
                // Unique keys so new activity can retrieve it and get that information
                intent.putExtra("userName", userName);
                intent.putExtra("homepage", userHomepage);
                intent.putExtra("email", userEmail);
                intent.putExtra("phoneNum", userPhoneNum);

                // Used to launch the EditUserProfileActivity with the intent containing the current user information
                editProfileLauncher.launch(intent);

        });
        return v;
    }

    public void generateImage(Drawable originalPic){
        if((user.getUserName() != null) && (user.getUserProfileImage()==null)){
            textOnProfilePic.setText(user.getUserName());
            profilePic.setVisibility(View.INVISIBLE);
        }
        if((user.getUserName() == null) && (user.getUserProfileImage()==null)){
            profilePic.setImageDrawable(originalPic);
        }

    }


    public void setImageDel(Drawable originalPic){
        profilePic.setVisibility(View.INVISIBLE);
        textOnProfilePic.setText(user.getUserName());
        profilePic.setImageDrawable(originalPic);

    }

}