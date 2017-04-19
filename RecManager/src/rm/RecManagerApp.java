
package rm;

import djf.AppTemplate;
import rm.data.RecRecord;
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
    
    public RecRecord getDataComponent() {return dataComponent;}
    public RecWorkspace getWorkspaceComponent() {return workspaceComponent;}
    public RecStyle getStyleComponent() {return styleComponent;}
    
    public void buildAppComponentsHook() {
        dataComponent = new RecRecord(this);
        workspaceComponent = new RecWorkspace(this);
        styleComponent = new RecStyle(this);
    }
    
}
