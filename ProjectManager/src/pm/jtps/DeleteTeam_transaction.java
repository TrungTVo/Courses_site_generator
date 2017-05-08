
package pm.jtps;

import java.util.Collections;
import pm.ProjectManagerApp;
import pm.data.ProjectRecord;
import pm.data.TeamData;
import pm.workspace.ProjectWorkspace;

/**
 *
 * @author trungvo
 */
public class DeleteTeam_transaction implements jTPS_project_transaction {
    ProjectManagerApp proManager;
    TeamData selectedTeam;
    
    public DeleteTeam_transaction(ProjectManagerApp proManager, TeamData selectedTeam) {
        this.proManager = proManager;
        this.selectedTeam = selectedTeam;
    }
    
    @Override
    public void doTransaction() {
        int indexOfSelectedTeam = proManager.getDataComponent().getTeamList().indexOf(selectedTeam);
        
        // remove selected team
        proManager.getDataComponent().getTeamList().remove(indexOfSelectedTeam);
        
        // refresh table
        proManager.getWorkspaceComponent().getTeamTable().refresh();
        
        // update student table 
        proManager.getWorkspaceComponent().getProjectController().updateStudentTableWithTeamDeleted(proManager.getDataComponent().getStudentList(), selectedTeam.getName(), "");
        proManager.getWorkspaceComponent().getStudentTable().refresh();
        
        // update team ComboBox in Student section
        ProjectWorkspace proWorkspace = proManager.getWorkspaceComponent();
        proManager.getWorkspaceComponent().getStudentTeamCombobox().setItems(proWorkspace.getTeamList(proManager.getDataComponent().getTeamList()));
    }

    @Override
    public void undoTransaction() {
        // get data
        ProjectRecord projectRecord = proManager.getDataComponent();
        
        projectRecord.getTeamList().add(selectedTeam);
        
        Collections.sort(projectRecord.getTeamList());
        
        // update back student table
        proManager.getWorkspaceComponent().getProjectController().updateStudentTableWithTeamDeleted(projectRecord.getStudentList(), "", selectedTeam.getName());
        proManager.getWorkspaceComponent().getStudentTable().refresh();
        
        // update team ComboBox in Student section
        ProjectWorkspace proWorkspace = proManager.getWorkspaceComponent();
        proManager.getWorkspaceComponent().getStudentTeamCombobox().setItems(proWorkspace.getTeamList(proManager.getDataComponent().getTeamList()));
    }
    
}
