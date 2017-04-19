
package pm.workspace;

import djf.components.AppDataComponent;
import djf.components.AppWorkspaceComponent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import pm.ProjectManagerApp;
import pm.ProjectManagerProp;
import pm.data.ProjectRecord;
import pm.data.StudentData;
import pm.data.TeamData;
import properties_manager.PropertiesManager;

public class ProjectWorkspace {
    
    ProjectManagerApp app;
    ScrollPane scrollPane;
    VBox wrapVBox;
    Label projectTitle;
    VBox teamVBox;
    HBox teamHeaderBox;
    Label teamTitle;
    Button deleteTeamButton;
    TableView<TeamData> teamTable;
    TableColumn<TeamData, String> nameCol;
    TableColumn<TeamData, String> colorCol;
    TableColumn<TeamData, String> textColorCol;
    TableColumn<TeamData, String> linkCol;
    GridPane addEditTeamGrid;
    Label addEditTeamTitle;
    Label nameLabel;
    TextField nameTF;
    Label colorLabel;
    Label textColorLabel;
    Label linkLabel;
    TextField linkTF;
    Button addUpdateTeamButton;
    Button clearTeamButton;
    ColorPicker colorCircle;
    ColorPicker textColorCircle;
    TextField colorTF;
    TextField textColorTF;

    VBox studentVBox;
    HBox studentHeaderBox;
    Label studentLabel;
    Button deleteStudentButton;
    TableView<StudentData> studentTable;
    TableColumn<StudentData, String> firstNameCol;
    TableColumn<StudentData, String> lastNameCol;
    TableColumn<StudentData, String> teamCol;
    TableColumn<StudentData, String> rolCol;
    GridPane addEditStudentGrid;
    Label addEditStudentTitle;
    Label firstNameLabel;
    TextField firstNameTF;
    Label lastNameLabel;
    TextField lastNameTF;
    Label teamsLabel;
    ComboBox teamCombo;
    Label roleLabel;
    TextField roleTF;
    Button addUpdateStudentButton;
    Button clearStudentButton;
    
    BorderPane workspace;
    boolean workspaceActivated;
    
    public VBox getWrapVBox() {return wrapVBox;}
    public Label getProjectTitle() {return projectTitle;}
    public Label getTeamTitle() {return teamTitle;}
    public Label getStudentLabel() {return studentLabel;}
    public Label getAddEditTeamLabel() {return addEditTeamTitle;}
    public Label getAddEditStudentLabel() {return addEditStudentTitle;}
    public Label getTextColorLabel() {return textColorLabel;}
    
    public HBox getTeamHeaderBox() {return teamHeaderBox;}
    public HBox getStudentHeaderBox() {return studentHeaderBox;}
    
    public VBox getTeamVBox() {return teamVBox;}
    public VBox getStudentVBox() {return studentVBox;}
    
    public BorderPane getWorkspace() {return workspace;}
    
    public ProjectWorkspace(ProjectManagerApp app) {
        this.app = app;
        
        // WE'LL NEED THIS TO GET LANGUAGE PROPERTIES FOR OUR UI
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        wrapVBox = new VBox();
        wrapVBox.setAlignment(Pos.CENTER);
        scrollPane = new ScrollPane(wrapVBox);
        wrapVBox.prefWidthProperty().bind(scrollPane.widthProperty().multiply(1));
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        
        projectTitle = new Label(props.getProperty(ProjectManagerProp.PROJECT_TITLE.toString()));
        wrapVBox.getChildren().add(projectTitle);
        
        buildTeamVBox(props);
        wrapVBox.getChildren().add(teamVBox);
        
        buildStudentVBox(props);
        wrapVBox.getChildren().add(studentVBox);
        
        workspace = new BorderPane();
        workspace.setCenter(scrollPane);
        wrapVBox.setStyle("-fx-background-color: #B0C4DE");
        
    }
    
    public void activateWorkspace(BorderPane appPane) {
        if (!workspaceActivated) {
            // PUT THE WORKSPACE IN THE GUI
            appPane.setCenter(workspace);
            workspaceActivated = true;
        }
    }
    
    private void buildTeamVBox(PropertiesManager props) {
        teamTitle = new Label(props.getProperty(ProjectManagerProp.TEAMS_TITLE.toString()));
        deleteTeamButton = new Button("-");
        Tooltip buttonTooltip = new Tooltip(props.getProperty(ProjectManagerProp.DELETE_TOOLTIP.toString()));
        deleteTeamButton.setTooltip(buttonTooltip);
        teamHeaderBox = new HBox();
        teamHeaderBox.getChildren().addAll(teamTitle, deleteTeamButton);
        
        // build Team table
         teamTable = new TableView();
         teamTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
         ProjectRecord projectRecord = (ProjectRecord) app.getDataComponent();
         ObservableList<TeamData> teamList = projectRecord.getTeamList();
         teamTable.setItems(teamList);
         
         // build columns for Team table
         nameCol = new TableColumn(props.getProperty(ProjectManagerProp.NAME_TABLECOLUMN.toString()));
         colorCol = new TableColumn(props.getProperty(ProjectManagerProp.COLOR_TABLECOLUMN.toString()));
         textColorCol = new TableColumn(props.getProperty(ProjectManagerProp.TEXTCOLOR_TABLECOLUMN.toString()));
         linkCol = new TableColumn(props.getProperty(ProjectManagerProp.LINK_TABLECOLUMN.toString()));
         
         nameCol.setCellValueFactory(new PropertyValueFactory<TeamData, String>("name"));
         colorCol.setCellValueFactory(new PropertyValueFactory<TeamData, String>("color"));
         textColorCol.setCellValueFactory(new PropertyValueFactory<TeamData, String>("textColor"));
         linkCol.setCellValueFactory(new PropertyValueFactory<TeamData, String>("link"));
         
         teamTable.getColumns().addAll(nameCol, colorCol, textColorCol, linkCol);
         nameCol.prefWidthProperty().bind(teamTable.widthProperty().multiply(0.2));
         colorCol.prefWidthProperty().bind(teamTable.widthProperty().multiply(0.2));
         textColorCol.prefWidthProperty().bind(teamTable.widthProperty().multiply(0.2));
         linkCol.prefWidthProperty().bind(teamTable.widthProperty().multiply(0.4));
         
         // Team Add/Edit
         addEditTeamTitle = new Label(props.getProperty(ProjectManagerProp.PROJ_ADDEDIT_LABEL.toString()));
         nameLabel = new Label(props.getProperty(ProjectManagerProp.NAME_LABEL.toString()));
         colorLabel = new Label(props.getProperty(ProjectManagerProp.COLOR_LABEL.toString()));
         textColorLabel = new Label(props.getProperty(ProjectManagerProp.TEXTCOLOR_LABEL.toString()));
         linkLabel = new Label(props.getProperty(ProjectManagerProp.LINK_LABEL.toString()));
         addUpdateTeamButton = new Button(props.getProperty(ProjectManagerProp.PROJ_ADDUPDATE_BUTTON.toString()));
         clearTeamButton = new Button(props.getProperty(ProjectManagerProp.PROJ_CLEAR_BUTTON.toString()));
         
         nameTF = new TextField();
         nameTF.setPromptText(props.getProperty(ProjectManagerProp.NAME_TABLECOLUMN.toString()));
         linkTF = new TextField();
         linkTF.setPromptText(props.getProperty(ProjectManagerProp.LINK_TABLECOLUMN.toString()));
         
         colorTF = new TextField();
         colorCircle = new ColorPicker();
         
         textColorTF = new TextField();
         textColorCircle = new ColorPicker();
         
         HBox tempBox = new HBox();
         tempBox.setAlignment(Pos.CENTER_LEFT);
         tempBox.getChildren().addAll(colorCircle, textColorLabel, textColorCircle);
         
         // Team grid
         addEditTeamGrid = new GridPane();
         addEditTeamGrid.setVgap(5);
         addEditTeamGrid.setHgap(10);
         addEditTeamGrid.add(nameLabel, 0, 0);
         addEditTeamGrid.add(nameTF, 1, 0);
         addEditTeamGrid.add(colorLabel, 0, 1);
         addEditTeamGrid.add(tempBox, 1, 1);
         addEditTeamGrid.add(linkLabel, 0, 2);
         addEditTeamGrid.add(linkTF, 1, 2);
         addEditTeamGrid.add(addUpdateTeamButton, 0, 3);
         addEditTeamGrid.add(clearTeamButton, 1, 3);
         
         // combine
         teamVBox = new VBox();
         teamVBox.getChildren().addAll(teamHeaderBox, teamTable, addEditTeamTitle, addEditTeamGrid);
         teamHeaderBox.setAlignment(Pos.CENTER);
         addEditTeamTitle.setAlignment(Pos.CENTER);
         addEditTeamGrid.setAlignment(Pos.CENTER);
         teamVBox.setAlignment(Pos.CENTER);
         
        teamVBox.setStyle("-fx-background-color: #E6E6FA");
        teamVBox.maxWidthProperty().bind(wrapVBox.widthProperty().multiply(0.8));
        teamTable.maxWidthProperty().bind(teamVBox.widthProperty().multiply(0.9));
        nameTF.minWidthProperty().bind(teamVBox.widthProperty().multiply(0.5));
    }
    
    private void buildStudentVBox(PropertiesManager props) {
        studentLabel = new Label(props.getProperty(ProjectManagerProp.STUDENT_LABEL.toString()));
        deleteStudentButton = new Button("-");
        Tooltip buttonTooltip = new Tooltip(props.getProperty(ProjectManagerProp.DELETE_TOOLTIP.toString()));
        deleteStudentButton.setTooltip(buttonTooltip);
        studentHeaderBox = new HBox();
        studentHeaderBox.setAlignment(Pos.CENTER);
        studentHeaderBox.getChildren().addAll(studentLabel, deleteStudentButton);
        
        // build Student table
        studentTable = new TableView();
        studentTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        ProjectRecord projectRecord = (ProjectRecord) app.getDataComponent();
        ObservableList<StudentData> studentList = projectRecord.getStudentList();
        studentTable.setItems(studentList);
        
        // build columns 
        firstNameCol = new TableColumn(props.getProperty(ProjectManagerProp.FIRSTNAME_TABLECOLUMN.toString()));
        lastNameCol = new TableColumn(props.getProperty(ProjectManagerProp.LASTNAME_TABLECOLUMN.toString()));
        teamCol = new TableColumn(props.getProperty(ProjectManagerProp.TEAM_TABLECOLUMN.toString()));
        rolCol = new TableColumn(props.getProperty(ProjectManagerProp.ROLE_TABLECOLUMN.toString()));
        
        firstNameCol.setCellValueFactory(new PropertyValueFactory<StudentData, String>("firstName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<StudentData, String>("lastName"));
        teamCol.setCellValueFactory(new PropertyValueFactory<StudentData, String>("team"));
        rolCol.setCellValueFactory(new PropertyValueFactory<StudentData, String>("role"));
        
        studentTable.getColumns().addAll(firstNameCol, lastNameCol, teamCol, rolCol);
        firstNameCol.prefWidthProperty().bind(studentTable.widthProperty().multiply(0.2));
        lastNameCol.prefWidthProperty().bind(studentTable.widthProperty().multiply(0.2));
        teamCol.prefWidthProperty().bind(studentTable.widthProperty().multiply(0.3));
        rolCol.prefWidthProperty().bind(studentTable.widthProperty().multiply(0.3));
        
        // add/edit
        addEditStudentTitle = new Label(props.getProperty(ProjectManagerProp.PROJ_ADDEDIT_LABEL.toString()));
        firstNameLabel = new Label(props.getProperty(ProjectManagerProp.FIRSTNAME_LABEL.toString()));
        lastNameLabel = new Label(props.getProperty(ProjectManagerProp.LASTNAME_LABEL.toString()));
        teamsLabel = new Label(props.getProperty(ProjectManagerProp.TEAM_LABEL.toString()));
        roleLabel = new Label(props.getProperty(ProjectManagerProp.ROLE_LABEL.toString()));
        addUpdateStudentButton = new Button(props.getProperty(ProjectManagerProp.PROJ_ADDUPDATE_BUTTON.toString()));
        clearStudentButton = new Button(props.getProperty(ProjectManagerProp.PROJ_CLEAR_BUTTON.toString()));
        
        firstNameTF = new TextField();
        lastNameTF = new TextField();
        roleTF = new TextField();
        firstNameTF.setPromptText(props.getProperty(ProjectManagerProp.FIRSTNAME_TABLECOLUMN.toString()));
        lastNameTF.setPromptText(props.getProperty(ProjectManagerProp.LASTNAME_TABLECOLUMN.toString()));
        roleTF.setPromptText(props.getProperty(ProjectManagerProp.ROLE_TABLECOLUMN.toString()));
        
        String[] teamList = {"A","B","C"};
        teamCombo = new ComboBox(generateComboBoxText(teamList));
        
        // grid
        addEditStudentGrid = new GridPane();
        addEditStudentGrid.setVgap(5);
        addEditStudentGrid.setHgap(10);
        addEditStudentGrid.add(firstNameLabel, 0, 0);
        addEditStudentGrid.add(firstNameTF, 1, 0);
        addEditStudentGrid.add(lastNameLabel, 0, 1);
        addEditStudentGrid.add(lastNameTF, 1, 1);
        addEditStudentGrid.add(teamsLabel, 0, 2);
        addEditStudentGrid.add(teamCombo, 1, 2);
        addEditStudentGrid.add(roleLabel, 0, 3);
        addEditStudentGrid.add(roleTF, 1, 3);
        addEditStudentGrid.add(addUpdateStudentButton, 0, 4);
        addEditStudentGrid.add(clearStudentButton, 1, 4);
        addEditStudentGrid.setAlignment(Pos.CENTER);
        
        studentVBox = new VBox();
        studentVBox.getChildren().addAll(studentHeaderBox, studentTable, addEditStudentTitle, addEditStudentGrid);
        studentVBox.setAlignment(Pos.CENTER);
        studentHeaderBox.setAlignment(Pos.CENTER);
        addEditStudentTitle.setAlignment(Pos.CENTER);
        addEditStudentGrid.setAlignment(Pos.CENTER);
        
        studentVBox.setStyle("-fx-background-color: #E6E6FA");
        studentVBox.maxWidthProperty().bind(wrapVBox.widthProperty().multiply(0.8));
        studentTable.maxWidthProperty().bind(studentVBox.widthProperty().multiply(0.9));
        firstNameTF.minWidthProperty().bind(studentVBox.widthProperty().multiply(0.5));
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

    public void reloadWorkspace(ProjectRecord dataComponent) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
