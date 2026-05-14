package com.kreeda.preranascout.ui.trial;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class BatchEntryActivity extends AppCompatActivity {

    private RecyclerView rvBatch;
    private Spinner spEvent;
    private BatchAdapter batchAdapter;
    private List<Athlete> athleteList = new ArrayList<>();
    private TextView tvCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batch_entry);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Batch Entry");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        rvBatch = findViewById(R.id.rv_batch);
        spEvent = findViewById(R.id.sp_batch_event);
        tvCount = findViewById(R.id.tv_batch_count);
        MaterialButton btnSaveAll = findViewById(R.id.btn_save_batch);

        // Setup event spinner
        String[] events = BenchmarkUtils.getAllEvents();
        ArrayAdapter<String> eventAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, events);
        spEvent.setAdapter(eventAdapter);

        rvBatch.setLayoutManager(new LinearLayoutManager(this));
        batchAdapter = new BatchAdapter(athleteList);
        rvBatch.setAdapter(batchAdapter);

        btnSaveAll.setOnClickListener(v -> saveAllTrials());

        loadAthletes();
    }

    private void loadAthletes() {
        KreedaApp.getDatabase().athleteDao().getAllAthletes().observe(this, athletes -> {
            athleteList.clear();
            athleteList.addAll(athletes);
            batchAdapter.notifyDataSetChanged();
            tvCount.setText(String.format(Locale.getDefault(),
                    "Athletes: %d (supports up to 30 at once)", athletes.size()));
        });
    }

    private void saveAllTrials() {
        if (athleteList.isEmpty()) {
            Toast.makeText(this, "No athletes to record", Toast.LENGTH_SHORT).show();
            return;
        }

        String eventType = spEvent.getSelectedItem().toString();
        String unit = BenchmarkUtils.getUnit(eventType);
        List<TrialRecord> records = new ArrayList<>();
        List<Integer> badgeAthleteIds = new ArrayList<>();

        for (int i = 0; i < batchAdapter.getItemCount(); i++) {
            String valueStr = batchAdapter.getValueAt(i);
            if (valueStr != null && !valueStr.trim().isEmpty()) {
                try {
                    double value = Double.parseDouble(valueStr.trim());
                    value = Math.round(value * 100.0) / 100.0;

                    TrialRecord record = new TrialRecord();
                    record.setAthleteId(athleteList.get(i).getId());
                    record.setEventType(eventType);
                    record.setValue(value);
                    record.setUnit(unit);
                    record.setTrialDate(System.currentTimeMillis());
                    records.add(record);

                    if (BenchmarkUtils.meetsDistrictBenchmark(eventType, value)) {
                        badgeAthleteIds.add(athleteList.get(i).getId());
                    }
                } catch (NumberFormatException e) {
                    // Skip invalid entries
                }
            }
        }

        if (records.isEmpty()) {
            Toast.makeText(this, "No values entered", Toast.LENGTH_SHORT).show();
            return;
        }

        Executors.newSingleThreadExecutor().execute(() -> {
            KreedaApp.getDatabase().trialRecordDao().insertAll(records);

            // Update badges
            for (int id : badgeAthleteIds) {
                KreedaApp.getDatabase().athleteDao().updateDistrictReadyStatus(id, true);
            }

            runOnUiThread(() -> {
                String msg = String.format(Locale.getDefault(),
                        "Saved %d records for %s", records.size(), eventType);
                if (!badgeAthleteIds.isEmpty()) {
                    msg += String.format("\n⭐ %d athlete(s) earned District Level Ready!", badgeAthleteIds.size());
                }
                Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
                batchAdapter.clearValues();
            });
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    // ===== Batch Adapter =====
    static class BatchAdapter extends RecyclerView.Adapter<BatchAdapter.ViewHolder> {
        private final List<Athlete> athletes;
        private final String[] values;

        BatchAdapter(List<Athlete> athletes) {
            this.athletes = athletes;
            this.values = new String[50]; // Support up to 50 students
        }

        String getValueAt(int position) {
            return values[position];
        }

        void clearValues() {
            for (int i = 0; i < values.length; i++) values[i] = null;
            notifyDataSetChanged();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_batch_entry, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Athlete athlete = athletes.get(position);
            holder.tvSerial.setText(String.valueOf(position + 1));
            holder.tvName.setText(athlete.getName());
            holder.tvClass.setText(athlete.getStudentClass());

            holder.etValue.setText(values[position] != null ? values[position] : "");
            holder.etValue.setOnFocusChangeListener((v, hasFocus) -> {
                if (!hasFocus) {
                    values[holder.getAdapterPosition()] = holder.etValue.getText().toString();
                }
            });
        }

        @Override
        public int getItemCount() { return athletes.size(); }

        static class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvSerial, tvName, tvClass;
            EditText etValue;
            ViewHolder(View view) {
                super(view);
                tvSerial = view.findViewById(R.id.tv_serial);
                tvName = view.findViewById(R.id.tv_batch_name);
                tvClass = view.findViewById(R.id.tv_batch_class);
                etValue = view.findViewById(R.id.et_batch_value);
            }
        }
    }
}
