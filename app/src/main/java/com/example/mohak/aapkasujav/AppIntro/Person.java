package com.example.mohak.aapkasujav.AppIntro;

/**
 * Created by mohak on 22/8/15.
 */
public class Person {


    private String sex;
    private String name;
    private String uid;



    public String getSex() {
        return sex;
    }

    public void setSex(String country) {
        this.sex = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String country) {
        this.name = country;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String country) {
        this.uid = country;
    }


    @Override
    public String toString() {
        return "Person [name:" + name + ",uid :" + uid + ",sex:" + sex + "]";
    }
}
