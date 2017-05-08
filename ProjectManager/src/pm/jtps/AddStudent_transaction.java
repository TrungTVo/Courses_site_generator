
package pm.jtps;

import java.util.Collections;
import pm.ProjectManagerApp;
import pm.data.ProjectRecord;
import pm.data.StudentData;
import pm.workspace.ProjectWorkspace;

/**
 *
 * @author trungvo
 */
public class AddStudent_transaction implements jTPS_project_transaction {
    ProjectManagerApp proManager;
    StudentData newStudent;
    
    public AddStudent_transaction(ProjectManagerApp proManager,  StudentData newStudent) {
        this.proManager = proManager;
        this.newStudent = newStudent;
    }
    
    @Override
    public void doTransaction() {
        // get data
        ProjectRecord projectRecord = proManager.getDataComponent();
        
        // get workspace
        ProjectWorkspace projectWorkspace = proManager.getWorkspaceComponent();
        
        projectRecord.getStudentList().add(newStudent);
        projectWorkspace.getStudentFNameTF().clear();
        projectWorkspace.getStudentLNameTF().clear();
        projectWorkspace.getStudentTeamCombobox().getSelectionModel().clearSelection();
        projectWorkspace.getStudentRoleTF().clear();
        Collections.sort(projectRecord.getStudentList());
    }

    @Override
    public void undoTransaction() {
        // get data
        ProjectRecord projectRecord = proManager.getDataComponent();
        
        projectRecord.getStudentList().remove(newStudent);
        Collections.sort(projectRecord.getStudentList());
        proManager.getWorkspaceComponent().getStudentTable().refresh();
    }
    
}
