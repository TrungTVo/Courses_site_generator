/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cm;

import cm.workspace.CourseWorkspace;
import djf.AppTemplate;

/**
 *
 * @author trungvo
 */
public class CourseManagerApp extends AppTemplate {

    @Override
    public void buildAppComponentsHook() {
        
        workspaceComponent = new CourseWorkspace(this);
        
    }
    
}
