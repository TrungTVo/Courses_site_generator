
package pm.style;

import djf.AppTemplate;
import djf.components.AppStyleComponent;
import pm.workspace.ProjectWorkspace;

public class ProjectStyle extends AppStyleComponent {
    private AppTemplate app;
    public static String CLASS_WRAP_VBOX = "wrapVBox";
    public static String CLASS_TEXTCOLOR_LABEL = "textColorLabel";
    public static String CLASS_PROJECT_TITLE = "projectTitle";
    public static String CLASS_TEAM_TITLE = "teamTitle";
    public static String CLASS_STUDENT_LABEL = "studentLabel";
    public static String CLASS_ADDEDIT_TEAM_LABEL = "addEditTeamTitle";
    public static String CLASS_ADDEDIT_STUDENT_LABEL = "addEditStudentTitle";
    public static String CLASS_TEAM_HEADERBOX = "teamHeaderBox";
    public static String CLASS_STUDENT_HEADERBOX = "studentHeaderBox";
    public static String CLASS_TEAM_VBOX = "teamVBox";
    public static String CLASS_STUDENT_VBOX = "studentVBox";
             
    public ProjectStyle(AppTemplate app){
        this.app = app;
        initProjectStyle();
    }
    
    private void initProjectStyle() {
        ProjectWorkspace workspaceComponent = (ProjectWorkspace)app.getWorkspaceComponent();
        workspaceComponent.getWrapVBox().getStyleClass().add(CLASS_WRAP_VBOX);
        workspaceComponent.getTextColorLabel().getStyleClass().add(CLASS_TEXTCOLOR_LABEL);
        workspaceComponent.getProjectTitle().getStyleClass().add(CLASS_PROJECT_TITLE);
        workspaceComponent.getTeamTitle().getStyleClass().add(CLASS_TEAM_TITLE);
        workspaceComponent.getStudentLabel().getStyleClass().add(CLASS_STUDENT_LABEL);
        workspaceComponent.getAddEditTeamLabel().getStyleClass().add(CLASS_ADDEDIT_TEAM_LABEL);
        workspaceComponent.getAddEditStudentLabel().getStyleClass().add(CLASS_ADDEDIT_STUDENT_LABEL);
        workspaceComponent.getTeamHeaderBox().getStyleClass().add(CLASS_TEAM_HEADERBOX);
        workspaceComponent.getStudentHeaderBox().getStyleClass().add(CLASS_STUDENT_HEADERBOX);
        workspaceComponent.getTeamVBox().getStyleClass().add(CLASS_TEAM_VBOX);
        workspaceComponent.getStudentVBox().getStyleClass().add(CLASS_STUDENT_VBOX);
    }
}
