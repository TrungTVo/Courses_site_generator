
package pm.workspace;

import djf.settings.AppPropertyType;
import djf.ui.AppMessageDialogSingleton;
import java.util.Collections;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pm.ProjectManagerApp;
import pm.data.ProjectRecord;
import pm.data.TeamData;
import properties_manager.PropertiesManager;

/**
 *
 * @author trungvo
 */
public class ProjectController {
    ProjectManagerApp projectManager;
    
    public ProjectController(ProjectManagerApp projectManager) {
        this.projectManager = projectManager;
    }
    
    public boolean handleAddTeam(TeamData newTeam) {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        // GET DATA
        ProjectRecord projectRecord = projectManager.getDataComponent();
        
        // GET WORKSPACE
        ProjectWorkspace projectWorkspace = projectManager.getWorkspaceComponent();
        
        if (newTeam.getName() == null || newTeam.getName().isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(AppPropertyType.TEAM_NAME_MISS.toString()), props.getProperty(AppPropertyType.TEAM_NAME_MISS_MESS.toString()));
        } else if (newTeam.getLink() == null || newTeam.getLink().isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(AppPropertyType.TEAM_LINK_MISS.toString()), props.getProperty(AppPropertyType.TEAM_LINK_MISS_MESS.toString()));
        } else {
            if (isContainTeam(projectRecord.getTeamList(), newTeam.getName())) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(AppPropertyType.TEAM_UNIQUE.toString()), props.getProperty(AppPropertyType.TEAM_UNIQUE_MESS.toString()));
            } else {
                projectRecord.getTeamList().add(newTeam);
                projectWorkspace.getTeamTF().clear();
                projectWorkspace.getTeamColor().setValue(null);
                projectWorkspace.getTeamTextColor().setValue(null);
                projectWorkspace.getTeamLink().clear();
                Collections.sort(projectRecord.getTeamList());
                return true;
            }
        }
        return false;
    }
    
    public boolean isContainTeam(ObservableList<TeamData> teamList, String teamName) {
        for (TeamData team:teamList){
            if (team.getName().equals(teamName))
                return true;
        }
        return false;
    }
    
    public boolean handleEditTeam(TeamData newTeam) {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        // GET DATA
        ProjectRecord projectRecord = projectManager.getDataComponent();
        
        // GET WORKSPACE
        ProjectWorkspace projectWorkspace = projectManager.getWorkspaceComponent();
        
        if (newTeam.getName() == null || newTeam.getName().isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(AppPropertyType.TEAM_NAME_MISS.toString()), props.getProperty(AppPropertyType.TEAM_NAME_MISS_MESS.toString()));
        } else if (newTeam.getLink() == null || newTeam.getLink().isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(AppPropertyType.TEAM_LINK_MISS.toString()), props.getProperty(AppPropertyType.TEAM_LINK_MISS_MESS.toString()));
        } else if (newTeam.getColor() == null || newTeam.getColor().isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(AppPropertyType.TEAM_COLOR_MISS.toString()), props.getProperty(AppPropertyType.TEAM_COLOR_MISS_MESS.toString()));
        } else if (newTeam.getTextColor()== null || newTeam.getTextColor().isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(AppPropertyType.TEAM_TEXTCOLOR_MISS.toString()), props.getProperty(AppPropertyType.TEAM_TEXTCOLOR_MISS_MESS.toString()));
        } else {
            // get current selected team in table
            TeamData selectedTeam = (TeamData) projectWorkspace.getTeamTable().getSelectionModel().getSelectedItem();  
            int indexOfSelectedTeam = projectRecord.getTeamList().indexOf(selectedTeam);
            
            // get clone team list 
            ObservableList<TeamData> teamList = cloneTeamList(projectRecord.getTeamList());
            List<TeamData> checkList = teamList.subList(0, indexOfSelectedTeam);
            checkList.addAll(teamList.subList(indexOfSelectedTeam+1, teamList.size()));
            
            // convert to ObservableList for passing as parameter into isContainTeam() function
            ObservableList<TeamData> tempList = FXCollections.observableArrayList();
            tempList.addAll(checkList);
            
            if (isContainTeam(tempList, newTeam.getName())) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(AppPropertyType.TEAM_UNIQUE.toString()), props.getProperty(AppPropertyType.TEAM_UNIQUE_MESS.toString()));
            } else {
                projectRecord.getTeamList().remove(indexOfSelectedTeam);
                selectedTeam.setName(newTeam.getName());
                selectedTeam.setColor(newTeam.getColor());
                selectedTeam.setTextColor(newTeam.getTextColor());
                selectedTeam.setLink(newTeam.getLink());
                projectRecord.getTeamList().add(selectedTeam);
                Collections.sort(projectRecord.getTeamList());
                
                // refresh table
                projectWorkspace.getTeamTable().refresh();
                projectWorkspace.clearTeamFields();
                return true;
            }
        }
        return false;
    }
    
    public ObservableList<TeamData> cloneTeamList(ObservableList<TeamData> list) {
        ObservableList<TeamData> res = FXCollections.observableArrayList();
        for (TeamData team:list){
           res.add(team);
        }
        return res;
    }
    
    public void handleDeleteTeam() {
        // GET WORKSPACE
        ProjectWorkspace projectWorkspace = projectManager.getWorkspaceComponent();
        
        // get current selected Rec in table
        TeamData selectedTeam = (TeamData) projectWorkspace.getTeamTable().getSelectionModel().getSelectedItem();
        int indexOfSelectedTeam = projectManager.getDataComponent().getTeamList().indexOf(selectedTeam);
        
        // remove selected team
        projectManager.getDataComponent().getTeamList().remove(indexOfSelectedTeam);
        
        // refresh table
        projectWorkspace.getTeamTable().refresh();
    }
}
