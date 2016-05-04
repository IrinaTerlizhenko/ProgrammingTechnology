package by.bsu.credit.entity;

/**
 * Created by User on 02.05.2016.
 */
public class User extends Entity {
    private String login;
    private String password;
    private int role;

    public User() {
    }

    public User(int id, String login, String password, int role) {
        super(id);
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (role != user.role) return false;
        if (!login.equals(user.login)) return false;
        return password.equals(user.password);

    }

    @Override
    public int hashCode() {
        int result = login.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + role;
        return result;
    }
}
