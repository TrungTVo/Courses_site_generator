
package pm.jtps;

import java.util.Collections;
import pm.ProjectManagerApp;
import pm.data.StudentData;

/**
 *
 * @author trungvo
 */
public class UpdateStudent_transaction implements jTPS_project_transaction {
    ProjectManagerApp proManager;
    StudentData selectedStudent;
    StudentData oldStudent;
    StudentData newStudent;
    
    public UpdateStudent_transaction(ProjectManagerApp proManager, StudentData selectedStudent, StudentData oldStudent, StudentData newStudent) {
        this.proManager = proManager;
        this.selectedStudent = selectedStudent;
        this.oldStudent = oldStudent;
        this.newStudent = newStudent;
    }
    
    @Override
    public void doTransaction() {
        selectedStudent.setFirstName(newStudent.getFirstName());
        selectedStudent.setLastName(newStudent.getLastName());
        selectedStudent.setTeam(newStudent.getTeam());
        selectedStudent.setRole(newStudent.getRole());
        
        Collections.sort(proManager.getDataComponent().getStudentList());
                
        // refresh table
        proManager.getWorkspaceComponent().getStudentTable().refresh();
        proManager.getWorkspaceComponent().clearStudentFields();
    }

    @Override
    public void undoTransaction() {
        selectedStudent.setFirstName(oldStudent.getFirstName());
        selectedStudent.setLastName(oldStudent.getLastName());
        selectedStudent.setTeam(oldStudent.getTeam());
        selectedStudent.setRole(oldStudent.getRole());
        
        Collections.sort(proManager.getDataComponent().getStudentList());
                
        // refresh table
        proManager.getWorkspaceComponent().getStudentTable().refresh();
        proManager.getWorkspaceComponent().clearStudentFields();
    }
    
}
