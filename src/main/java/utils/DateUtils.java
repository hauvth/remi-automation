package utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

public class DateUtils {

    public static String getCurrentDate() {
        return getCurrentDate("MM/DD/yyyy");
    }
    public static String getCurrentDate(String format) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            return LocalDateTime.now().format(formatter);
        }catch (IllegalArgumentException e) {
            return "Invalid format: " + format;
        }
    }
    public static String convertDateFormat(String date, String fromPattern, String toPattern) {
        try {
            DateTimeFormatter fromFormatter = DateTimeFormatter.ofPattern(fromPattern);
            LocalDate localDate = LocalDate.parse(date, fromFormatter);
            DateTimeFormatter toFormatter = DateTimeFormatter.ofPattern(toPattern);
            return localDate.format(toFormatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format: " + date + " (expected: " + fromPattern + ")", e);
        }
    }
    public static LocalDate parseDate(String date, String pattern) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            return LocalDate.parse(date, formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Failed to parse date: " + date + " with pattern " + pattern, e);
        }
    }

    public static String dayCalculate(long amount, String pattern) {
        try {
            LocalDate adjusted = LocalDate.now().plusDays(amount);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            return adjusted.format(formatter);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid date pattern: " + pattern, e);
        }
    }
    public static long getDateDifference(String startDate, String endDate, String pattern) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            LocalDate start = LocalDate.parse(startDate, formatter);
            LocalDate end = LocalDate.parse(endDate, formatter);
            return Math.abs(ChronoUnit.DAYS.between(start, end));
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date for difference calculation: " + startDate + " or " + endDate, e);
        }
    }
}
