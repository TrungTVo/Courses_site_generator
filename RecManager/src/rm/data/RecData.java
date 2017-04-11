
package rm.data;

import javafx.beans.property.StringProperty;

/**
 *
 * @author trungvo
 */
public class RecData {
    private StringProperty section;
    private StringProperty instructor;
    private StringProperty dayTime;
    private StringProperty location;
    private StringProperty ta1;
    private StringProperty ta2;
    
    public RecData(StringProperty section, StringProperty instructor, StringProperty dayTime, StringProperty location, StringProperty ta1, StringProperty ta2) {
        this.section = section;
        this.instructor = instructor;
        this.dayTime = dayTime;
        this.location = location;
        this.ta1 = ta1;
        this.ta2 = ta2;
    }
    
    public StringProperty getSection() {return section;}
    public StringProperty getInstructor() {return instructor;}
    public StringProperty getDayTime() {return dayTime;}
    public StringProperty getLocation() {return location;}
    public StringProperty getTa1() {return ta1;}
    public StringProperty getTa2() {return ta2;}
    
}
