package studentfiles;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UtilFilesTest {
    @Test
    public void getFileList_test (){
        Path kel= Paths.get("C:\\Users\\IrmaJ\\ProjektasStudentai\\StudentuTestai\\Duomenys\\Answers");
        List<String> failai = UtilFiles.getFileList(kel);
        assertEquals (2, failai.size());
        assertEquals (true, failai.contains("egzas1.json"));
        assertEquals (true, failai.contains("egzas2.json"));
    }
}
