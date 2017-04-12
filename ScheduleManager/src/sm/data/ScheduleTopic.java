
package sm.data;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ScheduleTopic {
    private final StringProperty type;
    private final StringProperty date;
    private final StringProperty time;
    private final StringProperty title;
    private final StringProperty topic;
    private final StringProperty link;
    private final StringProperty criteria;
    
    public ScheduleTopic(String type, String date, String time, String title, String topic, String link, String criteria) {
        this.type = new SimpleStringProperty(type);
        this.date = new SimpleStringProperty(date);
        this.time = new SimpleStringProperty(time);
        this.title = new SimpleStringProperty(title);
        this.topic = new SimpleStringProperty(topic);
        this.link = new SimpleStringProperty(link);
        this.criteria = new SimpleStringProperty(criteria);
    }
    
    public String getType() {return type.get();}
    public String getDate() {return date.get();}
    public String getTime() {return time.get();}
    public String getTitle() {return title.get();}
    public String getTopic() {return topic.get();}
    public String getLink() {return link.get();}
    public String getCriteria() {return criteria.get();}
}
