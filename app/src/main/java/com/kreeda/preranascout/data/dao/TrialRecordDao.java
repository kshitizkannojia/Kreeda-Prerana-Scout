package com.kreeda.preranascout.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.kreeda.preranascout.data.entity.TrialRecord;

import java.util.List;

@Dao
public interface TrialRecordDao {

    @Insert
    long insert(TrialRecord record);

    @Insert
    List<Long> insertAll(List<TrialRecord> records);

    @Delete
    void delete(TrialRecord record);

    @Query("SELECT * FROM trial_records WHERE athleteId = :athleteId ORDER BY trialDate DESC")
    LiveData<List<TrialRecord>> getTrialsByAthlete(int athleteId);

    @Query("SELECT * FROM trial_records WHERE athleteId = :athleteId ORDER BY trialDate DESC")
    List<TrialRecord> getTrialsByAthleteList(int athleteId);

    @Query("SELECT * FROM trial_records WHERE athleteId = :athleteId AND eventType = :eventType ORDER BY trialDate ASC")
    List<TrialRecord> getTrialsByAthleteAndEvent(int athleteId, String eventType);

    @Query("SELECT * FROM trial_records WHERE athleteId = :athleteId AND eventType = :eventType ORDER BY trialDate ASC")
    LiveData<List<TrialRecord>> getTrialsByAthleteAndEventLive(int athleteId, String eventType);

    @Query("SELECT MIN(value) FROM trial_records WHERE athleteId = :athleteId AND eventType = :eventType")
    Double getBestPerformance(int athleteId, String eventType);

    @Query("SELECT DISTINCT eventType FROM trial_records WHERE athleteId = :athleteId")
    List<String> getEventTypesForAthlete(int athleteId);

    @Query("SELECT DISTINCT eventType FROM trial_records")
    LiveData<List<String>> getAllEventTypes();

    // For leaderboard - get best (minimum for time events) performance per athlete per event
    @Query("SELECT t.* FROM trial_records t " +
           "INNER JOIN (SELECT athleteId, MIN(value) as minVal FROM trial_records WHERE eventType = :eventType GROUP BY athleteId) best " +
           "ON t.athleteId = best.athleteId AND t.value = best.minVal AND t.eventType = :eventType " +
           "GROUP BY t.athleteId ORDER BY t.value ASC")
    List<TrialRecord> getLeaderboardForTimedEvent(String eventType);

    // For distance events (higher is better)
    @Query("SELECT t.* FROM trial_records t " +
           "INNER JOIN (SELECT athleteId, MAX(value) as maxVal FROM trial_records WHERE eventType = :eventType GROUP BY athleteId) best " +
           "ON t.athleteId = best.athleteId AND t.value = best.maxVal AND t.eventType = :eventType " +
           "GROUP BY t.athleteId ORDER BY t.value DESC")
    List<TrialRecord> getLeaderboardForDistanceEvent(String eventType);
}
