package com.kreeda.preranascout.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.kreeda.preranascout.KreedaApp;
import com.kreeda.preranascout.R;
import com.kreeda.preranascout.ui.athlete.AddAthleteActivity;
import com.kreeda.preranascout.ui.leaderboard.LeaderboardActivity;
import com.kreeda.preranascout.ui.trial.TrialLoggerActivity;
import com.kreeda.preranascout.ui.trial.BatchEntryActivity;
import com.google.android.material.card.MaterialCardView;

import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private TextView tvAthleteCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Kreeda-Prerana Scout");
        }

        tvAthleteCount = findViewById(R.id.tv_athlete_count);
        setupDashboard();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateAthleteCount();
    }

    private void updateAthleteCount() {
        Executors.newSingleThreadExecutor().execute(() -> {
            int count = KreedaApp.getDatabase().athleteDao().getAthleteCount();
            runOnUiThread(() -> {
                if (count == 0) {
                    tvAthleteCount.setText("No athletes registered yet. Start by adding athletes!");
                } else {
                    tvAthleteCount.setText("Total Athletes Registered: " + count);
                }
            });
        });
    }

    private void setupDashboard() {
        // Athlete Profiles card
        MaterialCardView cardAthletes = findViewById(R.id.card_athletes);
        cardAthletes.setOnClickListener(v -> {
            startActivity(new Intent(this, AddAthleteActivity.class));
        });

        // Trial Logger card
        MaterialCardView cardTrialLogger = findViewById(R.id.card_trial_logger);
        cardTrialLogger.setOnClickListener(v -> {
            startActivity(new Intent(this, TrialLoggerActivity.class));
        });

        // Batch Entry card
        MaterialCardView cardBatchEntry = findViewById(R.id.card_batch_entry);
        cardBatchEntry.setOnClickListener(v -> {
            startActivity(new Intent(this, BatchEntryActivity.class));
        });

        // Leaderboard card
        MaterialCardView cardLeaderboard = findViewById(R.id.card_leaderboard);
        cardLeaderboard.setOnClickListener(v -> {
            startActivity(new Intent(this, LeaderboardActivity.class));
        });
    }
}
