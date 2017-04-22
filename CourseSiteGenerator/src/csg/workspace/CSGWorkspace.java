
package csg.workspace;

import csg.CourseSiteGenerator;
import djf.AppTemplate;
import djf.settings.AppPropertyType;
import static djf.settings.AppPropertyType.COURSE_TAB_TEXT;
import static djf.settings.AppPropertyType.PROJECT_TAB_TEXT;
import static djf.settings.AppPropertyType.REC_TAB_TEXT;
import static djf.settings.AppPropertyType.SCHEDULE_TAB_TEXT;
import static djf.settings.AppPropertyType.TA_TAB_TEXT;
import djf.ui.AppGUI;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import properties_manager.PropertiesManager;

public class CSGWorkspace {
    CourseSiteGenerator csg;
    protected TabPane tabPane;
    protected Tab courseTab;
    protected Tab taTab;
    protected Tab recitationTab;
    protected Tab scheduleTab;
    protected Tab projectTab;
    protected Pane bodyBox;
    
    boolean workspaceActivated;
    
    public CSGWorkspace(CourseSiteGenerator csg) {
        this.csg = csg;
        buildAppComponentsHook();
    }
    
    public Tab getCourseTab() {return courseTab;}
    public Tab getTATab() {return taTab;}
    public Tab getRecTab() {return recitationTab;}
    public Tab getScheduleTab() {return scheduleTab;}
    public Tab getProjectTab() {return projectTab;}
    public TabPane getTabPane() { return tabPane;}
    public Pane getBodyBox() {return bodyBox;}

    public void buildAppComponentsHook() {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        tabPane = new TabPane();
        courseTab = new Tab();
        taTab = new Tab();
        recitationTab = new Tab();
        scheduleTab = new Tab();
        projectTab = new Tab();
        
        courseTab.setClosable(false);
        taTab.setClosable(false);
        recitationTab.setClosable(false);
        scheduleTab.setClosable(false);
        projectTab.setClosable(false);
        
        courseTab.setText(props.getProperty(AppPropertyType.COURSE_TAB_TEXT.toString()));
        taTab.setText(props.getProperty(AppPropertyType.TA_TAB_TEXT.toString()));
        recitationTab.setText(props.getProperty(AppPropertyType.REC_TAB_TEXT.toString()));
        scheduleTab.setText(props.getProperty(AppPropertyType.SCHEDULE_TAB_TEXT.toString()));
        projectTab.setText(props.getProperty(AppPropertyType.PROJECT_TAB_TEXT.toString()));
        tabPane.getTabs().addAll(courseTab, taTab, recitationTab, scheduleTab, projectTab);
        bodyBox = new Pane();
        bodyBox.getChildren().add(tabPane);
        tabPane.prefWidthProperty().bind(bodyBox.widthProperty());
        tabPane.setTabMinWidth(bodyBox.getWidth()/5);
        
        
        tabPane.setOnMouseClicked(e -> {
            courseTab.setStyle("-fx-background-color: white");
            taTab.setStyle("-fx-background-color: white");
            recitationTab.setStyle("-fx-background-color: white");
            scheduleTab.setStyle("-fx-background-color: white");
            projectTab.setStyle("-fx-background-color: white");
            tabPane.getSelectionModel().getSelectedItem().setStyle("-fx-background-color: #F0E68C");
        });
    }
    
    public void resetWorkspace() {
        csg.buildAppComponentsHook();
    }
    
    public void activateWorkspace(BorderPane appPane) {
        if (!workspaceActivated) {
            csg.getCSGWorkspace().getCourseTab().setContent(csg.getCourse().getWorkspaceComponent().getWorkspace());
            csg.getCSGWorkspace().getTATab().setContent(csg.getTA().getWorkspaceComponent().getWorkspace());
            csg.getCSGWorkspace().getRecTab().setContent(csg.getRec().getWorkspaceComponent().getWorkspace());
            csg.getCSGWorkspace().getScheduleTab().setContent(csg.getSchedule().getWorkspaceComponent().getWorkspace());
            csg.getCSGWorkspace().getProjectTab().setContent(csg.getProject().getWorkspaceComponent().getWorkspace());
            // PUT THE WORKSPACE IN THE GUI
            appPane.setCenter(tabPane);
            workspaceActivated = true;
        }
    }
}
