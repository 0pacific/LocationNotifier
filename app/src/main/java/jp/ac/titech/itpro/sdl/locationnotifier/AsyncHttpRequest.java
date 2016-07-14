package jp.ac.titech.itpro.sdl.locationnotifier;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

public class AsyncHttpRequest extends AsyncTask<Uri.Builder, Void, String> {

    double lat,lon;
    String mail;

    public void setParams (double lat,double lon,String mail) {
        this.lat = lat;
        this.lon = lon;
        this.mail = mail;
    }

    @SuppressLint("UnlocalizedSms") @Override
    protected String doInBackground(Uri.Builder... params) {
        try {
            MailSender ms = new MailSender();
            ms.send(lat,lon,mail);
        } catch (Exception e) {
            return e.toString();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        Log.d("Mail", "メールを送信しました");
    }
}
