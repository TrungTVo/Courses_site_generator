
package rm.jtps;

import java.util.Collections;
import rm.RecManagerApp;
import rm.data.RecData;

/**
 *
 * @author trungvo
 */
public class UpdateRec_Transaction implements jTPS_rec_Transaction {
    RecManagerApp recManager;
    RecData selectedRec;
    RecData newRec;
    RecData oldRec;
    
    public UpdateRec_Transaction(RecManagerApp recManager, RecData selectedRec, RecData newRec, RecData oldRec) {
        this.recManager = recManager;
        this.selectedRec = selectedRec;
        this.newRec = newRec;
        this.oldRec = oldRec;
    }

    @Override
    public void doTransaction() {
        selectedRec.setSection(newRec.getSection());
        selectedRec.setInstructor(newRec.getInstructor());
        selectedRec.setDayTime(newRec.getDayTime());
        selectedRec.setLocation(newRec.getLocation());
        selectedRec.setTa1(newRec.getTa1());
        selectedRec.setTa2(newRec.getTa2());
        Collections.sort(recManager.getDataComponent().getRecRecord());
        recManager.getWorkspaceComponent().getRecTable().refresh();
    }

    @Override
    public void undoTransaction() {
        selectedRec.setSection(oldRec.getSection());
        selectedRec.setInstructor(oldRec.getInstructor());
        selectedRec.setDayTime(oldRec.getDayTime());
        selectedRec.setLocation(oldRec.getLocation());
        selectedRec.setTa1(oldRec.getTa1());
        selectedRec.setTa2(oldRec.getTa2());
        Collections.sort(recManager.getDataComponent().getRecRecord());
        recManager.getWorkspaceComponent().getRecTable().refresh();
    }
    
    
            
}
