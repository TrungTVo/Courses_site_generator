
package sm.style;

import djf.AppTemplate;
import djf.components.AppStyleComponent;
import sm.workspace.ScheduleWorkspace;

/**
 *
 * @author trungvo
 */
public class ScheduleStyle extends AppStyleComponent {
    private AppTemplate app;
    public static String CLASS_WRAP_BOX = "wrapVBox";
    public static String CLASS_TITLE = "scheTitle";
    public static String CLASS_CALENDAR_WRAPBOX = "calendarWrapBox";
    public static String CLASS_CALENDAR_TITLE = "calendarTitle";
    public static String CLASS_START_LABEL = "startMonLabel";
    public static String CLASS_END_LABEL = "endFriLabel";
    public static String CLASS_SCHEDULE_BOX = "scheduleBox";
    public static String CLASS_SCHEDULE_LABEL = "scheduleLabel";
    public static String CLASS_SCHDULE_HEADERBOX = "scheduleHeaderBox";
    public static String CLASS_SCHEDULE_TABLE = "scheduleTable";
    public static String CLASS_ADDEDIT_LABEL = "addEditLabel";
    public static String CLASS_ADDEDIT_GRID = "addEditGrid";
     
    public ScheduleStyle(AppTemplate app) {
        this.app = app;
        initScheduleStyle();
    }
    
    private void initScheduleStyle() {
        ScheduleWorkspace workspaceComponent = (ScheduleWorkspace) app.getWorkspaceComponent();
        workspaceComponent.getWrapVBox().getStyleClass().add(CLASS_WRAP_BOX);
        workspaceComponent.getTitle().getStyleClass().add(CLASS_TITLE);
        workspaceComponent.getCalendarWrapBox().getStyleClass().add(CLASS_CALENDAR_WRAPBOX);
        workspaceComponent.getCalendarTitle().getStyleClass().add(CLASS_CALENDAR_TITLE);
        workspaceComponent.getStartMonLabel().getStyleClass().add(CLASS_START_LABEL);
        workspaceComponent.getEndFriLabel().getStyleClass().add(CLASS_END_LABEL);
        workspaceComponent.getScheduleBox().getStyleClass().add(CLASS_SCHEDULE_BOX);
        workspaceComponent.getScheduleLabel().getStyleClass().add(CLASS_SCHEDULE_LABEL);
        workspaceComponent.getScheduleHeaderBox().getStyleClass().add(CLASS_SCHDULE_HEADERBOX);
        workspaceComponent.getScheTable().getStyleClass().add(CLASS_SCHEDULE_TABLE);
        workspaceComponent.getAddEditLabel().getStyleClass().add(CLASS_ADDEDIT_LABEL);
        workspaceComponent.getAddEditGrid().getStyleClass().add(CLASS_ADDEDIT_GRID);
    }
}
