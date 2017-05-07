
package rm.jtps;

import java.util.Collections;
import rm.RecManagerApp;
import rm.data.RecData;
import rm.data.RecRecord;
import rm.workspace.RecWorkspace;

/**
 *
 * @author trungvo
 */
public class AddRec_Transaction implements jTPS_rec_Transaction {
    
    RecManagerApp recManager;
    RecData newRec;
    
    public AddRec_Transaction(RecManagerApp recManager, RecData newRec) {
        this.recManager = recManager;
        this.newRec = newRec;
    }
    
    @Override
    public void doTransaction() {
        // get Data
        RecRecord recRecord = recManager.getDataComponent();
        
        // get workspace
        RecWorkspace recWorkspace = recManager.getWorkspaceComponent();
        
        recRecord.getRecRecord().add(newRec);
        RecWorkspace workspace = recManager.getWorkspaceComponent();
        workspace.getSectionTF().clear();
        workspace.getDayTimeTF().clear();
        workspace.getLocationTF().clear();
        workspace.getInstructorTF().clear();
        workspace.getTa1Combo().getSelectionModel().clearSelection();
        workspace.getTa2Combo().getSelectionModel().clearSelection();
                
        Collections.sort(recRecord.getRecRecord());
    }

    @Override
    public void undoTransaction() {
        // get Data
        RecRecord recRecord = recManager.getDataComponent();
        recRecord.getRecRecord().remove(newRec);
        Collections.sort(recRecord.getRecRecord());
        recManager.getWorkspaceComponent().getRecTable().refresh();
    }
    
}
