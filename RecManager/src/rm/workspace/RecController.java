
package rm.workspace;

import static djf.settings.AppPropertyType.ADD_BUTTON_TEXT;
import static djf.settings.AppPropertyType.REC_LOCATION_MISS;
import static djf.settings.AppPropertyType.REC_LOCATION_MISS_MESS;
import static djf.settings.AppPropertyType.REC_SECTION_MISS;
import static djf.settings.AppPropertyType.REC_SECTION_MISS_MESS;
import static djf.settings.AppPropertyType.REC_SECTION_UNIQUE;
import static djf.settings.AppPropertyType.REC_SECTION_UNIQUE_MESS;
import static djf.settings.AppPropertyType.REC_TIME_MISS;
import static djf.settings.AppPropertyType.REC_TIME_MISS_MESS;
import djf.ui.AppMessageDialogSingleton;
import java.util.Collections;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import properties_manager.PropertiesManager;
import rm.RecManagerApp;
import rm.data.RecData;
import rm.data.RecRecord;
import rm.jtps.AddRec_Transaction;
import rm.jtps.DeleteRec_Transaction;
import rm.jtps.UpdateRec_Transaction;
import rm.jtps.jTPS_rec;
import rm.jtps.jTPS_rec_Transaction;

/**
 *
 * @author trungvo
 */
public class RecController {
    RecManagerApp recManager;
    jTPS_rec jtpsRec;
    
    public RecController(RecManagerApp recManager){
        this.recManager = recManager;
        jtpsRec = new jTPS_rec();
    }
    
    public jTPS_rec getJtpsRec() {return jtpsRec;}
    
    public boolean handleAddRec(RecData newRec){
        // WE'LL NEED THIS IN CASE WE NEED TO DISPLAY ANY ERROR MESSAGES
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        // GET DATA
        RecRecord recRecord = recManager.getDataComponent();
        
        if (newRec.getSection() == null || newRec.getSection().isEmpty()){
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(REC_SECTION_MISS), props.getProperty(REC_SECTION_MISS_MESS));
        } else if (newRec.getDayTime() == null || newRec.getDayTime().isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(REC_TIME_MISS), props.getProperty(REC_TIME_MISS_MESS));
        } else if (newRec.getLocation()== null || newRec.getLocation().isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(REC_LOCATION_MISS), props.getProperty(REC_LOCATION_MISS_MESS));
        } else {
            if (isContainSection(recRecord.getRecRecord(), newRec.getSection())) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(REC_SECTION_UNIQUE), props.getProperty(REC_SECTION_UNIQUE_MESS));
            } else {
                /*
                recRecord.getRecRecord().add(newRec);
                RecWorkspace workspace = recManager.getWorkspaceComponent();
                workspace.getSectionTF().clear();
                workspace.getDayTimeTF().clear();
                workspace.getLocationTF().clear();
                workspace.getInstructorTF().clear();
                workspace.getTa1Combo().getSelectionModel().clearSelection();
                workspace.getTa2Combo().getSelectionModel().clearSelection();
                
                Collections.sort(recRecord.getRecRecord()); */
                jTPS_rec_Transaction transaction = (jTPS_rec_Transaction) new AddRec_Transaction(recManager, newRec);
                jtpsRec.addTransaction(transaction);
                
                return true;
            }
        }
        return false;
    }
    
    public boolean isContainSection(ObservableList<RecData> recList, String section) {
        for (RecData rec:recList){
            if (rec.getSection().equals(section))
                return true;
        }
        return false;
    }
    
    public ObservableList<RecData> cloneRecList(ObservableList<RecData> list) {
        ObservableList<RecData> res = FXCollections.observableArrayList();
        for (RecData rec:list) {
            res.add(rec);
        }
        return res;
    }
    
    public boolean handleUpdateRec(RecData newRec) {
        // WE'LL NEED THIS IN CASE WE NEED TO DISPLAY ANY ERROR MESSAGES
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        // GET DATA
        RecRecord recRecord = recManager.getDataComponent();
        
        // GET WORKSPACE
        RecWorkspace recWorkspace = recManager.getWorkspaceComponent();
        
        if (newRec.getSection() == null || newRec.getSection().isEmpty()){
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(REC_SECTION_MISS), props.getProperty(REC_SECTION_MISS_MESS));
        } else if (newRec.getDayTime() == null || newRec.getDayTime().isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(REC_TIME_MISS), props.getProperty(REC_TIME_MISS_MESS));
        } else if (newRec.getLocation()== null || newRec.getLocation().isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(REC_LOCATION_MISS), props.getProperty(REC_LOCATION_MISS_MESS));
        } else {
            // get current selected Rec in table
            RecData selectedRec = (RecData)recWorkspace.getRecTable().getSelectionModel().getSelectedItem();
            int indexOfCurrentRec = recManager.getDataComponent().getRecRecord().indexOf(selectedRec);
            
            // get list of recs, exclusive of current selected rec
            ObservableList<RecData> recList = cloneRecList(recManager.getDataComponent().getRecRecord());
            List<RecData> checkList = recList.subList(0, indexOfCurrentRec);
            checkList.addAll(recList.subList(indexOfCurrentRec+1, recList.size()));
            
            ObservableList<RecData> tempList = FXCollections.observableArrayList();
            tempList.addAll(checkList);
            // check to see if duplicate rec (after edited) contained in this list or not
            if (isContainSection(tempList, newRec.getSection())) {                     // if contained
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(REC_SECTION_UNIQUE), props.getProperty(REC_SECTION_UNIQUE_MESS));
            } else {
                RecData oldRec = new RecData(selectedRec.getSection(), selectedRec.getInstructor(), selectedRec.getDayTime(), selectedRec.getLocation(), selectedRec.getTa1(), selectedRec.getTa2());
                jTPS_rec_Transaction transaction = (jTPS_rec_Transaction) new UpdateRec_Transaction(recManager, selectedRec, newRec, oldRec);
                jtpsRec.addTransaction(transaction);
                
                // refresh table
                recWorkspace.getRecTable().refresh();
                
                // Change Button text back to Add
                recWorkspace.getAddUpdateButton().setText(props.getProperty(ADD_BUTTON_TEXT));
                
                // Clear fields
                recWorkspace.getSectionTF().clear();
                recWorkspace.getDayTimeTF().clear();
                recWorkspace.getLocationTF().clear();
                recWorkspace.getInstructorTF().clear();
                recWorkspace.getTa1Combo().getSelectionModel().clearSelection();
                recWorkspace.getTa2Combo().getSelectionModel().clearSelection();
                
                // clear table selection
                recWorkspace.getRecTable().getSelectionModel().clearSelection();
                return true;
            }
        }
        return false;
    }
    
    public RecData getRecData(ObservableList<RecData> recList, String section) {
        for (RecData rec:recList){
            if (rec.getSection().equals(section))
                return rec;
        }
        return null;
    }
    
    public void handleDeleteRec() {
        // GET WORKSPACE
        RecWorkspace recWorkspace = recManager.getWorkspaceComponent();
        
        // get current selected Rec in table
        RecData selectedRec = (RecData)recWorkspace.getRecTable().getSelectionModel().getSelectedItem();
        
        jTPS_rec_Transaction transaction = (jTPS_rec_Transaction) new DeleteRec_Transaction(recManager, selectedRec);
        jtpsRec.addTransaction(transaction);
        
        // UDPATE TABLE
        recWorkspace.getRecTable().refresh();
    }
    
}
