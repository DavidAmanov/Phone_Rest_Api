package com.phone.phone;

public class PhoneStatistics {
    private final int callCount;
    private final long totalDuration;

    public PhoneStatistics(int callCount, long totalDuration) {
        this.callCount = callCount;
        this.totalDuration = totalDuration;
    }

    public int getCallCount() {
        return callCount;
    }

    public long getTotalDuration() {
        return totalDuration;
    }
}
