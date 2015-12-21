package be.ehb.swp2.util;

import org.ini4j.Wini;

import java.io.File;
import java.io.IOException;


/**
 * Created by arnaudcoel on 29/10/15.
 */

/**
 * The config manager for the project
 */
public class ConfigurationHandler {
    Wini ini;

    /**
     * Default constructor
     */
    public ConfigurationHandler() {
        try {
            File cf = new File("config.ini");
            if (!cf.exists())
                cf.createNewFile();

            this.ini = new Wini(cf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setSetting(String section, String option, String value) {
        ini.put(section, option, value);
        this.save();
    }

    public String getSetting(String section, String option) {
        return ini.get(section, option);
    }

    @Deprecated
    public String getSession() {
        return ini.get("session", "id");
    }

    @Deprecated
    public void setSession(String session) {
        ini.put("session", "id", session);
        this.save();
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
