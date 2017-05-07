
package csg.file;

import cm.data.CourseData;
import cm.data.SitePage;
import csg.CourseSiteGenerator;
import djf.settings.AppPropertyType;
import djf.ui.AppMessageDialogSingleton;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.collections.ObservableList;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;
import pm.data.StudentData;
import pm.data.TeamData;
import properties_manager.PropertiesManager;
import rm.data.RecData;
import sm.data.ScheduleTopic;
import tam.data.TeachingAssistant;
import tam.file.TimeSlot;

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
        // parse TA List into Rec TA ComboBox 
        csg.getRec().getWorkspaceComponent().getTa1Combo().setItems(csg.getRec().getWorkspaceComponent().getTAList(csg.getTA().getDataComponent().getTeachingAssistants()));
        csg.getRec().getWorkspaceComponent().getTa2Combo().setItems(csg.getRec().getWorkspaceComponent().getTAList(csg.getTA().getDataComponent().getTeachingAssistants()));
        
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
        // parse Team list into team ComboBox in Student Section
        csg.getProject().getWorkspaceComponent().getStudentTeamCombobox().setItems(csg.getProject().getWorkspaceComponent().getTeamList(csg.getProject().getDataComponent().getTeamList()));
    }
    
    public static JsonObject loadJSONFile(String jsonFilePath) throws IOException {
        InputStream is = new FileInputStream(jsonFilePath);
	JsonReader jsonReader = Json.createReader(is);
	JsonObject json = jsonReader.readObject();
	jsonReader.close();
	is.close();
	return json;
    }
    
    public boolean saveData(CourseSiteGenerator csg, String filePath) throws IOException {
        JsonObject courseJson = null;
        JsonObject taJson = null;
        JsonArray recJsonArray = null;
        JsonObject scheJson = null;
        JsonArray teamJsonArray = null;
        JsonArray studentJsonArray = null;
        
        courseJson = saveCourse(courseJson);
        taJson = saveTA(taJson);
        recJsonArray = saveRec(recJsonArray);
        scheJson = saveSche(scheJson);
        teamJsonArray = saveTeam(teamJsonArray);
        studentJsonArray = saveStudent(studentJsonArray);
        
        if (courseJson != null && taJson != null && recJsonArray != null && scheJson != null && teamJsonArray != null && studentJsonArray != null) {
            // PUT EVERYTHING INTO A SINGLE JSON OBJECT
            JsonObject dataJson = Json.createObjectBuilder()
                                    .add("Course_Data", courseJson)
                                    .add("TA_Data", taJson)
                                    .add("Recitation_Data", recJsonArray)
                                    .add("Schedule_Data", scheJson)
                                    .add("Team_Data", teamJsonArray)
                                    .add("Student_Data", studentJsonArray).build();

            // AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
            Map<String, Object> properties = new HashMap<>(1);
            properties.put(JsonGenerator.PRETTY_PRINTING, true);
            JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
            StringWriter sw = new StringWriter();
            JsonWriter jsonWriter = writerFactory.createWriter(sw);
            jsonWriter.writeObject(dataJson);
            jsonWriter.close();

            // INIT THE WRITER
            OutputStream os = new FileOutputStream(filePath);
            JsonWriter jsonFileWriter = Json.createWriter(os);
            jsonFileWriter.writeObject(dataJson);
            String prettyPrinted = sw.toString();
            PrintWriter pw = new PrintWriter(filePath);
            pw.write(prettyPrinted);
            pw.close();
            return true;
        }
        return false;
    }
    
    public JsonObject saveCourse(JsonObject courseJson) {
        // SAVE DATA FOR COURSE TAB
        csg.getCourse().getDataComponent().setSubject(csg.getCourse().getWorkspaceComponent().getSubjectCombo().getSelectionModel().getSelectedItem().toString());
        csg.getCourse().getDataComponent().setNumber(csg.getCourse().getWorkspaceComponent().getNumberCombo().getSelectionModel().getSelectedItem().toString());
        csg.getCourse().getDataComponent().setSemester(csg.getCourse().getWorkspaceComponent().getSemesterCombo().getSelectionModel().getSelectedItem().toString());
        csg.getCourse().getDataComponent().setYear(csg.getCourse().getWorkspaceComponent().getYearCombo().getSelectionModel().getSelectedItem().toString());
        csg.getCourse().getDataComponent().setTitle(csg.getCourse().getWorkspaceComponent().getTitleTF().getText());
        csg.getCourse().getDataComponent().setInstructorName(csg.getCourse().getWorkspaceComponent().getInstructorNameTF().getText());
        csg.getCourse().getDataComponent().setInstructorHome(csg.getCourse().getWorkspaceComponent().getInstructorHomeTF().getText());
        
        CourseData courseData = csg.getCourse().getDataComponent();
        JsonArrayBuilder siteTemplateArrayBuilder = Json.createArrayBuilder();
        ObservableList<SitePage> courseTemplates = courseData.getTemplates();
        for (SitePage sp:courseTemplates){
            JsonObject spJson;
            if (sp.getIsUsed()) {
                spJson = Json.createObjectBuilder()
                        .add("Use", "yes")
                        .add("NavBar_Title", sp.getNavBar())
                        .add("File_Name", sp.getFile())
                        .add("Script", sp.getScript()).build();
            } else {
                spJson = Json.createObjectBuilder()
                        .add("Use", "no")
                        .add("NavBar_Title", sp.getNavBar())
                        .add("File_Name", sp.getFile())
                        .add("Script", sp.getScript()).build();
            }
            siteTemplateArrayBuilder.add(spJson);
        }
        JsonArray siteTemplatesArray = siteTemplateArrayBuilder.build();
        courseJson = Json.createObjectBuilder()
                                .add("Subject", courseData.getSubject())
                                .add("Number", courseData.getNumber())
                                .add("Semester", courseData.getSemester())
                                .add("Year", courseData.getYear())
                                .add("Title", courseData.getTitle())
                                .add("Instructor_Name", courseData.getInstructorName())
                                .add("Instructor_Home", courseData.getInstructorHome())
                                .add("Site_Templates", siteTemplatesArray)
                                .build();
        return courseJson;
    }
    
    public JsonObject saveTA(JsonObject taJsonObject) {
        JsonArrayBuilder taArrayBuilder = Json.createArrayBuilder();
	ObservableList<TeachingAssistant> tas = csg.getTA().getDataComponent().getTeachingAssistants();
	for (TeachingAssistant ta : tas) {
            JsonObject taJson;
            boolean isUndergrad = (ta.getIsUndergrad())? true:false;
            String undergrad = (isUndergrad)? "yes":"no";
            taJson = Json.createObjectBuilder()
                        .add("Name", ta.getName())
                        .add("Email", ta.getEmail())
                        .add("Undergrad", undergrad).build();
	    taArrayBuilder.add(taJson);
	}
	JsonArray undergradTAsArray = taArrayBuilder.build();

	// NOW BUILD THE TIME SLOT JSON OBJCTS TO SAVE
	JsonArrayBuilder timeSlotArrayBuilder = Json.createArrayBuilder();
	ArrayList<TimeSlot> officeHours = TimeSlot.buildOfficeHoursList(csg.getTA().getDataComponent());
	for (TimeSlot ts : officeHours) {	    
	    JsonObject tsJson = Json.createObjectBuilder()
		    .add("Day", ts.getDay())
		    .add("Time", ts.getTime())
		    .add("Name", ts.getName()).build();
	    timeSlotArrayBuilder.add(tsJson);
	}
	JsonArray timeSlotsArray = timeSlotArrayBuilder.build();
        
	// THEN PUT IT ALL TOGETHER IN A JsonObject
	taJsonObject = Json.createObjectBuilder()
		.add("StartHour", "" + csg.getTA().getDataComponent().getStartHour())
		.add("EndHour", "" + csg.getTA().getDataComponent().getEndHour())
                .add("Tas", undergradTAsArray)
                .add("OfficeHours", timeSlotsArray)
		.build();
        return taJsonObject;
    }
    
    public JsonArray saveRec(JsonArray recArray) {
        JsonArrayBuilder recArrayBuilder = Json.createArrayBuilder();
        for (RecData rec:csg.getRec().getDataComponent().getRecRecord()) {
            String ta1 = (rec.getTa1() != null)? rec.getTa1():"";
            String ta2 = (rec.getTa2() != null)? rec.getTa2():"";
            String instructor = (rec.getInstructor() != null)? rec.getInstructor():"";
            JsonObject recJson = Json.createObjectBuilder()
                                    .add("Section", rec.getSection())
                                    .add("Instructor", instructor)
                                    .add("Day/Time", rec.getDayTime())
                                    .add("Location", rec.getLocation())
                                    .add("Ta1", ta1)
                                    .add("Ta2", ta2).build();
            recArrayBuilder.add(recJson);
        }
        recArray = recArrayBuilder.build();
        return recArray;
    }
    
    public JsonObject saveSche(JsonObject scheJson){
        JsonArrayBuilder scheArrayBuilder = Json.createArrayBuilder();
        for (ScheduleTopic topic:csg.getSchedule().getDataComponent().getScheduleList()){
            String time = (topic.getTime() != null)? topic.getTime():"none";
            String title = (topic.getTitle() != null)? topic.getTitle():"none";
            String topicField = (topic.getTopic() != null)? topic.getTopic():"none";
            String link = (topic.getLink() != null)? topic.getLink():"none";
            String criteria = (topic.getCriteria() != null)? topic.getCriteria():"none";
            JsonObject topicJson = Json.createObjectBuilder()
                                    .add("Type", topic.getType())
                                    .add("Date", topic.getDate())
                                    .add("Time", time)
                                    .add("Title", title)
                                    .add("Topic", topicField)
                                    .add("Link", link)
                                    .add("Criteria", criteria).build();
            scheArrayBuilder.add(topicJson);
        }
        JsonArray scheArray = scheArrayBuilder.build();
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        
        // check if start Mon and end Fri is chosen or not
        if (csg.getSchedule().getWorkspaceComponent().getStartPicker().getValue() != null && csg.getSchedule().getWorkspaceComponent().getEndPicker().getValue() != null){
            csg.getSchedule().getDataComponent().setStart(formatter.format(csg.getSchedule().getWorkspaceComponent().getStartPicker().getValue()));
            csg.getSchedule().getDataComponent().setEnd(formatter.format(csg.getSchedule().getWorkspaceComponent().getEndPicker().getValue()));
            scheJson = Json.createObjectBuilder()
                                    .add("Start", csg.getSchedule().getDataComponent().getStart())
                                    .add("End", csg.getSchedule().getDataComponent().getEnd())
                                    .add("ScheduleList", scheArray).build();
            return scheJson;
        } else {
            if (csg.getSchedule().getWorkspaceComponent().getStartPicker().getValue() == null) {
                PropertiesManager props = PropertiesManager.getPropertiesManager();
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(AppPropertyType.SCHEDULE_START_MISS), props.getProperty(AppPropertyType.SCHEDULE_START_MISS_MESS));
            } else if (csg.getSchedule().getWorkspaceComponent().getEndPicker().getValue() == null) {
                PropertiesManager props = PropertiesManager.getPropertiesManager();
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(AppPropertyType.SCHEDULE_END_MISS), props.getProperty(AppPropertyType.SCHEDULE_END_MISS_MESS));
            }
            return null;
        }
    }
    
    public JsonArray saveTeam(JsonArray teamJsonArray) {
        JsonArrayBuilder teamArrayBuilder = Json.createArrayBuilder();
        for (TeamData team:csg.getProject().getDataComponent().getTeamList()){
            JsonObject teamJson = Json.createObjectBuilder()
                                    .add("Name", team.getName())
                                    .add("Color", team.getColor())
                                    .add("TextColor", team.getTextColor())
                                    .add("Link", team.getLink()).build();
            teamArrayBuilder.add(teamJson);
        }
        teamJsonArray = teamArrayBuilder.build();
        return teamJsonArray;
    }
    
    public JsonArray saveStudent(JsonArray studentJsonArray) {
        JsonArrayBuilder studentArrayBuilder = Json.createArrayBuilder();
        for (StudentData student:csg.getProject().getDataComponent().getStudentList()){
            JsonObject studentJson = Json.createObjectBuilder()
                                        .add("First_Name", student.getFirstName())
                                        .add("Last_Name", student.getLastName())
                                        .add("Team", student.getTeam())
                                        .add("Role", student.getRole()).build();
            studentArrayBuilder.add(studentJson);
        }
        studentJsonArray = studentArrayBuilder.build();
        return studentJsonArray;
    }
    
    public void saveDataForExport(CourseSiteGenerator csg) throws IOException {
        csg.getTA().getFilesComponent().saveData(csg.getTA().getDataComponent(), "./work/OfficeHoursGridData.json");
        csg.getRec().getFileComponent().saveData(csg.getRec().getDataComponent(), "./work/RecitationsData.json");
        csg.getSchedule().getFileComponent().saveData(csg.getSchedule().getDataComponent(), "./work/ScheduleData.json");
        csg.getProject().getFileComponent().saveDataForTeamStudent(csg.getProject().getDataComponent(), "./work/TeamsAndStudents.json");
        csg.getProject().getFileComponent().saveDataForProjects(csg.getProject().getDataComponent(), "./work/ProjectsData.json");
    }
}
