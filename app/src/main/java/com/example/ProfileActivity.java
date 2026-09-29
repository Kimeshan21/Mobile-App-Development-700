package com.example.thesmartpantrymanager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    private EditText editProfileName;
    private EditText editProfileEmail;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile);

        editProfileName = findViewById(R.id.editProfileName);
        editProfileEmail = findViewById(R.id.editProfileEmail);

        Button buttonSaveProfile =
                findViewById(R.id.buttonSaveProfile);

        preferences = getSharedPreferences(
                "SmartPantryProfile",
                MODE_PRIVATE
        );

        loadProfile();

        buttonSaveProfile.setOnClickListener(v -> saveProfile());
    }

    private void loadProfile() {

        String name = preferences.getString(
                "profile_name",
                ""
        );

        String email = preferences.getString(
                "profile_email",
                ""
        );

        editProfileName.setText(name);
        editProfileEmail.setText(email);
    }

    private void saveProfile() {

        String name =
                editProfileName.getText().toString().trim();

        String email =
                editProfileEmail.getText().toString().trim();

        if (name.isEmpty()) {
            editProfileName.setError("Please enter your name");
            editProfileName.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            editProfileEmail.setError("Please enter your email");
            editProfileEmail.requestFocus();
            return;
        }

        preferences.edit()
                .putString("profile_name", name)
                .putString("profile_email", email)
                .apply();

        Toast.makeText(
                this,
                "Profile saved successfully",
                Toast.LENGTH_SHORT
        ).show();
    }
}