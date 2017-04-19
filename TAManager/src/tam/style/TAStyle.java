package tam.style;

import djf.AppTemplate;
import djf.components.AppStyleComponent;
import java.util.HashMap;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import tam.data.TeachingAssistant;
import tam.workspace.TAWorkspace;
import javafx.scene.layout.Pane;
import tam.TAManagerApp;

/**
 * This class manages all CSS style for this application.
 * 
 * @author Richard McKenna - Trung Vo
 * @version 1.0
 */
public class TAStyle extends AppStyleComponent {
    // FIRST WE SHOULD DECLARE ALL OF THE STYLE TYPES WE PLAN TO USE
    
    // WE'LL USE THIS FOR ORGANIZING LEFT AND RIGHT CONTROLS
    public static String CLASS_PLAIN_PANE = "plain_pane";
    
    // THESE ARE THE HEADERS FOR EACH SIDE
    public static String CLASS_HEADER_PANE = "header_pane";
    public static String CLASS_HEADER_LABEL = "header_label";
    public static String CLASS_STARTTIME_LABEL = "startTime_label";
    public static String CLASS_ENDTIME_LABEL = "endTime_label";
    public static String CLASS_START_BOX = "startBox";
    public static String CLASS_END_BOX = "endBox";
    public static String CLASS_START_WRAP_BOX = "startWrapBox";
    public static String CLASS_END_WRAP_BOX = "endWrapBox";

    // ON THE LEFT WE HAVE THE TA ENTRY
    public static String CLASS_TA_TABLE = "ta_table";
    public static String CLASS_TA_TABLE_COLUMN_HEADER = "ta_table_column_header";
    public static String CLASS_ADD_TA_PANE = "add_ta_pane";
    public static String CLASS_ADD_TA_TEXT_FIELD = "add_ta_text_field";
    public static String CLASS_ADD_TA_BUTTON = "add_ta_button";
    public static String CLASS_CLEAR_TA_BUTOON = "clear_ta_button";

    // ON THE RIGHT WE HAVE THE OFFICE HOURS GRID
    public static String CLASS_OFFICE_HOURS_GRID = "office_hours_grid";
    public static String CLASS_OFFICE_HOURS_GRID_TIME_COLUMN_HEADER_PANE = "office_hours_grid_time_column_header_pane";
    public static String CLASS_OFFICE_HOURS_GRID_TIME_COLUMN_HEADER_LABEL = "office_hours_grid_time_column_header_label";
    public static String CLASS_OFFICE_HOURS_GRID_DAY_COLUMN_HEADER_PANE = "office_hours_grid_day_column_header_pane";
    public static String CLASS_OFFICE_HOURS_GRID_DAY_COLUMN_HEADER_LABEL = "office_hours_grid_day_column_header_label";
    public static String CLASS_OFFICE_HOURS_GRID_TIME_CELL_PANE = "office_hours_grid_time_cell_pane";
    public static String CLASS_OFFICE_HOURS_GRID_TIME_CELL_LABEL = "office_hours_grid_time_cell_label";
    public static String CLASS_OFFICE_HOURS_GRID_TA_CELL_PANE = "office_hours_grid_ta_cell_pane";
    public static String CLASS_OFFICE_HOURS_GRID_TA_CELL_LABEL = "office_hours_grid_ta_cell_label";
    
    // THIS PROVIDES ACCESS TO OTHER COMPONENTS
    private TAManagerApp app;
    
    /**
     * This constructor initializes all style for the application.
     * 
     * @param initApp The application to be stylized.
     */
    public TAStyle(TAManagerApp initApp) {
        // KEEP THIS FOR LATER
        app = initApp;

        // AND NOW OUR WORKSPACE STYLE
        initTAWorkspaceStyle();
    }

    /**
     * This function specifies all the style classes for
     * all user interface controls in the workspace.
     */
    private void initTAWorkspaceStyle() {
        // LEFT SIDE - THE HEADER
        TAWorkspace workspaceComponent = (TAWorkspace)app.getWorkspaceComponent();
        workspaceComponent.getTAsHeaderBox().getStyleClass().add(CLASS_HEADER_PANE);
        workspaceComponent.getTAsHeaderLabel().getStyleClass().add(CLASS_HEADER_LABEL);

        // LEFT SIDE - THE TABLE
        TableView<TeachingAssistant> taTable = workspaceComponent.getTATable();
        taTable.getStyleClass().add(CLASS_TA_TABLE);
        for (TableColumn tableColumn : taTable.getColumns()) {
            tableColumn.getStyleClass().add(CLASS_TA_TABLE_COLUMN_HEADER);
        }

        // LEFT SIDE - THE TA DATA ENTRY
        workspaceComponent.getAddBox().getStyleClass().add(CLASS_ADD_TA_PANE);
        workspaceComponent.getNameTextField().getStyleClass().add(CLASS_ADD_TA_TEXT_FIELD);
        workspaceComponent.getAddButton().getStyleClass().add(CLASS_ADD_TA_BUTTON);
        workspaceComponent.getClearButton().getStyleClass().add(CLASS_CLEAR_TA_BUTOON);

        // RIGHT SIDE - THE HEADER
        workspaceComponent.getOfficeHoursSubheaderBox().getStyleClass().add(CLASS_HEADER_PANE);
        workspaceComponent.getOfficeHoursSubheaderLabel().getStyleClass().add(CLASS_HEADER_LABEL);
        workspaceComponent.getStartTimeLabel().getStyleClass().add(CLASS_STARTTIME_LABEL);
        workspaceComponent.getEndTimeLabel().getStyleClass().add(CLASS_ENDTIME_LABEL);
        workspaceComponent.getStartBox().getStyleClass().add(CLASS_START_BOX);
        workspaceComponent.getEndBox().getStyleClass().add(CLASS_END_BOX);
        workspaceComponent.getStartWrapBox().getStyleClass().add(CLASS_START_WRAP_BOX);
        workspaceComponent.getEndWrapBox().getStyleClass().add(CLASS_END_WRAP_BOX);
    }
    
    /**
     * This method initializes the style for all UI components in
     * the office hours grid. Note that this should be called every
     * time a new TA Office Hours Grid is created or loaded.
     */
    public void initOfficeHoursGridStyle() {
        // RIGHT SIDE - THE OFFICE HOURS GRID TIME HEADERS
        TAWorkspace workspaceComponent = (TAWorkspace)app.getWorkspaceComponent();
        workspaceComponent.getOfficeHoursGridPane().getStyleClass().add(CLASS_OFFICE_HOURS_GRID);
        setStyleClassOnAll(workspaceComponent.getOfficeHoursGridTimeHeaderPanes(), CLASS_OFFICE_HOURS_GRID_TIME_COLUMN_HEADER_PANE);
        setStyleClassOnAll(workspaceComponent.getOfficeHoursGridTimeHeaderLabels(), CLASS_OFFICE_HOURS_GRID_TIME_COLUMN_HEADER_LABEL);
        setStyleClassOnAll(workspaceComponent.getOfficeHoursGridDayHeaderPanes(), CLASS_OFFICE_HOURS_GRID_DAY_COLUMN_HEADER_PANE);
        setStyleClassOnAll(workspaceComponent.getOfficeHoursGridDayHeaderLabels(), CLASS_OFFICE_HOURS_GRID_DAY_COLUMN_HEADER_LABEL);
        setStyleClassOnAll(workspaceComponent.getOfficeHoursGridTimeCellPanes(), CLASS_OFFICE_HOURS_GRID_TIME_CELL_PANE);
        setStyleClassOnAll(workspaceComponent.getOfficeHoursGridTimeCellLabels(), CLASS_OFFICE_HOURS_GRID_TIME_CELL_LABEL);
        setStyleClassOnAll(workspaceComponent.getOfficeHoursGridTACellPanes(), CLASS_OFFICE_HOURS_GRID_TA_CELL_PANE);
        setStyleClassOnAll(workspaceComponent.getOfficeHoursGridTACellLabels(), CLASS_OFFICE_HOURS_GRID_TA_CELL_LABEL);
        
        // hover effect
        for (Pane pane:workspaceComponent.getOfficeHoursGridTACellPanes().values()){
            pane.setOnMouseEntered(e -> {
                pane.setStyle("-fx-background-color: #ffff00; -fx-border-width: 2px; -fx-border-color: blue");
                //pane.setStyle("-fx-border-width: 2px");
                String cellKey = workspaceComponent.getCellKey(pane);
                if (cellKey != null) {
                    String[] rowCol = cellKey.split("_");           // [0]-col; [1]-row
                    workspaceComponent.getOfficeHoursGridTimeCellPanes().get("0_" + rowCol[1]).setStyle("-fx-background-color: #87CEEB");
                    workspaceComponent.getOfficeHoursGridTimeCellPanes().get("1_" + rowCol[1]).setStyle("-fx-background-color: #87CEEB");
                    workspaceComponent.getOfficeHoursGridDayHeaderPanes().get(String.valueOf(rowCol[0]) + "_0").setStyle("-fx-background-color: #87CEEB");
                    highlightLeftTargetCell(workspaceComponent, rowCol[0], rowCol[1], "#F0E68C");
                    highlightAboveTargetCell(workspaceComponent, rowCol[0], rowCol[1], "#F0E68C");
                }
            });
            pane.setOnMouseExited(e -> {
                pane.setStyle("-fx-background-color: #BDB76B; -fx-border-width: 0.5px; -fx-border-color: black");
                //pane.setStyle("-fx-border-width: 0.5px");
                String cellKey = workspaceComponent.getCellKey(pane);
                if (cellKey != null){
                    String[] rowCol = cellKey.split("_");           // [0]-col; [1]-row
                    workspaceComponent.getOfficeHoursGridTimeCellPanes().get("0_"+rowCol[1]).setStyle("-fx-background-color: #0000ff");
                    workspaceComponent.getOfficeHoursGridTimeCellPanes().get("1_"+rowCol[1]).setStyle("-fx-background-color: #0000ff");
                    workspaceComponent.getOfficeHoursGridDayHeaderPanes().get(String.valueOf(rowCol[0])+"_0").setStyle("-fx-background-color: navy");
                    highlightLeftTargetCell(workspaceComponent, rowCol[0], rowCol[1], "#BDB76B");
                    highlightAboveTargetCell(workspaceComponent, rowCol[0], rowCol[1], "#BDB76B");
                }
            });
        }
    }
    
    public void highlightLeftTargetCell(TAWorkspace workspaceComponent, String col, String row, String color) {
        for (int i=2; i<Integer.parseInt(col); i++){
            workspaceComponent.getOfficeHoursGridTACellPanes().get(String.valueOf(i)+"_"+String.valueOf(row)).setStyle("-fx-background-color: "+color);
        }
    }
    
    public void highlightAboveTargetCell(TAWorkspace workspaceComponent, String col, String row, String color) {
        for (int i=1; i<Integer.parseInt(row); i++){
            workspaceComponent.getOfficeHoursGridTACellPanes().get(String.valueOf(col)+"_"+String.valueOf(i)).setStyle("-fx-background-color: "+color);
        }
    }
    
    /**
     * This helper method initializes the style of all the nodes in the nodes
     * map to a common style, styleClass.
     */
    private void setStyleClassOnAll(HashMap nodes, String styleClass) {
        for (Object nodeObject : nodes.values()) {
            Node n = (Node)nodeObject;
            n.getStyleClass().add(styleClass);
        }
    }
}