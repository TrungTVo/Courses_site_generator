
package rm;

import cm.CourseManagerApp;
import djf.AppTemplate;
import rm.data.RecRecord;
import rm.file.RecFiles;
import rm.style.RecStyle;
import rm.workspace.RecWorkspace;

/**
 *
 * @author trungvo
 */
public class RecManagerApp {
    
    RecRecord dataComponent;
    RecWorkspace workspaceComponent;
    RecStyle styleComponent;
    RecFiles fileComponent;
    CourseManagerApp courseManagerApp;
    
    public RecManagerApp(CourseManagerApp courseManagerApp){
        this.courseManagerApp = courseManagerApp;
    }
    
    public RecRecord getDataComponent() {return dataComponent;}
    public RecWorkspace getWorkspaceComponent() {return workspaceComponent;}
    public RecStyle getStyleComponent() {return styleComponent;}
    public RecFiles getFileComponent() {return fileComponent;}
    public CourseManagerApp getCourseManagerApp() {return courseManagerApp;}
    
    public void buildAppComponentsHook() {
        dataComponent = new RecRecord(this);
        workspaceComponent = new RecWorkspace(this);
        styleComponent = new RecStyle(this);
        fileComponent = new RecFiles(this);
    }
    
}
