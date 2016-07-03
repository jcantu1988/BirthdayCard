package com.jucantu.birthdaycard;

import java.io.Serializable;

/**
 * Created by jcant on 7/2/2016.
 */
public class BirthdayCardNames implements Serializable{

    private String fromName;
    private String toName;

    public BirthdayCardNames(){

        fromName = "";
        toName = "";

    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }
}
