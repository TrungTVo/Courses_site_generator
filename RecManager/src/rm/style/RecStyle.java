/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rm.style;

import djf.AppTemplate;
import djf.components.AppStyleComponent;
import rm.workspace.RecWorkspace;

/**
 *
 * @author trungvo
 */
public class RecStyle extends AppStyleComponent {
    private AppTemplate app;
    public static String CLASS_WRAPVBOX = "wrapVBox";
    public static String CLASS_REC_TITLE = "recTitle";
    public static String CLASS_DELETE_BUTTON = "deleteButton";
    public static String CLASS_HEADER_BOX = "headerBox";
    
    public static String CLASS_REC_TABLE = "recTable";
    public static String CLASS_REC_TABLE_HEADER = "recTableHeader";
    public static String CLASS_REC_ADD_EDIT_GRID = "addEditGrid";
    public static String CLASS_REC_ADD_EDIT_BOX = "addEditBox";
    public static String CLASS_REC_ADDEDIT_TITLE = "addEditTitle";
    public static String CLASS_REC_ADDUPDATE_BUTTON = "addUpdateButton";
    public static String CLASS_REC_CLEAR_BUTTON = "clearButton";
    
    public RecStyle(AppTemplate app){
        this.app = app;
        initRecStyle();
    }
    
    private void initRecStyle() {
        RecWorkspace workspaceComponent = (RecWorkspace)app.getWorkspaceComponent();
        workspaceComponent.getWrapVBox().getStyleClass().add(CLASS_WRAPVBOX);
        workspaceComponent.getHeaderBox().getStyleClass().add(CLASS_HEADER_BOX);
        workspaceComponent.getTitle().getStyleClass().add(CLASS_REC_TITLE);
        workspaceComponent.getaddEditGrid().getStyleClass().add(CLASS_REC_ADD_EDIT_GRID);
        workspaceComponent.getAddEditBox().getStyleClass().add(CLASS_REC_ADD_EDIT_BOX);
    }
}
