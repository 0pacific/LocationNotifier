package jp.ac.titech.itpro.sdl.locationnotifier;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Register> regList = new ArrayList<Register>();


    private LinearLayout rootLayout;
    private TextView nothingNotice;

    private LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rootLayout = (LinearLayout)findViewById(R.id.root);

        nothingNotice = new TextView(this);
        nothingNotice.setText("時間とメールアドレスを登録してください");
        nothingNotice.setTextSize(24f);

        if(regList.size()==0)
            rootLayout.addView(nothingNotice);

        inflater = getLayoutInflater();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addRegister(View v) {
        if(regList.size()==0)
            rootLayout.removeView(nothingNotice);
        else if(regList.size() >= 10) {
            Toast.makeText(this,"登録は10件までです",Toast.LENGTH_LONG).show();
            return;
        }
        LinearLayout ll = (LinearLayout)inflater.inflate(R.layout.register_row,null);
        rootLayout.addView(ll);
        regList.add(new Register());
    }

    public void setTime(View v) {
        TimePickerDialogFragment timePicker = new TimePickerDialogFragment().setButton((Button)v);
        timePicker.show(getSupportFragmentManager(),"timePicker");

    }

    public void register(View v) {

        String[] time;
        int[] timeInt = new int[2];

        LinearLayout ll = (LinearLayout)v.getParent();

        Button bt = (Button)ll.findViewById(R.id.register_time);
        String btText = bt.getText().toString();
        if(btText.equals("時間を設定してください")) {
            Toast.makeText(this,"時間を設定してください",Toast.LENGTH_LONG).show();
            return;
        }
        else {
            time = btText.split(" : ");
            timeInt[0] = Integer.parseInt(time[0]);
            timeInt[1] = Integer.parseInt(time[1]);
        }

        EditText et = (EditText)ll.findViewById(R.id.register_mail);
        String etText = et.getText().toString();

        if(etText.indexOf("@") == -1) {
            Toast.makeText(this,"メールアドレスを入力してください",Toast.LENGTH_LONG).show();
            return;
        }

        int order = getOrder((LinearLayout)v.getParent().getParent());

        fixRow(v,regList.get(order).resetRegister(timeInt[0],timeInt[1],etText),order);

    }

    public void deleteRow(View v) {
        LinearLayout ll = (LinearLayout)v.getParent().getParent();
        regList.remove(getOrder(ll));
        ll.removeAllViews();
        rootLayout.removeView(ll);
    }

    public void changeRow(View v) {
        LinearLayout regll = (LinearLayout)inflater.inflate(R.layout.register_row,null);
        LinearLayout lstll = (LinearLayout)v.getParent().getParent();

        int order = getOrder(lstll);

        Register reg = regList.get(order);
        rootLayout.removeView(lstll);
        rootLayout.addView(regll,order);
        Button timeButton = (Button)regll.findViewById(R.id.register_time);
        timeButton.setText(reg.h + " : " + reg.m);
        EditText mailText = (EditText)regll.findViewById(R.id.register_mail);
        mailText.setText(reg.mail);

    }

    private void fixRow(View v,Register reg,int order) {
        LinearLayout regll = (LinearLayout)v.getParent().getParent();
        LinearLayout lstll = (LinearLayout)inflater.inflate(R.layout.listed_row,null);


        rootLayout.removeView(regll);
        rootLayout.addView(lstll,order);
        TextView timeText = (TextView)lstll.findViewById(R.id.listed_time);
        timeText.setText(reg.h + " : " + reg.m);
        TextView mailText = (TextView)lstll.findViewById(R.id.listed_mail);
        mailText.setText(reg.mail);

    }

    private int getOrder(LinearLayout ll) {
        for(int i=0;i < rootLayout.getChildCount();i++) {
            if(ll == rootLayout.getChildAt(i)) {
                return i;
            }
        }
        return -1;
    }
}
