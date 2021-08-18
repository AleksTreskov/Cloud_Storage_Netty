
public class RegistrationMsg extends AbstractMsg{

    private final String fio;
    private final String username;
    private final String password;
    private final String nickname;

    public RegistrationMsg(String fio, String username, String password, String nickname) {
        this.fio = fio;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
    }

    public String getFio() {
        return fio;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getNickname() {
        return nickname;
    }
}
