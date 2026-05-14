package com.kreeda.preranascout.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "athletes")
public class Athlete {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private int age;
    private String studentClass;
    private String school;
    private String primarySport;
    private boolean districtLevelReady;
    private long createdAt;

    public Athlete() {
        this.createdAt = System.currentTimeMillis();
        this.districtLevelReady = false;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getStudentClass() { return studentClass; }
    public void setStudentClass(String studentClass) { this.studentClass = studentClass; }

    public String getSchool() { return school; }
    public void setSchool(String school) { this.school = school; }

    public String getPrimarySport() { return primarySport; }
    public void setPrimarySport(String primarySport) { this.primarySport = primarySport; }

    public boolean isDistrictLevelReady() { return districtLevelReady; }
    public void setDistrictLevelReady(boolean districtLevelReady) { this.districtLevelReady = districtLevelReady; }

    public long getCreatedAt() { return createdAt; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }
}
