
package cm.data;

import cm.CourseManagerApp;
import djf.components.AppDataComponent;
import java.util.HashMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

public class CourseData {
    
    CourseManagerApp app;
    String subject;
    int number;
    String semester;
    int year;
    String title;
    String instructorName;
    String instructorHome;
    ObservableList<SitePage> templates;
    HashMap<String, Image> brandImages;
    
    public CourseData (CourseManagerApp initApp){
        app = initApp;
        subject = new String();
        semester = new String();
        title = new String();
        instructorName = new String();
        instructorHome = new String();
        templates = FXCollections.observableArrayList();
        brandImages = new HashMap();
    }
    
    public ObservableList<SitePage> getTemplates() {return templates;}
    
    public void resetData() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
