package com.jucantu.birthdaycard;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by jucantu on 7/2/2016.
 */
public class BirthdayCardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_birthdaycard);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            BirthdayCardNames names = (BirthdayCardNames)extras.getSerializable("birthdayCardNames");

            TextView messageView = (TextView) findViewById(R.id.messageTextView);
            TextView toTextView = (TextView) findViewById(R.id.fromNameTextView);


            messageView.append( " " + names.getToName().trim() + "!" );

            toTextView.append( " " + names.getFromName() );
        }


    }
}
