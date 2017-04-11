
package cm.style;

import cm.workspace.CourseWorkspace;
import djf.AppTemplate;
import djf.components.AppStyleComponent;

public class CourseStyle extends AppStyleComponent {
    private AppTemplate app;
    public static String CLASS_OUTER_WRAP = "outer_wrap";
    public static String CLASS_INFOBOX = "infoBox";
    public static String CLASS_INFO_GRID_PANE = "info_grid_pane";
    public static String CLASS_TEMPLATE_PANE = "template_pane";
    public static String CLASS_PAGE_STYLE_VBOX = "page_style_vbox";
    
    public static String CLASS_TITLE = "title";
    public static String CLASS_SITE_TITLE = "siteTitle";
    public static String CLASS_SUBJECTBOX = "subjectBox";
    public static String CLASS_SEMESTERBOX = "semesterBox";
    public static String CLASS_COURSE_EXPORT_DIR_CHANGEBUTTON = "course_export_dir_changebutton";
    
    public static String CLASS_TEMPLATE_DIR_BUTTON = "template_dir_button";
    public static String CLASS_SITE_TABLE = "site_table";
    public static String CLASS_SITE_TABLE_HEADER = "site_table_header";
    
    public static String CLASS_STYLE_COMBO = "style_combo";
    
    public CourseStyle(AppTemplate app){
        this.app = app;
        initCourseStyle();
    }
    
    private void initCourseStyle(){
        CourseWorkspace workspaceComponent = (CourseWorkspace)app.getWorkspaceComponent();
        workspaceComponent.getOuterWrap().getStyleClass().add(CLASS_OUTER_WRAP);
        workspaceComponent.getInfoBox().getStyleClass().add(CLASS_INFOBOX);
        workspaceComponent.getInfoGridPane().getStyleClass().add(CLASS_INFO_GRID_PANE);
        workspaceComponent.getSubjectBox().getStyleClass().add(CLASS_SUBJECTBOX);
        workspaceComponent.getSemesterBox().getStyleClass().add(CLASS_SEMESTERBOX);
        workspaceComponent.getTemplatePane().getStyleClass().add(CLASS_TEMPLATE_PANE);
        workspaceComponent.getPageStyleVBox().getStyleClass().add(CLASS_PAGE_STYLE_VBOX);
        workspaceComponent.getStyleTitle().getStyleClass().add(CLASS_TITLE);
        workspaceComponent.getTemplateTitle().getStyleClass().add(CLASS_TITLE);
        workspaceComponent.getInfoTitle().getStyleClass().add(CLASS_TITLE);
        workspaceComponent.getSiteTitle().getStyleClass().add(CLASS_SITE_TITLE);
        
    }
}
