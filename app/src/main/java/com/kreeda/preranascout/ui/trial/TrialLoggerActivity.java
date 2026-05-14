package com.kreeda.preranascout.ui.trial;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.kreeda.preranascout.KreedaApp;
import com.kreeda.preranascout.R;
import com.kreeda.preranascout.data.entity.Athlete;
import com.kreeda.preranascout.data.entity.TrialRecord;
import com.kreeda.preranascout.utils.BenchmarkUtils;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;

public class TrialLoggerActivity extends AppCompatActivity {

    private TextView tvTimer;
    private MaterialButton btnStart, btnStop, btnReset, btnSave;
    private Spinner spAthlete, spEvent;
    private EditText etManualValue;
    private View layoutTimer, layoutManual;
    private MaterialButton btnModeTimer, btnModeManual;

    // Stopwatch variables
    private Handler handler = new Handler(Looper.getMainLooper());
    private long startTime = 0;
    private long elapsedTime = 0;
    private boolean isRunning = false;

    private List<Athlete> athleteList = new ArrayList<>();
    private int preselectedAthleteId = -1;
    private boolean isTimerMode = true;

    private final Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            if (isRunning) {
                elapsedTime = SystemClock.elapsedRealtime() - startTime;
                updateTimerDisplay();
                handler.postDelayed(this, 10); // Update every 10ms for 2 decimal precision
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trial_logger);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Trial Logger");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        preselectedAthleteId = getIntent().getIntExtra("athlete_id", -1);

        // Initialize views
        tvTimer = findViewById(R.id.tv_timer);
        btnStart = findViewById(R.id.btn_start);
        btnStop = findViewById(R.id.btn_stop);
        btnReset = findViewById(R.id.btn_reset);
        btnSave = findViewById(R.id.btn_save_trial);
        spAthlete = findViewById(R.id.sp_athlete);
        spEvent = findViewById(R.id.sp_event);
        etManualValue = findViewById(R.id.et_manual_value);
        layoutTimer = findViewById(R.id.layout_timer);
        layoutManual = findViewById(R.id.layout_manual);
        btnModeTimer = findViewById(R.id.btn_mode_timer);
        btnModeManual = findViewById(R.id.btn_mode_manual);

        setupEventSpinner();
        setupModeToggle();
        setupTimerButtons();
        loadAthletes();

        btnSave.setOnClickListener(v -> saveTrial());
    }

    private void setupEventSpinner() {
        String[] events = BenchmarkUtils.getAllEvents();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, events);
        spEvent.setAdapter(adapter);
    }

    private void setupModeToggle() {
        btnModeTimer.setOnClickListener(v -> {
            isTimerMode = true;
            layoutTimer.setVisibility(View.VISIBLE);
            layoutManual.setVisibility(View.GONE);
            btnModeTimer.setEnabled(false);
            btnModeManual.setEnabled(true);
        });

        btnModeManual.setOnClickListener(v -> {
            isTimerMode = false;
            layoutTimer.setVisibility(View.GONE);
            layoutManual.setVisibility(View.VISIBLE);
            btnModeTimer.setEnabled(true);
            btnModeManual.setEnabled(false);
        });

        // Default to timer mode
        btnModeTimer.setEnabled(false);
        layoutManual.setVisibility(View.GONE);
    }

    private void setupTimerButtons() {
        btnStart.setOnClickListener(v -> {
            if (!isRunning) {
                startTime = SystemClock.elapsedRealtime() - elapsedTime;
                isRunning = true;
                handler.post(timerRunnable);
                btnStart.setEnabled(false);
                btnStop.setEnabled(true);
            }
        });

        btnStop.setOnClickListener(v -> {
            if (isRunning) {
                isRunning = false;
                handler.removeCallbacks(timerRunnable);
                elapsedTime = SystemClock.elapsedRealtime() - startTime;
                updateTimerDisplay();
                btnStart.setEnabled(true);
                btnStop.setEnabled(false);
            }
        });

        btnReset.setOnClickListener(v -> {
            isRunning = false;
            handler.removeCallbacks(timerRunnable);
            elapsedTime = 0;
            startTime = 0;
            tvTimer.setText("00:00.00");
            btnStart.setEnabled(true);
            btnStop.setEnabled(false);
        });

        btnStop.setEnabled(false);
    }

    private void updateTimerDisplay() {
        int seconds = (int) (elapsedTime / 1000);
        int hundredths = (int) ((elapsedTime % 1000) / 10);
        int minutes = seconds / 60;
        seconds = seconds % 60;
        tvTimer.setText(String.format(Locale.getDefault(), "%02d:%02d.%02d", minutes, seconds, hundredths));
    }

    private void loadAthletes() {
        KreedaApp.getDatabase().athleteDao().getAllAthletes().observe(this, athletes -> {
            athleteList.clear();
            athleteList.addAll(athletes);

            List<String> names = new ArrayList<>();
            int selectedIndex = 0;
            for (int i = 0; i < athletes.size(); i++) {
                names.add(athletes.get(i).getName() + " (" + athletes.get(i).getStudentClass() + ")");
                if (athletes.get(i).getId() == preselectedAthleteId) {
                    selectedIndex = i;
                }
            }

            if (names.isEmpty()) {
                names.add("No athletes - add one first");
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_dropdown_item, names);
            spAthlete.setAdapter(adapter);
            if (preselectedAthleteId != -1 && !athletes.isEmpty()) {
                spAthlete.setSelection(selectedIndex);
            }
        });
    }

    private void saveTrial() {
        if (athleteList.isEmpty()) {
            Toast.makeText(this, "No athletes available. Add athletes first.", Toast.LENGTH_SHORT).show();
            return;
        }

        int selectedPos = spAthlete.getSelectedItemPosition();
        if (selectedPos < 0 || selectedPos >= athleteList.size()) {
            Toast.makeText(this, "Please select an athlete", Toast.LENGTH_SHORT).show();
            return;
        }

        String eventType = spEvent.getSelectedItem().toString();
        double value;

        if (isTimerMode) {
            if (elapsedTime == 0) {
                Toast.makeText(this, "Please record a time first", Toast.LENGTH_SHORT).show();
                return;
            }
            value = elapsedTime / 1000.0; // Convert to seconds with decimal
        } else {
            String manualStr = etManualValue.getText().toString().trim();
            if (manualStr.isEmpty()) {
                Toast.makeText(this, "Please enter a value", Toast.LENGTH_SHORT).show();
                return;
            }
            value = Double.parseDouble(manualStr);
        }

        Athlete selectedAthlete = athleteList.get(selectedPos);
        TrialRecord record = new TrialRecord();
        record.setAthleteId(selectedAthlete.getId());
        record.setEventType(eventType);
        record.setValue(Math.round(value * 100.0) / 100.0); // Round to 2 decimal places
        record.setUnit(BenchmarkUtils.getUnit(eventType));
        record.setTrialDate(System.currentTimeMillis());

        Executors.newSingleThreadExecutor().execute(() -> {
            KreedaApp.getDatabase().trialRecordDao().insert(record);

            // Check benchmark and update badge
            boolean meetsBenchmark = BenchmarkUtils.meetsDistrictBenchmark(eventType, value);
            if (meetsBenchmark) {
                KreedaApp.getDatabase().athleteDao().updateDistrictReadyStatus(selectedAthlete.getId(), true);
            }

            runOnUiThread(() -> {
                String msg = String.format(Locale.getDefault(), "Saved: %.2f %s for %s",
                        record.getValue(), record.getUnit(), selectedAthlete.getName());
                if (meetsBenchmark) {
                    msg += "\n⭐ District Level Ready!";
                }
                Toast.makeText(this, msg, Toast.LENGTH_LONG).show();

                // Reset timer
                elapsedTime = 0;
                startTime = 0;
                tvTimer.setText("00:00.00");
                etManualValue.setText("");
                btnStart.setEnabled(true);
                btnStop.setEnabled(false);
            });
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(timerRunnable);
    }
}
