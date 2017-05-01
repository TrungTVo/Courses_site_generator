/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cm;

import cm.data.CourseData;
import cm.style.CourseStyle;
import cm.workspace.CourseWorkspace;
import csg.CourseSiteGenerator;
import djf.AppTemplate;
import djf.ui.AppGUI;

/**
 *
 * @author trungvo
 */
public class CourseManagerApp {
    CourseSiteGenerator csg;
    CourseData dataComponent;
    CourseWorkspace workspaceComponent;
    CourseStyle styleComponent;
    
    public CourseData getDataComponent() {return dataComponent;}
    public CourseWorkspace getWorkspaceComponent() {return workspaceComponent;}
    public CourseStyle getStyleComponent() {return styleComponent;}
    public CourseSiteGenerator getCSG() {return csg;}
    
    public CourseManagerApp(CourseSiteGenerator csg) {
        this.csg = csg;
    }
    
    public void buildAppComponentsHook() {
        dataComponent = new CourseData(this);
        workspaceComponent = new CourseWorkspace(this, csg);
        styleComponent = new CourseStyle(this);
    }
    
}
