package coydir.testutil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import coydir.commons.core.index.Index;
import coydir.model.Model;
import coydir.model.person.EmployeeId;
import coydir.model.person.Person;

/**
 * A utility class for test cases.
 */
public class TestUtil {

    /**
     * Folder used for temp files created during testing. Ignored by Git.
     */
    private static final Path SANDBOX_FOLDER = Paths.get("src", "test", "data", "sandbox");

    /**
     * Appends {@code fileName} to the sandbox folder path and returns the resulting path.
     * Creates the sandbox folder if it doesn't exist.
     */
    public static Path getFilePathInSandboxFolder(String fileName) {
        try {
            Files.createDirectories(SANDBOX_FOLDER);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return SANDBOX_FOLDER.resolve(fileName);
    }

    /**
     * Returns the middle index of the person in the {@code model}'s person list.
     */
    public static Index getMidIndex(Model model) {
        return Index.fromOneBased(model.getFilteredPersonList().size() / 2);
    }

    /**
     * Returns the last index of the person in the {@code model}'s person list.
     */
    public static Index getLastIndex(Model model) {
        return Index.fromOneBased(model.getFilteredPersonList().size());
    }

    /**
     * Returns the person in the {@code model}'s person list at {@code index}.
     */
    public static Person getPerson(Model model, Index index) {
        return model.getFilteredPersonList().get(index.getZeroBased());
    }

    public static int getMaxEmployeeId() {
        return EmployeeId.getCount();
    }

    public static void setMaxEmployeeId(int count) {
        EmployeeId.setCount(count);
    }

    public static void restartEmployeeId(int count) {
        EmployeeId.restart(count);
    }

    public static String getNextEmployeeId() {
        int count = EmployeeId.getCount();
        setMaxEmployeeId(count + 1);
        return String.valueOf(count);
    }

    /**
     * Resets (@code EmployeeId) class to the state it should have after loading typical persons
     */
    public static void resetTypicalEmployeeId() {
        restartEmployeeId(1);
        //7 is the number of employees in the typical
        for (int i = 0; i < 7; i++) {
            new EmployeeId();
        }
    }

}
