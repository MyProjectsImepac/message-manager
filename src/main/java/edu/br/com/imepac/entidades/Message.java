package edu.br.com.imepac.entidades;

public class Message {
    private long id;
    private Contact contactReceiver;
    private Contact contactSender;
    private String message;

    public Message() {
    }

    public Message(long id, Contact contactReceiver, Contact contactSender, String message) {
        this.id = id;
        this.contactReceiver = contactReceiver;
        this.contactSender = contactSender;
        this.message = message;
    }

    public Message(Contact contactReceiver, Contact contactSender, String message) {
        this.contactReceiver = contactReceiver;
        this.contactSender = contactSender;
        this.message = message;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Contact getContactReceiver() {
        return contactReceiver;
    }

    public void setContactReceiver(Contact contactReceiver) {
        this.contactReceiver = contactReceiver;
    }

    public Contact getContactSender() {
        return contactSender;
    }

    public void setContactSender(Contact contactSender) {
        this.contactSender = contactSender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
