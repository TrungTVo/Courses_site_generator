
package sm.jtps;

import java.util.Collections;
import sm.ScheduleManagerApp;
import sm.data.ScheduleTopic;

/**
 *
 * @author trungvo
 */
public class UpdateSche_transaction implements jTPS_sche_Transaction {
    ScheduleManagerApp scheManager;
    ScheduleTopic selectedSche;
    ScheduleTopic oldTopic;
    ScheduleTopic newTopic;
    
    public UpdateSche_transaction(ScheduleManagerApp scheManager, ScheduleTopic selectedSche, ScheduleTopic oldTopic, ScheduleTopic newTopic) {
        this.scheManager = scheManager;
        this.selectedSche = selectedSche;
        this.oldTopic = oldTopic;
        this.newTopic = newTopic;
    }

    @Override
    public void doTransaction() {
        selectedSche.setType(newTopic.getType());
        selectedSche.setDate(newTopic.getDate());
        selectedSche.setTime(newTopic.getTime());
        selectedSche.setTitle(newTopic.getTitle());
        selectedSche.setTopic(newTopic.getTopic());
        selectedSche.setLink(newTopic.getLink());
        selectedSche.setCriteria(newTopic.getCriteria());
        
        Collections.sort(scheManager.getDataComponent().getScheduleList());
        scheManager.getWorkspaceComponent().getScheTable().refresh();
    }

    @Override
    public void undoTransaction() {
        selectedSche.setType(oldTopic.getType());
        selectedSche.setDate(oldTopic.getDate());
        selectedSche.setTime(oldTopic.getTime());
        selectedSche.setTitle(oldTopic.getTitle());
        selectedSche.setTopic(oldTopic.getTopic());
        selectedSche.setLink(oldTopic.getLink());
        selectedSche.setCriteria(oldTopic.getCriteria());
        
        Collections.sort(scheManager.getDataComponent().getScheduleList());
        scheManager.getWorkspaceComponent().getScheTable().refresh();
    }
    
    
}
