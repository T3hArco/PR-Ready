package be.ehb.swp2.entity;

/**
 * Created by arnaudcoel on 26/10/15.
 */
public class Quiz implements Comparable<Quiz> {
    /**
     * Het unieke nummer van een Quiz
     */
    private int id;

    /**
     * De naam van een Quiz
     */
    private String name;

    /**
     * Het logo van een Quiz
     * @todo is dit het correcte type waar we achter zoeken? Ik denk niet dat het zo in elkaar mag zitten
     */
    private String logo;

    /**
     * De beschrijving van een Quiz
     */
    private String description;

    /**
     * Default constructor voor Quiz
     */
    public Quiz() {}

    /**
     * Constructor voor quiz
     * @param logo
     * @param name
     * @param description
     */
    public Quiz(String name, String logo, String description) {
        this.logo = logo;
        this.name = name;
        this.description = description;
    }

    /**
     * Getter voor id
     * @return Id van de Quiz
     */
    public int getId() {
        return id;
    }

    /**
     * Setter voor id
     * @param id de nieuwe id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter voor logo URI
     * @return logo URI
     */
    public String getLogo() {
        return logo;
    }

    /**
     * setter voor logo URI
     * @param logo logo URI
     */
    public void setLogo(String logo) {
        this.logo = logo;
    }

    /**
     * getter voor name
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * setter voor name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * getter voor description
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * setter voor description
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * compareTo methode voor klasse Quiz
     */
    public int compareTo(Quiz q) {
        int i = this.description.compareTo(q.getDescription());
        i += this.name.compareTo(q.getName());
        return i;
    }

}
