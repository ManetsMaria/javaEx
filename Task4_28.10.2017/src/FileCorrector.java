import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class FileCorrector {

    static public void changeKeyWords(String pathInputFile, String pathKeyWords) throws IOException {
        if (pathInputFile == null || pathKeyWords == null)
            return;
        List <String> words = Files.readAllLines(Paths.get(pathKeyWords));
        List <String> lines = Files.readAllLines(Paths.get(pathInputFile));
        for (int i = 0; i < lines.size(); i++) {
            StringBuilder stringBuilderLine = new StringBuilder(lines.get(i));
            for (String word: words){
                StringBuilder strB = new StringBuilder();
                strB.append("(?i)(?=([^\"]*\"[^\"]*\")*[^\"]*$)\\b");
                strB.append(word);
                strB.append("\\b");
                Pattern p = Pattern.compile(strB.toString());
                Matcher m = p.matcher(stringBuilderLine);
                int j = 0;
                while(m.find(j)){
                    stringBuilderLine.replace(m.start(), m.end(), word);
                    j = m.end();
                }
            }
            lines.set(i, stringBuilderLine.toString());
        }
        Writer writer = new FileWriter(pathInputFile);
        for (String s: lines){
            writer.write(s);
            writer.write('\n');
            writer.flush();
        }
    }
}
