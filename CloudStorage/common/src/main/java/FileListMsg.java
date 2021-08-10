

import java.io.File;
import java.util.List;

public class FileListMsg extends AbstractMsg {
    private List<File>  fileListMsg;

    public FileListMsg(List<File> fileListMsg) {
        this.fileListMsg = fileListMsg;
    }

    public List<File> getFileListMsg() {
        return fileListMsg;
    }
}
