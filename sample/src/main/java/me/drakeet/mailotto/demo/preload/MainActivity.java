package me.drakeet.mailotto.demo.preload;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import java.lang.ref.WeakReference;
import me.drakeet.mailotto.Mail;
import me.drakeet.mailotto.Mailbox;
import me.drakeet.mailotto.demo.R;

public class MainActivity extends AppCompatActivity {

    private Handler mHandler;


    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHandler = new Handler();
    }


    public void onPreload(final View view) {
        ((TextView)view).append(": loading...");
        mHandler.postDelayed(new InnerRunnable(view), 8 * 1000);
    }


    public void onStartNextPage(View view) {
        startActivity(NextActivity.getIntent(this, /*page = */2));
        Toast.makeText(this, "MainActivity has been destroyed.", Toast.LENGTH_LONG).show();
        this.finish();
    }


    public static class InnerRunnable implements Runnable {

        WeakReference<TextView> textViewPreference;


        InnerRunnable(View textView) {
            this.textViewPreference = new WeakReference<>((TextView) textView);
        }


        @Override public void run() {
            if (textViewPreference.get() != null) {
                textViewPreference.get().append(": done!");
            }
            Mailbox.getInstance().post(new Mail("A mail from MainActivity", TargetActivity.class));
        }
    }
}
