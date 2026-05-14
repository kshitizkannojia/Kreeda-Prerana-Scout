package com.kreeda.preranascout.ui.talentcurve;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.kreeda.preranascout.KreedaApp;
import com.kreeda.preranascout.R;
import com.kreeda.preranascout.data.entity.Athlete;
import com.kreeda.preranascout.data.entity.TrialRecord;
import com.kreeda.preranascout.utils.BenchmarkUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;

public class TalentCurveActivity extends AppCompatActivity {

    private LineChart lineChart;
    private Spinner spEvent;
    private TextView tvAthleteInfo, tvBestPerformance, tvImprovement;
    private int athleteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talent_curve);

        athleteId = getIntent().getIntExtra("athlete_id", -1);
        if (athleteId == -1) {
            Toast.makeText(this, "Invalid athlete", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Talent Curve");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        lineChart = findViewById(R.id.line_chart);
        spEvent = findViewById(R.id.sp_curve_event);
        tvAthleteInfo = findViewById(R.id.tv_curve_athlete_info);
        tvBestPerformance = findViewById(R.id.tv_best_performance);
        tvImprovement = findViewById(R.id.tv_improvement);

        setupChart();
        loadAthleteInfo();
        loadEventTypes();
    }

    private void setupChart() {
        lineChart.setTouchEnabled(true);
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);
        lineChart.setPinchZoom(true);
        lineChart.setBackgroundColor(Color.WHITE);
        lineChart.setDrawGridBackground(false);
        lineChart.getAxisRight().setEnabled(false);
        lineChart.setNoDataText("Select an event to view the talent curve");
        lineChart.setNoDataTextColor(Color.GRAY);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setLabelRotationAngle(-45);

        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.setGranularity(0.01f);
    }

    private void loadAthleteInfo() {
        Executors.newSingleThreadExecutor().execute(() -> {
            Athlete athlete = KreedaApp.getDatabase().athleteDao().getAthleteById(athleteId);
            if (athlete != null) {
                runOnUiThread(() -> {
                    tvAthleteInfo.setText(String.format("%s | %s | %s",
                            athlete.getName(), athlete.getStudentClass(), athlete.getSchool()));
                    if (getSupportActionBar() != null) {
                        getSupportActionBar().setTitle("Talent Curve - " + athlete.getName());
                    }
                });
            }
        });
    }

    private void loadEventTypes() {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<String> events = KreedaApp.getDatabase().trialRecordDao()
                    .getEventTypesForAthlete(athleteId);

            runOnUiThread(() -> {
                if (events.isEmpty()) {
                    Toast.makeText(this, "No trial records found", Toast.LENGTH_SHORT).show();
                    return;
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_dropdown_item, events);
                spEvent.setAdapter(adapter);

                spEvent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                        loadChartData(events.get(pos));
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {}
                });
            });
        });
    }

    private void loadChartData(String eventType) {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<TrialRecord> trials = KreedaApp.getDatabase().trialRecordDao()
                    .getTrialsByAthleteAndEvent(athleteId, eventType);

            if (trials.isEmpty()) {
                runOnUiThread(() -> {
                    lineChart.clear();
                    lineChart.invalidate();
                });
                return;
            }

            // Build chart data
            List<Entry> chartEntries = new ArrayList<>();
            List<String> dateLabels = new ArrayList<>();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM", Locale.getDefault());

            for (int i = 0; i < trials.size(); i++) {
                TrialRecord trial = trials.get(i);
                chartEntries.add(new Entry(i, (float) trial.getValue()));
                dateLabels.add(sdf.format(new Date(trial.getTrialDate())));
            }

            // Calculate stats
            double first = trials.get(0).getValue();
            double last = trials.get(trials.size() - 1).getValue();
            double best;
            if (BenchmarkUtils.isTimedEvent(eventType)) {
                best = trials.stream().mapToDouble(TrialRecord::getValue).min().orElse(0);
            } else {
                best = trials.stream().mapToDouble(TrialRecord::getValue).max().orElse(0);
            }

            double improvementPct;
            if (BenchmarkUtils.isTimedEvent(eventType)) {
                improvementPct = ((first - last) / first) * 100; // Lower is better
            } else {
                improvementPct = ((last - first) / first) * 100; // Higher is better
            }

            runOnUiThread(() -> {
                // Create dataset
                LineDataSet dataSet = new LineDataSet(chartEntries, eventType + " Performance");
                dataSet.setColor(Color.parseColor("#1976D2"));
                dataSet.setCircleColor(Color.parseColor("#1976D2"));
                dataSet.setLineWidth(2.5f);
                dataSet.setCircleRadius(4f);
                dataSet.setDrawCircleHole(true);
                dataSet.setValueTextSize(10f);
                dataSet.setDrawFilled(true);
                dataSet.setFillColor(Color.parseColor("#BBDEFB"));
                dataSet.setFillAlpha(100);
                dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);

                LineData lineData = new LineData(dataSet);
                lineChart.setData(lineData);

                // Set X axis labels
                XAxis xAxis = lineChart.getXAxis();
                xAxis.setValueFormatter(new IndexAxisValueFormatter(dateLabels));
                xAxis.setLabelCount(dateLabels.size());

                // Add benchmark line
                Double benchmark = BenchmarkUtils.getBenchmarkValue(eventType);
                if (benchmark != null) {
                    YAxis yAxis = lineChart.getAxisLeft();
                    yAxis.removeAllLimitLines();
                    LimitLine benchmarkLine = new LimitLine(benchmark.floatValue(),
                            "District Benchmark: " + benchmark);
                    benchmarkLine.setLineColor(Color.parseColor("#4CAF50"));
                    benchmarkLine.setLineWidth(2f);
                    benchmarkLine.setTextColor(Color.parseColor("#4CAF50"));
                    benchmarkLine.setTextSize(10f);
                    benchmarkLine.enableDashedLine(10f, 10f, 0);
                    yAxis.addLimitLine(benchmarkLine);
                }

                Description desc = new Description();
                desc.setText("Performance over time");
                desc.setTextSize(12f);
                lineChart.setDescription(desc);
                lineChart.animateX(800);
                lineChart.invalidate();

                // Update stats
                String unit = BenchmarkUtils.getUnit(eventType);
                tvBestPerformance.setText(String.format(Locale.getDefault(),
                        "Best: %.2f %s", best, unit));
                tvImprovement.setText(String.format(Locale.getDefault(),
                        "Improvement: %.1f%%", improvementPct));

                if (improvementPct > 0) {
                    tvImprovement.setTextColor(Color.parseColor("#4CAF50"));
                } else if (improvementPct < 0) {
                    tvImprovement.setTextColor(Color.parseColor("#F44336"));
                }
            });
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
