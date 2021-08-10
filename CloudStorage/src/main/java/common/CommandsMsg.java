package common;

public class CommandsMsg {
    private int command;

    private Object[] object;

    public CommandsMsg(int command, Object... objects) {
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
