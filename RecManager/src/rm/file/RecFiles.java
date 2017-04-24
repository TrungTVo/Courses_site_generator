
package rm.file;

import cm.CourseManagerApp;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import javafx.collections.ObservableList;
import javax.json.Json;
import javax.json.JsonArray;
import rm.RecManagerApp;
import rm.data.RecRecord;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;
import rm.data.RecData;

/**
 *
 * @author trungvo
 */
public class RecFiles {
    RecManagerApp app;
    CourseManagerApp courseApp;
    
    static final String JSON_SECTION = "section";
    static final String JSON_DAYTIME = "day_time";
    static final String JSON_LOCATION = "location";
    static final String JSON_TA1 = "ta_1";
    static final String JSON_TA2 = "ta_2";
    static final String JSON_RECITATIONS = "recitations";
    
    public RecFiles(RecManagerApp app) {
        this.app = app;
        this.courseApp = app.getCourseManagerApp();
    }
    
    public void saveData(RecRecord recRecord, String filePath) throws IOException {
        JsonArrayBuilder recArrayBuilder = Json.createArrayBuilder();
        ObservableList<RecData> recs = recRecord.getRecRecord();
        for  (RecData rec:recs) {
            JsonObject recJson = Json.createObjectBuilder()
                                    .add(JSON_SECTION, rec.getSection()+" ("+courseApp.getDataComponent().getInstructorName()+")")
                                    .add(JSON_DAYTIME, rec.getDayTime())
                                    .add(JSON_LOCATION, rec.getLocation())
                                    .add(JSON_TA1, rec.getTa1())
                                    .add(JSON_TA2, rec.getTa2()).build();
            recArrayBuilder.add(recJson);
        }
        JsonArray recJsonArray = recArrayBuilder.build();
        
        JsonObject recManagerJSON = Json.createObjectBuilder().add(JSON_RECITATIONS, recJsonArray).build();
        
        // AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
	Map<String, Object> properties = new HashMap<>(1);
	properties.put(JsonGenerator.PRETTY_PRINTING, true);
	JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
	StringWriter sw = new StringWriter();
	JsonWriter jsonWriter = writerFactory.createWriter(sw);
	jsonWriter.writeObject(recManagerJSON);
	jsonWriter.close();

	// INIT THE WRITER
	OutputStream os = new FileOutputStream(filePath);
	JsonWriter jsonFileWriter = Json.createWriter(os);
	jsonFileWriter.writeObject(recManagerJSON);
	String prettyPrinted = sw.toString();
	PrintWriter pw = new PrintWriter(filePath);
	pw.write(prettyPrinted);
	pw.close();
    }
}
