
package pm.data;

import djf.components.AppDataComponent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pm.ProjectManagerApp;

public class ProjectRecord implements AppDataComponent {
    
    ProjectManagerApp app;
    ObservableList<TeamData> teamList;
    ObservableList<StudentData> studentList;
    
    public ProjectRecord(ProjectManagerApp app){
        this.app = app;
        teamList = FXCollections.observableArrayList();
        studentList = FXCollections.observableArrayList();
    }
    
    public ObservableList<TeamData> getTeamList() {return teamList;}
    public ObservableList<StudentData> getStudentList() {return studentList;}
    
    @Override
    public void resetData() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
