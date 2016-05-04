package by.bsu.credit.entity;

import java.sql.Date;

/**
 * Created by User on 02.05.2016.
 */
public class Operation extends Entity {
    private int clientId;
    private boolean returned;
    private int money;
    private Date dueDate;
    private int interest;
    private boolean approved;

    public Operation() {
    }

    public Operation(int id, int clientId, boolean returned, int money, Date dueDate, int interest, boolean approved) {
        super(id);
        this.clientId = clientId;
        this.returned = returned;
        this.money = money;
        this.dueDate = dueDate;
        this.interest = interest;
        this.approved = approved;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public boolean isReturned() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public int getInterest() {
        return interest;
    }

    public void setInterest(int interest) {
        this.interest = interest;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }
}
