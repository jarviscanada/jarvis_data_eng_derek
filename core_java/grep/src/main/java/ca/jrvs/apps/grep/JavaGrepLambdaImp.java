package ca.jrvs.apps.grep;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JavaGrepLambdaImp extends JavaGrepImp {
    public static void main(String[] args) {
        if (args.length != 3) {
            throw new IllegalArgumentException("USAGE: regex rootPath outFile");
        }

        JavaGrepLambdaImp javaGrepLambdaImp = new JavaGrepLambdaImp();
        javaGrepLambdaImp.setRegex(args[0]);
        javaGrepLambdaImp.setRootPath(args[1]);
        javaGrepLambdaImp.setOutFile(args[2]);

        try {
            javaGrepLambdaImp.process();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<File> listFiles(String rootDir) throws IOException {
        List<File> lf = new ArrayList<>();
        try {
            lf = Files.walk(Paths.get(rootDir)).filter(Files::isRegularFile)
                    .map(x -> x.toFile()).collect(Collectors.toList());

        } catch (IOException e) {
            System.out.println("Error: directory is empty.");;
        }
        return lf;
    }

    @Override
    public List<String> readLines(File inputFile) throws IOException {
        List<String> lines = new ArrayList<>();
        try {
            lines = Files.readAllLines(Paths.get(String.valueOf(inputFile)));
        } catch (IOException ex) {
            System.out.println("Error: inputFile is not a file.");
        }
        return lines;
    }
}
