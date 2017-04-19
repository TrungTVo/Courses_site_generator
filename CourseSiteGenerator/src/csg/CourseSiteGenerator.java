
package csg;

import cm.CourseManagerApp;
import csg.workspace.CSGWorkspace;
import csg.style.CSGStyle;
import java.util.Locale;
import static javafx.application.Application.launch;
import djf.AppTemplate;
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
    
    public CourseManagerApp getCourse() {return courseComponent;}
    public TAManagerApp getTA() {return taComponent;}
    public RecManagerApp getRec() {return recComponent;}
    public ScheduleManagerApp getSchedule() {return scheComponent;}
    public ProjectManagerApp getProject() {return projectComponent;}
    
    public CSGWorkspace getCSGWorkspace() {return csgWorkspace;}
    
    @Override
    public void buildAppComponentsHook() {
        // sync csg in AppFileController with this current CSG object
        CourseSiteGenerator newCSG = getGUI().getAppFileController().getCSG();
        newCSG = this;
        getGUI().getAppFileController().setCSG(newCSG);
        
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
        courseComponent = new CourseManagerApp();
        courseComponent.buildAppComponentsHook();
        csgWorkspace.getCourseTab().setContent(courseComponent.getWorkspaceComponent().getWorkspace());
        
        // Recitation Data component
        recComponent = new RecManagerApp();
        recComponent.buildAppComponentsHook();
        csgWorkspace.getRecTab().setContent(recComponent.getWorkspaceComponent().getWorkspace());
        
        // Schedule Data component
        scheComponent = new ScheduleManagerApp();
        scheComponent.buildAppComponentsHook();
        csgWorkspace.getScheduleTab().setContent(scheComponent.getWorkspaceComponent().getWorkspace());
        
        // Project Data component
        projectComponent = new ProjectManagerApp();
        projectComponent.buildAppComponentsHook();
        csgWorkspace.getProjectTab().setContent(projectComponent.getWorkspaceComponent().getWorkspace());
        
    }
    
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        launch(args);
    }

}
