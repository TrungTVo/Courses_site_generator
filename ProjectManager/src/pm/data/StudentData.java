
package pm.data;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class StudentData<E extends Comparable<E>> implements Comparable<E> {
    private final StringProperty firstName;
    private final StringProperty lastName;
    private final StringProperty team;
    private final StringProperty role;
    
    public StudentData(String firstName, String lastName, String team, String role) {
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.team = new SimpleStringProperty(team);
        this.role = new SimpleStringProperty(role);
    }
    
    public String getFirstName() {return firstName.get();}
    public String getLastName() {return lastName.get();}
    public String getTeam() {return team.get();}
    public String getRole() {return role.get();}
    
    public void setFirstName(String firstName) {this.firstName.set(firstName);}
    public void setLastName(String lastName) {this.lastName.set(lastName);}
    public void setTeam(String team) {this.team.set(team);}
    public void setRole(String role) {this.role.set(role);}

    @Override
    public int compareTo(E student) {
        return getFirstName().compareTo(((StudentData)student).getFirstName());
    }
}
