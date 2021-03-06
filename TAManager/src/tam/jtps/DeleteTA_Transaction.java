/**
 * This class serves as a transaction object for delete TA. Used
 * in UNDO/REDO mode. This transaction object will be used to be pushed
 * into the stack using jTPS framework.
 * 
 * @author Trung Vo - CSE219
 */
package tam.jtps;

import java.util.Collections;
import java.util.HashMap;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import rm.workspace.RecWorkspace;
import tam.TAManagerApp;
import tam.jtps.jTPS_Transaction;
import tam.data.TAData;
import tam.data.TeachingAssistant;

public class DeleteTA_Transaction implements jTPS_Transaction {
    private String taName;
    private String taEmail;
    private HashMap<String, StringProperty> officeHours;
    private TAData taData;
    private HashMap<String, StringProperty> clonedOfficeHours = new HashMap<>();
    RecWorkspace recWorkspace;
    TAManagerApp taManager;
    
    public DeleteTA_Transaction(String taName, String taEmail, HashMap<String, StringProperty> officeHours, TAData taData, TAManagerApp taManager, RecWorkspace recWorkspace){
        this.taName = taName;
        this.taEmail = taEmail;
        this.officeHours = officeHours;
        this.taData = taData;
        clonedOfficeHours = clone(officeHours);
        this.taManager = taManager;
        this.recWorkspace = recWorkspace;
    }

    @Override
    public void doTransaction() {
        for (String cellKey:taData.getOfficeHours().keySet()){
            StringProperty cellText = taData.getOfficeHours().get(cellKey);
            if (cellText != null) {
                if (taData.isCellPaneHasTAName(cellText.getValue(), taName)) {
                    taData.removeTAFromCell(cellText, taName, null, false, cellKey);
                }
            }
        }
        
        int indexOfRemoveTA = -1;
        for (int i=0; i<taData.getTeachingAssistants().size(); i++){
            if (((TeachingAssistant)taData.getTeachingAssistants().get(i)).getName().equals(taName)){
                indexOfRemoveTA = i;
                break;
            }
        }
        if (indexOfRemoveTA != -1)
            taData.getTeachingAssistants().remove(indexOfRemoveTA);
        
        // update rec table
        taManager.getWorkspaceComponent().handleUpdateRecTable(taName, "");
        recWorkspace.getRecTable().refresh();
        
        // Reset TA ComboBox in Recitation tab
        this.recWorkspace.getTa1Combo().setItems(recWorkspace.getTAList(taManager.getDataComponent().getTeachingAssistants()));
        this.recWorkspace.getTa2Combo().setItems(recWorkspace.getTAList(taManager.getDataComponent().getTeachingAssistants()));
    }

    @Override
    public void undoTransaction() {
        // add TA back to table
        int indexOfRemoveTA = -1;
        for (int i=0; i<taData.getTeachingAssistants().size(); i++){
            if (((TeachingAssistant)taData.getTeachingAssistants().get(i)).getName().equals(taName)){
                indexOfRemoveTA = i;
                break;
            }
        }
        if (indexOfRemoveTA != -1)
            taData.getTeachingAssistants().remove(indexOfRemoveTA);
        
        taData.getTeachingAssistants().add(new TeachingAssistant(taName, taEmail, true));
        Collections.sort(taData.getTeachingAssistants());
        
        // put deleted TA back to cell
        for (String item:clonedOfficeHours.keySet()){
            if (taData.getOfficeHours().containsKey(item)){
                if (clonedOfficeHours.get(item) != null){
                    if (taData.getOfficeHours().get(item) != null)
                        taData.getOfficeHours().get(item).setValue(clonedOfficeHours.get(item).getValue());
                }
            }
        }
        
        // update rec table
        taManager.getWorkspaceComponent().handleUpdateRecTable("", taName);
        recWorkspace.getRecTable().refresh();
        
        // Reset TA ComboBox in Recitation tab
        this.recWorkspace.getTa1Combo().setItems(recWorkspace.getTAList(taManager.getDataComponent().getTeachingAssistants()));
        this.recWorkspace.getTa2Combo().setItems(recWorkspace.getTAList(taManager.getDataComponent().getTeachingAssistants()));
    }
    
    public HashMap<String, StringProperty> clone(HashMap<String,StringProperty> officeHours){
        clonedOfficeHours.clear();
        for (String item:officeHours.keySet()){
            clonedOfficeHours.put(item, new Label().textProperty());
            if (officeHours.get(item) != null)
                clonedOfficeHours.get(item).setValue(new String(officeHours.get(item).getValue()));
        }
        return clonedOfficeHours;
    }
}
