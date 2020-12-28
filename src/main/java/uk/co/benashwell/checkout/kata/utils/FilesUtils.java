package uk.co.benashwell.checkout.kata.utils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public class FilesUtils {

    private static final Logger LOGGER = Logger.getLogger(FilesUtils.class.getName());

    /**
     * This will get lines from the file with the given name
     * This will currently only look in the resources area
     * @param name File name
     * @return List of stirngs in file
     */
    public static List<String> getLinesFromFileWithName(String name) {
        try {
            return getLinesFromFile(getFileWithName(name));
        } catch (IOException | URISyntaxException | IllegalArgumentException e) {
            LOGGER.warning("Could not find or process the given file name.");
            return Collections.emptyList();
        }
    }

    private static List<String> getLinesFromFile(File file) throws IOException {
        return Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
    }

    private static File getFileWithName(String fileName) throws URISyntaxException {
        ClassLoader classLoader = FilesUtils.class.getClassLoader();
        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return new File(resource.toURI());
        }
    }
}
