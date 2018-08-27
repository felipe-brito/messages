package br.com.conductor.messages.service;

import br.com.twsoftware.alfred.object.Objeto;
import java.util.HashMap;
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
public class Request {

    private String name;

    private String key;

    private String keyValue;

    private String value;

    private String keyDescription;

    private String description;

    private String keyDiscriminator;

    private String discriminator;

    private HashMap<String, Attr> attrs = new HashMap<String, Attr>();

    public String toString() {

        String result = "";

        if (Objeto.notBlank(getKeyValue())) {
            result += getKeyValue() + "=" + getValue() + "\n";
        }

        if (Objeto.notBlank(getKeyDescription())) {
            result += getKeyDescription() + "=" + getDescription() + "\n";
        }

        if (Objeto.notBlank(getKeyDiscriminator())) {
            result += getKeyDiscriminator() + "=" + getDiscriminator() + "\n";
        }

//          if(Objeto.notBlank(getAttrs()) && !getAttrs().isEmpty()) {
//
//               for(String key : getAttrs().keySet()) {
//                    result += getAttrs().get(key).toString();
//               }
//
//          }
        return result;

    }

}
