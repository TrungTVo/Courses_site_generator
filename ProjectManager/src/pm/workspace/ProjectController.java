
package pm.workspace;

import djf.settings.AppPropertyType;
import djf.ui.AppMessageDialogSingleton;
import java.util.Collections;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pm.ProjectManagerApp;
import pm.data.ProjectRecord;
import pm.data.StudentData;
import pm.data.TeamData;
import pm.jtps.AddStudent_transaction;
import pm.jtps.AddTeam_transaction;
import pm.jtps.DeleteStudent_transaction;
import pm.jtps.DeleteTeam_transaction;
import pm.jtps.jTPS_project;
import pm.jtps.jTPS_project_transaction;
import properties_manager.PropertiesManager;

/**
 *
 * @author trungvo
 */
public class ProjectController {
    ProjectManagerApp projectManager;
    jTPS_project jtpsProject;
    
    public ProjectController(ProjectManagerApp projectManager) {
        this.projectManager = projectManager;
        jtpsProject = new jTPS_project();
    }
    
    public jTPS_project getJtpsProject() {return jtpsProject;}
    
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
                /*projectRecord.getTeamList().add(newTeam);
                projectWorkspace.getTeamTF().clear();
                projectWorkspace.getTeamColor().setValue(null);
                projectWorkspace.getTeamTextColor().setValue(null);
                projectWorkspace.getTeamLink().clear();
                Collections.sort(projectRecord.getTeamList());*/
                jTPS_project_transaction transation = (jTPS_project_transaction) new AddTeam_transaction(projectManager, newTeam);
                jtpsProject.addTransaction(transation);
                return true;
            }
        }
        return false;
    }
    
    public boolean handleAddStudent(StudentData newStudent) {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        // GET DATA
        ProjectRecord projectRecord = projectManager.getDataComponent();
        
        // GET WORKSPACE
        ProjectWorkspace projectWorkspace = projectManager.getWorkspaceComponent();
        
        if (newStudent.getFirstName() == null || newStudent.getFirstName().isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(AppPropertyType.STUDENT_FIRSTNAME_MISS.toString()), props.getProperty(AppPropertyType.STUDENT_FIRSTNAME_MISS_MESS.toString()));
        } else if (newStudent.getLastName() == null || newStudent.getLastName().isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(AppPropertyType.STUDENT_LASTNAME_MISS.toString()), props.getProperty(AppPropertyType.STUDENT_LASTNAME_MISS_MESS.toString()));
        } else if (newStudent.getTeam() == null || newStudent.getTeam().isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(AppPropertyType.STUDENT_TEAM_MISS.toString()), props.getProperty(AppPropertyType.STUDENT_TEAM_MISS_MESS.toString()));
        } else if (newStudent.getRole() == null || newStudent.getRole().isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(AppPropertyType.STUDENT_ROLE_MISS.toString()), props.getProperty(AppPropertyType.STUDENT_ROLE_MISS_MESS.toString()));
        } else if (isContainStudent(projectRecord.getStudentList(), newStudent.getFirstName(), newStudent.getLastName())) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(AppPropertyType.STUDENT_UNIQUE.toString()), props.getProperty(AppPropertyType.STUDENT_UNIQUE_MESS.toString()));
        } else {
            /*projectRecord.getStudentList().add(newStudent);
            projectWorkspace.getStudentFNameTF().clear();
            projectWorkspace.getStudentLNameTF().clear();
            projectWorkspace.getStudentTeamCombobox().getSelectionModel().clearSelection();
            projectWorkspace.getStudentRoleTF().clear();
            Collections.sort(projectRecord.getStudentList());*/
            jTPS_project_transaction transation = (jTPS_project_transaction) new AddStudent_transaction(projectManager, newStudent);
            jtpsProject.addTransaction(transation);
            return true;
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
    
    public boolean isContainStudent(ObservableList<StudentData> studentList, String firstName, String lastName) {
        for (StudentData student:studentList) {
            if (student.getFirstName().equals(firstName) && student.getLastName().equals(lastName)) {
                return true;
            }
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
            ObservableList<TeamData> teamList = cloneList(projectRecord.getTeamList());
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
    
    public boolean handleEditStudent(StudentData newStudent) {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        // GET DATA
        ProjectRecord projectRecord = projectManager.getDataComponent();
        
        // GET WORKSPACE
        ProjectWorkspace projectWorkspace = projectManager.getWorkspaceComponent();
        
        if (newStudent.getFirstName() == null || newStudent.getFirstName().isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(AppPropertyType.STUDENT_FIRSTNAME_MISS.toString()), props.getProperty(AppPropertyType.STUDENT_FIRSTNAME_MISS_MESS.toString()));
        } else if (newStudent.getLastName() == null || newStudent.getLastName().isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(AppPropertyType.STUDENT_LASTNAME_MISS.toString()), props.getProperty(AppPropertyType.STUDENT_LASTNAME_MISS_MESS.toString()));
        } else if (newStudent.getTeam() == null || newStudent.getTeam().isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(AppPropertyType.STUDENT_TEAM_MISS.toString()), props.getProperty(AppPropertyType.STUDENT_TEAM_MISS_MESS.toString()));
        } else if (newStudent.getRole() == null || newStudent.getRole().isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(AppPropertyType.STUDENT_ROLE_MISS.toString()), props.getProperty(AppPropertyType.STUDENT_ROLE_MISS_MESS.toString()));
        } else {
            // get current selected Rec in table
            StudentData selectedStudent = (StudentData) projectWorkspace.getStudentTable().getSelectionModel().getSelectedItem();
            int indexOfSelectedStudent = projectManager.getDataComponent().getStudentList().indexOf(selectedStudent);
            
            ObservableList<StudentData> studentList = cloneList(projectRecord.getStudentList());
            List<StudentData> checkList = studentList.subList(0, indexOfSelectedStudent);
            checkList.addAll(studentList.subList(indexOfSelectedStudent+1, studentList.size()));
            ObservableList<StudentData> tempList = FXCollections.observableArrayList();
            tempList.addAll(checkList);
            
            if (isContainStudent(tempList, newStudent.getFirstName(), newStudent.getLastName())) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(AppPropertyType.STUDENT_UNIQUE.toString()), props.getProperty(AppPropertyType.STUDENT_UNIQUE_MESS.toString()));
            } else {
                projectRecord.getStudentList().remove(indexOfSelectedStudent);
                selectedStudent.setFirstName(newStudent.getFirstName());
                selectedStudent.setLastName(newStudent.getLastName());
                selectedStudent.setTeam(newStudent.getTeam());
                selectedStudent.setRole(newStudent.getRole());
                projectRecord.getStudentList().add(newStudent);
                Collections.sort(projectRecord.getStudentList());
                
                // refresh table
                projectWorkspace.getStudentTable().refresh();
                projectWorkspace.clearStudentFields();
                return true;
            }
        }
        return false;
    }
    
    public <E> ObservableList<E> cloneList(ObservableList<E> list) {
        ObservableList<E> res = FXCollections.observableArrayList();
        for (E i:list){
           res.add(i);
        }
        return res;
    }
    
    public void handleDeleteTeam() {
        // GET WORKSPACE
        ProjectWorkspace projectWorkspace = projectManager.getWorkspaceComponent();
        
        // get current selected Rec in table
        TeamData selectedTeam = (TeamData) projectWorkspace.getTeamTable().getSelectionModel().getSelectedItem();
        
        jTPS_project_transaction transation = (jTPS_project_transaction) new DeleteTeam_transaction(projectManager, selectedTeam);
        jtpsProject.addTransaction(transation);
        
        // refresh table
        projectWorkspace.getTeamTable().refresh();
        
        // update student table 
        //updateStudentTableWithTeamDeleted(projectManager.getDataComponent().getStudentList(), selectedTeam.getName(), "");
        //projectManager.getWorkspaceComponent().getStudentTable().refresh();
    }
    
    public void updateStudentTableWithTeamDeleted(ObservableList<StudentData> studentList, String check, String updated) {
        for (StudentData student:studentList) {
            if (student.getTeam().equals(check)){
                student.setTeam(updated);
            }
        }
    }
    
    public void handleDeleteStudent() {
        // GET WORKSPACE
        ProjectWorkspace projectWorkspace = projectManager.getWorkspaceComponent();
        
        // get current selected Rec in table
        StudentData selectedStudent = (StudentData) projectWorkspace.getStudentTable().getSelectionModel().getSelectedItem();
        
        jTPS_project_transaction transation = (jTPS_project_transaction) new DeleteStudent_transaction(projectManager, selectedStudent);
        jtpsProject.addTransaction(transation);
        
        // refresh table
        projectWorkspace.getStudentTable().refresh();
    }
}
