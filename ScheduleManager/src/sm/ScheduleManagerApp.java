
package sm;

import djf.AppTemplate;
import sm.data.ScheduleData;
import sm.style.ScheduleStyle;
import sm.workspace.ScheduleWorkspace;

public class ScheduleManagerApp {
    
    ScheduleData dataComponent;
    ScheduleStyle styleComponent;
    ScheduleWorkspace workspaceComponent;
    
    public ScheduleData getDataComponent() {return dataComponent;}
    public ScheduleStyle getStyleComponent() {return styleComponent;}
    public ScheduleWorkspace getWorkspaceComponent() {return workspaceComponent;}
    
    public void buildAppComponentsHook() {
        dataComponent = new ScheduleData(this);
        workspaceComponent = new ScheduleWorkspace(this);
        styleComponent = new ScheduleStyle(this);
    }
    
}
