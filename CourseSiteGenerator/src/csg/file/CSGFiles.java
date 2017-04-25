
package csg.file;

import cm.data.SitePage;
import csg.CourseSiteGenerator;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import pm.data.StudentData;
import pm.data.TeamData;
import rm.data.RecData;
import sm.data.ScheduleTopic;
import tam.data.TeachingAssistant;

public class CSGFiles {
    CourseSiteGenerator csg;
    
    public CSGFiles(CourseSiteGenerator csg){
        this.csg = csg;
    }
    
    public void loadData(CourseSiteGenerator csg, String filePath) throws IOException {
        // LOAD THE JSON FILE WITH ALL THE DATA
	JsonObject jsonObject = loadJSONFile(filePath);
        
        // Course Data
        JsonObject courseDataJson = jsonObject.getJsonObject("Course_Data");
        csg.getCourse().getDataComponent().setSubject(courseDataJson.getString("Subject"));
        csg.getCourse().getDataComponent().setNumber(courseDataJson.getString("Number"));
        csg.getCourse().getDataComponent().setSemester(courseDataJson.getString("Semester"));
        csg.getCourse().getDataComponent().setYear(courseDataJson.getString("Year"));
        csg.getCourse().getDataComponent().setTitle(courseDataJson.getString("Title"));
        csg.getCourse().getDataComponent().setInstructorName(courseDataJson.getString("Instructor_Name"));
        csg.getCourse().getDataComponent().setInstructorHome(courseDataJson.getString("Instructor_Home"));
        
        // Fill in Textfields
        csg.getCourse().getWorkspaceComponent().getSubjectCombo().setValue(csg.getCourse().getDataComponent().getSubject());
        csg.getCourse().getWorkspaceComponent().getNumberCombo().setValue(csg.getCourse().getDataComponent().getNumber());
        csg.getCourse().getWorkspaceComponent().getSemesterCombo().setValue(csg.getCourse().getDataComponent().getSemester());
        csg.getCourse().getWorkspaceComponent().getYearCombo().setValue(csg.getCourse().getDataComponent().getYear());
        csg.getCourse().getWorkspaceComponent().getTitleTF().setText(csg.getCourse().getDataComponent().getTitle());
        csg.getCourse().getWorkspaceComponent().getInstructorNameTF().setText(csg.getCourse().getDataComponent().getInstructorName());
        csg.getCourse().getWorkspaceComponent().getInstructorHomeTF().setText(csg.getCourse().getDataComponent().getInstructorHome());
        
        JsonArray templateJson = courseDataJson.getJsonArray("Site_Templates");
        for (int i=1; i<=templateJson.size(); i++){
            JsonObject template = templateJson.getJsonObject(i-1);
            boolean isUse = (template.getString("Use").equals("yes"))? true:false;
            SitePage sp = new SitePage (
                    isUse,
                    template.getString("NavBar_Title"),
                    template.getString("File_Name"),
                    template.getString("Script")
            );
            csg.getCourse().getDataComponent().getTemplates().add(sp);
        }
        
        // TA Data
        JsonObject TADataJson = jsonObject.getJsonObject("TA_Data");
        csg.getTA().getDataComponent().setStartHour(Integer.parseInt(TADataJson.getString("StartHour")));
        csg.getTA().getDataComponent().setEndHour(Integer.parseInt(TADataJson.getString("EndHour")));
        
        JsonArray TasJson = TADataJson.getJsonArray("Tas");
        for (int i=1; i<=TasJson.size(); i++){
            JsonObject ta = TasJson.getJsonObject(i-1);
            boolean isUndergrad = (ta.getString("Undergrad").equals("yes"))? true:false;
            TeachingAssistant teachingAssistant = new TeachingAssistant(
                    ta.getString("Name"),
                    ta.getString("Email"),
                    isUndergrad
            );
            csg.getTA().getDataComponent().getTeachingAssistants().add(teachingAssistant);
        }
        
        JsonArray officeHoursJson = TADataJson.getJsonArray("OfficeHours");
        for (int i=1; i<=officeHoursJson.size(); i++){
            JsonObject hour = officeHoursJson.getJsonObject(i-1);
            String day = hour.getString("Day");
            String time = hour.getString("Time");
            String name = hour.getString("Name");
            csg.getTA().getDataComponent().addOfficeHoursReservation(day, time, name);
        }
        
        // Rec Data
        JsonArray recDataJson = jsonObject.getJsonArray("Recitation_Data");
        for (int i=1; i<=recDataJson.size(); i++){
            JsonObject recObject = recDataJson.getJsonObject(i-1);
            RecData rec = new RecData(
                    recObject.getString("Section"),
                    recObject.getString("Instructor"),
                    recObject.getString("Day/Time"),
                    recObject.getString("Location"),
                    recObject.getString("Ta1"),
                    recObject.getString("Ta2")
            );
            csg.getRec().getDataComponent().getRecRecord().add(rec);
        }
        
        // Schedule Data
        JsonObject scheDataJson = jsonObject.getJsonObject("Schedule_Data");
        csg.getSchedule().getDataComponent().setStart(scheDataJson.getString("Start"));
        csg.getSchedule().getDataComponent().setEnd(scheDataJson.getString("End"));
        JsonArray scheJson = scheDataJson.getJsonArray("ScheduleList");
        for (int i=1; i<=scheJson.size(); i++){
            JsonObject schedule = scheJson.getJsonObject(i-1);
            ScheduleTopic topic = new ScheduleTopic(
                    schedule.getString("Type"),
                    schedule.getString("Date"),
                    schedule.getString("Time"),
                    schedule.getString("Title"),
                    schedule.getString("Topic"),
                    schedule.getString("Link"),
                    schedule.getString("Criteria")
            );
            csg.getSchedule().getDataComponent().getScheduleList().add(topic);
        }
        
        csg.getSchedule().getWorkspaceComponent().parseDate(
                csg.getSchedule().getDataComponent().getStart(),
                csg.getSchedule().getDataComponent().getEnd()
        );
        
        // Team Data
        JsonArray teamDataJson = jsonObject.getJsonArray("Team_Data");
        for (int i=1; i<=teamDataJson.size(); i++){
            JsonObject team = teamDataJson.getJsonObject(i-1);
            TeamData teamData = new TeamData(
                    team.getString("Name"),
                    team.getString("Color"),
                    team.getString("TextColor"),
                    team.getString("Link")
            );
            csg.getProject().getDataComponent().getTeamList().add(teamData);
        }
        
        // Student Data
        JsonArray studentDataJson = jsonObject.getJsonArray("Student_Data");
        for (int i=1; i<=studentDataJson.size(); i++){
            JsonObject student = studentDataJson.getJsonObject(i-1);
            StudentData studentData = new StudentData(
                    student.getString("First_Name"),
                    student.getString("Last_Name"),
                    student.getString("Team"),
                    student.getString("Role")
            );
            csg.getProject().getDataComponent().getStudentList().add(studentData);
        }
        
    }
    
    public static JsonObject loadJSONFile(String jsonFilePath) throws IOException {
        InputStream is = new FileInputStream(jsonFilePath);
	JsonReader jsonReader = Json.createReader(is);
	JsonObject json = jsonReader.readObject();
	jsonReader.close();
	is.close();
	return json;
    }
    
    public void saveDataForExport(CourseSiteGenerator csg) throws IOException {
        csg.getTA().getFilesComponent().saveData(csg.getTA().getDataComponent(), "./work/OfficeHoursGridData.json");
        csg.getRec().getFileComponent().saveData(csg.getRec().getDataComponent(), "./work/RecitationsData.json");
        csg.getSchedule().getFileComponent().saveData(csg.getSchedule().getDataComponent(), "./work/ScheduleData.json");
        csg.getProject().getFileComponent().saveDataForTeamStudent(csg.getProject().getDataComponent(), "./work/TeamsAndStudents.json");
        csg.getProject().getFileComponent().saveDataForProjects(csg.getProject().getDataComponent(), "./work/ProjectsData.json");
    }
}
