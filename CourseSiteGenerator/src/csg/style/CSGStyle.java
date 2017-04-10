
package csg.style;

import csg.CourseSiteGenerator;
import djf.AppTemplate;
import djf.components.AppStyleComponent;

public class CSGStyle extends AppStyleComponent {
    // THIS PROVIDES ACCESS TO OTHER COMPONENTS
    private AppTemplate app;
    
    public CSGStyle(CourseSiteGenerator initApp){
        // KEEP THIS FOR LATER
        app = initApp;

        // LET'S USE THE DEFAULT STYLESHEET SETUP
        super.initStylesheet(app);
        
        // INIT THE STYLE FOR THE FILE TOOLBAR
        app.getGUI().initFileToolbarStyle();
    }
    
}
