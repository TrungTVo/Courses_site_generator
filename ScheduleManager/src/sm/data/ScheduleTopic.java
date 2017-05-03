
package sm.data;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ScheduleTopic<E extends Comparable<E>> implements Comparable<E> {
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
    
    public void setType(String type) {this.type.set(type);}
    public void setDate(String date) {this.date.set(date);}
    public void setTime(String time) {this.time.set(time);}
    public void setTitle(String title) {this.title.set(title);}
    public void setTopic(String topic) {this.topic.set(topic);}
    public void setLink(String link) {this.link.set(link);}
    public void setCriteria(String criteria) {this.criteria.set(criteria);}

    @Override
    public int compareTo(E scheTopic) {
        return getType().compareTo(((ScheduleTopic)scheTopic).getType());
    }
}
