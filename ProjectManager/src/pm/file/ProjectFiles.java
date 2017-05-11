
package pm.file;

import cm.CourseManagerApp;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.collections.ObservableList;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;
import pm.ProjectManagerApp;
import pm.data.ProjectRecord;
import pm.data.StudentData;
import pm.data.TeamData;

/**
 *
 * @author trungvo
 */
public class ProjectFiles {
    ProjectManagerApp app;
    CourseManagerApp courseApp;
    
    static final String JSON_TEAMS = "teams";
    static final String JSON_STUDENTS = "students";
    static final String JSON_NAME = "name";
    static final String JSON_RED = "red";
    static final String JSON_GREEN = "green";
    static final String JSON_BLUE = "blue";
    static final String JSON_TEXTCOLOR = "text_color";
    static final String JSON_LASTNAME = "lastName";
    static final String JSON_FIRSTNAME = "firstName";
    static final String JSON_TEAM = "team";
    static final String JSON_ROLE = "role";
    
    static final String JSON_WORK = "work";
    static final String JSON_SEMESTER = "semester";
    static final String JSON_PROJECTS = "projects";
    static final String JSON_LINK = "link";
    
    public ProjectFiles(ProjectManagerApp app) {
        this.app = app;
        this.courseApp = app.getCourseManagerApp();
    }
    
    public void saveDataForProjects(ProjectRecord data, String filePath) throws IOException {
        JsonArrayBuilder worksArrayBuilder = Json.createArrayBuilder();
        JsonArrayBuilder projectsArrayBuilder = Json.createArrayBuilder();
        ObservableList<TeamData> teamList = data.getTeamList();
        ObservableList<StudentData> studentList = data.getStudentList();
        
        for (TeamData team:teamList){
            JsonArrayBuilder studentGroup = Json.createArrayBuilder();
            for (StudentData student:studentList){
                if (student.getTeam().equals(team.getName())){
                    studentGroup.add(student.getFirstName()+" "+student.getLastName());
                }
            }
            JsonArray studentJsonArray = studentGroup.build();
            JsonObject projectJson = Json.createObjectBuilder()
                                        .add(JSON_NAME, team.getName())
                                        .add(JSON_STUDENTS, studentJsonArray)
                                        .add(JSON_LINK, team.getLink()).build();
            projectsArrayBuilder.add(projectJson);
        }
        JsonArray projectJsonArray = projectsArrayBuilder.build();
        
        // combine
        JsonObject json = Json.createObjectBuilder()
                            .add(JSON_SEMESTER, courseApp.getDataComponent().getSemester() + " " + courseApp.getDataComponent().getYear())
                            .add(JSON_PROJECTS, projectJsonArray).build();
        worksArrayBuilder.add(json);
        JsonArray workJsonArray = worksArrayBuilder.build();
        JsonObject finalJsonObject = Json.createObjectBuilder().add(JSON_WORK, workJsonArray).build();
        
        // AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
	Map<String, Object> properties = new HashMap<>(1);
	properties.put(JsonGenerator.PRETTY_PRINTING, true);
	JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
	StringWriter sw = new StringWriter();
	JsonWriter jsonWriter = writerFactory.createWriter(sw);
	jsonWriter.writeObject(finalJsonObject);
	jsonWriter.close();

	// INIT THE WRITER
	OutputStream os = new FileOutputStream(filePath);
	JsonWriter jsonFileWriter = Json.createWriter(os);
	jsonFileWriter.writeObject(finalJsonObject);
	String prettyPrinted = sw.toString();
	PrintWriter pw = new PrintWriter(filePath);
	pw.write(prettyPrinted);
	pw.close();
    }
    
    public void saveDataForTeamStudent(ProjectRecord data, String filePath) throws IOException {
        JsonArrayBuilder teamsArrayBuilder = Json.createArrayBuilder();
        ObservableList<TeamData> teamList = data.getTeamList();
        for (TeamData team:teamList){
            JsonObject teamJson = Json.createObjectBuilder()
                                    .add(JSON_NAME, team.getName())
                                    .add(JSON_RED, Integer.parseInt(team.getColor().substring(2,4),16))
                                    .add(JSON_GREEN, Integer.parseInt(team.getColor().substring(4, 6), 16))
                                    .add(JSON_BLUE, Integer.parseInt(team.getColor().substring(6, 8), 16))
                                    .add(JSON_TEXTCOLOR, team.getTextColor()).build();
            teamsArrayBuilder.add(teamJson);
        }
        JsonArray teamJsonArray = teamsArrayBuilder.build();
        
        JsonArrayBuilder studentsArrayBuilder = Json.createArrayBuilder();
        ObservableList<StudentData> studentList = data.getStudentList();
        for (StudentData student:studentList){
            JsonObject studentJson = Json.createObjectBuilder()
                                    .add(JSON_LASTNAME, student.getLastName())
                                    .add(JSON_FIRSTNAME, student.getFirstName())
                                    .add(JSON_TEAM, student.getTeam())
                                    .add(JSON_ROLE, student.getRole()).build();
            studentsArrayBuilder.add(studentJson);
        }
        JsonArray studentJsonArray = studentsArrayBuilder.build();
        
        // COMBINE
        JsonObject json = Json.createObjectBuilder()
                            .add(JSON_TEAMS, teamJsonArray)
                            .add(JSON_STUDENTS, studentJsonArray).build();
        
        // AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
	Map<String, Object> properties = new HashMap<>(1);
	properties.put(JsonGenerator.PRETTY_PRINTING, true);
	JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
	StringWriter sw = new StringWriter();
	JsonWriter jsonWriter = writerFactory.createWriter(sw);
	jsonWriter.writeObject(json);
	jsonWriter.close();

	// INIT THE WRITER
	OutputStream os = new FileOutputStream(filePath);
	JsonWriter jsonFileWriter = Json.createWriter(os);
	jsonFileWriter.writeObject(json);
	String prettyPrinted = sw.toString();
	PrintWriter pw = new PrintWriter(filePath);
	pw.write(prettyPrinted);
	pw.close();
    }
}
