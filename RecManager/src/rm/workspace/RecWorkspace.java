/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rm.workspace;

import djf.components.AppDataComponent;
import djf.components.AppWorkspaceComponent;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import properties_manager.PropertiesManager;
import rm.RecManagerApp;
import rm.RecManagerProp;
import rm.data.RecData;
import rm.data.RecRecord;

/**
 *
 * @author trungvo
 */
public class RecWorkspace extends AppWorkspaceComponent {
    RecManagerApp app;
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
    
    public VBox getWrapVBox() {return wrapVBox;}
    public Label getTitle() {return title;}
    public HBox getHeaderBox() {return headerBox;}
    public VBox getAddEditBox() {return addEditBox;}
    public GridPane getaddEditGrid() {return addEditGrid;}
    
    public RecWorkspace(RecManagerApp initApp) {
        app = initApp;
        
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
    }
    
    public void buildHeaderBox(PropertiesManager props) {
        headerBox = new HBox();
        // REC TITLE
        title = new Label(props.getProperty(RecManagerProp.RECITATION_TITLE.toString()));
        // Del button
        deleteButton = new Button("-");
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
        addUpdateButton = new Button(props.getProperty(RecManagerProp.RECITATION_ADDUPDATE_BUTTON.toString()));
        clearButton = new Button(props.getProperty(RecManagerProp.RECITATION_CLEAR_BUTTON.toString()));
        
        sectionTF = new TextField();
        sectionTF.setPromptText(props.getProperty(RecManagerProp.SECTION_PROMPT_TEXT.toString()));
        instructorTF = new TextField();
        instructorTF.setPromptText(props.getProperty(RecManagerProp.INSTRUCTOR_PROMPT_TEXT.toString()));
        dayTimeTF = new TextField();
        dayTimeTF.setPromptText(props.getProperty(RecManagerProp.DAYTIME_PROMPT_TEXT.toString()));
        locationTF = new TextField();
        locationTF.setPromptText(props.getProperty(RecManagerProp.LOCATION_PROMPT_TEXT.toString()));
        
        String[] ta1List = {"Trung Vo", "Hong Phuong", "Aiko", "Ron"};
        ta1Combo = new ComboBox(generateComboBoxText(ta1List));
        ta2Combo = new ComboBox(generateComboBoxText(ta1List));
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
