/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rm.workspace;

import csg.CourseSiteGenerator;
import djf.components.AppDataComponent;
import djf.components.AppWorkspaceComponent;
import djf.settings.AppPropertyType;
import static djf.settings.AppPropertyType.ADD_BUTTON_TEXT;
import static djf.settings.AppPropertyType.EDIT_BUTTON_TEXT;
import static djf.settings.AppPropertyType.UPDATE_BUTTON;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import properties_manager.PropertiesManager;
import rm.RecManagerApp;
import rm.RecManagerProp;
import rm.data.RecData;
import rm.data.RecRecord;
import tam.TAManagerApp;
import tam.data.TeachingAssistant;

/**
 *
 * @author trungvo
 */
public class RecWorkspace {
    CourseSiteGenerator csg;
    RecManagerApp app;
    TAManagerApp taApp;
    RecController recController;
    VBox wrapVBox;
    HBox headerBox;
    Label title;
    Button deleteButton;

    TableView<RecData> recTable;
    TableColumn<RecData, String> sectionCol;
    TableColumn<RecData, String> instructorCol;
    TableColumn<RecData, String> dayTimeCol;
    TableColumn<RecData, String> locationCol;
    TableColumn<RecData, String> ta1Col;
    TableColumn<RecData, String> ta2Col;

    VBox addEditBox;
    Label addEditTitle;
    GridPane addEditGrid;
    Label sectionLabel;
    TextField sectionTF;
    Label instructorLabel;
    TextField instructorTF;
    Label dayTimeLabel;
    TextField dayTimeTF;
    Label locationLabel;
    TextField locationTF;
    Label ta1Label;
    ComboBox ta1Combo;
    Label ta2Label;
    ComboBox ta2Combo;
    Button addUpdateButton;
    Button clearButton;
    
    BorderPane workspace;
    boolean workspaceActivated;
    
    public VBox getWrapVBox() {return wrapVBox;}
    public Label getTitle() {return title;}
    public HBox getHeaderBox() {return headerBox;}
    public VBox getAddEditBox() {return addEditBox;}
    public GridPane getaddEditGrid() {return addEditGrid;}
    
    public TextField getSectionTF() {return sectionTF;}
    public TextField getDayTimeTF() {return dayTimeTF;}
    public TextField getLocationTF() {return locationTF;}
    public TextField getInstructorTF() {return instructorTF;}
    public ComboBox getTa1Combo() {return ta1Combo;}
    public ComboBox getTa2Combo() {return ta2Combo;}
    public Button getAddUpdateButton() {return addUpdateButton;}
    public TableView getRecTable() {return recTable;}
    
    public BorderPane getWorkspace() {return workspace;}
    
    public RecWorkspace(RecManagerApp initApp, TAManagerApp taApp, CourseSiteGenerator csg) {
        app = initApp;
        this.taApp = taApp;
        this.csg = csg;
        
        // WE'LL NEED THIS TO GET LANGUAGE PROPERTIES FOR OUR UI
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        wrapVBox = new VBox();
        wrapVBox.setAlignment(Pos.CENTER);
        
        buildHeaderBox(props);
        buildTable(props);
        buildAddEditBox(props);
        wrapVBox.getChildren().addAll(headerBox, recTable, addEditBox);
        
        workspace = new BorderPane();
        ((BorderPane)workspace).setCenter(wrapVBox);
        workspace.setStyle("-fx-background-color: #B0C4DE");
        
        // Init Controller
        recController = new RecController(app);
        
        // Handle Delete Rec
        deleteButton.setOnAction(e -> {
            recController.handleDeleteRec();
            clearFields();
            // mark as edited, update toolbar
            csg.getGUI().getAppFileController().markAsEdited(csg.getGUI());
        });
        
        // handle Add/Update Button
        addUpdateButton.setOnAction(e -> {
            String newSection = sectionTF.getText();
            String newInstructor = instructorTF.getText();
            String newDayTimeTF = dayTimeTF.getText();
            String newLocation = locationTF.getText();
            String newTA1 = null;
            String newTA2 = null;
            if (ta1Combo.getSelectionModel().getSelectedItem() != null) {
                newTA1 = ta1Combo.getSelectionModel().getSelectedItem().toString();
            }
            if (ta2Combo.getSelectionModel().getSelectedItem() != null) {
                newTA2 = ta2Combo.getSelectionModel().getSelectedItem().toString();
            }
            RecData newRec = new RecData(newSection, newInstructor, newDayTimeTF, newLocation, newTA1, newTA2);
            
            boolean changed = false;
            if (addUpdateButton.getText().equals(props.getProperty(AppPropertyType.ADD_BUTTON_TEXT.toString()))){
                changed = recController.handleAddRec(newRec);
            } else if (addUpdateButton.getText().equals(props.getProperty(AppPropertyType.UPDATE_BUTTON.toString()))) {
                changed = recController.handleUpdateRec(newRec);
            }
            if (changed){
                // mark as edited, update toolbar
                csg.getGUI().getAppFileController().markAsEdited(csg.getGUI());
            }
        });
        
        // Clear Fields
        clearButton.setOnAction(e -> {
            clearFields();
        });
        
        // handle when clicking on Recitation Table, parse Info into text fields
        recTable.setOnMouseClicked(e -> {
            Object selectedItem = recTable.getSelectionModel().getSelectedItem();
            RecData rec = (RecData) selectedItem;
            if (rec != null){
                sectionTF.setText(rec.getSection());
                instructorTF.setText(rec.getInstructor());
                dayTimeTF.setText(rec.getDayTime());
                locationTF.setText(rec.getLocation());
                ta1Combo.getSelectionModel().select(getTAIndex(ta1Combo.getItems(), rec.getTa1()));
                ta2Combo.getSelectionModel().select(getTAIndex(ta2Combo.getItems(), rec.getTa2()));
                
                // Change button text to Edit
                addUpdateButton.setText(props.getProperty(AppPropertyType.UPDATE_BUTTON.toString()));
            }
        });
        
        // Handle Key Pressed on the Rec Table
        recTable.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
            Object selectedItem = recTable.getSelectionModel().getSelectedItem();
            if (selectedItem != null){
                RecData rec = (RecData) selectedItem;
                if (e.getCode() == KeyCode.BACK_SPACE || e.getCode() == KeyCode.DELETE){
                    recController.handleDeleteRec();
                    clearFields();
                    // mark as edited, update toolbar
                    csg.getGUI().getAppFileController().markAsEdited(csg.getGUI());
                } else if (e.getCode() == KeyCode.UP || e.getCode() == KeyCode.DOWN){
                    int indexOfOldRec = ((RecRecord)app.getDataComponent()).getRecRecord().indexOf(rec);
                    int indexOfNewRec;
                    RecData newRec = null;
                    
                    if (e.getCode() == KeyCode.UP){
                        if (indexOfOldRec != 0){
                            indexOfNewRec = indexOfOldRec - 1;
                            newRec = (RecData)((RecRecord)app.getDataComponent()).getRecRecord().get(indexOfNewRec);
                        }
                    } else if (e.getCode() == KeyCode.DOWN) {
                        if (indexOfOldRec != ((RecRecord)app.getDataComponent()).getRecRecord().size()-1){
                            indexOfNewRec = indexOfOldRec + 1;
                            newRec = (RecData)((RecRecord)app.getDataComponent()).getRecRecord().get(indexOfNewRec);
                        }
                    }
                    
                    // parse Info into text fields
                    if (newRec != null) {
                        sectionTF.setText(newRec.getSection());
                        instructorTF.setText(newRec.getInstructor());
                        dayTimeTF.setText(newRec.getDayTime());
                        locationTF.setText(newRec.getLocation());
                        ta1Combo.getSelectionModel().select(getTAIndex(ta1Combo.getItems(), newRec.getTa1()));
                        ta2Combo.getSelectionModel().select(getTAIndex(ta2Combo.getItems(), newRec.getTa2()));
                        
                        // Change button text to Edit
                        addUpdateButton.setText(props.getProperty(AppPropertyType.UPDATE_BUTTON.toString()));
                    }
                }
            }
        });
        
        // UNDO/REDO
        KeyCombination undo = new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN);
        KeyCombination redo = new KeyCodeCombination(KeyCode.Y, KeyCombination.CONTROL_DOWN);
        
        csg.getGUI().getAppPane().setOnKeyReleased(e -> {
            
        });
    }
    
    public int getTAIndex(ObservableList<String> taList, String taName) {
        for (int i=0; i<taList.size(); i++){
            if (taList.get(i).equals(taName)) {
                return i;
            }
        }
        return -1;
    }
    
    public void clearFields() {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        sectionTF.clear();
        dayTimeTF.clear();
        locationTF.clear();
        instructorTF.clear();
        ta1Combo.getSelectionModel().clearSelection();
        ta2Combo.getSelectionModel().clearSelection();
            
        // Change Button text to Add
        addUpdateButton.setText(props.getProperty(AppPropertyType.ADD_BUTTON_TEXT.toString()));
        recTable.getSelectionModel().clearSelection();
    }
    
    public void buildHeaderBox(PropertiesManager props) {
        headerBox = new HBox();
        // REC TITLE
        title = new Label(props.getProperty(RecManagerProp.RECITATION_TITLE.toString()));
        // Del button
        deleteButton = new Button("-");
        Tooltip buttonTooltip = new Tooltip(props.getProperty(RecManagerProp.DELETE_TOOLTIP.toString()));
        deleteButton.setTooltip(buttonTooltip);
        headerBox.getChildren().addAll(title, deleteButton);
        headerBox.setAlignment(Pos.CENTER);
    }
    
    public void buildTable(PropertiesManager props) {
        recTable = new TableView();
        recTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        RecRecord recRecord = (RecRecord)app.getDataComponent();
        ObservableList<RecData> recData = recRecord.getRecRecord();
        recTable.setItems(recData);
        
        // build columns
        sectionCol = new TableColumn(props.getProperty(RecManagerProp.RECITATION_SECTION_TABLECOLUMN.toString()));
        instructorCol = new TableColumn(props.getProperty(RecManagerProp.RECITATION_INSTRUCTOR_TABLECOLUMN.toString()));
        dayTimeCol = new TableColumn(props.getProperty(RecManagerProp.RECITATION_DAYTIME_TABLECOLUMN.toString()));
        locationCol = new TableColumn(props.getProperty(RecManagerProp.RECITATION_LOCATION_TABLECOLUMN.toString()));
        ta1Col = new TableColumn(props.getProperty(RecManagerProp.RECITATION_TA_TABLECOLUMN.toString()));
        ta2Col = new TableColumn(props.getProperty(RecManagerProp.RECITATION_TA_TABLECOLUMN.toString()));
        
        sectionCol.setCellValueFactory(new PropertyValueFactory<RecData, String>("section"));
        instructorCol.setCellValueFactory(new PropertyValueFactory<RecData, String>("instructor"));
        dayTimeCol.setCellValueFactory(new PropertyValueFactory<RecData, String>("dayTime"));
        locationCol.setCellValueFactory(new PropertyValueFactory<RecData, String>("location"));
        ta1Col.setCellValueFactory(new PropertyValueFactory<RecData, String>("ta1"));
        ta2Col.setCellValueFactory(new PropertyValueFactory<RecData, String>("ta2"));
        
        recTable.getColumns().addAll(sectionCol, instructorCol, dayTimeCol, locationCol, ta1Col, ta2Col);
        sectionCol.prefWidthProperty().bind(recTable.widthProperty().multiply(0.1));
        instructorCol.prefWidthProperty().bind(recTable.widthProperty().multiply(0.1));
        dayTimeCol.prefWidthProperty().bind(recTable.widthProperty().multiply(0.2));
        locationCol.prefWidthProperty().bind(recTable.widthProperty().multiply(0.2));
        ta1Col.prefWidthProperty().bind(recTable.widthProperty().multiply(0.2));
        ta2Col.prefWidthProperty().bind(recTable.widthProperty().multiply(0.2));
        
        recTable.maxWidthProperty().bind(wrapVBox.widthProperty().multiply(0.8));
    }
    
    public void buildAddEditBox(PropertiesManager props) {
        addEditTitle = new Label(props.getProperty(RecManagerProp.RECITATION_ADDEDIT_LABEL.toString()));
        sectionLabel = new Label(props.getProperty(RecManagerProp.RECITATION_SECTION_LABEL.toString()));
        instructorLabel = new Label(props.getProperty(RecManagerProp.RECITATION_INSTRUCTOR_LABEL.toString()));
        dayTimeLabel = new Label(props.getProperty(RecManagerProp.RECITATION_DAYTIME_LABEL.toString()));
        locationLabel = new Label(props.getProperty(RecManagerProp.RECITATION_LOCATION_LABEL.toString()));
        ta1Label = new Label(props.getProperty(RecManagerProp.RECITATION_TA_LABEL.toString()));
        ta2Label = new Label(props.getProperty(RecManagerProp.RECITATION_TA_LABEL.toString()));
        addUpdateButton = new Button(props.getProperty(AppPropertyType.ADD_BUTTON_TEXT.toString()));
        clearButton = new Button(props.getProperty(RecManagerProp.RECITATION_CLEAR_BUTTON.toString()));
        
        sectionTF = new TextField();
        sectionTF.setPromptText(props.getProperty(RecManagerProp.SECTION_PROMPT_TEXT.toString()));
        instructorTF = new TextField();
        instructorTF.setPromptText(props.getProperty(RecManagerProp.INSTRUCTOR_PROMPT_TEXT.toString()));
        dayTimeTF = new TextField();
        dayTimeTF.setPromptText(props.getProperty(RecManagerProp.DAYTIME_PROMPT_TEXT.toString()));
        locationTF = new TextField();
        locationTF.setPromptText(props.getProperty(RecManagerProp.LOCATION_PROMPT_TEXT.toString()));
        
        //String[] ta1List = {"Trung Vo", "Hong Phuong", "Aiko", "Ron"};
        ta1Combo = new ComboBox();
        ta2Combo = new ComboBox();
        if (taApp.getDataComponent() != null) {
            ta1Combo.setItems(getTAList(taApp.getDataComponent().getTeachingAssistants()));
            ta2Combo.setItems(getTAList(taApp.getDataComponent().getTeachingAssistants()));
        }
        // put into grid pane
        addEditGrid = new GridPane();
        addEditGrid.add(sectionLabel, 0, 0);
        addEditGrid.add(sectionTF, 1, 0);
        addEditGrid.add(instructorLabel, 0, 1);
        addEditGrid.add(instructorTF, 1, 1);
        addEditGrid.add(dayTimeLabel, 0, 2);
        addEditGrid.add(dayTimeTF, 1, 2);
        addEditGrid.add(locationLabel, 0, 3);
        addEditGrid.add(locationTF, 1, 3);
        addEditGrid.add(ta1Label, 0, 4);
        addEditGrid.add(ta1Combo, 1, 4);
        addEditGrid.add(ta2Label, 0, 5);
        addEditGrid.add(ta2Combo, 1, 5);
        addEditGrid.add(addUpdateButton, 0, 6);
        addEditGrid.add(clearButton, 1, 6);
        addEditGrid.setHgap(10);
        addEditGrid.setVgap(10);
        addEditGrid.setAlignment(Pos.CENTER);
        
        addEditBox = new VBox();
        addEditBox.getChildren().addAll(addEditTitle, addEditGrid);
        addEditBox.setAlignment(Pos.CENTER);
        addEditBox.maxWidthProperty().bind(wrapVBox.widthProperty().multiply(0.8));
        dayTimeTF.minWidthProperty().bind(addEditBox.widthProperty().multiply(0.5));
    }
    
    public ObservableList<String> getTAList(ObservableList<TeachingAssistant> taList) {
        ObservableList<String> res = FXCollections.observableArrayList();
        for (TeachingAssistant ta:taList){
            res.add(ta.getName());
        }
        return res;
    }
    
    public ObservableList<String> generateComboBoxText(String[] list) {
        ObservableList<String> res = FXCollections.observableArrayList();
        for (String i:list){
            res.add(i);
        }
        return res;
    }
    
    public void activateWorkspace(BorderPane appPane) {
        if (!workspaceActivated) {
            // PUT THE WORKSPACE IN THE GUI
            appPane.setCenter(workspace);
            workspaceActivated = true;
        }
    }
    
    public void resetWorkspace() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void reloadWorkspace(RecRecord dataComponent) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
