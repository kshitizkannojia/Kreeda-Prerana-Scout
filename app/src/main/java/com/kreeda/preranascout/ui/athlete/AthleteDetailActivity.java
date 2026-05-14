package com.kreeda.preranascout.ui.athlete;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kreeda.preranascout.KreedaApp;
import com.kreeda.preranascout.R;
import com.kreeda.preranascout.data.entity.Athlete;
import com.kreeda.preranascout.data.entity.TrialRecord;
import com.kreeda.preranascout.ui.talentcurve.TalentCurveActivity;
import com.kreeda.preranascout.ui.trial.TrialLoggerActivity;
import com.kreeda.preranascout.utils.BenchmarkUtils;
import com.google.android.material.button.MaterialButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;

public class AthleteDetailActivity extends AppCompatActivity {

    private int athleteId;
    private TextView tvName, tvAge, tvClass, tvSchool, tvSport, tvBadge;
    private RecyclerView rvTrials;
    private TrialAdapter trialAdapter;
    private List<TrialRecord> trialList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_athlete_detail);

        athleteId = getIntent().getIntExtra("athlete_id", -1);
        if (athleteId == -1) {
            Toast.makeText(this, "Invalid athlete", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        tvName = findViewById(R.id.tv_detail_name);
        tvAge = findViewById(R.id.tv_detail_age);
        tvClass = findViewById(R.id.tv_detail_class);
        tvSchool = findViewById(R.id.tv_detail_school);
        tvSport = findViewById(R.id.tv_detail_sport);
        tvBadge = findViewById(R.id.tv_detail_badge);
        rvTrials = findViewById(R.id.rv_trials);
        MaterialButton btnLogTrial = findViewById(R.id.btn_log_trial);
        MaterialButton btnTalentCurve = findViewById(R.id.btn_talent_curve);
        MaterialButton btnDelete = findViewById(R.id.btn_delete_athlete);

        rvTrials.setLayoutManager(new LinearLayoutManager(this));
        trialAdapter = new TrialAdapter(trialList);
        rvTrials.setAdapter(trialAdapter);

        btnLogTrial.setOnClickListener(v -> {
            Intent intent = new Intent(this, TrialLoggerActivity.class);
            intent.putExtra("athlete_id", athleteId);
            startActivity(intent);
        });

        btnTalentCurve.setOnClickListener(v -> {
            Intent intent = new Intent(this, TalentCurveActivity.class);
            intent.putExtra("athlete_id", athleteId);
            startActivity(intent);
        });

        btnDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                .setTitle("Delete Athlete")
                .setMessage("Are you sure you want to delete this athlete and all their records?")
                .setPositiveButton("Delete", (d, w) -> {
                    Executors.newSingleThreadExecutor().execute(() -> {
                        Athlete a = KreedaApp.getDatabase().athleteDao().getAthleteById(athleteId);
                        if (a != null) {
                            KreedaApp.getDatabase().athleteDao().delete(a);
                            runOnUiThread(() -> {
                                Toast.makeText(this, "Athlete deleted", Toast.LENGTH_SHORT).show();
                                finish();
                            });
                        }
                    });
                })
                .setNegativeButton("Cancel", null)
                .show();
        });

        loadAthleteData();
        loadTrials();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadAthleteData();
        loadTrials();
    }

    private void loadAthleteData() {
        KreedaApp.getDatabase().athleteDao().getAthleteByIdLive(athleteId).observe(this, athlete -> {
            if (athlete == null) return;
            if (getSupportActionBar() != null) getSupportActionBar().setTitle(athlete.getName());
            tvName.setText(athlete.getName());
            tvAge.setText("Age: " + athlete.getAge());
            tvClass.setText("Class: " + athlete.getStudentClass());
            tvSchool.setText("School: " + athlete.getSchool());
            tvSport.setText("Sport: " + athlete.getPrimarySport());

            if (athlete.isDistrictLevelReady()) {
                tvBadge.setVisibility(View.VISIBLE);
                tvBadge.setText("⭐ DISTRICT LEVEL READY ⭐");
            } else {
                tvBadge.setVisibility(View.GONE);
            }
        });
    }

    private void loadTrials() {
        KreedaApp.getDatabase().trialRecordDao().getTrialsByAthlete(athleteId).observe(this, trials -> {
            trialList.clear();
            trialList.addAll(trials);
            trialAdapter.notifyDataSetChanged();
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    // ===== Trial Adapter =====
    static class TrialAdapter extends RecyclerView.Adapter<TrialAdapter.ViewHolder> {
        private final List<TrialRecord> trials;
        private final SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault());

        TrialAdapter(List<TrialRecord> trials) { this.trials = trials; }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_trial, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            TrialRecord trial = trials.get(position);
            holder.tvEvent.setText(trial.getEventType());
            holder.tvValue.setText(String.format(Locale.getDefault(), "%.2f %s", trial.getValue(), trial.getUnit()));
            holder.tvDate.setText(sdf.format(new Date(trial.getTrialDate())));

            boolean meetsBenchmark = BenchmarkUtils.meetsDistrictBenchmark(trial.getEventType(), trial.getValue());
            holder.tvBenchmark.setVisibility(meetsBenchmark ? View.VISIBLE : View.GONE);
            if (meetsBenchmark) {
                holder.tvBenchmark.setText("✓ Meets District Benchmark");
            }
        }

        @Override
        public int getItemCount() { return trials.size(); }

        static class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvEvent, tvValue, tvDate, tvBenchmark;
            ViewHolder(View view) {
                super(view);
                tvEvent = view.findViewById(R.id.tv_trial_event);
                tvValue = view.findViewById(R.id.tv_trial_value);
                tvDate = view.findViewById(R.id.tv_trial_date);
                tvBenchmark = view.findViewById(R.id.tv_trial_benchmark);
            }
        }
    }
}
