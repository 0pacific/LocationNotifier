package jp.ac.titech.itpro.sdl.locationnotifier;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

/**
 * Created by admin on 16/07/13.
 */
public class Notifier extends BroadcastReceiver{

    public void onReceive (Context context, Intent intent) {

        Uri.Builder builder = new Uri.Builder();
        AsyncHttpRequest task = new AsyncHttpRequest();
        task.setParams(1,1,(String)intent.getSerializableExtra("mail"));
        task.execute(builder);
        Log.d("Notifier",(String)intent.getSerializableExtra("mail"));

    }
}
