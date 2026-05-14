package com.kreeda.preranascout.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.kreeda.preranascout.data.entity.Athlete;

import java.util.List;

@Dao
public interface AthleteDao {

    @Insert
    long insert(Athlete athlete);

    @Insert
    List<Long> insertAll(List<Athlete> athletes);

    @Update
    void update(Athlete athlete);

    @Delete
    void delete(Athlete athlete);

    @Query("SELECT * FROM athletes ORDER BY name ASC")
    LiveData<List<Athlete>> getAllAthletes();

    @Query("SELECT * FROM athletes ORDER BY name ASC")
    List<Athlete> getAllAthletesList();

    @Query("SELECT * FROM athletes WHERE id = :id")
    Athlete getAthleteById(int id);

    @Query("SELECT * FROM athletes WHERE id = :id")
    LiveData<Athlete> getAthleteByIdLive(int id);

    @Query("SELECT * FROM athletes WHERE primarySport = :sport ORDER BY name ASC")
    LiveData<List<Athlete>> getAthletesBySport(String sport);

    @Query("SELECT * FROM athletes WHERE school = :school ORDER BY name ASC")
    LiveData<List<Athlete>> getAthletesBySchool(String school);

    @Query("SELECT DISTINCT primarySport FROM athletes")
    LiveData<List<String>> getAllSports();

    @Query("SELECT DISTINCT school FROM athletes")
    LiveData<List<String>> getAllSchools();

    @Query("UPDATE athletes SET districtLevelReady = :ready WHERE id = :athleteId")
    void updateDistrictReadyStatus(int athleteId, boolean ready);

    @Query("SELECT COUNT(*) FROM athletes")
    int getAthleteCount();
}
