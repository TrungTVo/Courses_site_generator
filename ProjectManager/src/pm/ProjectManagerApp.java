/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pm;

import cm.CourseManagerApp;
import csg.CourseSiteGenerator;
import djf.AppTemplate;
import pm.data.ProjectRecord;
import pm.file.ProjectFiles;
import pm.style.ProjectStyle;
import pm.workspace.ProjectWorkspace;

/**
 *
 * @author trungvo
 */
public class ProjectManagerApp {

    ProjectRecord dataComponent;
    ProjectStyle styleComponent;
    ProjectWorkspace workspaceComponent;
    ProjectFiles fileComponent;
    
    CourseManagerApp courseManagerApp;
    CourseSiteGenerator csg;
    
    public ProjectManagerApp(CourseManagerApp courseManagerApp, CourseSiteGenerator csg){
        this.courseManagerApp = courseManagerApp;
        this.csg = csg;
    }
    
    public ProjectRecord getDataComponent() {return dataComponent;}
    public ProjectStyle getStyleComponent() {return styleComponent;}
    public ProjectWorkspace getWorkspaceComponent() {return workspaceComponent;}
    public ProjectFiles getFileComponent() {return fileComponent;}
    public CourseManagerApp getCourseManagerApp() {return courseManagerApp;}
    
    public void buildAppComponentsHook() {
        dataComponent = new ProjectRecord(this);
        workspaceComponent = new ProjectWorkspace(this, csg);
        styleComponent = new ProjectStyle(this);
        fileComponent = new ProjectFiles(this);
    }
    
}
