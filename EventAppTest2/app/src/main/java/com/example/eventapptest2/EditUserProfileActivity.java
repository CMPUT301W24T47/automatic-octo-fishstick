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


//    FirebaseFirestore db = FirebaseFirestore.getInstance();
//    CollectionReference usersRef = db.collection("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_profile);

        backButton = findViewById(R.id.back_button);
        // Goes back to the previous screen and user information wont be updated
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Get the User object information from the previous Activity (UserProfileFragment)


        editName = findViewById(R.id.userEditNameText);
        editHomePage = findViewById(R.id.userEditHomepageText);
        editEmail = findViewById(R.id.userEditEmailText);
        editPhoneNum = findViewById(R.id.userEditPhoneNumText);

        // Get the User object information from the previous Activity (UserProfileFragment)
        // bundle gets the Users information the was passed from the previous screen
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            // Get the information from the previous screen
            String userName = bundle.getString("userName");
            String userHomepage = bundle.getString("homepage");
            String userEmail = bundle.getString("email");
            String userPhoneNum = bundle.getString("phoneNum");

            // Then set users information from the previous screen
            // Can now see it on the Edit screen
            editName.setText(userName);
            editHomePage.setText(userHomepage);
            editEmail.setText(userEmail);
            editPhoneNum.setText(userPhoneNum);
        }


        // Implement Verify button
        // - Updates the changed user information
        verifyButton = findViewById(R.id.button_verify);
        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserProfile();
            }
        });
    }


    // Update user's profile with the edited information
    private void updateUserProfile() {
        // Varialbes for the changed infomration
        // - when the user updates there information they are stored in these varaibles
        String updatedName = editName.getText().toString();
        String updatedHomePage = editHomePage.getText().toString();
        String updatedEmail = editEmail.getText().toString();
        String updatedPhoneNum = editPhoneNum.getText().toString();



        // Pass back updated user object to UserProfileFragment
        Intent updateIntent = new Intent();

        // Carry the updated version from this screen and pass it back to the previous screen
        // Need to use unique id to update the previous screen
        updateIntent.putExtra("updatedUserName", updatedName);
        updateIntent.putExtra("updatedUserHomepage", updatedHomePage);
        updateIntent.putExtra("updatedUserEmail", updatedEmail);
        updateIntent.putExtra("updatedUserPhoneNum", updatedPhoneNum);
        setResult(Activity.RESULT_OK, updateIntent);
        finish();
    }


}
