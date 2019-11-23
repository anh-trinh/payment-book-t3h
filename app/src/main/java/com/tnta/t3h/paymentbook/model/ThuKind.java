package com.tnta.t3h.paymentbook.model;

/**
 * Created by edunetjsc on 7/9/17.
 */

public class ThuKind {

    int id;
    String name;
    int img;

    public ThuKind() {
    }

    public ThuKind(int id, String name, int img) {
        this.id = id;
        this.name = name;
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

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return name;
    }
}
