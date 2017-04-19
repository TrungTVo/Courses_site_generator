
package sm.data;

import djf.components.AppDataComponent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sm.ScheduleManagerApp;

public class ScheduleData {
    
    ScheduleManagerApp app;
    String startMon;
    String endFri;
    ObservableList<ScheduleTopic> scheduleList;
    
    public ScheduleData(ScheduleManagerApp app) {
        this.app = app;
        startMon = new String();
        endFri = new String();
        scheduleList = FXCollections.observableArrayList();
    }
    
    public ObservableList<ScheduleTopic> getScheduleList() {return scheduleList;}
    
    public void resetData() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
