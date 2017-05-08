
package pm.jtps;

import java.util.Collections;
import pm.ProjectManagerApp;
import pm.data.ProjectRecord;
import pm.data.StudentData;

/**
 *
 * @author trungvo
 */
public class DeleteStudent_transaction implements jTPS_project_transaction {
    ProjectManagerApp proManager;
    StudentData selectedStudent;
    
    public DeleteStudent_transaction(ProjectManagerApp proManager,  StudentData selectedStudent) {
        this.proManager = proManager;
        this.selectedStudent = selectedStudent;
    }

    @Override
    public void doTransaction() {
        int indexOfSelectedStudent = proManager.getDataComponent().getStudentList().indexOf(selectedStudent);
        
        // remove selected team
        proManager.getDataComponent().getStudentList().remove(indexOfSelectedStudent);
        
        // refresh table
        proManager.getWorkspaceComponent().getStudentTable().refresh();
    }

    @Override
    public void undoTransaction() {
        // get data
        ProjectRecord projectRecord = proManager.getDataComponent();
        
        projectRecord.getStudentList().add(selectedStudent);
        Collections.sort(projectRecord.getStudentList());
        proManager.getWorkspaceComponent().getStudentTable().refresh();
    }
}
