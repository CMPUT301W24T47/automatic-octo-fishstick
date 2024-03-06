package com.example.eventapptest2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class EditUserProfileActivity extends AppCompatActivity {
    Button backButton;
    Button verifyButton;
    EditText editName;
    EditText editHomePage;
    EditText editEmail;
    EditText editPhoneNum;




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


    }
}
