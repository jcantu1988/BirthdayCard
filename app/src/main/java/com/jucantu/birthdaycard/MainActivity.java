package com.jucantu.birthdaycard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button generateCardButton;
    private BirthdayCardNames names;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        generateCardButton = (Button) findViewById(R.id.generateCardButton);

        generateCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                names = new BirthdayCardNames();

                names.setFromName(((EditText)findViewById(R.id.fromEditText)).getText().toString());
                names.setToName(((EditText)findViewById(R.id.toEditTextView)).getText().toString());

                TextView fromError = ((TextView) findViewById(R.id.fromErrorTextView));
                fromError.setVisibility(View.INVISIBLE);

                TextView toError = ((TextView) findViewById(R.id.toErrorTextView));
                toError.setVisibility(View.INVISIBLE);

                if(names.getFromName().trim().equals("")){
                    fromError.setVisibility(View.VISIBLE);
                }

                if(names.getToName().trim().equals("")){
                    toError.setVisibility(View.VISIBLE);
                }

                if(toError.getVisibility() != View.VISIBLE && fromError.getVisibility() != View.VISIBLE) {
                    Intent i = new Intent(getApplicationContext(), BirthdayCardActivity.class);
                    i.putExtra("birthdayCardNames", names);
                    startActivity(i);
                }
            }
        });
    }
}
