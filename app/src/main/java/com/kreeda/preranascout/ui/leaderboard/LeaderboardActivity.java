package com.kreeda.preranascout.ui.leaderboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kreeda.preranascout.KreedaApp;
import com.kreeda.preranascout.R;
import com.kreeda.preranascout.data.entity.Athlete;
import com.kreeda.preranascout.data.entity.TrialRecord;
import com.kreeda.preranascout.utils.BenchmarkUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;

public class LeaderboardActivity extends AppCompatActivity {

    private RecyclerView rvLeaderboard;
    private Spinner spEventFilter;
    private LeaderboardAdapter adapter;
    private List<LeaderboardEntry> entries = new ArrayList<>();
    private TextView tvEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Leaderboard");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        rvLeaderboard = findViewById(R.id.rv_leaderboard);
        spEventFilter = findViewById(R.id.sp_leaderboard_event);
        tvEmpty = findViewById(R.id.tv_leaderboard_empty);

        rvLeaderboard.setLayoutManager(new LinearLayoutManager(this));
        adapter = new LeaderboardAdapter(entries);
        rvLeaderboard.setAdapter(adapter);

        // Setup event filter
        String[] events = BenchmarkUtils.getAllEvents();
        ArrayAdapter<String> eventAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, events);
        spEventFilter.setAdapter(eventAdapter);

        spEventFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loadLeaderboard(events[position]);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void loadLeaderboard(String eventType) {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<TrialRecord> records;
            if (BenchmarkUtils.isTimedEvent(eventType)) {
                records = KreedaApp.getDatabase().trialRecordDao().getLeaderboardForTimedEvent(eventType);
            } else {
                records = KreedaApp.getDatabase().trialRecordDao().getLeaderboardForDistanceEvent(eventType);
            }

            List<LeaderboardEntry> newEntries = new ArrayList<>();
            int rank = 1;
            for (TrialRecord record : records) {
                Athlete athlete = KreedaApp.getDatabase().athleteDao().getAthleteById(record.getAthleteId());
                if (athlete != null) {
                    boolean meetsBenchmark = BenchmarkUtils.meetsDistrictBenchmark(eventType, record.getValue());
                    newEntries.add(new LeaderboardEntry(
                            rank++,
                            athlete.getName(),
                            athlete.getSchool(),
                            athlete.getStudentClass(),
                            record.getValue(),
                            record.getUnit(),
                            meetsBenchmark
                    ));
                }
            }

            runOnUiThread(() -> {
                entries.clear();
                entries.addAll(newEntries);
                adapter.notifyDataSetChanged();
                tvEmpty.setVisibility(entries.isEmpty() ? View.VISIBLE : View.GONE);
                rvLeaderboard.setVisibility(entries.isEmpty() ? View.GONE : View.VISIBLE);
            });
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    // ===== Data class =====
    static class LeaderboardEntry {
        int rank;
        String name, school, studentClass, unit;
        double value;
        boolean meetsBenchmark;

        LeaderboardEntry(int rank, String name, String school, String studentClass,
                         double value, String unit, boolean meetsBenchmark) {
            this.rank = rank;
            this.name = name;
            this.school = school;
            this.studentClass = studentClass;
            this.value = value;
            this.unit = unit;
            this.meetsBenchmark = meetsBenchmark;
        }
    }

    // ===== Adapter =====
    static class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.ViewHolder> {
        private final List<LeaderboardEntry> entries;

        LeaderboardAdapter(List<LeaderboardEntry> entries) { this.entries = entries; }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_leaderboard, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            LeaderboardEntry entry = entries.get(position);
            holder.tvRank.setText(String.valueOf(entry.rank));
            holder.tvName.setText(entry.name);
            holder.tvSchool.setText(entry.school + " | " + entry.studentClass);
            holder.tvScore.setText(String.format(Locale.getDefault(), "%.2f %s", entry.value, entry.unit));

            // Highlight top 3
            if (entry.rank == 1) {
                holder.tvRank.setBackgroundResource(R.drawable.bg_rank_gold);
            } else if (entry.rank == 2) {
                holder.tvRank.setBackgroundResource(R.drawable.bg_rank_silver);
            } else if (entry.rank == 3) {
                holder.tvRank.setBackgroundResource(R.drawable.bg_rank_bronze);
            } else {
                holder.tvRank.setBackgroundResource(R.drawable.bg_rank_default);
            }

            holder.tvBadge.setVisibility(entry.meetsBenchmark ? View.VISIBLE : View.GONE);
        }

        @Override
        public int getItemCount() { return entries.size(); }

        static class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvRank, tvName, tvSchool, tvScore, tvBadge;
            ViewHolder(View view) {
                super(view);
                tvRank = view.findViewById(R.id.tv_rank);
                tvName = view.findViewById(R.id.tv_lb_name);
                tvSchool = view.findViewById(R.id.tv_lb_school);
                tvScore = view.findViewById(R.id.tv_lb_score);
                tvBadge = view.findViewById(R.id.tv_lb_badge);
            }
        }
    }
}


//JBJBKJ//
