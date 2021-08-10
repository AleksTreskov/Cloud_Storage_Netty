package common;

import java.util.List;

public class FileListMsg extends AbstractMsg {
    List<String>  fileListMsg;

    public FileListMsg(List<String> fileListMsg) {
        this.fileListMsg = fileListMsg;
    }

    public List<String> getFileListMsg() {
        return fileListMsg;
    }
}
