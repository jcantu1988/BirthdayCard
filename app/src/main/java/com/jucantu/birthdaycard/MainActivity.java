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

                //set the names
                names.setFromName(((EditText)findViewById(R.id.fromEditText)).getText().toString());
                names.setToName(((EditText)findViewById(R.id.toEditTextView)).getText().toString());

                //get the error message textviews
                TextView fromError = ((TextView) findViewById(R.id.fromErrorTextView));
                TextView toError = ((TextView) findViewById(R.id.toErrorTextView));

                //set/reset to invisible
                fromError.setVisibility(View.INVISIBLE);
                toError.setVisibility(View.INVISIBLE);

                //set visible the "From" error message if user didn't type name
                if(names.getFromName().trim().equals("")){
                    fromError.setVisibility(View.VISIBLE);
                }

                //set visible the "To" error message if user didn't type name
                if(names.getToName().trim().equals("")){
                    toError.setVisibility(View.VISIBLE);
                }

                //go to BirthdayCardActivity if user typed both names
                if(toError.getVisibility() != View.VISIBLE && fromError.getVisibility() != View.VISIBLE) {
                    Intent i = new Intent(getApplicationContext(), BirthdayCardActivity.class);

                    //set the info that is going to be used in the other activity
                    i.putExtra("birthdayCardNames", names);

                    startActivity(i);
                }
            }
        });
    }
}
