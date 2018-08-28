package br.com.conductor.messages.service;

import br.com.conductor.messages.util.Utilitarios;
import br.com.conductor.messages.visitor.CountVisitor;
import br.com.twsoftware.alfred.object.Objeto;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.google.common.collect.Lists;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Felipe Brito
 */
public class ArquivoService {

    private static CompilationUnit parse;

    /**
     * Conta o número de arquivos que podem ser internacionalizados.
     *
     * @param origem - arquivo de origem da internacionalização
     */
    public void contarTotalAquivos(File origem) {
        percorrerDiretorios(origem, new CountVisitor(), null);
    }

    /**
     * Verifica se existe um arquivo no destino com o mesmo nome do informado.
     *
     * @param destino - arquivo de destino (diretório)
     * @param nomeArquivo - nome do arquivo de destino
     * @return {@link Boolean}
     */
    public Boolean existeArquivoDestino(File destino, String nomeArquivo) throws NullPointerException {

        StringBuilder builder = new StringBuilder(destino.getAbsolutePath());
        builder.append(File.separator).append(nomeArquivo);

        try {

            return new File(builder.toString()).exists();
        } catch (NullPointerException e) {
            throw new NullPointerException("Nome informado está nulo.");
        }
    }

    public void gerarArquivoMessage(File origem, File destino) {

        try {
            Properties props = new Properties();
            props.load(new FileInputStream(destino));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ArquivoService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ArquivoService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void percorrerDiretorios(File file, VoidVisitorAdapter visitor, Object arg) {
        if (file.isDirectory()) {

            File[] diretorios = file.listFiles();

            Lists.newArrayList(diretorios).stream().forEach(d -> {
                inOrdem(0, d, visitor, arg);
            });
        }
    }

    private void inOrdem(Integer nivel, File diretorio, VoidVisitorAdapter visitor, Object arg) {

        if (Objeto.notBlank(diretorio)) {
            Integer incremento = ++nivel;
            File[] subDiretorios = diretorio.listFiles();
            if (Objeto.notBlank((Object[]) subDiretorios)) {
                Lists.newArrayList(subDiretorios).stream().forEach(d -> {
                    if (d.isDirectory()) {
                        inOrdem(incremento, d, visitor, arg);
                    } else {
                        parse(d, visitor, arg);
                    }
                });
            }
        }
    }

    private void parse(File file, VoidVisitorAdapter visitor, Object arg) {
        try {

            parse = JavaParser.parse(file, Utilitarios.CHARSET);
            parse.accept(visitor, arg);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }
}
