package utilitsTest;

import org.junit.jupiter.api.Test;
import utilits.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UtilFilesTest {
    //tikrinam ar teisingai nuskaito aplanko failu sarasa
    @Test
    public void getFileList_test() {
        Path kel = Paths.get("src\\main\\resources");
        List<String> failai = UtilFiles.getFileList(kel);
        assertEquals(5, failai.size());
        assertEquals(true, failai.contains("egzas1.json"));
        assertEquals(true, failai.contains("egzas2.json"));
    }
}
