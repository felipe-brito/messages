package br.com.conductor.messages.enums;

import lombok.Getter;

/**
 *
 * @author Felipe Brito
 */
public enum AttributeAnnotationSwagger {

    VALUE("value"),
    NAME("name"),
    DESCRIPTION("description"),
    DISCRIMINATOR("discriminator"),
    NOTES("notes"),
    REQUIRED("required"),
    EXAMPLE("example"),
    HIDDEN("hidden"),
    POSITION("position");

    @Getter
    private final String value;

    AttributeAnnotationSwagger(String value) {
        this.value = value;
    }

}
