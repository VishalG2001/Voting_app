package com.example.test2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class VotingActivity extends AppCompatActivity {
    private RadioGroup candidateRadioGroup;
    private Button voteButton, btnlogout;
    private SQLiteHelper dbHelper;
    private String currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voting);

        // Initialize views
        candidateRadioGroup = findViewById(R.id.radioGroupCandidates);
        voteButton = findViewById(R.id.btnVote);
        btnlogout = findViewById(R.id.btnLogout);
        dbHelper = new SQLiteHelper(this);

        // Get current user (Assuming you have implemented a session management system)
        currentUser = getCurrentUser(); // You need to implement this method

        // Check if the user has already voted
        if (dbHelper.hasUserVoted(currentUser)) {
            voteButton.setEnabled(false); // Disable vote button if user has already voted
            Toast.makeText(this, "You already voted", Toast.LENGTH_SHORT).show();
        } else {
            voteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int selectedCandidateId = candidateRadioGroup.getCheckedRadioButtonId();
                    if (selectedCandidateId != -1) {
                        RadioButton selectedCandidateRadioButton = findViewById(selectedCandidateId);
                        String selectedCandidate = selectedCandidateRadioButton.getText().toString();
                        // Save vote in database
                        dbHelper.saveVote(currentUser, selectedCandidate);
                        Toast.makeText(VotingActivity.this, "Vote casted successfully", Toast.LENGTH_SHORT).show();
                        // Disable vote button to prevent multiple votes
                        voteButton.setEnabled(false);
                    } else {
                        Toast.makeText(VotingActivity.this, "Please select a candidate", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            btnlogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Clear session data and navigate back to MainActivity

                    Intent intent = new Intent(VotingActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }
    }

    // Method to get current logged in user (you need to implement this)
    private String getCurrentUser() {
        // Implement your session management logic here
        return "current_user"; // Placeholder, replace with actual logic
    }

}
