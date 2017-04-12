
package sm.workspace;

import djf.components.AppDataComponent;
import djf.components.AppWorkspaceComponent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import properties_manager.PropertiesManager;
import sm.ScheduleManagerApp;
import sm.ScheduleManagerProp;
import sm.data.ScheduleData;
import sm.data.ScheduleTopic;

/**
 *
 * @author trungvo
 */
public class ScheduleWorkspace extends AppWorkspaceComponent {
    ScheduleManagerApp app;
    VBox wrapVBox;
    Label scheTitle;
    
    VBox calendarWrapBox;
    Label calendarTitle;
    HBox calendarHBox;
    Label startMonLabel;
    DatePicker startPicker;
    Label endFriLabel;
    DatePicker endPicker;

    VBox scheduleBox;
    Label scheduleLabel;
    Button deleteScheduleButton;
    HBox scheduleHeaderBox;
    TableView<ScheduleTopic> scheduleTable;
    TableColumn<ScheduleTopic, String> scheduleType;
    TableColumn<ScheduleTopic, String> scheduleDate;
    TableColumn<ScheduleTopic, String> scheduleTitle;
    TableColumn<ScheduleTopic, String> scheduleTopic; 

    Label addEditLabel;
    GridPane addEditGrid;
    Label typeLabel;
    ComboBox typeCombo;
    Label dateLabel;
    DatePicker datePicker;
    Label timeLabel;
    TextField timeTF;
    Label titleLabel;
    TextField titleTF;
    Label topicLabel;
    TextField topicTF;
    Label linkLabel;
    TextField linkTF;
    Label criteriaLabel;
    TextField criteriaTF;
    Button addUpdateButton;
    Button clearButton;
    
    public VBox getWrapVBox() {return wrapVBox;}
    public Label getTitle() {return scheTitle;}
    public VBox getCalendarWrapBox() {return calendarWrapBox;}
    public Label getCalendarTitle() {return calendarTitle;}
    public Label getStartMonLabel() {return startMonLabel;}
    public Label getEndFriLabel() {return endFriLabel;}
    
    public VBox getScheduleBox() {return scheduleBox;}
    public Label getScheduleLabel() {return scheduleLabel;}
    public HBox getScheduleHeaderBox() {return scheduleHeaderBox;}
    public TableView getScheTable() {return scheduleTable;}
    public Label getAddEditLabel() {return addEditLabel;}
    public GridPane getAddEditGrid() {return addEditGrid;}
    
    public ScheduleWorkspace(ScheduleManagerApp app) {
        this.app = app;
        
        // WE'LL NEED THIS TO GET LANGUAGE PROPERTIES FOR OUR UI
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        wrapVBox = new VBox();
        wrapVBox.setAlignment(Pos.CENTER);
        scheTitle = new Label(props.getProperty(ScheduleManagerProp.SCHEDULE_TITLE.toString()));
        wrapVBox.getChildren().add(scheTitle);
        
        buildCalendarBox(props);
        wrapVBox.getChildren().add(calendarWrapBox);
        
        buildScheduleBox(props);
        wrapVBox.getChildren().add(scheduleBox);
        
        workspace = new BorderPane();
        ((BorderPane)workspace).setCenter(wrapVBox);
        workspace.setStyle("-fx-background-color: #B0C4DE");
    }
    
    private void buildCalendarBox(PropertiesManager props) {
        calendarWrapBox = new VBox();
        calendarTitle = new Label(props.getProperty(ScheduleManagerProp.CALENDAR_LABEL.toString()));
        calendarHBox = new HBox();
        
        startMonLabel = new Label(props.getProperty(ScheduleManagerProp.START_MON_LABEL.toString()));
        endFriLabel = new Label(props.getProperty(ScheduleManagerProp.END_FRI_LABEL.toString()));
        startPicker = new DatePicker();
        endPicker = new DatePicker();
        
        calendarHBox.getChildren().addAll(startMonLabel, startPicker, endFriLabel, endPicker);
        calendarWrapBox.getChildren().addAll(calendarTitle, calendarHBox);
        calendarWrapBox.setAlignment(Pos.CENTER);
        calendarHBox.setAlignment(Pos.CENTER);
    }
    
    private void buildScheduleBox(PropertiesManager props) {
        scheduleBox = new VBox();
        scheduleBox.setAlignment(Pos.CENTER);
        scheduleLabel = new Label(props.getProperty(ScheduleManagerProp.SCHEDULE_LABEL.toString()));
        deleteScheduleButton = new Button("-");
        scheduleHeaderBox = new HBox();
        scheduleHeaderBox.setAlignment(Pos.CENTER);
        scheduleHeaderBox.getChildren().addAll(scheduleLabel, deleteScheduleButton);
        
        // Build table
        scheduleTable = new TableView();
        scheduleTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        ScheduleData scheData = (ScheduleData)app.getDataComponent();
        ObservableList<ScheduleTopic> scheduleList = scheData.getScheduleList();
        scheduleTable.setItems(scheduleList);
        scheduleTable.maxWidthProperty().bind(scheduleBox.widthProperty().multiply(0.6));
        
        // Build columns
        scheduleType = new TableColumn(props.getProperty(ScheduleManagerProp.TYPE_TABLECOLUMN.toString()));
        scheduleDate = new TableColumn(props.getProperty(ScheduleManagerProp.DATE_TABLECOLUMN.toString()));
        scheduleTitle = new TableColumn(props.getProperty(ScheduleManagerProp.TITLE_TABLECOLUMN.toString()));
        scheduleTopic = new TableColumn(props.getProperty(ScheduleManagerProp.TOPIC_TABLECOLUMN.toString()));
        
        scheduleType.setCellValueFactory(new PropertyValueFactory<ScheduleTopic, String>("type"));
        scheduleDate.setCellValueFactory(new PropertyValueFactory<ScheduleTopic, String>("date"));
        scheduleTitle.setCellValueFactory(new PropertyValueFactory<ScheduleTopic, String>("title"));
        scheduleTopic.setCellValueFactory(new PropertyValueFactory<ScheduleTopic, String>("topic"));
        
        scheduleTable.getColumns().addAll(scheduleType, scheduleDate, scheduleTitle, scheduleTopic);
        scheduleType.prefWidthProperty().bind(scheduleTable.widthProperty().multiply(0.2));
        scheduleDate.prefWidthProperty().bind(scheduleTable.widthProperty().multiply(0.2));
        scheduleTitle.prefWidthProperty().bind(scheduleTable.widthProperty().multiply(0.3));
        scheduleTopic.prefWidthProperty().bind(scheduleTable.widthProperty().multiply(0.3));
        
        // Add/Edit
        addEditLabel = new Label(props.getProperty(ScheduleManagerProp.SCHE_ADDEDIT_LABEL.toString()));
        addEditLabel.setAlignment(Pos.CENTER);
        typeLabel = new Label(props.getProperty(ScheduleManagerProp.TYPE_LABEL.toString()));
        dateLabel = new Label(props.getProperty(ScheduleManagerProp.DATE_LABEL.toString()));
        timeLabel = new Label(props.getProperty(ScheduleManagerProp.TIME_LABEL.toString()));
        titleLabel = new Label(props.getProperty(ScheduleManagerProp.TITLE_LABEL.toString()));
        topicLabel = new Label(props.getProperty(ScheduleManagerProp.TOPIC_LABEL.toString()));
        linkLabel = new Label(props.getProperty(ScheduleManagerProp.LINK_LABEL.toString()));
        criteriaLabel = new Label(props.getProperty(ScheduleManagerProp.CRITERIA_LABEL.toString()));
        
        String[] typeList = {"Holiday", "Snow Day", "Reading Day", "Spring Break", "Winter Break"};
        typeCombo = new ComboBox(generateComboBoxText(typeList));
        datePicker = new DatePicker();
        
        timeTF = new TextField();
        timeTF.prefWidthProperty().bind(scheduleBox.widthProperty().multiply(0.5));
        timeTF.setPromptText(props.getProperty(ScheduleManagerProp.TIME_PROMPT_TEXT.toString()));
        titleTF = new TextField();
        titleTF.setPromptText(props.getProperty(ScheduleManagerProp.TITLE_PROMPT_TEXT.toString()));
        topicTF = new TextField();
        topicTF.setPromptText(props.getProperty(ScheduleManagerProp.TOPIC_PROMPT_TEXT.toString()));
        linkTF = new TextField();
        linkTF.setPromptText(props.getProperty(ScheduleManagerProp.LINK_PROMPT_TEXT.toString()));
        criteriaTF = new TextField();
        criteriaTF.setPromptText(props.getProperty(ScheduleManagerProp.CRITERIA_PROMPT_TEXT.toString()));
        
        // buttons
        addUpdateButton = new Button(props.getProperty(ScheduleManagerProp.SCHE_ADDUPDATE_BUTTON.toString()));
        clearButton = new Button(props.getProperty(ScheduleManagerProp.SCHE_CLEAR_BUTTON.toString()));
        
        // combine
        addEditGrid = new GridPane();
        addEditGrid.add(typeLabel, 0, 0);
        addEditGrid.add(typeCombo, 1, 0);
        addEditGrid.add(dateLabel, 0, 1);
        addEditGrid.add(datePicker, 1, 1);
        addEditGrid.add(timeLabel, 0, 2);
        addEditGrid.add(timeTF, 1, 2);
        addEditGrid.add(titleLabel, 0, 3);
        addEditGrid.add(titleTF, 1, 3);
        addEditGrid.add(topicLabel, 0, 4);
        addEditGrid.add(topicTF, 1, 4);
        addEditGrid.add(linkLabel, 0, 5);
        addEditGrid.add(linkTF, 1, 5);
        addEditGrid.add(criteriaLabel, 0, 6);
        addEditGrid.add(criteriaTF, 1, 6);
        addEditGrid.add(addUpdateButton, 0, 7);
        addEditGrid.add(clearButton, 1, 7);
        addEditGrid.setHgap(10);
        addEditGrid.setVgap(10);
        
        addEditGrid.setAlignment(Pos.CENTER);
        scheduleBox.getChildren().addAll(scheduleHeaderBox, scheduleTable, addEditLabel, addEditGrid);
    }
    
    public ObservableList<String> generateComboBoxText(String[] list) {
        ObservableList<String> res = FXCollections.observableArrayList();
        for (String i:list){
            res.add(i);
        }
        return res;
    }
    
    @Override
    public void resetWorkspace() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void reloadWorkspace(AppDataComponent dataComponent) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
