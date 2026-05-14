package com.kreeda.preranascout.ui.athlete;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kreeda.preranascout.KreedaApp;
import com.kreeda.preranascout.R;
import com.kreeda.preranascout.data.entity.Athlete;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class AddAthleteActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AthleteAdapter adapter;
    private List<Athlete> athleteList = new ArrayList<>();
    private TextView tvEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_athlete_list);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Athletes");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        recyclerView = findViewById(R.id.rv_athletes);
        tvEmpty = findViewById(R.id.tv_empty);
        FloatingActionButton fabAdd = findViewById(R.id.fab_add_athlete);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AthleteAdapter(athleteList, athlete -> {
            Intent intent = new Intent(this, AthleteDetailActivity.class);
            intent.putExtra("athlete_id", athlete.getId());
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);

        fabAdd.setOnClickListener(v -> showAddAthleteDialog());

        loadAthletes();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadAthletes();
    }

    private void loadAthletes() {
        KreedaApp.getDatabase().athleteDao().getAllAthletes().observe(this, athletes -> {
            athleteList.clear();
            athleteList.addAll(athletes);
            adapter.notifyDataSetChanged();
            tvEmpty.setVisibility(athletes.isEmpty() ? View.VISIBLE : View.GONE);
            recyclerView.setVisibility(athletes.isEmpty() ? View.GONE : View.VISIBLE);
        });
    }

    private void showAddAthleteDialog() {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_athlete, null);

        EditText etName = dialogView.findViewById(R.id.et_name);
        EditText etAge = dialogView.findViewById(R.id.et_age);
        EditText etClass = dialogView.findViewById(R.id.et_class);
        EditText etSchool = dialogView.findViewById(R.id.et_school);
        Spinner spSport = dialogView.findViewById(R.id.sp_sport);

        String[] sports = {"Athletics", "Kabaddi", "Kho-Kho", "Wrestling", "Boxing", "Archery", "Hockey", "Football", "Volleyball", "Badminton"};
        ArrayAdapter<String> sportAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, sports);
        spSport.setAdapter(sportAdapter);

        new AlertDialog.Builder(this)
                .setTitle("Add New Athlete")
                .setView(dialogView)
                .setPositiveButton("Save", (dialog, which) -> {
                    String name = etName.getText().toString().trim();
                    String ageStr = etAge.getText().toString().trim();
                    String studentClass = etClass.getText().toString().trim();
                    String school = etSchool.getText().toString().trim();
                    String sport = spSport.getSelectedItem().toString();

                    if (name.isEmpty() || ageStr.isEmpty() || studentClass.isEmpty() || school.isEmpty()) {
                        Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Athlete athlete = new Athlete();
                    athlete.setName(name);
                    athlete.setAge(Integer.parseInt(ageStr));
                    athlete.setStudentClass(studentClass);
                    athlete.setSchool(school);
                    athlete.setPrimarySport(sport);

                    Executors.newSingleThreadExecutor().execute(() -> {
                        KreedaApp.getDatabase().athleteDao().insert(athlete);
                        runOnUiThread(() -> Toast.makeText(this, name + " added!", Toast.LENGTH_SHORT).show());
                    });
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    // ===== Inner Adapter =====
    static class AthleteAdapter extends RecyclerView.Adapter<AthleteAdapter.ViewHolder> {
        private final List<Athlete> athletes;
        private final OnAthleteClickListener listener;

        interface OnAthleteClickListener {
            void onClick(Athlete athlete);
        }

        AthleteAdapter(List<Athlete> athletes, OnAthleteClickListener listener) {
            this.athletes = athletes;
            this.listener = listener;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_athlete, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Athlete athlete = athletes.get(position);
            holder.tvName.setText(athlete.getName());
            holder.tvDetails.setText(String.format("Age: %d | Class: %s | %s",
                    athlete.getAge(), athlete.getStudentClass(), athlete.getPrimarySport()));
            holder.tvSchool.setText(athlete.getSchool());

            if (athlete.isDistrictLevelReady()) {
                holder.tvBadge.setVisibility(View.VISIBLE);
                holder.tvBadge.setText("⭐ District Level Ready");
            } else {
                holder.tvBadge.setVisibility(View.GONE);
            }

            holder.itemView.setOnClickListener(v -> listener.onClick(athlete));
        }

        @Override
        public int getItemCount() { return athletes.size(); }

        static class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvName, tvDetails, tvSchool, tvBadge;
            ViewHolder(View view) {
                super(view);
                tvName = view.findViewById(R.id.tv_athlete_name);
                tvDetails = view.findViewById(R.id.tv_athlete_details);
                tvSchool = view.findViewById(R.id.tv_athlete_school);
                tvBadge = view.findViewById(R.id.tv_badge);
            }
        }
    }
}
