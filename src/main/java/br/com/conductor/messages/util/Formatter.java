package br.com.conductor.messages.util;

import br.com.conductor.messages.enums.FormatterType;
import lombok.Data;

/**
 *
 * @author Felipe Brito
 */
@Data
public class Formatter {

    private StringBuilder formatter;

    public Formatter(String className, FormatterType formatterType) {
        factory(className, formatterType);
    }

    public void add(String key, String value) {

        formatter.append(key).append("=").append(value).append("\n");
    }

    private void factory(String className, FormatterType formatterType) {
        switch (formatterType) {
            case POJO: {
                formatter = formatterPojo(className);
            }
            break;
            case RESOURCE: {
                formatter = formatterResource(className);
            }
            break;
            case CONSTANT: {
                formatter = formatterConstant();
            }
            break;
            case EXCEPTION: {
                formatter = formatterException();
            }
            break;
        }
    }

    private StringBuilder formatterPojo(String className) {

        StringBuilder builder = new StringBuilder();

        builder.append("\n")
                .append("#\n")
                .append("# POJO: ")
                .append(className)
                .append("\n")
                .append("#\n");

        return builder;
    }

    private StringBuilder formatterResource(String className) {
        StringBuilder builder = new StringBuilder();

        builder.append("\n")
                .append("#\n")
                .append("# RESOURCE: ")
                .append(className)
                .append("\n")
                .append("#\n");

        return builder;
    }

    private StringBuilder formatterConstant() {
        StringBuilder builder = new StringBuilder();

        builder.append("\n")
                .append("#\n")
                .append("# TAGS")
                .append("\n")
                .append("#\n");

        return builder;
    }

    private StringBuilder formatterException() {
        StringBuilder builder = new StringBuilder();

        builder.append("\n")
                .append("#\n")
                .append("# EXCEPTION ")
                .append("\n")
                .append("#\n");

        return builder;
    }

}
