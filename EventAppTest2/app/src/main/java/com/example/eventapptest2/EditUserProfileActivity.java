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
 * This is a class for editing user profile information.
 * This class lets users edit their profile details such as name, homepage, email, and phone number.
 */
public class EditUserProfileActivity extends AppCompatActivity {
    Button backButton;
    Button verifyButton;
    EditText editName;
    EditText editHomePage;
    EditText editEmail;
    EditText editPhoneNum;

    /**
     * Starts when users enters edit profile activity.
     * Method sets up the layout when editing profile information and retrieves the user profile
     * information.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_profile);

        backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        editName = findViewById(R.id.userEditNameText);
        editHomePage = findViewById(R.id.userEditHomepageText);
        editEmail = findViewById(R.id.userEditEmailText);
        editPhoneNum = findViewById(R.id.userEditPhoneNumText);

        // Get the User object information from the previous Activity (UserProfileFragment)
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String userName = bundle.getString("userName");
            String userHomepage = bundle.getString("homepage");
            String userEmail = bundle.getString("email");
            String userPhoneNum = bundle.getString("phoneNum");

            // Set users information to corresponding edited text
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

    /**
     * Updates the user's profile with the edited information.
     * Method retrieves the edited information from the EditText fields and passes it back
     * to the previous activity.
     */
    private void updateUserProfile() {
        String updatedName = editName.getText().toString();
        String updatedHomePage = editHomePage.getText().toString();
        String updatedEmail = editEmail.getText().toString();
        String updatedPhoneNum = editPhoneNum.getText().toString();

        // Pass back updated user object to UserProfileFragment
        Intent updateIntent = new Intent();
        updateIntent.putExtra("updatedUserName", updatedName);
        updateIntent.putExtra("updatedUserHomepage", updatedHomePage);
        updateIntent.putExtra("updatedUserEmail", updatedEmail);
        updateIntent.putExtra("updatedUserPhoneNum", updatedPhoneNum);

        // Set the result of the activity and finish
        setResult(Activity.RESULT_OK, updateIntent);
        finish();
    }
}
