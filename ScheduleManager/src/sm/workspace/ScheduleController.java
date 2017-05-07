
package sm.workspace;

import djf.settings.AppPropertyType;
import djf.ui.AppMessageDialogSingleton;
import java.util.Collections;
import properties_manager.PropertiesManager;
import sm.ScheduleManagerApp;
import sm.data.ScheduleData;
import sm.data.ScheduleTopic;
import sm.jtps.AddSche_transaction;
import sm.jtps.DeleteSche_transaction;
import sm.jtps.jTPS_sche;
import sm.jtps.jTPS_sche_Transaction;

public class ScheduleController {
    ScheduleManagerApp scheduleManager;
    jTPS_sche jtpsSche;
    
    public ScheduleController(ScheduleManagerApp scheduleManager) {
        this.scheduleManager = scheduleManager;
        jtpsSche = new jTPS_sche();
    }
    public jTPS_sche getJtpsSche() {return jtpsSche;}
    
    public boolean handleAddSchedule(ScheduleTopic newTopic) {
        // WE'LL NEED THIS IN CASE WE NEED TO DISPLAY ANY ERROR MESSAGES
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        // GET DATA
        ScheduleData scheData = scheduleManager.getDataComponent();
        
        // GET WORKSPACE
        ScheduleWorkspace scheWorkspace = scheduleManager.getWorkspaceComponent();
        
        if (scheWorkspace.getTypeComboBox().getSelectionModel().getSelectedItem() == null){
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(AppPropertyType.SCHEDULE_TYPE_MISS.toString()), props.getProperty(AppPropertyType.SCHEDULE_TYPE_MISS_MESS.toString()));
        } else if (scheWorkspace.getDatePicker().getValue() == null) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(AppPropertyType.SCHEDULE_DATE_MISS.toString()), props.getProperty(AppPropertyType.SCHEDULE_DATE_MISS_MESS.toString()));
        } else {
            /*scheData.getScheduleList().add(newTopic);
            scheWorkspace.getTypeComboBox().getSelectionModel().clearSelection();
            scheWorkspace.getDatePicker().setValue(null);
            scheWorkspace.getTimeTF().clear();
            scheWorkspace.getTitleTF().clear();
            scheWorkspace.getTopicTF().clear();
            scheWorkspace.getLinkTF().clear();
            scheWorkspace.getCriteriaTF().clear();
            Collections.sort(scheData.getScheduleList());*/
            jTPS_sche_Transaction transaction = (jTPS_sche_Transaction) new AddSche_transaction(scheduleManager, newTopic);
            jtpsSche.addTransaction(transaction);
            return true;
        }
        return false;
    }
    
    public void handleUpdateSchedule(ScheduleTopic selectedTopic, ScheduleTopic editTopic) {
        selectedTopic.setType(editTopic.getType());
        selectedTopic.setDate(editTopic.getDate());
        selectedTopic.setTime(editTopic.getTime());
        selectedTopic.setTitle(editTopic.getTitle());
        selectedTopic.setTopic(editTopic.getTopic());
        selectedTopic.setLink(editTopic.getLink());
        selectedTopic.setCriteria(editTopic.getCriteria());
        
        Collections.sort(scheduleManager.getDataComponent().getScheduleList());
        ScheduleWorkspace scheWorkspace = scheduleManager.getWorkspaceComponent();
        scheWorkspace.getScheTable().refresh();
        scheWorkspace.clearFields();
    }
    
    public void handleDeleteSchedule(ScheduleTopic topic) {
        // GET WORKSPACE
        ScheduleWorkspace scheWorkspace = scheduleManager.getWorkspaceComponent();
        
        // get current selected
        ScheduleTopic selectedSche = (ScheduleTopic) scheWorkspace.getScheTable().getSelectionModel().getSelectedItem();
        jTPS_sche_Transaction transaction = (jTPS_sche_Transaction) new DeleteSche_transaction(scheduleManager, selectedSche);
        jtpsSche.addTransaction(transaction);
        
        // refresh table
        scheWorkspace.getScheTable().refresh();
    }
}
