package com.example.test2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.HashMap;
import java.util.Map;

public class AdminActivity extends AppCompatActivity {
    private TextView candidate1CountTextView, candidate2CountTextView,candidate3CountTextView, candidate4CountTextView; // Update these with actual TextViews

    Button btnlo;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        // Initialize TextViews
        candidate1CountTextView = findViewById(R.id.candidate1_count_text_view);
        candidate2CountTextView = findViewById(R.id.candidate2_count_text_view);
        candidate3CountTextView = findViewById(R.id.candidate3_count_text_view);
        candidate4CountTextView = findViewById(R.id.candidate4_count_text_view);
        btnlo = findViewById(R.id.btnLogout);



        // Retrieve and display vote counts
        displayVoteCounts();

        btnlo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(AdminActivity.this, MainActivity.class);
                startActivity(home);
            }
        });

    }


    private void displayVoteCounts() {
        // Retrieve vote counts from the database
        Map<String, Integer> voteCounts = getVoteCountsFromDatabase(); // Implement this method

        if (voteCounts.containsKey("Candidate 1")) {
            candidate1CountTextView.setText(String.valueOf(voteCounts.get("Candidate 1")));
        }
        if (voteCounts.containsKey("Candidate 2")) {
            candidate2CountTextView.setText(String.valueOf(voteCounts.get("Candidate 2")));
        }
        if (voteCounts.containsKey("Candidate 3")) {
            candidate2CountTextView.setText(String.valueOf(voteCounts.get("Candidate 3")));
        }
        if (voteCounts.containsKey("Candidate 4")) {
            candidate2CountTextView.setText(String.valueOf(voteCounts.get("Candidate 4")));
        }
    }



    private Map<String, Integer> getVoteCountsFromDatabase() {

        Map<String, Integer> voteCounts = new HashMap<>();

        voteCounts.put("Candidate 1", 2);
        voteCounts.put("Candidate 2", 1);
        voteCounts.put("Candidate 3", 0);
        voteCounts.put("Candidate 4", 0);
        return voteCounts;
    }

}
