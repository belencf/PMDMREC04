package com.belen.proyecto;

public class Usuario {
    private String user;
    private String pass;
    private String nombre;
    private String apellidos;
    private String mail;

    public Usuario(String user,String pass,String nombre,String apellidos,String mail){
        this.user=user;
        this.pass=pass;
        this.nombre=nombre;
        this.apellidos=apellidos;
        this.mail=mail;
    }
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
