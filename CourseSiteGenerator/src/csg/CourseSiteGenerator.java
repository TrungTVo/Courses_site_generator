
package csg;

import cm.CourseManagerApp;
import csg.style.CSGStyle;
import java.util.Locale;
import static javafx.application.Application.launch;
import djf.AppTemplate;
import tam.TAManagerApp;

/**
 *
 * @author trungvo
 */
public class CourseSiteGenerator extends AppTemplate {
    CourseManagerApp courseComponent;
    TAManagerApp taComponent;
    CSGStyle csgStyle;
    
    @Override
    public void buildAppComponentsHook() {
        csgStyle = new CSGStyle(this);
        
        // Build each component for each tab
        taComponent = new TAManagerApp();
        taComponent.buildAppComponentsHook();
        System.out.println(taComponent.getWorkspaceComponent().getWorkspace());
        getGUI().getTATab().setContent(taComponent.getWorkspaceComponent().getWorkspace());
        
        courseComponent = new CourseManagerApp();
        courseComponent.buildAppComponentsHook();
        System.out.println(courseComponent.getWorkspaceComponent().getWorkspace());
        getGUI().getCourseTab().setContent(courseComponent.getWorkspaceComponent().getWorkspace());
    }
    
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        launch(args);
    }

}
