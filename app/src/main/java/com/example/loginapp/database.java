package com.example.loginapp;

public class database {
   public String username,mail_id,phone_no,age;
    public database(){

    }
    public database(String username, String mail_id, String phone_no, String age) {
        this.username = username;
        this.mail_id = mail_id;
        this.phone_no = phone_no;
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMail_id() {
        return mail_id;
    }

    public void setMail_id(String mail_id) {
        this.mail_id = mail_id;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
