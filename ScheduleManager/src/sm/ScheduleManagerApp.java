
package sm;

import djf.AppTemplate;
import sm.data.ScheduleData;
import sm.workspace.ScheduleWorkspace;

public class ScheduleManagerApp extends AppTemplate {

    @Override
    public void buildAppComponentsHook() {
        dataComponent = new ScheduleData(this);
        workspaceComponent = new ScheduleWorkspace(this);
        
    }
    
}
