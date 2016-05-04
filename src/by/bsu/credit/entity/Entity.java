package by.bsu.credit.entity;

/**
 * Created by User on 02.05.2016.
 */
public abstract class Entity {
    private int id;

    public Entity() {
    }

    public Entity(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
