
package csg.test_bed;

import cm.data.CourseData;
import cm.data.SitePage;
import csg.CourseSiteGenerator;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;
import pm.data.ProjectRecord;
import pm.data.StudentData;
import pm.data.TeamData;
import rm.data.RecData;
import rm.data.RecRecord;
import sm.data.ScheduleData;
import sm.data.ScheduleTopic;
import tam.data.TAData;
import tam.data.TeachingAssistant;
import tam.file.TimeSlot;

/**
 *
 * @author trungvo
 */
public class TestSave {
    
    public static void saveData(CourseSiteGenerator csg, String filePath) throws IOException {
	// GET THE DATA
	CourseData courseData = csg.getCourse().getDataComponent();
        TAData taData = csg.getTA().getDataComponent();
        RecRecord recRecord = csg.getRec().getDataComponent();
        ScheduleData scheduleData = csg.getSchedule().getDataComponent();
        ProjectRecord projectData = csg.getProject().getDataComponent();
        
        // Hard code
        // COURSE DATA
        courseData.setSubject("CSE");
        courseData.setSemester("Fall");
        courseData.setNumber("219");
        courseData.setYear("2017");
        courseData.setTitle("Computer Science III");
        courseData.setInstructorName("Richard McKenna");
        courseData.setInstructorHome("http://www3.cs.stonybrook.edu/~richard/");
        
        ObservableList<SitePage> templates = FXCollections.observableArrayList();
        for (int i=1; i<=3; i++){
            SitePage sp = new SitePage(true, "navBar "+i, "file "+i, "script "+i);
            templates.add(sp);
        }
        courseData.setTemplates(templates);
        
        // Teaching Assistant Data
        ObservableList<TeachingAssistant> teachingAssistants = FXCollections.observableArrayList();
        for (int i=1; i<=3; i++){
            TeachingAssistant ta = new TeachingAssistant("TA "+i, "Email TA "+i, true);
            teachingAssistants.add(ta);
        }
        taData.setTeachingAssistants(teachingAssistants);
        
        ArrayList<TimeSlot> officeHours = new ArrayList<>();
        TimeSlot ts1 = new TimeSlot("Monday", "8_00am", "Trung Vo");
        TimeSlot ts2 = new TimeSlot("Tuesday", "9_00am", "Aiko Chu");
        TimeSlot ts3 = new TimeSlot("Wednesday", "10_00am", "Hong Phuong");
        officeHours.add(ts1);
        officeHours.add(ts2);
        officeHours.add(ts3);
        
        // Recitation Data
        RecData recData1 = new RecData("R02", "McKenna", "Wed 3:30pm-4:20pm", "Old CS 2114", "Trung Vo", "Hong Phuong");
        RecData recData2 = new RecData("R03", "Stevens", "Tues 3:00pm-4:00pm", "New CS 1114", "Ron Vo", "Aiko Chu");
        ObservableList<RecData> recList = FXCollections.observableArrayList();
        recList.add(recData1);
        recList.add(recData2);
        recRecord.setRecRecord(recList);
        
        // Schedule Data
        scheduleData.setStart("4/22/2012");
        scheduleData.setEnd("4/20/2013");
        ScheduleTopic scheTopic1 = new ScheduleTopic("Holiday", "2/9/2017", "4:00pm", "SNOW DAY", "Event Programming", "https://course.com", "");
        ScheduleTopic scheTopic2 = new ScheduleTopic("Homework", "3/9/2017", "5:00pm", "HW5", "File Management", "https://course.com", "");
        ObservableList<ScheduleTopic> scheTopicList = FXCollections.observableArrayList();
        scheTopicList.add(scheTopic1);
        scheTopicList.add(scheTopic2);
        
        // Project Data
        TeamData team1 = new TeamData("Trung Vo", "ffffff", "123456", "https://sample.com");
        TeamData team2 = new TeamData("Ron Vo", "ffffff", "123456", "https://sample.com");
        StudentData student1 = new StudentData("Trung", "Vo", "Database", "Data Sciencetist");
        StudentData student2 = new StudentData("Phuong", "Chu", "Web Design", "Web Developer");
        ObservableList<TeamData> teamList = FXCollections.observableArrayList();
        ObservableList<StudentData> studentList = FXCollections.observableArrayList();
        teamList.addAll(team1, team2);
        studentList.addAll(student1, student2);
        
        
        // SAVING DATA INTO JSON OBJECTS
        // ================== CourseJson ==================
        JsonArrayBuilder siteTemplateArrayBuilder = Json.createArrayBuilder();
        ObservableList<SitePage> courseTemplates = courseData.getTemplates();
        for (SitePage sp:courseTemplates){
            JsonObject spJson;
            if (sp.getIsUsed()) {
                spJson = Json.createObjectBuilder()
                        .add("Use", "yes")
                        .add("NavBar Title", sp.getNavBar())
                        .add("File Name", sp.getFile())
                        .add("Script", sp.getScript()).build();
            } else {
                spJson = Json.createObjectBuilder()
                        .add("Use", "no")
                        .add("NavBar Title", sp.getNavBar())
                        .add("File Name", sp.getFile())
                        .add("Script", sp.getScript()).build();
            }
            siteTemplateArrayBuilder.add(spJson);
        }
        JsonArray siteTemplatesArray = siteTemplateArrayBuilder.build();
        JsonObject courseJson = Json.createObjectBuilder()
                                .add("Subject", courseData.getSubject())
                                .add("Number", courseData.getNumber())
                                .add("Semester", courseData.getSemester())
                                .add("Year", courseData.getYear())
                                .add("Title", courseData.getTitle())
                                .add("Instructor Name", courseData.getInstructorName())
                                .add("Instructor Home", courseData.getInstructorHome())
                                .add("Site Templates", siteTemplatesArray)
                                .build();
        
        // ================== TAJson ==================
        JsonArrayBuilder taArrayBuilder = Json.createArrayBuilder();
        ObservableList<TeachingAssistant> tas = taData.getTeachingAssistants();
        for (TeachingAssistant ta : tas) {
            JsonObject taJson;
            if (ta.getIsUndergrad()){
                taJson = Json.createObjectBuilder()
		    .add("Name", ta.getName()).add("Email", ta.getEmail()).add("Undergrad", "yes").build();
            } else {
                taJson = Json.createObjectBuilder()
		    .add("Name", ta.getName()).add("Email", ta.getEmail()).add("Undergrad", "no").build();
            }
	    taArrayBuilder.add(taJson);
	}
	JsonArray tasArray = taArrayBuilder.build();
        
        JsonArrayBuilder officeHoursArrayBuilder = Json.createArrayBuilder();
        for (TimeSlot ts:officeHours) {
            JsonObject timeSlotJson = Json.createObjectBuilder()
                                        .add("Day", ts.getDay())
                                        .add("Time", ts.getTime())
                                        .add("Name", ts.getName()).build();
            officeHoursArrayBuilder.add(timeSlotJson);
        }
        JsonArray officeHoursArray = officeHoursArrayBuilder.build();
	JsonObject taJson = Json.createObjectBuilder()
                            .add("StartHour", String.valueOf(taData.getStartHour()))
                            .add("EndHour", String.valueOf(taData.getEndHour()))
                            .add("Tas", tasArray)
                            .add("OfficeHours", officeHoursArray).build();
        
        // ================== RecJson ==================
        JsonArrayBuilder recArrayBuilder = Json.createArrayBuilder();
        for (RecData rec:recList) {
            JsonObject recJson = Json.createObjectBuilder()
                                    .add("Section", rec.getSection())
                                    .add("Instructor", rec.getInstructor())
                                    .add("Day/Time", rec.getDayTime())
                                    .add("Location", rec.getLocation())
                                    .add("Ta1", rec.getTa1())
                                    .add("Ta2", rec.getTa2()).build();
            recArrayBuilder.add(recJson);
        }
        JsonArray recArray = recArrayBuilder.build();
        
        // ================== ScheJson ==================
        JsonArrayBuilder scheArrayBuilder = Json.createArrayBuilder();
        for (ScheduleTopic topic:scheTopicList){
            JsonObject topicJson = Json.createObjectBuilder()
                                    .add("Type", topic.getType())
                                    .add("Date", topic.getDate())
                                    .add("Time", topic.getTime())
                                    .add("Title", topic.getTitle())
                                    .add("Topic", topic.getTopic())
                                    .add("Link", topic.getLink())
                                    .add("Criteria", topic.getCriteria()).build();
            scheArrayBuilder.add(topicJson);
        }
        JsonArray scheArray = scheArrayBuilder.build();
        JsonObject scheJson = Json.createObjectBuilder()
                                .add("Start", scheduleData.getStart())
                                .add("End", scheduleData.getEnd())
                                .add("ScheduleList", scheArray).build();
        
        // ================== TeamJson ==================
        JsonArrayBuilder teamArrayBuilder = Json.createArrayBuilder();
        for (TeamData team:teamList){
            JsonObject teamJson = Json.createObjectBuilder()
                                    .add("Name", team.getName())
                                    .add("Color", team.getColor())
                                    .add("TextColor", team.getTextColor())
                                    .add("Link", team.getLink()).build();
            teamArrayBuilder.add(teamJson);
        }
        JsonArray teamArray = teamArrayBuilder.build();
        
        // ================== StudentJson ==================
        JsonArrayBuilder studentArrayBuilder = Json.createArrayBuilder();
        for (StudentData student:studentList){
            JsonObject studentJson = Json.createObjectBuilder()
                                        .add("First Name", student.getFirstName())
                                        .add("Last Name", student.getLastName())
                                        .add("Team", student.getTeam())
                                        .add("Role", student.getRole()).build();
            studentArrayBuilder.add(studentJson);
        }
        JsonArray studentArray = studentArrayBuilder.build();
        
        // PUT EVERYTHING INTO A SINGLE JSON OBJECT
        JsonObject dataJson = Json.createObjectBuilder()
                                .add("Course Data", courseJson)
                                .add("TA Data", taJson)
                                .add("Recitation Data", recArray)
                                .add("Schedule Data", scheJson)
                                .add("Team Data", teamArray)
                                .add("Student Data", studentArray).build();
	
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
    }
}
