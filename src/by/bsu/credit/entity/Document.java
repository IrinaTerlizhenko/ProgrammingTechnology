package by.bsu.credit.entity;

/**
 * Created by User on 02.05.2016.
 */
public class Document extends Entity {
    private int requestId;
    private String document;

    public Document() {
    }

    public Document(int id, int requestId, String document) {
        super(id);
        this.requestId = requestId;
        this.document = document;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }
}
