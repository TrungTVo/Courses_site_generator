package tam.data;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * This class represents a Teaching Assistant for the table of TAs.
 * 
 * @author Richard McKenna - Trung Vo
 */
public class TeachingAssistant<E extends Comparable<E>> implements Comparable<E>  {
    // THE TABLE WILL STORE TA NAMES AND EMAILS
    private final StringProperty name;
    private final StringProperty email;
    private BooleanProperty isUndergrad;

    /**
     * Constructor initializes the TA name
     */
    public TeachingAssistant(String initName, String initEmail, boolean isUndergrad) {
        name = new SimpleStringProperty(initName);
        email = new SimpleStringProperty(initEmail);
        this.isUndergrad = new SimpleBooleanProperty(isUndergrad);
    }

    // ACCESSORS AND MUTATORS FOR THE PROPERTIES

    public String getName() {
        return name.get();
    }
    
    public String getEmail(){
        return email.get();
    }
    
    public boolean getIsUndergrad() {
        return isUndergrad.get();
    }
    
    public BooleanProperty getIsUnderGrad() {
        return isUndergrad;
    }

    public void setName(String initName) {
        name.set(initName);
    }
    
    public void setEmail(String initEmail){
        email.set(initEmail);
    }
    
    public void setIsUndergrad(boolean isUndergrad) {
        this.isUndergrad.set(isUndergrad);
    }

    @Override
    public int compareTo(E otherTA) {
        return getName().compareTo(((TeachingAssistant)otherTA).getName());
    }
    
    @Override
    public String toString() {
        return name.getValue();
    }
}