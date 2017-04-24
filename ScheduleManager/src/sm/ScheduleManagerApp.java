
package sm;

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
    
    public ScheduleData getDataComponent() {return dataComponent;}
    public ScheduleStyle getStyleComponent() {return styleComponent;}
    public ScheduleWorkspace getWorkspaceComponent() {return workspaceComponent;}
    public ScheduleFiles getFileComponent() {return fileComponent;}
    
    public void buildAppComponentsHook() {
        dataComponent = new ScheduleData(this);
        workspaceComponent = new ScheduleWorkspace(this);
        styleComponent = new ScheduleStyle(this);
        fileComponent = new ScheduleFiles(this);
    }
    
}
