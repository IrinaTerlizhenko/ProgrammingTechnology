package by.bsu.credit.database;

/**
 * Created by User on 02.05.2016.
 */
public enum Column {
    ID,
    LOGIN,
    PASSWORD,
    ROLE,

    FIRSTNAME,
    LASTNAME,
    PASSPORT,

    CLIENT_ID,
    MONEY,

    REQUEST_ID,
    DOCUMENT,

    RETURNED,
    DUE_DATE,
    INTEREST,
    APPROVED;

    public String column() {
        return this.name().toLowerCase();
    }
}
