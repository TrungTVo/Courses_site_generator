/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.test_bed;

import java.io.IOException;
import javax.json.JsonArray;
import javax.json.JsonObject;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author trungvo
 */
public class loadJUnitTest extends TestCase {
    static final String jsonFilePath = "./work/SiteSaveTest.json";
    
    public loadJUnitTest(String testName) {
        super(testName);
    }
    

    /**
     * Test of loadTesting method, of class loadTest.
     */
    @Test
    public void testLoadTesting() throws IOException {
        System.out.println("loadTesting");
        JsonObject jsonObject = loadTest.loadJSONFile(jsonFilePath);
        
        // Course Data
        JsonObject courseDataJson = jsonObject.getJsonObject("Course_Data");
        assertTrue(loadTest.loadTesting(courseDataJson, "Subject", "CSE"));
        assertTrue(loadTest.loadTesting(courseDataJson, "Number", "219"));
        assertTrue(loadTest.loadTesting(courseDataJson, "Semester", "Fall"));
        assertTrue(loadTest.loadTesting(courseDataJson, "Year", "2017"));
        assertTrue(loadTest.loadTesting(courseDataJson, "Title", "Computer Science III"));
        assertTrue(loadTest.loadTesting(courseDataJson, "Instructor_Name", "Richard McKenna"));
        assertTrue(loadTest.loadTesting(courseDataJson, "Instructor_Home", "http://www3.cs.stonybrook.edu/~richard/"));
        
        JsonArray templateJson = courseDataJson.getJsonArray("Site_Templates");
        for (int i=1; i<=templateJson.size(); i++){
            JsonObject template = templateJson.getJsonObject(i-1);
            assertTrue(loadTest.loadTesting(template, "Use", "yes"));
            assertTrue(loadTest.loadTesting(template, "NavBar_Title", "navBar "+String.valueOf(i)));
            assertTrue(loadTest.loadTesting(template, "File_Name", "file "+String.valueOf(i)));
            assertTrue(loadTest.loadTesting(template, "Script", "script "+String.valueOf(i)));
        } 
        
        // TA Data
        JsonObject TADataJson = jsonObject.getJsonObject("TA_Data");
        assertTrue(loadTest.loadTesting(TADataJson, "StartHour", "0"));
        assertTrue(loadTest.loadTesting(TADataJson, "EndHour", "23"));
        
        
        JsonArray TasJson = TADataJson.getJsonArray("Tas");
        for (int i=1; i<=TasJson.size(); i++){
            JsonObject ta = TasJson.getJsonObject(i-1);
            assertTrue(loadTest.loadTesting(ta, "Name", "TA "+i));
            assertTrue(loadTest.loadTesting(ta, "Email", "Email TA "+i));
            assertTrue(loadTest.loadTesting(ta, "Undergrad", "yes"));
        }
        JsonArray officeHoursJson = TADataJson.getJsonArray("OfficeHours");
        for (int i=1; i<=officeHoursJson.size(); i++){
            JsonObject hour = officeHoursJson.getJsonObject(i-1);
            assertTrue(loadTest.loadTesting(hour, "Day", "MONDAY"));
            assertTrue(loadTest.loadTesting(hour, "Time", "8_00am"));
            assertTrue(loadTest.loadTesting(hour, "Name", "TA "+i));
        }
        
        // Rec Data
        JsonArray recDataJson = jsonObject.getJsonArray("Recitation_Data");
        for (int i=1; i<=recDataJson.size(); i++){
            JsonObject recObject = recDataJson.getJsonObject(i-1);
            assertTrue(loadTest.loadTesting(recObject, "Section", "R0"+i));
            assertTrue(loadTest.loadTesting(recObject, "Instructor", "Instructor "+i));
            assertTrue(loadTest.loadTesting(recObject, "Day/Time", "DayTime "+i));
            assertTrue(loadTest.loadTesting(recObject, "Location", "Location "+i));
            assertTrue(loadTest.loadTesting(recObject, "Ta1", "Trung Vo"));
            assertTrue(loadTest.loadTesting(recObject, "Ta2", "Hong Phuong"));
        }
        
        // Schedule Data
        JsonObject scheDataJson = jsonObject.getJsonObject("Schedule_Data");
        assertTrue(loadTest.loadTesting(scheDataJson, "Start", "03/22/2012"));
        assertTrue(loadTest.loadTesting(scheDataJson, "End", "04/20/2013"));
        JsonArray scheJson = scheDataJson.getJsonArray("ScheduleList");
        for (int i=1; i<=scheJson.size(); i++){
            JsonObject schedule = scheJson.getJsonObject(i-1);
            assertTrue(loadTest.loadTesting(schedule, "Type", "Holiday"));
            assertTrue(loadTest.loadTesting(schedule, "Date", "03/25/2012"));
            assertTrue(loadTest.loadTesting(schedule, "Time", "Time "+i));
            assertTrue(loadTest.loadTesting(schedule, "Title", "Title "+i));
            assertTrue(loadTest.loadTesting(schedule, "Topic", "Topic "+i));
            assertTrue(loadTest.loadTesting(schedule, "Link", "Link "+i));
            assertTrue(loadTest.loadTesting(schedule, "Criteria", "Criteria "+i));
        }
        
        // Project Data
        // Team Data
        JsonArray teamDataJson = jsonObject.getJsonArray("Team_Data");
        for (int i=1; i<=teamDataJson.size(); i++){
            JsonObject team = teamDataJson.getJsonObject(i-1);
            assertTrue(loadTest.loadTesting(team, "Name", "A"));
            assertTrue(loadTest.loadTesting(team, "Color", "b31a1a"));
            assertTrue(loadTest.loadTesting(team, "TextColor", "334db3"));
            assertTrue(loadTest.loadTesting(team, "Link", "Link "+i));
        }
        
        // Student Data
        JsonArray studentDataJson = jsonObject.getJsonArray("Student_Data");
        for (int i=1; i<=studentDataJson.size(); i++){
            JsonObject student = studentDataJson.getJsonObject(i-1);
            assertTrue(loadTest.loadTesting(student, "First_Name", "fName "+i));
            assertTrue(loadTest.loadTesting(student, "Last_Name", "lName "+i));
            assertTrue(loadTest.loadTesting(student, "Team", "B"));
            assertTrue(loadTest.loadTesting(student, "Role", "Role "+i));
        }
        
        
    }
    
}
