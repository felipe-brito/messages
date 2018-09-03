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

    public Formatter() {
    }

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

    public String header() {

        StringBuilder sb = new StringBuilder();

        sb.append("# COMO MONTAR A MENSAGEM")
                .append("\n")
                .append("#")
                .append("\n")
                .append("# na propria exception contém uma lista de erros com o seguinte formato:\n")
                .append("\n")
                .append("# {")
                .append("\n")
                .append("# \t \"defaultMessage\": \"Informe a data no formato aaaa-MM-dd. \",")
                .append("\n")
                .append("# \t \"objectName\": \"PessoaPersist\",")
                .append("\n")
                .append("# \t \"field\": \"dataNascimento\",")
                .append("\n")
                .append("# \t \"code\": \"typeMismatch\"")
                .append("\n")
                .append("# }")
                .append("\n")
                .append("#")
                .append("\n")
                .append("# a mensagem deve ser montada com as seguintes informações da exception")
                .append("\n")
                .append("#")
                .append("\n")
                .append("# code.objectName.field=message")
                .append("\n")
                .append("#")
                .append("\n")
                .append("#")
                .append("\n")
                .append("# OBSERVAÇÕES:")
                .append("\n")
                .append("#")
                .append("\n")
                .append("# agrupe as mensagens por classe sempre pulando uma linha entre o nome da classe e as mensagens. Ex.:")
                .append("\n")
                .append("#")
                .append("\n")
                .append("\n")
                .append("# CLASSE")
                .append("\n")
                .append("\n")
                .append("# code.objectName.field1=message1")
                .append("\n")
                .append("# code.objectName.field2=message2")
                .append("\n")
                .append("# code.objectName.field3=message3")
                .append("\n")
                .append("\n")
                .append("# ========================================================================================================")
                .append("\n")
                .append("\n")
                .append("# PESSOAPERSIST")
                .append("\n")
                .append("\n")
                .append("typeMismatch.UsuarioUpdate.status=Status incorreto.")
                .append("\n")
                .append("typeMismatch.PessoaPersist.dataNascimento=Informe a data no formato aaaa-MM-dd.")
                .append("\n")
                .append("typeMismatch.PessoaPersist.dataNascimento=Data informada em um formato não compatível com essa API, informe a data no formato aaaa-MM-dd, norma ISO 8601.")
                .append("\n")
                .append("typeMismatch.AtendimentoCliente.dataAtendimento=Data informada em um formato não compatível com essa API, informe a data no formato yyyy-MM-dd'T'HH:mm:ss.SSS'Z', norma ISO 8601.")
                .append("\n")
                .append("typeMismatch.AtendimentoCliente.dataAgendamento=Data informada em um formato não compatível com essa API, informe a data no formato yyyy-MM-dd'T'HH:mm:ss.SSS'Z', norma ISO 8601.")
                .append("\n")
                .append("typeMismatch.AtendimentoCliente.dataHoraInicioAtendimento=Data informada em um formato não compatível com essa API, informe a data no formato yyyy-MM-dd'T'HH:mm:ss.SSS'Z', norma ISO 8601.")
                .append("\n")
                .append("typeMismatch.AtendimentoCliente.dataHoraFimAtendimento=Data informada em um formato não compatível com essa API, informe a data no formato yyyy-MM-dd'T'HH:mm:ss.SSS'Z', norma ISO 8601.")
                .append("\n")
                .append("\n")
                .append("# MAQUINETA")
                .append("\n")
                .append("typeMismatch.Maquineta.dataImplantacao=Data informada em um formato nao compatível com essa API, informe a data no formato yyyy-MM-dd'T'HH:mm:ss.SSS'Z', norma ISO 8601.")
                .append("\n");

        return sb.toString();

    }

}
