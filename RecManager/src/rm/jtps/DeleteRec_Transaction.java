
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
public class DeleteRec_Transaction implements jTPS_rec_Transaction {
    RecManagerApp recManager;
    RecData selectedRec;
    
    public DeleteRec_Transaction(RecManagerApp recManager, RecData selectedRec) {
        this.recManager = recManager;
        this.selectedRec = selectedRec;
    }

    @Override
    public void doTransaction() {
        int indexOfCurrentRec = recManager.getDataComponent().getRecRecord().indexOf(selectedRec);
        
        // REMOVE SELECTED REC
        recManager.getDataComponent().getRecRecord().remove(indexOfCurrentRec);
    }

    @Override
    public void undoTransaction() {
        // get data
        RecRecord recRecord = recManager.getDataComponent();
        
        recRecord.getRecRecord().add(selectedRec);
                
        Collections.sort(recRecord.getRecRecord());
    }
    
    
}
