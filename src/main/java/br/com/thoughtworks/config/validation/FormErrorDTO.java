package br.com.thoughtworks.config.validation;

public class FormErrorDTO {
    private String field;
    private String error;

    public FormErrorDTO(String field, String error) {
        this.field = field;
        this.error = error;
    }


    public String getField() {
        return field;
    }

    public String getError() {
        return error;
    }
}
