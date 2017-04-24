
package sm.file;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;
import sm.ScheduleManagerApp;
import sm.data.ScheduleData;
import sm.data.ScheduleTopic;

/**
 *
 * @author trungvo
 */
public class ScheduleFiles {
    ScheduleManagerApp app;
    
    static final String JSON_STARTMONTH = "startingMondayMonth";
    static final String JSON_STARTDAY = "startingMondayDay";
    static final String JSON_ENDMONTH = "endingFridayMonth";
    static final String JSON_ENDDAY = "endingFridayDay";
    static final String JSON_HOLIDAYS = "holidays";
    static final String JSON_MONTH = "month";
    static final String JSON_DAY = "day";
    static final String JSON_TITLE = "title";
    static final String JSON_TOPIC = "topic";
    static final String JSON_LINK = "link";
    static final String JSON_LECTURES = "lectures";
    static final String JSON_REFERENCES = "references";
    static final String JSON_RECITATIONS = "recitations";
    static final String JSON_HWS = "hws";
    static final String JSON_TIME = "time";
    static final String JSON_CRITERIA = "criteria";
    
    public ScheduleFiles(ScheduleManagerApp app) {
        this.app = app;
    }
    
    public void saveData(ScheduleData data, String filePath) throws IOException {
        
        // Holiday Json Array
        // ===================
        JsonArrayBuilder holidaysArrayBuilder = Json.createArrayBuilder();
        ObservableList<ScheduleTopic> holidaysList = getTypeList(data.getScheduleList(), "Holiday");
        for (ScheduleTopic topic:holidaysList){
            String link = (topic.getLink() == null || topic.getLink().isEmpty())? "":topic.getLink();
            String title = (topic.getTitle() == null || topic.getTitle().isEmpty())? "":topic.getTitle();
            JsonObject topicJson = Json.createObjectBuilder()
                                    .add(JSON_MONTH, topic.getDate().split("/")[0])
                                    .add(JSON_DAY, topic.getDate().split("/")[1])
                                    .add(JSON_TITLE, title+"<br/><br/><br/><br/>")
                                    .add(JSON_LINK, link).build();
            holidaysArrayBuilder.add(topicJson);
        }
        JsonArray holidayJsonArray = holidaysArrayBuilder.build();
        
        // Lectures Json Array
        // ===================
        JsonArrayBuilder lecturesArrayBuilder = Json.createArrayBuilder();
        ObservableList<ScheduleTopic> lecturesList = getTypeList(data.getScheduleList(), "Lecture");
        for (ScheduleTopic topic:lecturesList){
            String link = (topic.getLink() == null || topic.getLink().isEmpty())? "none":topic.getLink();
            String title = (topic.getTitle() == null || topic.getTitle().isEmpty())? "":topic.getTitle();
            JsonObject topicJson = Json.createObjectBuilder()
                                    .add(JSON_MONTH, topic.getDate().split("/")[0])
                                    .add(JSON_DAY, topic.getDate().split("/")[1])
                                    .add(JSON_TITLE, title)
                                    .add(JSON_TOPIC, topic.getTopic()+"<br/><br/><br/><br/>")
                                    .add(JSON_LINK, link).build();
            lecturesArrayBuilder.add(topicJson);
        }
        JsonArray lectureJsonArray = lecturesArrayBuilder.build();
        
        // References Json Array
        // ===================
        JsonArrayBuilder refsArrayBuilder = Json.createArrayBuilder();
        ObservableList<ScheduleTopic> refsList = getTypeList(data.getScheduleList(), "Reference");
        for (ScheduleTopic topic:refsList){
            String link = (topic.getLink() == null || topic.getLink().isEmpty())? "none":topic.getLink();
            JsonObject topicJson = Json.createObjectBuilder()
                                    .add(JSON_MONTH, topic.getDate().split("/")[0])
                                    .add(JSON_DAY, topic.getDate().split("/")[1])
                                    .add(JSON_TITLE, topic.getTitle())
                                    .add(JSON_TOPIC, topic.getTopic()+"<br/><br/><br/><br/>")
                                    .add(JSON_LINK, link).build();
            refsArrayBuilder.add(topicJson);
        }
        JsonArray refJsonArray = refsArrayBuilder.build();
        
        // Recitations Json Array
        // ===================
        JsonArrayBuilder recsArrayBuilder = Json.createArrayBuilder();
        ObservableList<ScheduleTopic> recsList = getTypeList(data.getScheduleList(), "Recitation");
        for (ScheduleTopic topic:recsList){
            String link = (topic.getLink() == null || topic.getLink().isEmpty())? "none":topic.getLink();
            JsonObject topicJson = Json.createObjectBuilder()
                                    .add(JSON_MONTH, topic.getDate().split("/")[0])
                                    .add(JSON_DAY, topic.getDate().split("/")[1])
                                    .add(JSON_TITLE, topic.getTitle())
                                    .add(JSON_TOPIC, topic.getTopic()+"<br/><br/><br/>").build();
            recsArrayBuilder.add(topicJson);
        }
        JsonArray recJsonArray = recsArrayBuilder.build();
        
        // HWS Json Array
        // ===================
        JsonArrayBuilder hwsArrayBuilder = Json.createArrayBuilder();
        ObservableList<ScheduleTopic> hwsList = getTypeList(data.getScheduleList(), "Homework");
        for (ScheduleTopic topic:hwsList){
            String link = (topic.getLink() == null || topic.getLink().isEmpty())? "none":topic.getLink();
            String criteria = (topic.getCriteria()== null || topic.getCriteria().isEmpty())? "none":topic.getCriteria();
            JsonObject topicJson = Json.createObjectBuilder()
                                    .add(JSON_MONTH, topic.getDate().split("/")[0])
                                    .add(JSON_DAY, topic.getDate().split("/")[1])
                                    .add(JSON_TITLE, topic.getTitle())
                                    .add(JSON_TOPIC, "due @ "+topic.getTime()+"<br/>("+ topic.getTopic()+")")
                                    .add(JSON_LINK, link)
                                    .add(JSON_TIME, topic.getTime())
                                    .add(JSON_CRITERIA, criteria).build();
            hwsArrayBuilder.add(topicJson);
        }
        JsonArray hwJsonArray = hwsArrayBuilder.build();
        
        // PUT EVERYTHING TOGETHER
        JsonObject json = Json.createObjectBuilder()
                            .add(JSON_STARTMONTH, data.getStart().split("/")[0])
                            .add(JSON_STARTDAY, data.getStart().split("/")[1])
                            .add(JSON_ENDMONTH, data.getEnd().split("/")[0])
                            .add(JSON_ENDDAY, data.getEnd().split("/")[1])
                            .add(JSON_HOLIDAYS, holidayJsonArray)
                            .add(JSON_LECTURES, lectureJsonArray)
                            .add(JSON_REFERENCES, refJsonArray)
                            .add(JSON_RECITATIONS, recJsonArray)
                            .add(JSON_HWS, hwJsonArray).build();
        
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
    
    // HELPER METHOD GETTING ALL SCHEDULE TOPICS THAT IS "HOLIDAY" TYPE
    public ObservableList<ScheduleTopic> getTypeList(ObservableList<ScheduleTopic> scheduleList, String type){
        ObservableList<ScheduleTopic> res = FXCollections.observableArrayList();
        for (ScheduleTopic topic:scheduleList){
            if (topic.getType().equals(type)){
                res.add(topic);
            }
        }
        return res;
    }
    
}
