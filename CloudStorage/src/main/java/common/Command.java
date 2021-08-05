package common;

public enum Command {
    DOWNLOAD(15791546),
    LIST(31646987);

    private final int command;

    Command(int command) {
        this.command = command;
    }

    public int getCommand() {
        return command;
    }
}
