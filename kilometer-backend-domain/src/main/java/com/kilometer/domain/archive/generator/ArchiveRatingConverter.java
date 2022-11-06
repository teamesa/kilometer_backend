package com.kilometer.domain.archive.generator;


public class ArchiveRatingConverter {

    private final static double ROUND_DECIMAL_PLACE = 10.0;

    public static double convertArchiveRatingAverage(double archiveRatingAverage) {
        return Math.round(archiveRatingAverage * ROUND_DECIMAL_PLACE) / ROUND_DECIMAL_PLACE;
    }
}
