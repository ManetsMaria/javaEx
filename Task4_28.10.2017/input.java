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

static
public void changeKeyWords(String pathInputFile, String pathKeyWordsthrows IOException {
       if (pathInputFile == null || pathKeyWords == null)
            return;
	String FORName = "ForName FoR fghg clASS" class;
        List <String> wordsnew = Files.readAllLines(Paths.get(pathKeyWords));
        List <String> lines = Files.readAllLines(Paths.get(pathInputFile));
       // boolean commentsFlag = false;
       for int i = 0; i < lines.size(); i++) {
            List integer> stringsIndexes =new ArrayList<>();
            Pattern pattern = Pattern.compile("\"");
            Matcher matcher = pattern.matcher(lines.get(i));
           int index = 0;
           while(matcher.find(index)){
                stringsIndexes.add(matcher.start());
                index = matcher.end();
            }
            //System.out.println(stringsIndexes);
            StringBuilder stringBuilderLine =new StringBuilder(lines.get(i));
            //System.out.println(stringBuilderLine);
           for (String word: words){
                StringBuilder strB =new StringBuilder();
                strB.append("(?i)(\\W|^)(");
                strB.append(word);
                strB.append(")(\\W|$)");
                Pattern p = Pattern.compile(strB.toString());
                Matcher m = p.matcher(stringBuilderLine);
               int j = 0;
               int indexBrack = 0;
               while(m.find(j)){
                   if (indexBrack < stringsIndexes.size() && stringsIndexes.get(indexBrack) < m.start()){
                       j = Math.max(stringsIndexes.get(indexBrack + 1), m.end() - 1);
                       indexBrack += 2;
                      continue;
                    }
                    System.out.println(m.start()+" "+ m.end() +" "+word.length());
                   if (m.end() - m.start() == word.length()) {
                        stringBuilderLine.replace(m.start(), m.end(), word);
                        j = m.end();
                        //System.out.println(stringBuilderLine.subSequence(m.start(),m.end()));
                    }
                   elseif (m.end() - m.start() - 2 == word.length()){
                        stringBuilderLine.replace(m.start() + 1, m.end() - 1, word);
                        j = m.end() - 1;
                    }
                   elseif (String.valueOf(stringBuilderLine.charAt(0)).matches("(?i)"+word.substring(0,1))){
                        stringBuilderLine.replace(m.start(), m.end() - 1, word);
                        j = m.end() - 1;
                    }
                   else{
                        stringBuilderLine.replace(m.start() + 1, m.end(), word);
                        j = m.end();
                    }
                }
            }
            //System.out.println(stringBuilderLine);
            lines.set(i, stringBuilderLine.toString());
        }
        Writer writer =new FileWriter(pathInputFile);
       for (String s: lines){
            writer.write(s);
            writer.write('\n');
            writer.flush();
        }
    }
}

