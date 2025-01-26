package kr.kdev.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.MonthDay;
import java.time.Year;
import java.time.YearMonth;
import java.time.temporal.TemporalAdjusters;

@DisplayName("Java Date and Time API Test")
class DateTimeTest {
    @Test
    void givenYearMonth_whenAtEndOfMonth_thenEqualsLastDayOfMonth() {
        // given
        YearMonth yearMonth = YearMonth.of(2025, 1);

        // when
        LocalDate endDate = yearMonth.atEndOfMonth();
        LocalDate lastDate = yearMonth.atDay(yearMonth.lengthOfMonth());
        LocalDate lastDayOfMonth = yearMonth.atDay(1).with(TemporalAdjusters.lastDayOfMonth());

        // then
        Assertions.assertEquals(endDate, lastDate);
        Assertions.assertEquals(endDate, lastDayOfMonth);
    }

    @Test
    void givenYear_whenAtDay_thenEqualsLastDayOfYear() {
        // given
        Year year = Year.of(2025);

        // when
        LocalDate lastDay = year.atDay(year.length());
        LocalDate lastDate = year.atDay(1).with(TemporalAdjusters.lastDayOfYear());

        // then
        Assertions.assertEquals(lastDay, lastDate);
        Assertions.assertEquals(year.atDay(1), year.atMonthDay(MonthDay.of(1, 1)));
    }
}
