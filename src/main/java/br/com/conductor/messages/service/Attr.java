package br.com.conductor.messages.service;

import br.com.twsoftware.alfred.object.Objeto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Felipe Brito
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Attr {

    private String key;

    private String keyValue;

    private String value;

    private String name;

    private String keyName;

//     private String regexp;
//
//     private String keyRegexp;
    private String message;

    private String keyMessage;

    public String toString() {

        String result = "";
        if (Objeto.notBlank(getKeyName())) {
            result += getKeyName() + "=" + getName() + "\n";
        }

        if (Objeto.notBlank(getKeyValue())) {
            result += getKeyValue() + "=" + getValue() + "\n";
        }

        if (Objeto.notBlank(getKeyMessage())) {
            result += getKeyMessage() + "=" + getMessage() + "\n";
        }

        return result;

    }

}
