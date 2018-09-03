package br.com.conductor.messages.service;

import br.com.conductor.messages.exception.ArquivoException;
import br.com.conductor.messages.util.Formatter;
import br.com.conductor.messages.util.Utilitarios;
import br.com.conductor.messages.visitor.ClassVisitor;
import br.com.twsoftware.alfred.object.Objeto;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;

/**
 *
 * @author Felipe Brito
 */
public class ArquivoService {

    private static CompilationUnit parse;

    /**
     * Verifica se existe um arquivo no destino com o mesmo nome do informado.
     *
     * @param destino arquivo de destino (diretório)
     * @param nomeArquivo nome do arquivo de destino
     *
     * @throws NullPointerException Se o nome do arquivo não for informado
     * @throws SecurityException Se o arquivo ou pasta possuir restrição de
     * acesso a leitura
     * @return {@link Boolean}
     */
    public Boolean existeArquivoDestino(File destino, String nomeArquivo) throws NullPointerException, SecurityException {

        StringBuilder builder = new StringBuilder(destino.getAbsolutePath());
        builder.append(File.separator).append(nomeArquivo);

        try {

            return new File(builder.toString()).exists();
        } catch (NullPointerException npe) {
            throw new NullPointerException("Nome informado está nulo.");
        } catch (SecurityException se) {
            throw new SecurityException("Arquivo não permite acesso de leitura.");
        }
    }

    public void escrever(String texto, File file) {

        try {
            Files.write(file.toPath(), texto.getBytes(Utilitarios.CHARSET), StandardOpenOption.APPEND);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public String validarArquivo(File arquivo) {

        try {
            StringBuilder builder = new StringBuilder();

            List<String> linhas = Files.readAllLines(Paths.get(arquivo.toURI()));

            List<String> ocorrencias = Lists.newArrayList();
            Set<String> chaves = Sets.newHashSet();

            linhas.stream().forEach(key -> {
                String[] chave = key.split("=");
                ocorrencias.add(chave[0]);
                chaves.add(chave[0]);
            });

            chaves.forEach(chave -> {
                Integer qtde = Collections.frequency(ocorrencias, chave);
                if (qtde > 1) {

                    builder.append(chave)
                            .append(": ")
                            .append(qtde)
                            .append("\n");

                }
            });

            UUID uuid = UUID.randomUUID();

            File log = new File(arquivo.getParentFile().getAbsolutePath() + File.separator + "log_" + uuid.toString() + ".txt");

            if (!log.exists()) {
                log.createNewFile();
            }

            escrever(builder.toString(), log);

            return log.getAbsolutePath();

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public void gerarArquivoMessage(File origem, File destino, Properties message) {
        Formatter formatter = new Formatter();
        escrever(formatter.header(), destino);
        percorrerDiretorios(origem, new ClassVisitor(destino, message), null);
    }

    private void percorrerDiretorios(File file, VoidVisitorAdapter visitor, Object arg) {
        if (file.isDirectory()) {

            File[] diretorios = file.listFiles();

            Lists.newArrayList(diretorios).stream().forEach(d -> {
                inOrdem(d, visitor, arg);
            });
        }
    }

    private void inOrdem(File diretorio, VoidVisitorAdapter visitor, Object arg) {

        if (Objeto.notBlank(diretorio)) {

            File[] subDiretorios = diretorio.listFiles();
            if (Objeto.notBlank((Object[]) subDiretorios)) {
                Lists.newArrayList(subDiretorios).stream().forEach(d -> {
                    if (d.isDirectory()) {
                        inOrdem(d, visitor, arg);
                    } else {
                        try {
                            if (d.getAbsolutePath().endsWith(".java")) {
                                parse(d, visitor, arg);
                            }
                        } catch (ArquivoException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
            }
        }
    }

    private void parse(File file, VoidVisitorAdapter visitor, Object arg) throws ArquivoException {

        try {

            parse = JavaParser.parse(file, Utilitarios.CHARSET_UTF_8);
            parse.accept(visitor, arg);

        } catch (FileNotFoundException ex) {
            throw new ArquivoException(ex.getMessage());
        }
    }
}
