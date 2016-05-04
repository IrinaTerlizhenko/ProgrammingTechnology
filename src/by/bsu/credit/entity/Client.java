package by.bsu.credit.entity;

/**
 * Created by User on 02.05.2016.
 */
public class Client extends Entity {
    private String firstname;
    private String lastname;
    private String passport;

    public Client() {
    }

    public Client(int id, String firstname, String lastname, String passport) {
        super(id);
        this.firstname = firstname;
        this.lastname = lastname;
        this.passport = passport;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }
}
