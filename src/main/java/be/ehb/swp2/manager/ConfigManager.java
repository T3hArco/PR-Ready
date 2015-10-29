package be.ehb.swp2.manager;

import org.ini4j.Wini;

import java.io.File;
import java.io.IOException;


/**
 * Created by arnaudcoel on 29/10/15.
 */

/**
 * The config manager for the project
 */
public class ConfigManager {
    Wini ini;

    /**
     * Default constructor
     */
    public ConfigManager() {
        try {
            File cf = new File("config.ini");
            if(!cf.exists())
                cf.createNewFile();

            this.ini = new Wini(cf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setSession(String session) {
        ini.put("session", "id", session);
        this.save();
    }

    public String getSession() {
        return ini.get("session", "id");
    }

    private void save() {
        try {
            this.ini.store();
        } catch (IOException e) {
            System.err.println("Unable to write settings file!");
            e.printStackTrace();
        }
    }

}
