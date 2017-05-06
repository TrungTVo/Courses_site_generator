
package rm;

import cm.CourseManagerApp;
import djf.AppTemplate;
import rm.data.RecRecord;
import rm.file.RecFiles;
import rm.style.RecStyle;
import rm.workspace.RecWorkspace;
import tam.TAManagerApp;

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
    TAManagerApp taManagerApp;
    
    public RecManagerApp(CourseManagerApp courseManagerApp){
        this.courseManagerApp = courseManagerApp;
    }
    
    public void setTaManagerApp(TAManagerApp taManagerApp) {
        this.taManagerApp = taManagerApp;
    }
    
    public RecRecord getDataComponent() {return dataComponent;}
    public RecWorkspace getWorkspaceComponent() {return workspaceComponent;}
    public RecStyle getStyleComponent() {return styleComponent;}
    public RecFiles getFileComponent() {return fileComponent;}
    public CourseManagerApp getCourseManagerApp() {return courseManagerApp;}
    
    public void buildAppComponentsHook() {
        dataComponent = new RecRecord(this);
        workspaceComponent = new RecWorkspace(this, taManagerApp);
        styleComponent = new RecStyle(this);
        fileComponent = new RecFiles(this);
    }
    
}
