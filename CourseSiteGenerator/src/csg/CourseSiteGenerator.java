
package csg;

import cm.CourseManagerApp;
import cm.workspace.CSGWorkspace;
import csg.style.CSGStyle;
import java.util.Locale;
import static javafx.application.Application.launch;
import djf.AppTemplate;
import rm.RecManagerApp;
import tam.TAManagerApp;

/**
 *
 * @author trungvo
 */
public class CourseSiteGenerator extends AppTemplate {
    CourseManagerApp courseComponent;
    TAManagerApp taComponent;
    RecManagerApp recComponent;
    CSGStyle csgStyle;
    CSGWorkspace csgWorkspace;
    
    @Override
    public void buildAppComponentsHook() {
        // Style App
        csgStyle = new CSGStyle(this);
        
        // Create Tab Pane for App
        csgWorkspace = new CSGWorkspace();
        getGUI().getAppPane().setCenter(csgWorkspace.getTabPane());
        
        // Build each component for each tab
        // TA component
        taComponent = new TAManagerApp();
        taComponent.buildAppComponentsHook();
        csgWorkspace.getTATab().setContent(taComponent.getWorkspaceComponent().getWorkspace());
        
        // Course Data component
        courseComponent = new CourseManagerApp();
        courseComponent.buildAppComponentsHook();
        csgWorkspace.getCourseTab().setContent(courseComponent.getWorkspaceComponent().getWorkspace());
        
        // Recitation Data component
        recComponent = new RecManagerApp();
        recComponent.buildAppComponentsHook();
        csgWorkspace.getRecTab().setContent(recComponent.getWorkspaceComponent().getWorkspace());
    }
    
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        launch(args);
    }

}
