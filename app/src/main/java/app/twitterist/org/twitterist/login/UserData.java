package app.twitterist.org.twitterist.login;

/**
 * Created by marcowuthrich on 09.09.15.
 */
public class UserData {

    private String userID;
    private String username;
    private boolean login;

    //Default Construktor
    public UserData() {
    }

    public UserData(String userID, String username, boolean login) {
        this.userID = userID;
        this.username = username;
        this.login = login;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isLogin() {
        return login;
    }

    public void setLogin(boolean login) {
        this.login = login;
    }
}
