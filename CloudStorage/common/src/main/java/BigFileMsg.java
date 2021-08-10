public class BigFileMsg extends AbstractMsg {
    private byte[] data;
    private int portions;
    private int currentPortion;
    private String fileName;
    private String login;
    public BigFileMsg(String login,String fileName, byte[] bytes, int currentPortion, int portions){
        this.login = login;
        this.fileName = fileName;
        this.data = bytes;
        this.currentPortion = currentPortion;
        this.portions = portions;

    }

    public String getLogin() {
        return login;
    }

    public byte[] getData() {
        return data;
    }

    public String getFileName() {
        return fileName;
    }

    public int getPortions() {
        return portions;
    }

    public int getCurrentPortion() {
        return currentPortion;
    }
}
