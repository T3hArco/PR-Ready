package be.ehb.swp2.entity;

/**
 * Created by Thomas on 29/10/2015.
 */
public abstract class Question {
    /**
     * De naam van een vraag
     */
    private String name;

    /**
     * De beschrijving van een vraag
     */
    private String description;

    /**
     * De tijd voor een vraag
     * @todo verduidelijk dit concept
     */
    private int time;

    /**
     * De time on voor een vraag
     * @todo verduidelijk dit concept
     */
    private boolean timeOn;

    /**
     * De default constructor, set naam op 'Naamloos'.
     */
    @Deprecated
    public Question() {
        this.name = "Naamloos";
    }

    /**
     * De constructor van Question
     * @param name
     * @param description
     * @param time
     * @param timeOn
     */
    public Question(String name, String description, int time, boolean timeOn) {
        this.name = name;
        this.description = description;
        this.time = time;
        this.timeOn = timeOn;
    }

    /**
     * Geeft de naam van een vraag terug weer
     * @return
     */
    public String getName() {
        return this.name;
    }

    /**
     * Geeft de beschrijving weer van een vraag
     * @return
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Geeft de tijd weer van een vraag
     * @return
     */
    public int getTime() {
        return this.time;
    }

    /**
     * Geeft de time on weer van een vraag
     * @todo verduidelijk dit concept!
     * @return
     */
    public boolean isTimeOn() {
        return this.timeOn;
    }

    /**
     * Zet de naam van een vraag
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Zet de beschrijving van een vraag
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Zet de tijd van een vraag
     * @param time
     */
    public void setTime(int time) {
        this.time = time;
    }

    /**
     * Zet de time on van een vraag
     * @todo implementeer dit
     * @param timeOn
     */
    public void setTimeOn(boolean timeOn) {

    }

}
