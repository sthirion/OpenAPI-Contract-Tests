package model;

public class Error {
    private String message;
    private String required_fields;
    private String example;


    // Getter Methods

    public String getMessage() {
        return message;
    }

    public String getRequired_fields() {
        return required_fields;
    }

    public String getExample() {
        return example;
    }

    // Setter Methods

    public void setMessage(String message) {
        this.message = message;
    }

    public void setRequired_fields(String required_fields) {
        this.required_fields = required_fields;
    }

    public void setExample(String example) {
        this.example = example;
    }
}
