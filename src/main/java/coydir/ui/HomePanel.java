package coydir.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;


/**
 * An UI component that displays the home "page" for the app.
 * Also acts as the default panel to return to, for when there is nothing to display,
 * e.g. after deleting the employee currently displayed.
 */
public class HomePanel extends UiPart<Region> {
    private static final String FXML = "HomePanel.fxml";
    private static final String LOGO = "COYDIR";
    private static final String MESSAGE = "Hello.";

    @FXML
    private VBox homePanel;
    @FXML
    private ImageView logo;
    @FXML
    private Label message;

    /**
     * Creates a {@code HomePanel} for display.
     */
    public HomePanel() {
        super(FXML);
        this.initialize();
    }

    private void initialize() {
        Image logoImage = new Image(this.getClass().getResourceAsStream("/images/logo.png"));
        logo.setImage(logoImage);
        message.setText(MESSAGE);
    }
}