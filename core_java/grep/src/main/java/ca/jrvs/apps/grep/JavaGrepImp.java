package ca.jrvs.apps.grep;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavaGrepImp implements JavaGrep {

    private String regex;
    private String rootPath;
    private String outFile;

    public static void main(String[] args) {
        if (args.length != 3) {
            throw new IllegalArgumentException("USAGE: regex rootPath outFile");
        }
        JavaGrepImp javaGrepImp = new JavaGrepImp();
        javaGrepImp.setRegex(args[0]);
        javaGrepImp.setRootPath(args[1]);
        javaGrepImp.setOutFile(args[2]);

        try {
            javaGrepImp.process();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void process() throws IOException {
        List<String> matchedLines = new ArrayList<>();
        for (File file : listFiles(rootPath)) {
            for (String line : readLines(file)) {
                if (containsPattern(line)) {
                    matchedLines.add(line);
                }
            }
        }
        writeToFile(matchedLines);
    }

    @Override
    public List<File> listFiles(String rootDir) {
        File dir = new File(rootDir);
        File[] file = dir.listFiles();
        List<File> lf = new ArrayList<>();
        if (file != null) {
            for (File f : file) {
                if (f.isFile()) {
                    lf.add(f);
                } else {
                    lf.addAll(listFiles(f.getAbsolutePath()));
                }
            }
            return lf;

        } else {
            System.out.println("Directory is empty.");
            return null;
        }
    }

    @Override
    public List<String> readLines(File inputFile) throws IOException {
        List<String> lines = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(inputFile));
            String line = br.readLine();
            while (line != null) {
                lines.add(line);
                line = br.readLine();
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Error: inputFile is not a file.");
        }
        return lines;
    }

    @Override
    public boolean containsPattern(String line) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void writeToFile(List<String> lines) throws IOException {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(outFile));
            for(String line: lines){
                bw.write(line + "\n");
            }
            bw.close();
        } catch (Exception e) {
            System.out.println("Cannot write to file!");
        }
    }

    @Override
    public String getRegex() {
        return regex;
    }

    @Override
    public void setRegex(String regex) {
        this.regex = regex;
    }

    @Override
    public String getRootPath() {
        return rootPath;
    }

    @Override
    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    @Override
    public String getOutFile() {
        return outFile;
    }

    @Override
    public void setOutFile(String outFile) {
        this.outFile = outFile;
    }
}