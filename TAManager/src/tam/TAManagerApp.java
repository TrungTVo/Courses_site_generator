package tam;

import java.util.Locale;
import tam.data.TAData;
import tam.file.TAFiles;
import tam.workspace.TAWorkspace;
import djf.AppTemplate;
import tam.style.TAStyle;
import static javafx.application.Application.launch;
import rm.workspace.RecWorkspace;

/**
 * This class serves as the application class for our TA Manager App program. 
 * Note that much of its behavior is inherited from AppTemplate, as defined in
 * the Desktop Java Framework. This app starts by loading all the UI-specific
 * settings like icon files and tooltips and other things, then the full 
 * User Interface is loaded using those settings. Note that this is a 
 * JavaFX application.
 * 
 * @author Richard McKenna
 * @version 1.0
 */
public class TAManagerApp {
    
    TAData dataComponent;
    TAWorkspace workspaceComponent;
    TAFiles fileComponent;
    TAStyle styleComponent;
    RecWorkspace recWorkspace;
    
    public TAData getDataComponent() {return dataComponent;}
    public TAWorkspace getWorkspaceComponent() {return workspaceComponent;}
    public TAFiles getFilesComponent() {return fileComponent;}
    public TAStyle getStyleComponent() {return styleComponent;}
    
    public void setRecWorkspace(RecWorkspace recWorkspace) {this.recWorkspace = recWorkspace;}
    
    public TAManagerApp(RecWorkspace recWorkspace) {
        this.recWorkspace = recWorkspace;
    }
    
    public void buildAppComponents() {
        // CONSTRUCT ALL FOUR COMPONENTS. NOTE THAT FOR THIS APP
        // THE WORKSPACE NEEDS THE DATA COMPONENT TO EXIST ALREADY
        // WHEN IT IS CONSTRUCTED, SO BE CAREFUL OF THE ORDER
        dataComponent = new TAData(this);
        workspaceComponent = new TAWorkspace(this, recWorkspace);
        fileComponent = new TAFiles(this);
        styleComponent = new TAStyle(this);
    }
    
}