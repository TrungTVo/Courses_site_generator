/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cm;

import cm.data.CourseData;
import cm.style.CourseStyle;
import cm.workspace.CourseWorkspace;
import djf.AppTemplate;
import djf.ui.AppGUI;

/**
 *
 * @author trungvo
 */
public class CourseManagerApp extends AppTemplate {
    
    @Override
    public void buildAppComponentsHook() {
        dataComponent = new CourseData(this);
        workspaceComponent = new CourseWorkspace(this);
        styleComponent = new CourseStyle(this);
    }
    
}
