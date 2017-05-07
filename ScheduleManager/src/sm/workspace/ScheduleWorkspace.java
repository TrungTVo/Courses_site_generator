
package sm.workspace;

import csg.CourseSiteGenerator;
import djf.components.AppDataComponent;
import djf.components.AppWorkspaceComponent;
import djf.settings.AppPropertyType;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import properties_manager.PropertiesManager;
import sm.ScheduleManagerApp;
import sm.ScheduleManagerProp;
import sm.data.ScheduleData;
import sm.data.ScheduleTopic;

/**
 *
 * @author trungvo
 */
public class ScheduleWorkspace {
    ScheduleManagerApp app;
    ScheduleController scheController;
    CourseSiteGenerator csg;
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
    
    BorderPane workspace;
    boolean workspaceActivated;
    
    public DatePicker getStartPicker() {return startPicker;}
    public DatePicker getEndPicker() {return endPicker;}
    public ComboBox getTypeComboBox() {return typeCombo;}
    public DatePicker getDatePicker() {return datePicker;}
    public TextField getTimeTF() {return timeTF;}
    public TextField getTitleTF() {return titleTF;}
    public TextField getTopicTF() {return topicTF;}
    public TextField getLinkTF() {return linkTF;}
    public TextField getCriteriaTF() {return criteriaTF;}
    
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
    
    public BorderPane getWorkspace() {return workspace;}
    public ScheduleController getScheController() {return scheController;}
    
    public void parseDate(String startDate, String endDate){
        if (!startDate.isEmpty() && startDate != null)
            startPicker.setValue(LocalDate.parse(startDate, DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        if (!endDate.isEmpty() && endDate != null)
            endPicker.setValue(LocalDate.parse(endDate, DateTimeFormatter.ofPattern("MM/dd/yyyy")));
    }
    
    public ScheduleWorkspace(ScheduleManagerApp app, CourseSiteGenerator csg) {
        this.app = app;
        this.csg = csg;
        
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
        
        // Init controller
        scheController = new ScheduleController(app);
        
        clearButton.setOnAction(e -> {
            clearFields();
        });
        
        deleteScheduleButton.setOnAction(e -> {
            scheController.handleDeleteSchedule(scheduleTable.getSelectionModel().getSelectedItem());
            clearFields();
            // mark as edited, update toolbar
            csg.getGUI().getAppFileController().markAsEdited(csg.getGUI());
        });
        
        addUpdateButton.setOnAction(e -> {
            String type = null;
            String date = null;
            if (typeCombo.getSelectionModel().getSelectedItem() != null){
                type = typeCombo.getSelectionModel().getSelectedItem().toString();
            }
            if (datePicker.getValue() != null){
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                date = formatter.format(datePicker.getValue());
            }
            String time = timeTF.getText();
            String title = titleTF.getText();
            String topic = topicTF.getText();
            String link = linkTF.getText();
            String criteria = criteriaTF.getText();
            
            ScheduleTopic newTopic = new ScheduleTopic(type, date, time, title, topic, link, criteria);
            boolean changed = false;
            if (addUpdateButton.getText().equals(props.getProperty(AppPropertyType.ADD_BUTTON_TEXT.toString()))) {
                changed = scheController.handleAddSchedule(newTopic);
                if (changed){
                    // mark as edited, update toolbar
                    csg.getGUI().getAppFileController().markAsEdited(csg.getGUI());
                }
            } else if (addUpdateButton.getText().equals(props.getProperty(AppPropertyType.UPDATE_BUTTON.toString()))) {
                ScheduleTopic selectedTopic = scheduleTable.getSelectionModel().getSelectedItem();
                scheController.handleUpdateSchedule(selectedTopic, newTopic);
                // mark as edited, update toolbar
                csg.getGUI().getAppFileController().markAsEdited(csg.getGUI());
            }
        });
        
        // handle when clicking on Schedule table, parse Info into text fields
        scheduleTable.setOnMouseClicked(e -> {
            Object selectedItem = scheduleTable.getSelectionModel().getSelectedItem();
            ScheduleTopic sche = (ScheduleTopic) selectedItem;
            if (sche != null){
                typeCombo.setValue(sche.getType());
                datePicker.setValue(LocalDate.parse(sche.getDate(), DateTimeFormatter.ofPattern("MM/dd/yyyy")));
                timeTF.setText(sche.getTime());
                titleTF.setText(sche.getTitle());
                topicTF.setText(sche.getTopic());
                linkTF.setText(sche.getLink());
                criteriaTF.setText(sche.getCriteria());
                
                // change Add/update button to Update
                addUpdateButton.setText(props.getProperty(AppPropertyType.UPDATE_BUTTON.toString()));
            }
        });
        
        // Handle Key Pressed on the Schedule Table
        scheduleTable.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
            Object selectedItem = scheduleTable.getSelectionModel().getSelectedItem();
            if (selectedItem != null){
                ScheduleTopic sche = (ScheduleTopic) selectedItem;
                if (e.getCode() == KeyCode.BACK_SPACE || e.getCode() == KeyCode.DELETE){
                    scheController.handleDeleteSchedule(sche);
                    clearFields();
                    // mark as edited, update toolbar
                    csg.getGUI().getAppFileController().markAsEdited(csg.getGUI());
                }
                if (e.getCode() == KeyCode.UP || e.getCode() == KeyCode.DOWN){
                    int indexOfOldSche = ((ScheduleData)app.getDataComponent()).getScheduleList().indexOf(sche);
                    int indexOfNewSche;
                    ScheduleTopic newSche = null;
                    
                    if (e.getCode() == KeyCode.UP){
                        if (indexOfOldSche != 0){
                            indexOfNewSche = indexOfOldSche - 1;
                            newSche = (ScheduleTopic)((ScheduleData)app.getDataComponent()).getScheduleList().get(indexOfNewSche);
                        }
                    } else if (e.getCode() == KeyCode.DOWN){
                        if (indexOfOldSche != ((ScheduleData)app.getDataComponent()).getScheduleList().size()-1){
                            indexOfNewSche = indexOfOldSche + 1;
                            newSche = (ScheduleTopic)((ScheduleData)app.getDataComponent()).getScheduleList().get(indexOfNewSche);
                        }
                    }
                    
                    // parse Info into text fields
                    if (newSche != null){
                        typeCombo.setValue(newSche.getType());
                        datePicker.setValue(LocalDate.parse(newSche.getDate(), DateTimeFormatter.ofPattern("MM/dd/yyyy")));
                        timeTF.setText(newSche.getTime());
                        titleTF.setText(newSche.getTitle());
                        topicTF.setText(newSche.getTopic());
                        linkTF.setText(newSche.getLink());
                        criteriaTF.setText(newSche.getCriteria());
                        
                        // change Add/update button to Update
                        addUpdateButton.setText(props.getProperty(AppPropertyType.UPDATE_BUTTON.toString()));
                    }
                }
            }
        });
    }
    
    public void clearFields() {
        typeCombo.getSelectionModel().clearSelection();
        datePicker.setValue(null);
        timeTF.clear();
        titleTF.clear();
        topicTF.clear();
        linkTF.clear();
        criteriaTF.clear();
        
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        // change add/update button text to add
        addUpdateButton.setText(props.getProperty(AppPropertyType.ADD_BUTTON_TEXT.toString()));
        // clear selected item in table
        scheduleTable.getSelectionModel().clearSelection();
    }
    
    public void activateWorkspace(BorderPane appPane) {
        if (!workspaceActivated) {
            // PUT THE WORKSPACE IN THE GUI
            appPane.setCenter(workspace);
            workspaceActivated = true;
        }
    }
    
    private void buildCalendarBox(PropertiesManager props) {
        calendarWrapBox = new VBox();
        calendarTitle = new Label(props.getProperty(ScheduleManagerProp.CALENDAR_LABEL.toString()));
        calendarHBox = new HBox();
        
        startMonLabel = new Label(props.getProperty(ScheduleManagerProp.START_MON_LABEL.toString()));
        endFriLabel = new Label(props.getProperty(ScheduleManagerProp.END_FRI_LABEL.toString()));
        startPicker = new DatePicker();
        endPicker = new DatePicker();
        
        // if start date clicked, flag edited
        startPicker.setOnAction(e -> {
            csg.getGUI().getAppFileController().markAsEdited(csg.getGUI());
        });
        
        // if end date is clicked, flag edited
        endPicker.setOnAction(e -> {
            csg.getGUI().getAppFileController().markAsEdited(csg.getGUI());
        });
        
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
        Tooltip buttonTooltip = new Tooltip(props.getProperty(ScheduleManagerProp.DELETE_TOOLTIP.toString()));
        deleteScheduleButton.setTooltip(buttonTooltip);
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
        
        String[] typeList = {"Lectures","Holiday", "Snow Day", "Reading Day", "Spring Break", "Winter Break"};
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
        addUpdateButton = new Button(props.getProperty(AppPropertyType.ADD_BUTTON_TEXT.toString()));
        clearButton = new Button(props.getProperty(AppPropertyType.CLEAR_BUTTON_TEXT.toString()));
        
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
    
    public void resetWorkspace() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void reloadWorkspace(ScheduleData dataComponent) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
