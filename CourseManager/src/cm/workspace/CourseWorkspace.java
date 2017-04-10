/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.workspace;

import cm.CourseManagerApp;
import cm.CourseManagerProp;
import cm.data.SitePage;
import djf.components.AppDataComponent;
import djf.components.AppWorkspaceComponent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import properties_manager.PropertiesManager;

/**
 *
 * @author trungvo
 */
public class CourseWorkspace extends AppWorkspaceComponent {
    CourseManagerApp app;
    VBox outerWrap;
    GridPane infoGridPane;
    VBox temPlatePane;

    Label infoTitle;
    Label subjectLabel;
    ComboBox subjectCombo;
    Label numberLabel;
    ComboBox numberCombo;

    Label semesterLabel;
    ComboBox semesterCombo;
    Label yearLabel;
    ComboBox yearCombo;

    Label titleLabel;
    TextField titleTF; 
    Label instructorNameLabel;
    TextField instructorNameTF;
    Label instructorHomeLabel;
    TextField instructorHomeTF;
    Label exportDirLabel;
    Label dirLabel;
    Button courseExportDirChangeButton;

    HBox subjectBox;
    HBox semesterBox;
    HBox dirBox;
    BorderPane bpSubject;
    BorderPane bpSemester;
    VBox infoBox;

    Label templateTitle;
    Label explainText;
    Label templateDir;
    Button templateDirButton;
    Label siteTitle;
    TableView<SitePage> siteTable;		
    TableColumn<SitePage, CheckBox> useCol;
    TableColumn<SitePage, String> navBarTitleCol;
    TableColumn<SitePage, String> fileCol;
    TableColumn<SitePage, String> scriptCol;

    VBox pageStyleVBox;
    Label styleTitle;
    Label bannerImageLabel;
    Image bannerImage;
    Button bannerChangeButton;
    Label leftImageLabel;
    Image leftImage;
    Button leftImageButton;
    Label rightImageLabel;
    Image rightImage;
    Button rightImageButton;
    Label stylesheetLabel;
    ComboBox styleCombo;
    Label noteLabel;
    
    public CourseWorkspace(CourseManagerApp initApp) {
        app = initApp;
        
        // WE'LL NEED THIS TO GET LANGUAGE PROPERTIES FOR OUR UI
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        outerWrap = new VBox();
        infoGridPane = new GridPane();
        temPlatePane = new VBox();
        pageStyleVBox = new VBox();
        
        buildInfoSection(props);
        infoBox = new VBox();
        infoBox.getChildren().addAll(infoTitle, infoGridPane);
        outerWrap.getChildren().add(infoBox);
        workspace = new BorderPane();
        ((BorderPane)workspace).setCenter(outerWrap);
    }
    
    private void buildInfoSection(PropertiesManager props) {
        
        infoTitle = new Label(props.getProperty(CourseManagerProp.COURSEINFO_TITLE.toString()));
        
        // line 1
        subjectLabel = new Label(props.getProperty(CourseManagerProp.SUBJECT_LABEL.toString()));
        String[] courseList = {"CSE","ISE","ITS"};
        subjectCombo = new ComboBox(generateComboBoxText(courseList));
        numberLabel = new Label(props.getProperty(CourseManagerProp.NUMBER_LABEL.toString()));
        String[] numberList = {"219","308","380","102"};
        numberCombo = new ComboBox(generateComboBoxText(numberList));
        
        subjectBox = new HBox(numberLabel);
        subjectBox.getChildren().add(numberCombo);
        bpSubject = new BorderPane();
        bpSubject.setRight(subjectBox);
        bpSubject.setLeft(subjectCombo);
        
        // line 2
        semesterLabel = new Label(props.getProperty(CourseManagerProp.SEMESTER_LABEL.toString()));
        String[] semesterList = {"Fall","Spring"};
        semesterCombo = new ComboBox(generateComboBoxText(semesterList));
        yearLabel = new Label(props.getProperty(CourseManagerProp.YEAR_LABEL.toString()));
        String[] yearList = {"2014","2015","2016","2017"};
        yearCombo = new ComboBox(generateComboBoxText(yearList));
        
        semesterBox = new HBox(yearLabel);
        semesterBox.getChildren().add(yearCombo);
        bpSemester = new BorderPane();
        bpSemester.setRight(semesterBox);
        bpSemester.setLeft(semesterCombo);
        
        // line 3,4,5
        titleLabel = new Label(props.getProperty(CourseManagerProp.TITLE_LABEL.toString()));
        titleTF = new TextField();
        titleTF.setPromptText(props.getProperty(CourseManagerProp.TITLE_PROMPT_TEXT.toString()));
        instructorNameLabel = new Label(props.getProperty(CourseManagerProp.INSTRUCTOR_NAME_LABEL.toString()));
        instructorNameTF = new TextField();
        instructorNameTF.setPromptText(props.getProperty(CourseManagerProp.INSTRUCTOR_NAME_PROMT_TEXT.toString()));
        instructorHomeLabel = new Label(props.getProperty(CourseManagerProp.INSTRUCTOR_HOME_LABEL.toString()));
        instructorHomeTF = new TextField();
        instructorHomeTF.setPromptText(props.getProperty(CourseManagerProp.INSTRUCTOR_HOME_PROMT_TEXT.toString()));
        
        // line 6
        exportDirLabel = new Label(props.getProperty(CourseManagerProp.EXPORT_DIR_LABEL.toString()));
        dirLabel = new Label();
        courseExportDirChangeButton = new Button(props.getProperty(CourseManagerProp.CHANGE_BUTTON.toString()));
        dirBox = new HBox(dirLabel);
        dirBox.getChildren().add(courseExportDirChangeButton);
        
        // combine to grid
        infoGridPane.add(subjectLabel, 0, 0);
        infoGridPane.add(bpSubject, 1, 0);
        infoGridPane.add(semesterLabel, 0, 1);
        infoGridPane.add(bpSemester, 1, 1);
        infoGridPane.add(titleLabel, 0, 2);
        infoGridPane.add(titleTF, 1, 2);
        infoGridPane.add(instructorNameLabel, 0, 3);
        infoGridPane.add(instructorNameTF, 1, 3);
        infoGridPane.add(instructorHomeLabel, 0, 4);
        infoGridPane.add(instructorHomeTF, 1, 4);
        infoGridPane.add(exportDirLabel, 0, 5);
        infoGridPane.add(dirBox, 1, 5);
    }
    
    public ObservableList<String> generateComboBoxText(String[] list) {
        ObservableList<String> res = FXCollections.observableArrayList();
        for (String i:list){
            res.add(i);
        }
        return res;
    }
    
    private void buildTemplateSection() {
        
    }
    
    private void buildPageStyleSection() {
        
    }
    
    @Override
    public void resetWorkspace() {
        
    }

    @Override
    public void reloadWorkspace(AppDataComponent dataComponent) {
        
    }
    
}
