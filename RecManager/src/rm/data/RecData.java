
package rm.data;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author trungvo
 */
public class RecData {
    private final StringProperty section;
    private final StringProperty instructor;
    private final StringProperty dayTime;
    private final StringProperty location;
    private final StringProperty ta1;
    private final StringProperty ta2;
    
    public RecData(String section, String instructor, String dayTime, String location, String ta1, String ta2) {
        this.section = new SimpleStringProperty(section);
        this.instructor = new SimpleStringProperty(instructor);
        this.dayTime = new SimpleStringProperty(dayTime);
        this.location = new SimpleStringProperty(location);
        this.ta1 = new SimpleStringProperty(ta1);
        this.ta2 = new SimpleStringProperty(ta2);
    }
    
    public String getSection() {return section.get();}
    public String getInstructor() {return instructor.get();}
    public String getDayTime() {return dayTime.get();}
    public String getLocation() {return location.get();}
    public String getTa1() {return ta1.get();}
    public String getTa2() {return ta2.get();}
    
    public void setSection(String section) {this.section.set(section);}
    public void setInstructor(String instructor) {this.instructor.set(instructor);}
    public void setDayTime(String dayTime) {this.dayTime.set(dayTime);}
    public void setLocation(String location) {this.location.set(location);}
    public void setTa1(String ta1) {this.ta1.set(ta1);}
    public void setTa2(String ta2) {this.ta2.set(ta2);}
    
}
