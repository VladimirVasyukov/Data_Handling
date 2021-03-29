package com.epam.datahandling.utils;

import com.epam.datahandling.lexis.Text;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectOutputStream;

public class TextSerializer {
    private static final Logger LOG = Logger.getLogger(TextSerializer.class);

    public void serializeText(Text text, File file) {
        try (
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)
        ) {
            objectOutputStream.writeObject(text);
            objectOutputStream.flush();
        } catch (FileNotFoundException fileNotFoundException) {
            LOG.debug(String.format("%s %s %s", "Output file", file.getPath(), " not found!"),
                fileNotFoundException);
        } catch (NotSerializableException notSerializableException) {
            LOG.debug("Unexpected NotSerializableException in the serializeText method!", notSerializableException);
        } catch (IOException ioException) {
            LOG.debug("Unexpected IOException in the serializeText method!", ioException);
        }
    }
}
