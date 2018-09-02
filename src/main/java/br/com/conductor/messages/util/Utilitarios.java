package br.com.conductor.messages.util;

import com.google.common.base.Charsets;
import java.nio.charset.Charset;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Felipe Brito
 */
public class Utilitarios {

    /**
     * Constante para o Charset
     */
    public static final Charset CHARSET = Charsets.UTF_8;

    /**
     * Constantes para o nome da chave value das anotações
     */
    public static final String VALUE = "value";

    /**
     * Constante para o nome da chave name das anotações
     */
    public static final String NAME = "name";

    /**
     * Constante para o nome da chave da descrição das anotações
     */
    public static final String DESCRIPTION = "description";

    /**
     * Constantes para o nome da chave das descriminações das anotações
     */
    public static final String DISCRIMINATOR = "discriminator";

    /**
     * Constante com a nomenclatura das tags
     */
    public static String CLASS_CONTANTES_TAGS = "ConstantesTags";

    /**
     * Constante com a nomenclatura da exception pier
     */
    public static String ENUM_PIER_EXCEPTION = "ExceptionsMessagesPIEREnum";

    /**
     * Constante com a nomenclatura para o ApiOperation
     */
    public static String NOTES = "notes";

    public static String replace(String texto) {

        texto = StringUtils.trim(texto);
        texto = StringUtils.removeStart(texto, "\"{{{");
        texto = StringUtils.removeEnd(texto, "}}}\"");
        texto = StringUtils.trim(texto);

        return texto;

    }

}
