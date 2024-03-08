package com.example.eventapptest2;


import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.google.firebase.firestore.FirebaseFirestore;



/**
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
                if (result.getResultCode() == RESULT_OK && result.getData()!= null){
                    Uri picture = result.getData().getData();

                    assert picture != null;
                    user.setUserProfileImage(picture.toString());
                    profilePic.setImageURI(picture);

                    profilePic.setImageURI(Uri.parse(user.getUserProfileImage()));
                    /*updates image user uses in firebase*/
                    textOnProfilePic.setText(null);
                    usersRef.document(user.getUserName()).set(user);

                }
            }
    );

    private final ActivityResultLauncher<Intent> editProfileLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result ->{
                if (result.getResultCode() == getActivity().RESULT_OK && result.getData() != null){
                    String updatedUserName = result.getData().getStringExtra("updatedUserName");
                    String updatedUserHomepage = result.getData().getStringExtra("updatedUserHomepage");
                    String updatedUserEmail = result.getData().getStringExtra("updatedUserEmail");
                    String updatedUserPhoneNum = result.getData().getStringExtra("updatedUserPhoneNum");

                    userNameTextView.setText(updatedUserName);
                    userHomePageTextView.setText(updatedUserHomepage);
                    userEmailTextView.setText(updatedUserEmail);
                    userPhoneNumTextView.setText(updatedUserPhoneNum);
                }
            }
    );

    public UserProfileFragment() {
        // Required empty public constructor
    }

    public User CreateUser(){
        User user = new User("abdimare25", "thatdudeinblue","aamare@gmail.com"
                ,"7807088263", null, null, null,null);
        return user;
    }


    @SuppressLint("WrongViewCast")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_user_profile, container, false);
        user = CreateUser();

        // delete images
        profilePic = v.findViewById(R.id.userImage);
        textOnProfilePic = v.findViewById(R.id.textOnImage);
        Button replaceButton = v.findViewById(R.id.replaceImageButton);
        Button deleteButton = v.findViewById(R.id.deleteImageButton);

        Drawable originalPic = profilePic.getDrawable();
        setImageInitially(originalPic);
        usersRef.document(user.getUserName()).set(user);
//        textOnProfilePic.setText(user.getUserName());
//        user.setUserProfileImage(originalPic.toString());
//        usersRef.document(user.getUserProfileImage());
//

        //Bitmap generatePic = createBitmapFromView(fullLayout);
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
                setImageInitially(originalPic);
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
        userNameTextView.setText(user.getUserName());
        userHomePageTextView.setText(user.getUserHomepage());
        userEmailTextView.setText(user.getUserEmail());
        userPhoneNumTextView.setText(user.getUserPhoneNumber());


        editProfileButton.setOnClickListener(v1 -> {
                // Get text of user information
                String userName = userNameTextView.getText().toString();
                String userHomepage = userHomePageTextView.getText().toString();
                String userEmail = userEmailTextView.getText().toString();
                String userPhoneNum = userPhoneNumTextView.getText().toString();
                // Carry the current information of the user and show it on the EdutUserProfile Activity
                Intent intent = new Intent(getActivity(), EditUserProfileActivity.class);
                intent.putExtra("userName", userName);
                intent.putExtra("homepage", userHomepage);
                intent.putExtra("email", userEmail);
                intent.putExtra("phoneNum", userPhoneNum);
                editProfileLauncher.launch(intent);
        });
        return v;
    }


    // Testing profile information


    public void setImageInitially(Drawable originalPic){
        profilePic.setImageDrawable(originalPic);
//        Drawable originalPic = profilePic.getDrawable();
        textOnProfilePic.setText(user.getUserName());
        user.setUserProfileImage(originalPic.toString());
        usersRef.document(user.getUserProfileImage());
        usersRef.document(user.getUserName()).set(user);
//        user.setUserProfileImage(null);
//        Bitmap generatePic = createBitmapFromView(fullLayout);
//        textOnProfilePic.setText(user.getUserName());
//        profilePic.setImageBitmap(generatePic);
    }


}