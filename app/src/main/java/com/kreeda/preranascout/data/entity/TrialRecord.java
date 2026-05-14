package com.kreeda.preranascout.data.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "trial_records",
        foreignKeys = @ForeignKey(
                entity = Athlete.class,
                parentColumns = "id",
                childColumns = "athleteId",
                onDelete = ForeignKey.CASCADE
        ),
        indices = {@Index("athleteId")}
)
public class TrialRecord {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int athleteId;
    private String eventType;       // "100m Sprint", "200m Sprint", "Long Jump", "Shot Put", etc.
    private double value;           // time in seconds or distance in meters
    private String unit;            // "seconds" or "meters"
    private long trialDate;
    private String notes;

    public TrialRecord() {
        this.trialDate = System.currentTimeMillis();
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getAthleteId() { return athleteId; }
    public void setAthleteId(int athleteId) { this.athleteId = athleteId; }

    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }

    public double getValue() { return value; }
    public void setValue(double value) { this.value = value; }

    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }

    public long getTrialDate() { return trialDate; }
    public void setTrialDate(long trialDate) { this.trialDate = trialDate; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
