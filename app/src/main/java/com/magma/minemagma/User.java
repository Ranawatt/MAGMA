package com.magma.minemagma;

/**
 * Created by apple on 9/22/16.
 */
public class User {

    int id;
    String name;
    String pdfText;

    public User(int id, String name, String pdfText) {
        this.id = id;
        this.name = name;
        this.pdfText = pdfText;
    }

    public User() {

    }
    public User( String name, String pdfText) {
        this.name = name;
        this.pdfText = pdfText;
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

    public String getPdfText(){
        return this.pdfText;
    }

    public void setPdfText(String pdfText){
        this.pdfText = pdfText;
    }
}


