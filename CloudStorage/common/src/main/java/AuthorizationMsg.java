
public class AuthorizationMsg extends AbstractMsg{
    private String login;
    private String password;
    private String nickname;

    public AuthorizationMsg(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public AuthorizationMsg(String nickname) {
        this.nickname = nickname;
    }


    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getNickname() {
        return nickname;
    }

}
