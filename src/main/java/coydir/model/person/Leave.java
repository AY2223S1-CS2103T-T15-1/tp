package coydir.model.person;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

import javafx.beans.property.SimpleStringProperty;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * Represents a Leave in the database.
 * Guarantees: immutable; name is valid as declared in {@link #isValidLeave}
 */
public class Leave {
    public static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public static final String MESSAGE_CONSTRAINTS = "Input for leave period is invalid \n";
    public static final CustomLeaveComparator COMPARATOR = new CustomLeaveComparator();
    public final LocalDate startDate;
    public final LocalDate endDate;

    private final SimpleStringProperty col1;
    private final SimpleStringProperty col2;
    private final SimpleStringProperty col3;

    /**
     * Constructs a {@code Leave}.
     *
     * @param startDate A valid start date.
     * @param endDate A valid start date.
     */
    public Leave(String startDate, String endDate) {

        this.startDate = LocalDate.parse(startDate, FORMAT);
        this.endDate = LocalDate.parse(endDate, FORMAT);
        this.col1 = new SimpleStringProperty(this.startDate.format(FORMAT));
        this.col2 = new SimpleStringProperty((this.endDate.format(FORMAT)));
        if (getTotalDays() <= 1) {
            this.col3 = new SimpleStringProperty(String.valueOf(getTotalDays()) + " day");
        }
        else {
            this.col3 = new SimpleStringProperty(String.valueOf(getTotalDays()) + " days");
        }

    }

    public String getCol1() {
        return this.col1.get();
    }

    public String getCol2() {
        return this.col2.get();
    }

    public String getCol3() {
        return this.col3.get();
    }

    /**
     * Check whether current date falls under this leave time frame.
     * @return true if current date falls under this leave time frame, false otherwise.
     */
    public boolean isOnLeave() {
        LocalDate currentDate = LocalDate.now();
        return !(currentDate.isBefore(startDate) || currentDate.isAfter(endDate));
    }

    /**
     * Returns true if a endDate and StartDate makes sense.
     */
    public static boolean isValidLeave(String startDate, String endDate) {
        final int b = LocalDate.parse(endDate, FORMAT).compareTo(LocalDate.parse(startDate, FORMAT));
        return b >= 0;
    }

    /**
     * Returns true if a leave overlaps with another.
     */
    public boolean isOverlapping(Leave otherLeave) {
        return (this.startDate.compareTo(otherLeave.startDate) >= 0
                && this.startDate.compareTo(otherLeave.endDate) <= 0)
                || (this.endDate.compareTo(otherLeave.startDate) >= 0
                && this.endDate.compareTo(otherLeave.endDate) <= 0);

    }

    /**
     * Returns a comparator for two Leave objects in a queue.
     */
    public static class CustomLeaveComparator implements Comparator<Leave> {
        @Override
        public int compare(Leave o1, Leave o2) {
            return o2.startDate.compareTo(o1.startDate);
        }
    }

    /**
     * Returns number of days
     */
    public int getTotalDays() {
        return (int) DAYS.between(startDate, endDate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Leave)) {
            return false;
        }

        Leave otherLeave = (Leave) other;
        return this.startDate.equals(otherLeave.startDate)
                && this.endDate.equals(otherLeave.endDate);
    }

    @Override
    public String toString() {
        return String.format("%s - %s", startDate, endDate);
    }
}
