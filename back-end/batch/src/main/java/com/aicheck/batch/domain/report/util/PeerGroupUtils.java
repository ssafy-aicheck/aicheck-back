package com.aicheck.batch.domain.report.util;

import java.time.LocalDate;
import java.time.Period;

public class PeerGroupUtils {

    private PeerGroupUtils() {
    }

    public static String getPeerGroup(LocalDate birth, int year, int month) {
        LocalDate referenceDate = LocalDate.of(year, month, 1);
        int age = Period.between(birth, referenceDate).getYears();
        if (age >= 8 && age <= 10) {
            return "age_8_10";
        }
        if (age >= 11 && age <= 13) {
            return "age_11_13";
        }
        if (age >= 14 && age <= 16) {
            return "age_14_16";
        }
        if (age >= 17 && age <= 19) {
            return "age_17_19";
        }
        return null;
    }

}
