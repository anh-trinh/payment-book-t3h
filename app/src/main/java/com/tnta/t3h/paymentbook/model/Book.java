package com.tnta.t3h.paymentbook.model;

/**
 * Created by edunetjsc on 7/9/17.
 */

public class Book {

    int id;
    String name;
    int kind_id;
    int money;
    String date;
    String note;
    int book_type;
    int img;

    public Book() {
    }

    public Book(int id, String name, int kind_id, int money, String date, String note, int book_type, int img) {
        this.id = id;
        this.name = name;
        this.kind_id = kind_id;
        this.money = money;
        this.date = date;
        this.note = note;
        this.book_type = book_type;
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getKind_id() {
        return kind_id;
    }

    public void setKind_id(int chi_kind_id) {
        this.kind_id = chi_kind_id;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getBook_type() {
        return book_type;
    }

    public void setBook_type(int book_type) {
        this.book_type = book_type;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
