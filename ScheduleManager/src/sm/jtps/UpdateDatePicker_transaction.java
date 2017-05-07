
package sm.jtps;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import sm.ScheduleManagerApp;
import sm.data.ScheduleData;
import sm.workspace.ScheduleWorkspace;

/**
 *
 * @author trungvo
 */
public class UpdateDatePicker_transaction implements jTPS_sche_Transaction {
    ScheduleManagerApp scheManager;
    String oldStart;
    String newStart;
    String oldEnd;
    String newEnd;
    
    public UpdateDatePicker_transaction(ScheduleManagerApp scheManager, String oldStart, String newStart, String oldEnd, String newEnd) {
        this.scheManager = scheManager;
        this.oldStart = oldStart;
        this.oldEnd = oldEnd;
        this.newStart = newStart;
        this.newEnd = newEnd;
    }
    @Override
    public void doTransaction() {
        // get data
        ScheduleData scheData = scheManager.getDataComponent();
        
        //get workspace
        ScheduleWorkspace scheWorkspace = scheManager.getWorkspaceComponent();
        
        scheWorkspace.getStartPicker().setValue(LocalDate.parse(newStart, DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        scheWorkspace.getEndPicker().setValue(LocalDate.parse(newEnd, DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        scheData.setStart(newStart);
        scheData.setEnd(newEnd);
    }

    @Override
    public void undoTransaction() {
        // get data
        ScheduleData scheData = scheManager.getDataComponent();
        
        //get workspace
        ScheduleWorkspace scheWorkspace = scheManager.getWorkspaceComponent();
        
        scheWorkspace.getStartPicker().setValue(LocalDate.parse(oldStart, DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        scheWorkspace.getEndPicker().setValue(LocalDate.parse(oldEnd, DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        scheData.setStart(oldStart);
        scheData.setEnd(oldEnd);
    }
    
}
