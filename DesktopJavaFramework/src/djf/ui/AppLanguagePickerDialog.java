
package djf.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author trungvo
 */
public class AppLanguagePickerDialog extends Stage {
    static AppLanguagePickerDialog singleton = null;
    
    // HERE ARE THE DIALOG COMPONENTS
    VBox messagePane;
    Scene messageScene;
    Label messageLabel;
    ComboBox languages;
    Button closeButton;
    String pickedLanguage;
    
    private AppLanguagePickerDialog() {}
    
    public static AppLanguagePickerDialog getSingleton() {
	if (singleton == null)
            singleton = new AppLanguagePickerDialog();
        return singleton;
    }
    
    public String getLanguage() {return pickedLanguage;}
    public void init(Stage owner) {
        // MAKE IT MODAL
        initModality(Modality.WINDOW_MODAL);
        initOwner(owner);
        
        // LABEL TO DISPLAY THE CUSTOM MESSAGE
        messageLabel = new Label("Please choose a language:");        
        
        // combobox
        String[] languageList = {"English", "Tiếng Việt"};
        languages = new ComboBox(generateComboBoxText(languageList));
        
        // CLOSE BUTTON
        closeButton = new Button("Choose");
        closeButton.setOnAction(e->{ 
            if (languages.getSelectionModel().getSelectedItem() != null){
                pickedLanguage = (String) languages.getSelectionModel().getSelectedItem();
                AppLanguagePickerDialog.this.close(); 
            }
        });
        
        // WE'LL PUT EVERYTHING HERE
        messagePane = new VBox();
        messagePane.setAlignment(Pos.CENTER);
        messagePane.getChildren().add(messageLabel);
        messagePane.getChildren().add(languages);
        messagePane.getChildren().add(closeButton);
        
        // MAKE IT LOOK NICE
        messagePane.setPadding(new Insets(80, 60, 80, 60));
        messagePane.setSpacing(20);

        // AND PUT IT IN THE WINDOW
        messageScene = new Scene(messagePane);
        this.setScene(messageScene);
    }
    
    public ObservableList<String> generateComboBoxText(String[] list) {
        ObservableList<String> res = FXCollections.observableArrayList();
        for (String i:list){
            res.add(i);
        }
        return res;
    }
    
    public void show(String title) {
	// SET THE DIALOG TITLE BAR TITLE
	setTitle(title);
	
	// AND OPEN UP THIS DIALOG, MAKING SURE THE APPLICATION
	// WAITS FOR IT TO BE RESOLVED BEFORE LETTING THE USER
	// DO MORE WORK.
        showAndWait();
    }
}
