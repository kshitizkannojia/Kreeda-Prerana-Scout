package com.kreeda.preranascout.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Benchmark thresholds for "District Level Ready" badge.
 * For timed events: value must be LESS than threshold (faster).
 * For distance events: value must be GREATER than threshold (farther/higher).
 */
public class BenchmarkUtils {

    // Timed events (seconds) - lower is better
    private static final Map<String, Double> TIMED_BENCHMARKS = new HashMap<>();
    // Distance events (meters) - higher is better
    private static final Map<String, Double> DISTANCE_BENCHMARKS = new HashMap<>();

    static {
        // Sprint benchmarks (seconds)
        TIMED_BENCHMARKS.put("100m Sprint", 14.0);
        TIMED_BENCHMARKS.put("200m Sprint", 28.0);
        TIMED_BENCHMARKS.put("400m Sprint", 62.0);
        TIMED_BENCHMARKS.put("800m Run", 150.0);
        TIMED_BENCHMARKS.put("1500m Run", 320.0);
        TIMED_BENCHMARKS.put("50m Sprint", 7.5);

        // Distance/field benchmarks (meters)
        DISTANCE_BENCHMARKS.put("Long Jump", 4.5);
        DISTANCE_BENCHMARKS.put("High Jump", 1.4);
        DISTANCE_BENCHMARKS.put("Shot Put", 8.0);
        DISTANCE_BENCHMARKS.put("Discus Throw", 25.0);
        DISTANCE_BENCHMARKS.put("Javelin Throw", 30.0);

        // Kabaddi (points scored as performance metric)
        DISTANCE_BENCHMARKS.put("Kabaddi Raid Points", 15.0);
        DISTANCE_BENCHMARKS.put("Kabaddi Tackle Points", 12.0);
    }

    public static boolean isTimedEvent(String eventType) {
        return TIMED_BENCHMARKS.containsKey(eventType);
    }

    public static boolean isDistanceEvent(String eventType) {
        return DISTANCE_BENCHMARKS.containsKey(eventType);
    }

    /**
     * Check if the value meets district-level benchmark.
     */
    public static boolean meetsDistrictBenchmark(String eventType, double value) {
        if (TIMED_BENCHMARKS.containsKey(eventType)) {
            return value <= TIMED_BENCHMARKS.get(eventType);
        } else if (DISTANCE_BENCHMARKS.containsKey(eventType)) {
            return value >= DISTANCE_BENCHMARKS.get(eventType);
        }
        return false;
    }

    public static Double getBenchmarkValue(String eventType) {
        if (TIMED_BENCHMARKS.containsKey(eventType)) {
            return TIMED_BENCHMARKS.get(eventType);
        } else if (DISTANCE_BENCHMARKS.containsKey(eventType)) {
            return DISTANCE_BENCHMARKS.get(eventType);
        }
        return null;
    }

    public static String getBenchmarkDescription(String eventType) {
        Double benchmark = getBenchmarkValue(eventType);
        if (benchmark == null) return "No benchmark set";

        if (isTimedEvent(eventType)) {
            return String.format("Under %.2f seconds", benchmark);
        } else {
            return String.format("Over %.2f meters/points", benchmark);
        }
    }

    public static String getUnit(String eventType) {
        if (isTimedEvent(eventType)) return "seconds";
        return "meters";
    }

    public static String[] getAllEvents() {
        String[] events = new String[TIMED_BENCHMARKS.size() + DISTANCE_BENCHMARKS.size()];
        int i = 0;
        for (String key : TIMED_BENCHMARKS.keySet()) events[i++] = key;
        for (String key : DISTANCE_BENCHMARKS.keySet()) events[i++] = key;
        return events;
    }

    public static String[] getTimedEvents() {
        return TIMED_BENCHMARKS.keySet().toArray(new String[0]);
    }

    public static String[] getDistanceEvents() {
        return DISTANCE_BENCHMARKS.keySet().toArray(new String[0]);
    }
}
