package com.example.loginapp;

class Credentials {
    private String admin;
    private String adminpas;
    Credentials(String admin, String adminpas)
    {
        this.admin=admin;
        this.adminpas=adminpas;
    }
    public String getAdmin() {
        return admin;
    }

    public String getAdminpas() {
        return adminpas;
    }
}
