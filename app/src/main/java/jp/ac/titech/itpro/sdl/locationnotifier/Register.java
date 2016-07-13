package jp.ac.titech.itpro.sdl.locationnotifier;

import android.util.Log;

import java.util.Date;

public class Register {

    int h,m;
    String mail;

    public Register () {
        this.h = -1;
        this.m = -1;
        this.mail = "null";
    }

    public Register resetRegister(int h,int m,String mail) {
        this.h = h;
        this.m = m;
        this.mail = mail;
        Log.d("Reg","Registered" + h + ":" + m + ":" + mail);
        return this;
    }
}
