package studentfiles;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Util {


    static HashMap<String, ExamTestAnswers> getAllAnswers(Path kelias) {
        HashMap<String, ExamTestAnswers> mas = new HashMap<>();
        try {
            if (Files.isDirectory(kelias)) {

                List<String> collect = Files.walk(Paths.get(String.valueOf(kelias)))
                        .filter(Files::isRegularFile)
                        .map(path -> path.getFileName().toString())
                        .collect(Collectors.toList());
                for (String s : collect
                ) {
                    Path isKur = kelias.resolve(s);
                    System.out.println(isKur);
                    ExamTestAnswers ats = new ExamTestAnswers();
                    ats .getFromFile(isKur);

                    mas.put(ats.getExamID(),ats);
                }
                //  return mas;
            } else {
                System.out.println("Neteisingai nurodytas atsakymu aplankas");
                System.exit(-1);
            }
        } catch (IOException e) {
            System.out.println("blogai");
        }
        return mas;
    }
    static int getRezult(StudensExamAnswers studentans, ExamTestAnswers trueAnswers) {
        int result = 0;
        int kiekKlausimu = studentans.getAts().length;
        for (int i = 0; i < kiekKlausimu; i++)
            if (studentans.getAts()[i].equals(trueAnswers.getAts()[i])) result++;

        return result;
    }
}
