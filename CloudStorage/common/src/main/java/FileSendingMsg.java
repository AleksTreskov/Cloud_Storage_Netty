

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileSendingMsg {
    private String fileName;
    private String path;
    private byte[] data;
    private String login;
    private boolean isDirectory;
    private boolean isEmpty;

    public FileSendingMsg(Path filepath) throws IOException {
        this.path = filepath.toString();
        this.fileName = filepath.getFileName().toString();
        this.data = Files.readAllBytes(filepath);
    }

    public FileSendingMsg(String login, Path path) throws IOException {
        fileName = path.getFileName().toString();
        data = Files.readAllBytes(path);
        this.login = login;
    }

    public FileSendingMsg(String filename, boolean isDirectory, boolean isEmpty) {
        this.fileName = filename;
        this.isDirectory = isDirectory;
        this.isEmpty = isEmpty;
    }

    public String getFilename() {
        return fileName;
    }

    public String getPath() {
        return path;
    }

    public String getLogin() {
        return login;
    }

    public boolean isDirectory() {
        return isDirectory;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public byte[] getData() {
        return data;
    }
}
