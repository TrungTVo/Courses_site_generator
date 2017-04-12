
package rm;

import djf.AppTemplate;
import rm.data.RecRecord;
import rm.style.RecStyle;
import rm.workspace.RecWorkspace;

/**
 *
 * @author trungvo
 */
public class RecManagerApp extends AppTemplate {

    @Override
    public void buildAppComponentsHook() {
        dataComponent = new RecRecord(this);
        workspaceComponent = new RecWorkspace(this);
        styleComponent = new RecStyle(this);
    }
    
}
