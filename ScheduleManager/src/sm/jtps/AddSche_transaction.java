
package sm.jtps;

import java.util.Collections;
import sm.ScheduleManagerApp;
import sm.data.ScheduleData;
import sm.data.ScheduleTopic;
import sm.workspace.ScheduleWorkspace;

/**
 *
 * @author trungvo
 */
public class AddSche_transaction implements jTPS_sche_Transaction {
    
    ScheduleManagerApp scheManager;
    ScheduleTopic newTopic;
    
    public AddSche_transaction(ScheduleManagerApp scheManager, ScheduleTopic newTopic) {
        this.scheManager = scheManager;
        this.newTopic = newTopic;
    }
    
    @Override
    public void doTransaction() {
        // get data
        ScheduleData scheData = scheManager.getDataComponent();
        
        //get workspace
        ScheduleWorkspace scheWorkspace = scheManager.getWorkspaceComponent();
        
        scheData.getScheduleList().add(newTopic);
        scheWorkspace.getTypeComboBox().getSelectionModel().clearSelection();
        scheWorkspace.getDatePicker().setValue(null);
        scheWorkspace.getTimeTF().clear();
        scheWorkspace.getTitleTF().clear();
        scheWorkspace.getTopicTF().clear();
        scheWorkspace.getLinkTF().clear();
        scheWorkspace.getCriteriaTF().clear();
        Collections.sort(scheData.getScheduleList());
    }

    @Override
    public void undoTransaction() {
        // get data
        ScheduleData scheData = scheManager.getDataComponent();
        scheData.getScheduleList().remove(newTopic);
        Collections.sort(scheData.getScheduleList());
        scheManager.getWorkspaceComponent().getScheTable().refresh();
    }
    
}
