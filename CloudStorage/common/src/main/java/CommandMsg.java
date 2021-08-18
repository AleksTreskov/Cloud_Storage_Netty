public class CommandMsg extends AbstractMsg {
    private int command;
    public static final int LIST_FILES = 1;
    public static final int DOWNLOAD_FILE =2;
    public static final int DELETE = 3;
    public static final int AUTH_OK = 4;
    public static final int CREATE_DIR = 5;
    //public static final int DELETE_DIR = 562129356;

    private Object[] object;

    public CommandMsg(int command, Object... objects) {
        this.command = command;
        this.object = objects;
    }

    public int getCommand() {
        return command;
    }

    public Object[] getObject() {
        return object;
    }
}
