/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.data;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SitePage {
    private boolean isUsed;
    private final StringProperty navBar;
    private final StringProperty file;
    private final StringProperty script;
    
    public SitePage(boolean isUsed, String navBar, String file, String script){
        this.isUsed = isUsed;
        this.navBar = new SimpleStringProperty(navBar);
        this.file = new SimpleStringProperty(file);
        this.script = new SimpleStringProperty(script);
    }
    
    public boolean getIsUsed() {return isUsed;}
    public String getNavBar() {return navBar.get();}
    public String getFile() {return file.get();}
    public String getScript() {return script.get();}
    
    public void setIsUsed(boolean isUsed){this.isUsed = isUsed;}
    public void setNavBar(String navBar){this.navBar.set(navBar);}
    public void setFile(String file){this.file.set(file);}
    public void setScript(String script){this.script.set(script);}
}
