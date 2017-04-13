package tam.workspace;

import static tam.TAManagerProp.*;
import djf.ui.AppMessageDialogSingleton;
import java.util.Collections;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import properties_manager.PropertiesManager;
import tam.TAManagerApp;
import tam.data.TAData;
import tam.data.TeachingAssistant;
import tam.workspace.TAWorkspace;
import javafx.beans.property.StringProperty;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tam.TAManagerProp;
import tam.jtps.AddingTA_Transaction;
import tam.jtps.DeleteTA_Transaction;
import tam.jtps.EditTA_Transaction;
import tam.jtps.ToggleCell_Transaction;
import tam.jtps.jTPS_Transaction;

/**
 * This class provides responses to all workspace interactions, meaning
 * interactions with the application controls not including the file
 * toolbar.
 * 
 * @author Richard McKenna - Trung Vo
 * @version 1.0
 */
public class TAController {
    private Pattern pattern;
    private Matcher matcher;
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
		+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private ObservableList<TeachingAssistant> listExcludeOldTA;            // In EditTA mode, for checking if current edited TA already contained in the list or not
    private int indexOfOldTA;                            // In EditTA mode, for retrieving index of current edited TA in the list
    
    // THE APP PROVIDES ACCESS TO OTHER COMPONENTS AS NEEDED
    TAManagerApp app;

    /**
     * Constructor, note that the app must already be constructed.
     */
    public TAController(TAManagerApp initApp) {
        // KEEP THIS FOR LATER
        app = initApp;
        listExcludeOldTA = FXCollections.observableArrayList();
    }
    
    /**
     * This method responds to when the user requests to add
     * a new TA via the UI. Note that it must first do some
     * validation to make sure a unique name and email address
     * has been provided.
     */
    public boolean handleAddTA() {
        // WE'LL NEED THE WORKSPACE TO RETRIEVE THE USER INPUT VALUES
        TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
        TextField nameTextField = workspace.getNameTextField();
        TextField emailTextField = workspace.getEmailTextField();
        String name = nameTextField.getText();
        String email = emailTextField.getText();
        
        // WE'LL NEED TO ASK THE DATA SOME QUESTIONS TOO
        TAData data = (TAData)app.getDataComponent();
        
        // WE'LL NEED THIS IN CASE WE NEED TO DISPLAY ANY ERROR MESSAGES
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        // DID THE USER NEGLECT TO PROVIDE A TA NAME?
        if (name.isEmpty()) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_TA_NAME_TITLE), props.getProperty(MISSING_TA_NAME_MESSAGE));            
            return false;
        } else if (email.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(MISSING_TA_EMAIL_TITLE), props.getProperty(MISSING_TA_EMAIL_MESSAGE));
            return false;
        } else {
            if (workspace.getAddButton().getText().equals(props.getProperty(TAManagerProp.ADD_BUTTON_TEXT.toString())))
                listExcludeOldTA = data.clone(data.getTeachingAssistants());
                
            // DOES A TA ALREADY HAVE THE SAME NAME OR EMAIL?
            if (data.containsTA(name, listExcludeOldTA)) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(TA_NAME_AND_EMAIL_NOT_UNIQUE_TITLE), props.getProperty(TA_NAME_AND_EMAIL_NOT_UNIQUE_MESSAGE));                                    
                return false;
            } else if (data.containsTAEmail(email, listExcludeOldTA)){
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(TA_NAME_AND_EMAIL_NOT_UNIQUE_TITLE), props.getProperty(TA_NAME_AND_EMAIL_NOT_UNIQUE_MESSAGE));
                return false;
            }
            // EVERYTHING IS FINE, ADD A NEW TA
            else {
                if (isValidEmail(email)) {
                    if (workspace.getAddButton().getText().equals(props.getProperty(TAManagerProp.ADD_BUTTON_TEXT.toString()))){
                        // ADD THE NEW TA TO THE DATA
                        data.addTA(name, email);

                        // CLEAR THE TEXT FIELDS
                        nameTextField.setText("");
                        emailTextField.setText("");
                    } else {            // if current mode is Edit TA
                        data.getTeachingAssistants().remove((TeachingAssistant)data.getTeachingAssistants().get(indexOfOldTA));
                    }
                    // AND SEND THE CARET BACK TO THE NAME TEXT FIELD FOR EASY DATA ENTRY
                    nameTextField.requestFocus();
                    return true;
                } else {
                    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                    dialog.show("Invalid email format", "Please enter a valid email address.");
                    return false;
                }
            }
        }
    }
    
    // helper method to validate Email's string
    public boolean isValidEmail(String email){
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }
    
    // HANDLE EDIT TA FROM TEXTFIELD
    public boolean handleEditTA(TeachingAssistant ta, String oldName, String oldEmail){
        TAData data = (TAData)app.getDataComponent();
        TAWorkspace workspace = (TAWorkspace) app.getWorkspaceComponent();
        String nameToUpdate = workspace.getNameTextField().getText();
        String emailToUpdate = workspace.getEmailTextField().getText();
        
        // create a list of TAs excluding old TA, for testing if new edited TA is contained in the list or not
        indexOfOldTA = data.getTeachingAssistants().indexOf(ta);
        listExcludeOldTA.clear();
        listExcludeOldTA.addAll(data.getTeachingAssistants().subList(0, indexOfOldTA));
        listExcludeOldTA.addAll(data.getTeachingAssistants().subList(indexOfOldTA+1, data.getTeachingAssistants().size()));
        
        boolean edited = handleAddTA();               // then edit it
        if (edited){
            // push current state into stack before transaction
            //TeachingAssistant newEditTA = ((AddingTA_Transaction)((TAWorkspace)app.getWorkspaceComponent()).getJTPS().getTransactions().get(((TAWorkspace)app.getWorkspaceComponent()).getJTPS().getMostRecentTransaction())).getTA();           // get the new TA that just has been edited (added) from the AddingTA_Transaction 
            
            jTPS_Transaction transaction = new EditTA_Transaction(workspace, data, oldName, oldEmail, nameToUpdate, emailToUpdate, edited);
            ((TAWorkspace)app.getWorkspaceComponent()).getJTPS().addTransaction(transaction);
            /*
            StringBuilder cellTextStr;
            for (String cellKey:data.getOfficeHours().keySet()){
                StringProperty cellText = data.getOfficeHours().get(cellKey);
                if (cellText != null) {
                    cellTextStr = new StringBuilder(cellText.getValue());
                    if (data.isCellPaneHasTAName(cellTextStr.toString(), oldName)) {
                        data.removeTAFromCell(cellText, oldName, nameToUpdate, edited, cellKey);
                    }
                }
            }*/
        } else {                // if there is an error occured while editing, set TA Object back to old ones with old name & email
            ta.setName(oldName);
            ta.setEmail(oldEmail);
        }
        
        return edited;
    }
    
    /**
     * This function provides a response for when the user clicks
     * on the office hours grid to add or remove a TA to a time slot.
     * 
     * @param pane The pane that was toggled.
     */
    public boolean handleCellToggle(Pane pane) {
        // GET THE TABLE
        TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
        TableView taTable = workspace.getTATable();
        
        // IS A TA SELECTED IN THE TABLE?
        Object selectedItem = taTable.getSelectionModel().getSelectedItem();
        // GET THE TA
        TeachingAssistant ta = (TeachingAssistant)selectedItem;
        if (ta != null){
            String taName = ta.getName();
            TAData data = (TAData)app.getDataComponent();
            String cellKey = pane.getId();
            
            // push current state into stack before transaction
            jTPS_Transaction transaction = new ToggleCell_Transaction(taName, pane, data);
            ((TAWorkspace)app.getWorkspaceComponent()).getJTPS().addTransaction(transaction);
            
            // AND TOGGLE THE OFFICE HOURS IN THE CLICKED CELL
            //data.toggleTAOfficeHours(cellKey, taName);
            return true;
        } else {
            return false;
        }
    }
    
    public void handleDeleteTAfromTable(TAData data, TeachingAssistant ta) {
        
        // push current state into stack before transaction
        jTPS_Transaction transaction = new DeleteTA_Transaction(ta.getName(), ta.getEmail(), data.getOfficeHours(), data);
        ((TAWorkspace)app.getWorkspaceComponent()).getJTPS().addTransaction(transaction);
        
        /*
        String taName = ta.getName();
        for (String cellKey:data.getOfficeHours().keySet()){
            StringProperty cellText = data.getOfficeHours().get(cellKey);
            if (cellText != null) {
                if (data.isCellPaneHasTAName(cellText.getValue(), taName)) {
                    data.removeTAFromCell(cellText, taName, null, false, cellKey);
                }
            }
        }
        data.getTeachingAssistants().remove(ta);
        */
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        TAWorkspace workspace = (TAWorkspace) app.getWorkspaceComponent();
        workspace.getNameTextField().clear();
        workspace.getEmailTextField().clear();
        workspace.getAddButton().setText(props.getProperty(TAManagerProp.ADD_BUTTON_TEXT.toString()));
    }
}