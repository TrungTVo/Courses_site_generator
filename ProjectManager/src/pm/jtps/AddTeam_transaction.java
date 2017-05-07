
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
public class AddTeam_transaction implements jTPS_project_transaction {
    ProjectManagerApp proManager;
    TeamData newTeam;
    
    public AddTeam_transaction(ProjectManagerApp proManager, TeamData newTeam) {
        this.proManager = proManager;
        this.newTeam = newTeam;
    }

    @Override
    public void doTransaction() {
        // get data
        ProjectRecord projectRecord = proManager.getDataComponent();
        
        // get workspace
        ProjectWorkspace projectWorkspace = proManager.getWorkspaceComponent();
        
        projectRecord.getTeamList().add(newTeam);
        projectWorkspace.getTeamTF().clear();
        projectWorkspace.getTeamColor().setValue(null);
        projectWorkspace.getTeamTextColor().setValue(null);
        projectWorkspace.getTeamLink().clear();
        Collections.sort(projectRecord.getTeamList());
    }

    @Override
    public void undoTransaction() {
        // get data
        ProjectRecord projectRecord = proManager.getDataComponent();
        
        projectRecord.getTeamList().remove(newTeam);
        Collections.sort(projectRecord.getTeamList());
        proManager.getWorkspaceComponent().getTeamTable().refresh();
    }
    
    
}
