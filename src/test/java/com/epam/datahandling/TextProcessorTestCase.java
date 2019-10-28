package com.epam.datahandling;

import com.epam.datahandling.lexis.Sentence;
import com.epam.datahandling.lexis.Text;
import com.epam.datahandling.lexis.Word;
import com.epam.datahandling.utils.BackupUtils;
import org.hamcrest.collection.IsArrayContainingInOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.*;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class TextProcessorTestCase {

    @Test
    public void testParse() throws IOException {
        String input = "Test";

        File src = TestUtils.prepareSource(input);

        TextProcessor processor = new TextProcessor();
        Text result = processor.parse(src);

        assertEquals("Method TextProcessor.parse return wrong result", input, result.getContent());
    }

    @Test
    public void testParseText() {
        String input = "Test. Wow! How?";
        Sentence[] expectedSentences = new Sentence[] {
                new Sentence("Test."),  new Sentence("Wow!"),  new Sentence("How?")
        };

        Text text = new Text(input);

        assertEquals("Method Text.getContent return wrong result", input, text.getContent());
        assertThat("Text was parsed in wrong way", text.getSentences(), IsArrayContainingInOrder.arrayContaining(expectedSentences));
    }

    @Test
    public void testParseSentence() {
        String input = "Hello, world!";
        Word[] expectedWords = new Word[] {
                new Word("Hello"),  new Word("world")
        };

        Sentence sentence = new Sentence(input);

        assertEquals("Method Sentence.getContent return wrong result", input, sentence.getContent());
        assertThat("Sentence was parsed in wrong way", sentence.getWords(), IsArrayContainingInOrder.arrayContaining(expectedWords));
    }

    @Test
    public void testBackup() throws IOException {
        String input = "Test";

        File src = TestUtils.prepareSource(input);
        File dest = TestUtils.prepareDestination();

        BackupUtils.backup(src.getAbsolutePath(), dest.getAbsolutePath());

        String destContent = TestUtils.readFromFile(dest);
        assertEquals("Backup content should be the same as original one", input, destContent);
    }

    @Test
    public void testBackupShouldCreateNotExistingDestFile() throws IOException {
        String input = "Test";
        String destPath = "non_existing_directory/non_existing_path_to_dest.bak";

        File src = TestUtils.prepareSource(input);

        BackupUtils.backup(src.getAbsolutePath(), destPath);

        File createdDestFile = new File(destPath);
        createdDestFile.deleteOnExit();

        assertTrue("Backup process should create backup file if it doesn't exist", createdDestFile.exists());
    }

    @Test
    public void testBackupShouldRecreateDestIfExists() throws IOException {
        String input = "Test";

        File src = TestUtils.prepareSource(input);
        File dest = TestUtils.prepareSource(input);

        BackupUtils.backup(src.getAbsolutePath(), dest.getAbsolutePath());

        String destContent = TestUtils.readFromFile(dest);
        assertEquals("Backup process should override existing backup file", input, destContent);
    }

    @Test(expected = FileNotFoundException.class)
    public void testBackupShouldThrowIOExceptionIfSourceFileNotExist() throws IOException {
        File dest = TestUtils.prepareDestination();
        BackupUtils.backup("non_existing_path_to_src", dest.getAbsolutePath());
    }

}
