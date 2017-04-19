/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pm;

import djf.AppTemplate;
import pm.data.ProjectRecord;
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
    
    public ProjectRecord getDataComponent() {return dataComponent;}
    public ProjectStyle getStyleComponent() {return styleComponent;}
    public ProjectWorkspace getWorkspaceComponent() {return workspaceComponent;}
    
    public void buildAppComponentsHook() {
        dataComponent = new ProjectRecord(this);
        workspaceComponent = new ProjectWorkspace(this);
        styleComponent = new ProjectStyle(this);
    }
    
}
