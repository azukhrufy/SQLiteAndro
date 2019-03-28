package com.example.gita.listview;

//Membuat Encapsulasi dari Object Object yang terlibat

import android.graphics.drawable.Drawable;

public class People {
    private int id;
    private String name;
    private String desc;
    private int img;


    public People(String name, String desc, int img) {
        this.name = name;
        this.desc = desc;
        this.img = img;
    }


    public People() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}


