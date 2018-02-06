package com.magma.minemagma;

/**
 * Created by apple on 9/30/16.
 */
import java.io.Serializable;

@SuppressWarnings("serial")

public class pdfParse implements Serializable {

    public pdfParse(String name, String text) {
        this.name = name;
        this.text = text;
    }

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public String gettext() {
        return this.text;
    }

    public void settext(String text) {
        this.text = text;
    }

    private String text;
    private String name;
}

