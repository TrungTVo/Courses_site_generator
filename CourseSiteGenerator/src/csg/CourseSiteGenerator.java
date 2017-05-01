
package csg;

import cm.CourseManagerApp;
import csg.file.CSGFiles;
import csg.workspace.CSGWorkspace;
import csg.style.CSGStyle;
import csg.test_bed.TestSave;
import java.util.Locale;
import static javafx.application.Application.launch;
import djf.AppTemplate;
import static djf.settings.AppStartupConstants.FILE_PROTOCOL;
import static djf.settings.AppStartupConstants.PATH_WORK;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.stage.Stage;
import pm.ProjectManagerApp;
import rm.RecManagerApp;
import sm.ScheduleManagerApp;
import tam.TAManagerApp;

/**
 *
 * @author trungvo
 */
public class CourseSiteGenerator extends AppTemplate {
    CourseManagerApp courseComponent;
    TAManagerApp taComponent;
    RecManagerApp recComponent;
    ScheduleManagerApp scheComponent;
    ProjectManagerApp projectComponent;
    
    CSGStyle csgStyle;
    CSGWorkspace csgWorkspace;
    CSGFiles csgFiles;
    
    public CourseManagerApp getCourse() {return courseComponent;}
    public TAManagerApp getTA() {return taComponent;}
    public RecManagerApp getRec() {return recComponent;}
    public ScheduleManagerApp getSchedule() {return scheComponent;}
    public ProjectManagerApp getProject() {return projectComponent;}
    
    public CSGWorkspace getCSGWorkspace() {return csgWorkspace;}
    public CSGFiles getCSGFiles() {return csgFiles;}
    public Stage getWindow() {return getGUI().getWindow();}
    
    @Override
    public void buildAppComponentsHook() {
        // sync csg in AppFileController with this current CSG object
        getGUI().getAppFileController().setCSG(this);
        
        // Init Files Manager
        csgFiles = new CSGFiles(this);
        
        // Style App
        csgStyle = new CSGStyle(this);
        
        // Create Tab Pane for App
        this.csgWorkspace = new CSGWorkspace(this);
        getGUI().getAppPane().setCenter(this.csgWorkspace.getTabPane());
        
        // Build each component for each tab
        // TA component
        taComponent = new TAManagerApp();
        taComponent.buildAppComponents();
        csgWorkspace.getTATab().setContent(taComponent.getWorkspaceComponent().getWorkspace());
        
        // Course Data component
        courseComponent = new CourseManagerApp(this);
        courseComponent.buildAppComponentsHook();
        csgWorkspace.getCourseTab().setContent(courseComponent.getWorkspaceComponent().getWorkspace());
        
        // Recitation Data component
        recComponent = new RecManagerApp(courseComponent);
        recComponent.buildAppComponentsHook();
        csgWorkspace.getRecTab().setContent(recComponent.getWorkspaceComponent().getWorkspace());
        
        // Schedule Data component
        scheComponent = new ScheduleManagerApp();
        scheComponent.buildAppComponentsHook();
        csgWorkspace.getScheduleTab().setContent(scheComponent.getWorkspaceComponent().getWorkspace());
        
        // Project Data component
        projectComponent = new ProjectManagerApp(courseComponent);
        projectComponent.buildAppComponentsHook();
        csgWorkspace.getProjectTab().setContent(projectComponent.getWorkspaceComponent().getWorkspace());
        
    }
    
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        launch(args);
    }

}
