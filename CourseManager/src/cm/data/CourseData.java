
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
    String number;
    String semester;
    String year;
    String title;
    String instructorName;
    String instructorHome;
    ObservableList<SitePage> templates;
    HashMap<String, Image> brandImages;
    
    public void setSubject(String subject) {this.subject = subject;}
    public void setNumber(String number) {this.number = number;}
    public void setSemester(String semester) {this.semester = semester;}
    public void setYear(String year) {this.year = year;}
    public void setTitle(String title) {this.title = title;}
    public void setInstructorName(String instructorName) {this.instructorName = instructorName;}
    public void setInstructorHome(String instructorHome) {this.instructorHome = instructorHome;}
    public void setTemplates(ObservableList<SitePage> templates) {this.templates = templates;}
    public void setBrandImages(HashMap<String, Image> brandImages) {this.brandImages = brandImages;}
    
    public String getSubject() {return subject;}
    public String getNumber() {return number;}
    public String getSemester() {return semester;}
    public String getYear() {return year;}
    public String getTitle() {return title;}
    public String getInstructorName() {return instructorName;}
    public String getInstructorHome() {return instructorHome;}
    public ObservableList<SitePage> getTemplates() {return templates;}
    public HashMap<String, Image> getBrandImages() {return brandImages;}
    
    public CourseData (CourseManagerApp initApp){
        app = initApp;
        subject = new String();
        number = new String();
        semester = new String();
        year = new String();
        title = new String();
        instructorName = new String();
        instructorHome = new String();
        templates = FXCollections.observableArrayList();
        brandImages = new HashMap();
    }
    
    public void resetData() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
