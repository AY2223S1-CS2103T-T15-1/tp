package coydir.ui;

import java.time.format.DateTimeFormatter;
import java.util.Comparator;

import coydir.model.person.Leave;
import coydir.model.person.Person;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * An UI component that displays detailed information of a {@code Person}.
 */
public class PersonInfo extends UiPart<Region> {
    private static final String FXML = "PersonInfo.fxml";

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private VBox personInfo;
    @FXML
    private Label name;
    @FXML
    private Label employeeId;
    @FXML
    private Label phone;
    @FXML
    private Label email;
    @FXML
    private Label position;
    @FXML
    private Label onLeave;
    @FXML
    private Label department;
    @FXML
    private Label address;
    @FXML
    private Label totalLeave;
    @FXML
    private Label leaveLeft;
    @FXML
    private Label performance;
    @FXML
    private FlowPane tags;
    @FXML
    private TableView<Leave> leaveTable;
    @FXML
    private LineChart<String, Number> lineChart;

    /**
     * Creates a {@code PersonInfo} to display the {@code Person} particulars.
     */
    public PersonInfo(Person person) {
        super(FXML);
        update(person);
    }

    /**
     * Initializing the leave table at the start of the program.
     */
    public void initializeLeaveTable() {
        leaveTable.setSelectionModel(null);
        leaveTable.setEditable(false);
        leaveTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        leaveTable.addEventFilter(MouseEvent.ANY, MouseEvent::consume);
    }

    /**
     * Update the person particulars in the {@code PersonInfo} panel.
     * @param person the person to be displayed
     */
    public void update(Person person) {
        name.setText(person.getName().fullName);
        employeeId.setText("Employee ID:  " + String.format("%6s", person.getEmployeeId().value).replace(' ', '0'));
        phone.setText("Phone number:  " + person.getPhone().value);
        email.setText("Email address:  " + person.getEmail().value);
        position.setText("Position:  " + person.getPosition().value);
        department.setText("Department:  " + person.getDepartment().value);
        address.setText("Address:  " + person.getAddress().value);
        totalLeave.setText("Total Leaves: " + person.getTotalNumberOfLeaves());
        leaveLeft.setText("Leaves Left: " + person.getLeavesLeft());
        performance.setText("Performance: " + person.getRating().value);
        onLeave.setText("On leave: " + person.onLeaveStatus());
        tags.getChildren().clear();
        leaveTable.setItems(person.getObservableListLeaves());
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));

        TableColumn<Leave, String> index = new TableColumn<>("No.");
        index.setCellFactory(col -> new TableCell<Leave, String>() {
            @Override
            public void updateIndex(int index) {
                super.updateIndex(index);
                if (isEmpty() || index < 0) {
                    setText(null);
                } else {
                    setText(Integer.toString(index + 1));
                }
            }
        });
        index.setSortable(false);
        index.setReorderable(false);
        index.setMaxWidth(2000);

        TableColumn<Leave, String> startDate = new TableColumn<>("Start Date");
        startDate.setCellValueFactory(new PropertyValueFactory<>("col1"));
        startDate.setSortable(false);
        startDate.setReorderable(false);

        TableColumn<Leave, String> endDate = new TableColumn<>("End Date");
        endDate.setCellValueFactory(new PropertyValueFactory<>("col2"));
        endDate.setSortable(false);
        endDate.setReorderable(false);

        TableColumn<Leave, Integer> durations = new TableColumn<>("Durations");
        durations.setCellValueFactory(new PropertyValueFactory<>("col3"));
        durations.setSortable(false);
        durations.setReorderable(false);
        leaveTable.getColumns().clear();
        leaveTable.getColumns().addAll(index, startDate, endDate, durations);

        lineChart.setAnimated(false);
        lineChart.setTitle("Performance History");
        yAxis.setLabel("Rating");
        xAxis.setLabel("Timestamp");

        XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
        series.setName("Performance");

        for (int i = 0; i < person.getRatingHistory().size(); i++) {
            String timestamp = person.getRatingHistory().get(i).timestamp
                .format(DateTimeFormatter.ofPattern("dd-MMM-yy"));
            int value = Integer.parseInt(person.getRatingHistory().get(i).value);
            series.getData().add(new XYChart.Data<String, Number>(timestamp, value));
        }

        lineChart.getData().clear();
        lineChart.getData().add(series);

        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
