package ru.job4j.cinema.model;

import java.util.Objects;

public class Ticket {

    private int id;
    private Session session;
    private int row;
    private int cell;
    private int userId;

    public Ticket() {
    }

    public Ticket(Session session, int row, int cell, int userId) {
        this(0, session, row, cell, userId);
    }

    public Ticket(int id, Session session, int row, int cell, int userId) {
        this.id = id;
        this.session = session;
        this.row = row;
        this.cell = cell;
        this.userId = userId;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCell() {
        return cell;
    }

    public void setCell(int cell) {
        this.cell = cell;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ticket)) {
            return false;
        }
        Ticket ticket = (Ticket) o;
        return id == ticket.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Ticket{");
        sb.append("id=").append(id);
        sb.append(", session=").append(session);
        sb.append(", row=").append(row);
        sb.append(", cell=").append(cell);
        sb.append(", userId=").append(userId);
        sb.append('}');
        return sb.toString();
    }
}
