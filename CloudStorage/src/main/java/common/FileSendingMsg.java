package common;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileSendingMsg {
    private String filename;
    private String path;
    private byte[] data;

    public FileSendingMsg(Path filepath) throws IOException {
        this.path = filepath.toString();
        this.filename = filepath.getFileName().toString();
        this.data = Files.readAllBytes(filepath);
    }

    public String getFilename() {
        return filename;
    }

    public String getPath() {
        return path;
    }

    public byte[] getData() {
        return data;
    }
}
