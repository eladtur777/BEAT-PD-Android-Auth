package com.example.eltur.parkinsonbp.Security;

import static java.lang.String.format;
/**
 * Created by liran on 11/7/17.
 */

public class AuthEnc {

    public String i;
    public String p;

    public AuthEnc(){}

    public AuthEnc(String i, String p) {
        this.i = i;
        this.p = p;
    }

    @Override
    public String toString(){
        return format("i:\"%s\",p:\"%s\"",i,p);
    }

}