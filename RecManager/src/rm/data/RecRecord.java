
package rm.data;

import djf.components.AppDataComponent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import rm.RecManagerApp;

/**
 *
 * @author trungvo
 */
public class RecRecord {
    ObservableList<RecData> recRecord;
    RecManagerApp app;
    
    public RecRecord(RecManagerApp app) {
        this.app = app;
        recRecord = FXCollections.observableArrayList();
    }
    
    public ObservableList<RecData> getRecRecord() {return recRecord;}

    public void resetData() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
