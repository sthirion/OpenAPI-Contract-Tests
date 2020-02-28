package model.petstore;

import java.util.ArrayList;

public class Pet {
    private Integer id;
    private String name;
    private String status;
    ArrayList< String > photoUrls = new ArrayList < String > ();
    ArrayList< String > tags = new ArrayList < String > ();


    // Getter Methods

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    // Setter Methods

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
