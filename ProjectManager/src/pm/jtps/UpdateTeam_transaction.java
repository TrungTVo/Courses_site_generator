
package pm.jtps;

import java.util.Collections;
import pm.ProjectManagerApp;
import pm.data.TeamData;
import pm.workspace.ProjectWorkspace;

/**
 *
 * @author trungvo
 */
public class UpdateTeam_transaction implements jTPS_project_transaction {
    ProjectManagerApp proManager;
    TeamData selectedTeam;
    TeamData oldTeam;
    TeamData newTeam;
    
    public UpdateTeam_transaction(ProjectManagerApp proManager, TeamData selectedTeam, TeamData oldTeam, TeamData newTeam) {
        this.proManager = proManager;
        this.selectedTeam = selectedTeam;
        this.oldTeam = oldTeam;
        this.newTeam = newTeam;
    }
    
    @Override
    public void doTransaction() {
        selectedTeam.setName(newTeam.getName());
        selectedTeam.setColor(newTeam.getColor());
        selectedTeam.setTextColor(newTeam.getTextColor());
        selectedTeam.setLink(newTeam.getLink());
        
        Collections.sort(proManager.getDataComponent().getTeamList());
                
        // refresh table
        proManager.getWorkspaceComponent().getTeamTable().refresh();
        
        // update team ComboBox in Student section
        ProjectWorkspace proWorkspace = proManager.getWorkspaceComponent();
        proManager.getWorkspaceComponent().getStudentTeamCombobox().setItems(proWorkspace.getTeamList(proManager.getDataComponent().getTeamList()));
        
        // update student table
        proManager.getWorkspaceComponent().handleUpdateStudentTable(oldTeam.getName(), newTeam.getName());
        proManager.getWorkspaceComponent().getStudentTable().refresh();
    }

    @Override
    public void undoTransaction() {
        selectedTeam.setName(oldTeam.getName());
        selectedTeam.setColor(oldTeam.getColor());
        selectedTeam.setTextColor(oldTeam.getTextColor());
        selectedTeam.setLink(oldTeam.getLink());
        
        Collections.sort(proManager.getDataComponent().getTeamList());
                
        // refresh table
        proManager.getWorkspaceComponent().getTeamTable().refresh();
        
        // update team ComboBox in Student section
        ProjectWorkspace proWorkspace = proManager.getWorkspaceComponent();
        proManager.getWorkspaceComponent().getStudentTeamCombobox().setItems(proWorkspace.getTeamList(proManager.getDataComponent().getTeamList()));
        
        // update student table
        proManager.getWorkspaceComponent().handleUpdateStudentTable(newTeam.getName(), oldTeam.getName());
        proManager.getWorkspaceComponent().getStudentTable().refresh();
    }
}
