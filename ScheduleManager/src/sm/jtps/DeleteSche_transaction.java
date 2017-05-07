
package sm.jtps;

import java.util.Collections;
import sm.ScheduleManagerApp;
import sm.data.ScheduleData;
import sm.data.ScheduleTopic;

/**
 *
 * @author trungvo
 */
public class DeleteSche_transaction implements jTPS_sche_Transaction {
    ScheduleManagerApp scheManager;
    ScheduleTopic selectedSche;
    
    public DeleteSche_transaction(ScheduleManagerApp scheManager, ScheduleTopic selectedSche) {
        this.scheManager = scheManager;
        this.selectedSche = selectedSche;
    }

    @Override
    public void doTransaction() {
        int indexOfSelected = scheManager.getDataComponent().getScheduleList().indexOf(selectedSche);
        
        // remove selected at specified index
        scheManager.getDataComponent().getScheduleList().remove(indexOfSelected);
    }

    @Override
    public void undoTransaction() {
        // get data
        ScheduleData scheData = scheManager.getDataComponent();
        
        scheData.getScheduleList().add(selectedSche);
        
        Collections.sort(scheData.getScheduleList());
    }
}
