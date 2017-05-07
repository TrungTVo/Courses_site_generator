/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.workspace;

import cm.CourseManagerApp;
import cm.CourseManagerProp;
import cm.data.CourseData;
import cm.data.SitePage;
import csg.CourseSiteGenerator;
import djf.AppTemplate;
import djf.components.AppDataComponent;
import djf.components.AppWorkspaceComponent;
import static djf.settings.AppStartupConstants.FILE_PROTOCOL;
import static djf.settings.AppStartupConstants.PATH_IMAGES;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import properties_manager.PropertiesManager;

/**
 *
 * @author trungvo
 */
public class CourseWorkspace {
    CourseManagerApp app;
    CourseSiteGenerator csg;
    VBox outerWrap;
    GridPane infoGridPane;
    VBox temPlatePane;
    HBox invisibleTemPlateBox;
    ScrollPane scrollPane;

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
    
    GridPane subjectBox;
    GridPane semesterBox;
    BorderPane dirBox;
    BorderPane bpSubject;
    BorderPane bpSemester;
    VBox infoBox;

    Label templateTitle;
    Label explainText;
    Label templateDir;
    Button templateDirButton;
    Label siteTitle;
    TableView<SitePage> siteTable;		
    TableColumn<SitePage, Boolean> useCol;
    TableColumn<SitePage, String> navBarTitleCol;
    TableColumn<SitePage, String> fileCol;
    TableColumn<SitePage, String> scriptCol;

    VBox pageStyleVBox;
    GridPane styleGridPane;
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
    
    BorderPane workspace;
    boolean workspaceActivated;
    
    public VBox getOuterWrap() {return outerWrap;}
    public VBox getInfoBox() {return infoBox;}
    public GridPane getInfoGridPane() {return infoGridPane;}
    public VBox getTemplatePane() {return temPlatePane;}
    public VBox getPageStyleVBox() {return pageStyleVBox;}
    public Label getInfoTitle() {return infoTitle;}
    public Label getTemplateTitle() {return templateTitle;}
    public Label getStyleTitle() {return styleTitle;}
    
    public Label getSubjectLabel() {return subjectLabel;}
    public ComboBox getSubjectCombo() {return subjectCombo;}
    public Label getNumberLabel() {return numberLabel;}
    public ComboBox getNumberCombo() {return numberCombo;}
    public Label getSemesterLabel() {return semesterLabel;}
    public ComboBox getSemesterCombo() {return semesterCombo;}
    public Label getYearLabel() {return yearLabel;}
    public ComboBox getYearCombo() {return yearCombo;}
    
    public Label getTitleLabel() {return titleLabel;}
    public Label getInstructorNameLabel() {return instructorNameLabel;}
    public Label getInstructorHomeLabel() {return instructorHomeLabel;}
    public BorderPane getDirBox() {return dirBox;}
    public TextField getTitleTF() {return titleTF;}
    public TextField getInstructorNameTF() {return instructorNameTF;}
    public TextField getInstructorHomeTF() {return instructorHomeTF;}
    
    public GridPane getSubjectBox() {return subjectBox;}
    public GridPane getSemesterBox() {return semesterBox;}
    
    public Label getSiteTitle() {return siteTitle;}
    public Button getBannerButton() {return bannerChangeButton;}
    public Button getLeftImageButton() {return leftImageButton;}
    public Button getRightImageButton() {return rightImageButton;}
    
    public BorderPane getWorkspace() {return workspace;}
    
    public CourseWorkspace(CourseManagerApp initApp, CourseSiteGenerator csg) {
        app = initApp;
        this.csg = csg;
        
        // WE'LL NEED THIS TO GET LANGUAGE PROPERTIES FOR OUR UI
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        outerWrap = new VBox();
        outerWrap.setAlignment(Pos.CENTER);
        scrollPane = new ScrollPane(outerWrap);
        outerWrap.prefWidthProperty().bind(scrollPane.widthProperty().multiply(1));
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        //scrollPane.setPrefWidth(500);
        
        buildInfoSection(props);
        outerWrap.getChildren().add(infoBox);
        infoBox.maxWidthProperty().bind(outerWrap.widthProperty().multiply(0.7));
        
        buildTemplateSection(props);
        outerWrap.getChildren().add(temPlatePane);
        temPlatePane.maxWidthProperty().bind(outerWrap.widthProperty().multiply(0.7));
        
        buildPageStyleSection(props);
        outerWrap.getChildren().add(pageStyleVBox);
        pageStyleVBox.maxWidthProperty().bind(outerWrap.widthProperty().multiply(0.7));
        
        workspace = new BorderPane();
        workspace.setCenter(scrollPane);
        outerWrap.setStyle("-fx-background-color: #B0C4DE");
    }
    
    private void buildInfoSection(PropertiesManager props) {
        infoGridPane = new GridPane();
        infoTitle = new Label(props.getProperty(CourseManagerProp.COURSEINFO_TITLE.toString()));
        
        // line 1
        subjectLabel = new Label(props.getProperty(CourseManagerProp.SUBJECT_LABEL.toString()));
        String[] courseList = {"CSE","ISE","ITS"};
        subjectCombo = new ComboBox(generateComboBoxText(courseList));
        numberLabel = new Label(props.getProperty(CourseManagerProp.NUMBER_LABEL.toString()));
        String[] numberList = {"219","308","380","102"};
        numberCombo = new ComboBox(generateComboBoxText(numberList));
        
        subjectBox = new GridPane();
        subjectBox.add(numberLabel, 0, 0);
        subjectBox.add(numberCombo, 1, 0);
        subjectBox.setHgap(10);
        subjectBox.setAlignment(Pos.CENTER);
        bpSubject = new BorderPane();
        bpSubject.setRight(subjectBox);
        bpSubject.setLeft(subjectCombo);
        
        // line 2
        semesterLabel = new Label(props.getProperty(CourseManagerProp.SEMESTER_LABEL.toString()));
        String[] semesterList = {"Fall","Spring"};
        semesterCombo = new ComboBox(generateComboBoxText(semesterList));
        semesterCombo.setMinWidth(25);
        yearLabel = new Label(props.getProperty(CourseManagerProp.YEAR_LABEL.toString()));
        String[] yearList = {"2014","2015","2016","2017"};
        yearCombo = new ComboBox(generateComboBoxText(yearList));
        yearCombo.setMinWidth(25);
        
        subjectCombo.setMinWidth(semesterCombo.getWidth());
        numberCombo.setMinWidth(yearCombo.getWidth());
        
        semesterBox = new GridPane();
        semesterBox.add(yearLabel, 0, 0);
        semesterBox.add(yearCombo, 1, 0);
        semesterBox.setHgap(10);
        semesterBox.setAlignment(Pos.CENTER);
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
        
        courseExportDirChangeButton.setOnAction(e -> {
            DirectoryChooser chooser = new DirectoryChooser();
            chooser.setTitle("Export");
            File defaultDirectory = new File("/Users/");
            chooser.setInitialDirectory(defaultDirectory);
            File selectedDirectory = chooser.showDialog(csg.getGUI().getWindow());
            if (selectedDirectory != null) {
                String path = selectedDirectory.getPath();
                dirLabel.setText(path);
                csg.getGUI().getExportButton().setDisable(false);
            }
        });
        
        dirBox = new BorderPane();
        dirBox.setCenter(dirLabel);
        dirBox.setRight(courseExportDirChangeButton);
        
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
        infoGridPane.setAlignment(Pos.CENTER);
        infoGridPane.setVgap(5);
        infoGridPane.setHgap(10);
        
        infoBox = new VBox();
        instructorHomeTF.minWidthProperty().bind(infoBox.widthProperty().multiply(0.4));
        infoBox.getChildren().addAll(infoTitle, infoGridPane);
        infoBox.setAlignment(Pos.CENTER);
    }
    
    public ObservableList<String> generateComboBoxText(String[] list) {
        ObservableList<String> res = FXCollections.observableArrayList();
        for (String i:list){
            res.add(i);
        }
        return res;
    }
    
    private void buildTemplateSection(PropertiesManager props) {
        templateTitle = new Label(props.getProperty(CourseManagerProp.TEMPLATE_TITLE.toString()));
        explainText = new Label(props.getProperty(CourseManagerProp.EXPLAIN_TEXT.toString()));
        templateDir = new Label();
        templateDirButton = new Button(props.getProperty(CourseManagerProp.TEMPLATE_DIR_BUTTON.toString()));
        
        siteTitle = new Label(props.getProperty(CourseManagerProp.SITE_TABLE_TITLE.toString()));
        siteTable = new TableView();
        CourseData courseData = (CourseData) app.getDataComponent();
        ObservableList<SitePage> templateList = courseData.getTemplates();
        siteTable.setItems(templateList);
        
        // columns
        useCol = new TableColumn(props.getProperty(CourseManagerProp.USE_COLUMN_TEXT.toString()));
        navBarTitleCol = new TableColumn(props.getProperty(CourseManagerProp.NAVBAR_COLUMN_TEXT.toString()));
        navBarTitleCol.setStyle("-fx-alignment: CENTER-LEFT");
        fileCol = new TableColumn(props.getProperty(CourseManagerProp.FILENAME_COLUMN_TEXT.toString()));
        fileCol.setStyle("-fx-alignment: CENTER-LEFT");
        scriptCol = new TableColumn(props.getProperty(CourseManagerProp.SCRIPT_COLUMN_TEXT.toString()));
        useCol.prefWidthProperty().bind(siteTable.widthProperty().multiply(0.1));
        navBarTitleCol.prefWidthProperty().bind(siteTable.widthProperty().multiply(0.3));
        fileCol.prefWidthProperty().bind(siteTable.widthProperty().multiply(0.3));
        scriptCol.prefWidthProperty().bind(siteTable.widthProperty().multiply(0.3));
        
        useCol.setCellValueFactory(
                new Callback<CellDataFeatures<SitePage, Boolean>, ObservableValue<Boolean>>(){
                    @Override
                    public ObservableValue<Boolean> call(CellDataFeatures<SitePage, Boolean> param) {
                        return param.getValue().getIsUsedProp();
                    }
                }
        );
        
        navBarTitleCol.setCellValueFactory(
                new PropertyValueFactory<SitePage, String>("navBar")
        );
        fileCol.setCellValueFactory(
                new PropertyValueFactory<SitePage, String>("file")
        );
        scriptCol.setCellValueFactory(
                new PropertyValueFactory<SitePage, String>("script")
        );
        useCol.setCellFactory(CheckBoxTableCell.forTableColumn(useCol));
        siteTable.getColumns().addAll(useCol, navBarTitleCol, fileCol, scriptCol);
        siteTable.setEditable(true);
        useCol.setEditable(true);
        
        // combine
        temPlatePane = new VBox();
        temPlatePane.getChildren().addAll(templateTitle, explainText, templateDir, templateDirButton, siteTitle, siteTable);
        siteTable.maxWidthProperty().bind(temPlatePane.widthProperty().multiply(0.7));
        
        temPlatePane.setAlignment(Pos.CENTER);
        
        
        templateDirButton.setOnAction(e -> {
            DirectoryChooser chooser = new DirectoryChooser();
            chooser.setTitle("Choose template");
            File defaultDirectory = new File("./work/templates/");
            chooser.setInitialDirectory(defaultDirectory);
            File selectedDirectory = chooser.showDialog(csg.getGUI().getWindow());
            if (selectedDirectory != null) {
                if(selectedDirectory.isDirectory()){
                    File[] filesList = selectedDirectory.listFiles();
                    List<String> htmlList = new ArrayList<>();
                    List<String> scriptList = new ArrayList<>();
                    for (File file:filesList){
                        String fileName = file.toString().substring(file.toString().lastIndexOf("/")+1);
                        if (fileName.substring(fileName.length()-2).equals("ml")) {
                            htmlList.add(fileName);
                        } else if (fileName.substring(fileName.length()-2).equals("js")) {
                            scriptList.add(fileName);
                        }
                    }
                    SitePage site = null;
                    for (String html:htmlList) {
                        if (html.contains("index")){
                            site = new SitePage(true, "Home", html, findJSFile(scriptList, "Home"));
                        } else if (html.contains("syllabus")) {
                            site = new SitePage(true, "Syllabus", html, findJSFile(scriptList, "Office")+"\n"+findJSFile(scriptList, "Recitation"));
                        } else if (html.contains("schedule")) {
                            site = new SitePage(true, "Schedule", html, findJSFile(scriptList, "Schedule"));
                        } else if (html.contains("hw")) {
                            site = new SitePage(true, "HWs", html, findJSFile(scriptList, "HW"));
                        } else if (html.contains("project")) {
                            site = new SitePage(true, "Projects", html, findJSFile(scriptList, "Project"));
                        }
                        if (site != null){
                            app.getDataComponent().getTemplates().add(site);
                        }
                    }
                }
            }
        });
        
    }
    
    public String findJSFile(List<String> files, String pattern) {
        for (String file:files){
            if (file.contains(pattern))
                return file;
        }
        return null;
    }
    
    private void buildPageStyleSection(PropertiesManager props) {
        styleTitle = new Label(props.getProperty(CourseManagerProp.PAGESTYLE_TITLE.toString()));
        styleGridPane = new GridPane();
        
        // line 1
        bannerImageLabel = new Label(props.getProperty(CourseManagerProp.BANNER_IMAGE_LABEL.toString()));
        bannerChangeButton = new Button(props.getProperty(CourseManagerProp.CHANGE_BUTTON.toString()));
        ImageView bannerImageView = new ImageView();
        bannerChangeButton.setOnAction(e -> {
            // PROMPT THE USER FOR A FILE NAME
            FileChooser fc = new FileChooser();
            fc.setInitialDirectory(new File(PATH_IMAGES));
            fc.setTitle(props.getProperty("Choose Image"));
            File selectedFile = fc.showOpenDialog(csg.getWindow());
            if (selectedFile != null){
                String imageString = selectedFile.toString();
                bannerImage = new Image("file:"+imageString);
                if (bannerImage != null){
                    styleGridPane.getChildren().remove(bannerImageView);
                    bannerImageView.setImage(bannerImage);
                    styleGridPane.add(bannerImageView, 1, 0);
                }
            }
        });
        
        // line 2
        leftImageLabel = new Label(props.getProperty(CourseManagerProp.LEFT_IMAGE_LABEL.toString()));
        leftImageButton = new Button(props.getProperty(CourseManagerProp.CHANGE_BUTTON.toString()));
        ImageView leftImageView = new ImageView();
        leftImageButton.setOnAction(e -> {
            // PROMPT THE USER FOR A FILE NAME
            FileChooser fc = new FileChooser();
            fc.setInitialDirectory(new File(PATH_IMAGES));
            fc.setTitle(props.getProperty("Choose Image"));
            File selectedFile = fc.showOpenDialog(csg.getWindow());
            if (selectedFile != null){
                String imageString = selectedFile.toString();
                leftImage = new Image("file:"+imageString);
                if (leftImage != null){
                    styleGridPane.getChildren().remove(leftImageView);
                    leftImageView.setImage(leftImage);
                    styleGridPane.add(leftImageView, 1, 1);
                }
            }
        });
        
        // line 3
        rightImageLabel = new Label(props.getProperty(CourseManagerProp.RIGHT_IMAGE_LABEL.toString()));
        rightImageButton = new Button(props.getProperty(CourseManagerProp.CHANGE_BUTTON.toString()));
        ImageView rightImageView = new ImageView();
        rightImageButton.setOnAction(e -> {
            // PROMPT THE USER FOR A FILE NAME
            FileChooser fc = new FileChooser();
            fc.setInitialDirectory(new File(PATH_IMAGES));
            fc.setTitle(props.getProperty("Choose Image"));
            File selectedFile = fc.showOpenDialog(csg.getWindow());
            if (selectedFile != null){
                String imageString = selectedFile.toString();
                rightImage = new Image("file:"+imageString);
                if (rightImage != null){
                    styleGridPane.getChildren().remove(rightImageView);
                    rightImageView.setImage(rightImage);
                    styleGridPane.add(rightImageView, 1, 2);
                }
            }
        });
        
        // line 4
        stylesheetLabel = new Label(props.getProperty(CourseManagerProp.STYLE_SHEET_LABEL.toString()));
        styleCombo = new ComboBox();
        
        noteLabel = new Label(props.getProperty(CourseManagerProp.NOTE_TEXT.toString()));
        
        // combine
        // banner
        styleGridPane.add(bannerImageLabel, 0, 0);
        if (bannerImage != null){
            styleGridPane.add(new ImageView(bannerImage), 1, 0);
        }
        styleGridPane.add(bannerChangeButton, 2, 0);
        
        // left
        styleGridPane.add(leftImageLabel, 0, 1);
        if (leftImage != null){
            styleGridPane.add(new ImageView(leftImage), 1, 1);
        }
        styleGridPane.add(leftImageButton, 2, 1);
        
        // right
        styleGridPane.add(rightImageLabel, 0, 2);
        if (rightImage != null){
            styleGridPane.add(new ImageView(rightImage), 1, 2);
        }
        styleGridPane.add(rightImageButton, 2, 2);
        
        styleGridPane.add(stylesheetLabel, 0, 3);
        styleGridPane.add(styleCombo, 1, 3);
        styleGridPane.setAlignment(Pos.CENTER);
        styleGridPane.setVgap(5);
        styleGridPane.setHgap(10);
        
        pageStyleVBox = new VBox();
        pageStyleVBox.getChildren().addAll(styleTitle, styleGridPane, noteLabel);
        pageStyleVBox.setAlignment(Pos.CENTER);
    }
    
    public void activateWorkspace(BorderPane appPane) {
        if (!workspaceActivated) {
            // PUT THE WORKSPACE IN THE GUI
            appPane.setCenter(workspace);
            workspaceActivated = true;
        }
    }
    
    public void resetWorkspace() {
        
    }

    public void reloadWorkspace(CourseData dataComponent) {
        
    }
    
}
