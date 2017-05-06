
package sm;

import csg.CourseSiteGenerator;
import djf.AppTemplate;
import sm.data.ScheduleData;
import sm.file.ScheduleFiles;
import sm.style.ScheduleStyle;
import sm.workspace.ScheduleWorkspace;

public class ScheduleManagerApp {
    
    ScheduleData dataComponent;
    ScheduleStyle styleComponent;
    ScheduleWorkspace workspaceComponent;
    ScheduleFiles fileComponent;
    
    CourseSiteGenerator csg;
    
    public ScheduleData getDataComponent() {return dataComponent;}
    public ScheduleStyle getStyleComponent() {return styleComponent;}
    public ScheduleWorkspace getWorkspaceComponent() {return workspaceComponent;}
    public ScheduleFiles getFileComponent() {return fileComponent;}
    
    public ScheduleManagerApp(CourseSiteGenerator csg) {
        this.csg = csg;
    }
    
    public void buildAppComponentsHook() {
        dataComponent = new ScheduleData(this);
        workspaceComponent = new ScheduleWorkspace(this, csg);
        styleComponent = new ScheduleStyle(this);
        fileComponent = new ScheduleFiles(this);
    }
    
}
