package jp.ac.titech.itpro.sdl.locationnotifier;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class Register implements Serializable{

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

    public long nextRegisteredTime () {
        Calendar cal = Calendar.getInstance();
//        if(cal.get(Calendar.HOUR_OF_DAY) < h || cal.get(Calendar.HOUR_OF_DAY) == h && cal.get(Calendar.MINUTE) < m) {
//            cal.set(Calendar.HOUR_OF_DAY,h);
//            cal.set(Calendar.MINUTE,m);
//            cal.set(Calendar.SECOND,0);
//        }
//        else {
//            cal.add(Calendar.DATE,1);
//            cal.set(Calendar.HOUR_OF_DAY,h);
//            cal.set(Calendar.MINUTE,m);
//            cal.set(Calendar.SECOND,0);
//        }
//        return cal.getTimeInMillis();

        cal.add(Calendar.SECOND,10);
        return cal.getTimeInMillis();

    }
}
