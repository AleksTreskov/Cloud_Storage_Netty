import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;

public class UpdateMsg extends AbstractMsg {
    private HashMap<Integer, LinkedList<File>> cldStrCnt;
    private String login;

    public UpdateMsg(HashMap<Integer, LinkedList<File>> cldStrCnt) {
        this.cldStrCnt = cldStrCnt;
    }

    public UpdateMsg(String login) {
        this.login = login;
    }

    public HashMap<Integer, LinkedList<File>> getCldStrCnt() {
        return cldStrCnt;
    }

    public String getLogin() {
        return login;
    }
}
