package coydir.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /** Help information should be shown to the user. */
    private final boolean showHelp;

    /** The application should exit. */
    private final boolean exit;

    /** The application should display a person's profile. */
    private final boolean viewPerson;

    /** The home panel should be displayed. */
    private final boolean goHome;

    /** The displayed person should be updated. */
    private final boolean update;

    private int viewIndex;


    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit, boolean goHome) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.goHome = goHome;
        this.viewPerson = false;
        this.update = true;
        this.viewIndex = -1;
    }

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     * Used only for the {@code View} command.
     */
    public CommandResult(String feedbackToUser, int index) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = false;
        this.exit = false;
        this.goHome = false;
        this.viewPerson = true;
        this.update = false;
        this.viewIndex = index;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false, false);
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public boolean isShowHelp() {
        return showHelp;
    }

    public boolean isExit() {
        return exit;
    }

    public boolean isViewPerson() {
        return viewPerson;
    }

    public boolean isGoHome() {
        return goHome;
    }

    public boolean isUpdate() {
        return update;
    }

    public int getViewIndex() {
        return viewIndex;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommandResult)) {
            return false;
        }

        CommandResult otherCommandResult = (CommandResult) other;
        return feedbackToUser.equals(otherCommandResult.feedbackToUser)
                && showHelp == otherCommandResult.showHelp
                && exit == otherCommandResult.exit
                && viewPerson == otherCommandResult.viewPerson
                && goHome == otherCommandResult.goHome
                && update == otherCommandResult.update
                && viewIndex == otherCommandResult.viewIndex;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit, viewPerson, goHome, update, viewIndex);
    }

}
