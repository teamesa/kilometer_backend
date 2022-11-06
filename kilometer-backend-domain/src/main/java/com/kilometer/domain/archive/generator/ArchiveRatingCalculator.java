package com.kilometer.domain.archive.generator;


public class ArchiveRatingCalculator {

    private final static double ROUND_DECIMAL_PLACE = 10.0;

    public static double convertArchiveRatingAverage(Double archiveRatingAverage) {
        if (archiveRatingAverage == null) {
            return 0.0;
        }
        return Math.round(archiveRatingAverage * ROUND_DECIMAL_PLACE) / ROUND_DECIMAL_PLACE;
    }
}
