
package pm.data;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TeamData {
    private final StringProperty name;
    private final StringProperty color;
    private final StringProperty textColor;
    private final StringProperty link;
    
    public TeamData(String name, String color, String textColor, String link){
        this.name = new SimpleStringProperty(name);
        this.color = new SimpleStringProperty(color);
        this.textColor = new SimpleStringProperty(textColor);
        this.link = new SimpleStringProperty(link);
    }
    
    public String getName() {return name.get();}
    public String getColor() {return color.get();}
    public String getTextColor() {return textColor.get();}
    public String getLink() {return link.get();}
    
    public void setName(String name) {this.name.set(name);}
    public void setColor(String color) {this.color.set(color);}
    public void setTextColor(String textColor) {this.textColor.set(textColor);}
    public void setLink(String link) {this.link.set(link);}
}
