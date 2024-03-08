package com.example.eventapptest2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.Firebase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;


/**
 * This is a class that
 */
public class EditUserProfileActivity extends AppCompatActivity {
    Button backButton;
    Button verifyButton;
    EditText editName;
    EditText editHomePage;
    EditText editEmail;
    EditText editPhoneNum;
    User user;

//    public EditUserProfileActivity(User userJay){
//        this.user = userJay;
//    }

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference usersRef = db.collection("users");





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_profile);
        Bundle bundle = getIntent().getExtras();

        backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Get the User object information from the previous Activity (UserProfileFragment)
//        user = (User) getIntent().getSerializableExtra("user");

        editName = findViewById(R.id.userEditNameText);
        editHomePage = findViewById(R.id.userEditHomepageText);
        editEmail = findViewById(R.id.userEditEmailText);
        editPhoneNum = findViewById(R.id.userEditPhoneNumText);

        // Get each specific user information from the previous screen
        // - getStringExtra(key)
        //  - Takes the unique key information from the previous page to allow to show on the new screen
        String userName = getIntent().getStringExtra("userName");
        editName.setText(userName);
        String userHomepage = getIntent().getStringExtra("homepage");
        editHomePage.setText(userHomepage);

//        String userEmail = getIntent().getStringExtra("email");
//        editEmail.setText(userEmail);
//        String userPhoneNum = getIntent().getStringExtra("phoneNum");
//        editPhoneNum.setText(userPhoneNum);

//        // Karan
//        User updateUser = getIntent().getParcelableExtra("user");
//        editName.setText(updateUser.getUserName());



        // Implement Verify button
        // - Updates the changed user information
        verifyButton = findViewById(R.id.button_verify);
        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                updateUser.setUserName(editName.getText().toString());
                updateUserProfile();

                usersRef.document("abdimare25").set(user);
            }
        });

    }

    private void updateUserProfile() {
        String updatedName = editName.getText().toString();
        String updatedHomePage = editHomePage.getText().toString();
//        String updatedEmail = editEmail.getText().toString();
//        String updatedPhoneNum = editPhoneNum.getText().toString();

        user.setUserName(updatedName);
        user.setUserHomepage(updatedHomePage);
//        user.setUserEmail(updatedEmail);
//        user.getUserPhoneNumber(updatedPhoneNum);

        // Pass updated information back to the UserProfileFragment
        Intent intent = new Intent();
        intent.putExtra("updatedUser", user);
        setResult(Activity.RESULT_OK, intent);

        finish();
    }


    // Update the users profile with the updated inputted information
//    private void updateUserProfile(){
        // Get the in
//        String updatedName = editName.setText().toString();

//        String updatedHomePage = editHomePage.getText().toString();
//        String updatedEmail = editEmail.getText().toString();
//        String updatedPhoneNum = editPhoneNum.getText().toString();
//
//        user.setUserName(editName.toString());
////        user.setUserHomepage(updatedHomePage);
////        user.setUserEmail(updatedEmail);
////        user.getUserPhoneNumber(updatedPhoneNum);
//
//        // Pass updated information back to the UserProfileFragment
//        Intent intent = new Intent();
//        intent.putExtra("updatedUser", user);
//        setResult(Activity.RESULT_OK, intent);
//
//        finish();
}
