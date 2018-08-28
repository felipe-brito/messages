package br.com.conductor.messages.app;

import br.com.conductor.messages.controller.ObserverController;
import br.com.conductor.messages.controller.TesteObserver;
import br.com.twsoftware.alfred.object.Objeto;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Felipe Brito
 */
public class Aplicacao {

    static File arquivoEstruturaPastas = new File("F:\\desenv\\java-parse\\resultado\\estructor.txt");

    public static void main(String[] args) {

        final String PATH_PIER = "F:\\desenv\\java-parse\\config\\src\\main\\java\\br\\com\\conductor\\pier";

        if (!arquivoEstruturaPastas.exists()) {
            try {
                arquivoEstruturaPastas.createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        try {

            TesteObserver t = new TesteObserver();

            ObserverController.getInstance().register(t);

            URL urlArquivoPT = ClassLoader.getSystemResource("messages_pt_BR.properties");

            File arquivoMessagePT = new File(urlArquivoPT.toURI());
            File pier = new File(PATH_PIER);

            if (pier.isDirectory()) {

                File[] diretorios = pier.listFiles();

                Lists.newArrayList(diretorios).stream().forEach(d -> {
                    print(0, d);
                });

            }
            System.out.println("Countados todas as instancias");
            System.out.println("Total: " + t.getCount());

            ObserverController.getInstance().unregister(t);

        } catch (URISyntaxException ex) {
            ex.printStackTrace();
        }
    }
    private static CompilationUnit parse;

    public static void print(Integer nivel, File diretorio) {

        if (Objeto.notBlank(diretorio)) {

            if (diretorio.isDirectory()) {
                println(nivel, diretorio.getName().toUpperCase());
            }
            Integer incremento = ++nivel;

            File[] subDiretorios = diretorio.listFiles();
            if (Objeto.notBlank((Object[]) subDiretorios)) {
                Lists.newArrayList(subDiretorios).stream().forEach(d -> {
                    if (d.isDirectory()) {
                        print(incremento, d);
                    } else {

                        try {
                            parse = JavaParser.parse(d, Charsets.UTF_8);
//                            parse.accept(new ClassVisitor(), new Request());
                            println(incremento, d.getName());

                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(Aplicacao.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
            }
        }
    }

    public static void println(Integer position, String name) {

        StringBuilder builder = new StringBuilder();
        builder.append((position > 0) ? Strings.repeat("\t", position) + "| " : "");
        builder.append(name);
        builder.append("\n");

//        try {
//            Files.append(txt, arquivoEstruturaPastas, Charsets.UTF_8);
//            Files.write(arquivoEstruturaPastas.toPath(), builder.toString().getBytes(Charsets.UTF_8), StandardOpenOption.APPEND);
//        } catch (IOException ex) {
//            ex.printStackTrace();
//    }
    }

//ler as pastas
//localizar as classes que tem o ApiModel
//recuperar as keys dos arquivos
//se a opção original foi marcada
//confrontar no arquivo de message da pasta resource
//lançar exception caso exista um arquivo / caso não exista o arquivo
//recuperar os valores das chaves
//gravar em um novo arquivo definido pelo utilizador
}
