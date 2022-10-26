package coydir.model.person;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

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

    /**
     * Constructs a {@code Leave}.
     *
     * @param startDate A valid start date.
     * @param endDate A valid end date.
     */
    public Leave(String startDate, String endDate) {

        this.startDate = LocalDate.parse(startDate, FORMAT);
        this.endDate = LocalDate.parse(endDate, FORMAT);
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
        return this.endDate.compareTo(this.startDate) + 1;
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
