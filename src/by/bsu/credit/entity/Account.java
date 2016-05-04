package by.bsu.credit.entity;

/**
 * Created by User on 03.05.2016.
 */
public class Account extends Entity {
    private int clientId;
    private int money;

    public Account() {
    }

    public Account(int id, int clientId, int money) {
        super(id);
        this.clientId = clientId;
        this.money = money;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}
