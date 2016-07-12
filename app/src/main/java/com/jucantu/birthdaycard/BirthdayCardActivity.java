package com.jucantu.birthdaycard;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by jucantu on 7/2/2016.
 */
public class BirthdayCardActivity extends AppCompatActivity {

    ImageView cardImage;
    TextView asciiImageTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_birthdaycard);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            //get the names from previous activity
            BirthdayCardNames names = (BirthdayCardNames)extras.getSerializable("birthdayCardNames");

            //get the "message" and "to" TextViews and append the names typed by the user
            TextView messageView = (TextView) findViewById(R.id.messageTextView);
            messageView.append( " " + names.getToName().trim() + "!" );
            TextView toTextView = (TextView) findViewById(R.id.fromNameTextView);
            toTextView.append( " " + names.getFromName().trim() );


            cardImage = (ImageView) findViewById(R.id.cardImage);
            asciiImageTextView = (TextView) findViewById(R.id.androidCakeASCII);

            cardImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("MEASUREMENTS", "Width: "+cardImage.getDrawable().getIntrinsicWidth() +" HEIGHT: "+cardImage.getDrawable().getIntrinsicHeight());
                    Log.d("MEASUREMENTS", "Width: "+asciiImageTextView.getWidth() +" HEIGHT: "+asciiImageTextView.getHeight());
                    if(asciiImageTextView.getText() == "" ) {

                        //get the card image that is displayed
                        BitmapDrawable drawable = (BitmapDrawable) cardImage.getDrawable();

                        //get the width of a text character in the aciiImageTextView
                        float textWidth = asciiImageTextView.getPaint().measureText("_");

                        Rect bounds = new Rect();
                        asciiImageTextView.getPaint().getTextBounds("_#8&o:*. ", 0, 9, bounds);
                        float textHeight = asciiImageTextView.getPaint().getTextSize();//bounds.height();
                        float boundsHeight = bounds.bottom+ bounds.height();
                        Log.d("TEXT_SIZE", "Width: "+textWidth +" HEIGHT: "+textHeight +" BOUNDS: "+boundsHeight);

                        //set a scaled width and height so that all the characters
                        //of the ascii image text fit in the screen. (Each pixle in image will represent a character)
                        int scaled_width = (int) (asciiImageTextView.getWidth() / textWidth);
                        int scaled_height = (int) (scaled_width*((float)asciiImageTextView.getHeight() /(float)asciiImageTextView.getWidth()))/2;//(asciiImageTextView.getHeight() / boundsHeight)/2;//

                        //create a bitmap from the drawable image and
                        //convert it to ascii text
                        Bitmap bitmap = drawable.getBitmap();
                        bitmap = Bitmap.createScaledBitmap(bitmap, scaled_width, scaled_height, true);
                        convert(bitmap);

                    }else{
                        cardImage.setVisibility(View.INVISIBLE);
                        asciiImageTextView.setVisibility(View.VISIBLE);
                    }
                }
            });

            asciiImageTextView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    asciiImageTextView.setVisibility(View.INVISIBLE);
                    cardImage.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    /**
     *
     * @param image The bitmap image being converted to ascii text.
     */
    public void convert(final Bitmap image) {

        //Progress dialog to be displayed depending on how long it takes
        //to build a string from Bitmap image
        final ProgressDialog progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.show();

        final StringBuilder sb = new StringBuilder((image.getWidth() + 1) * image.getHeight());

        new AsyncTask<Void, Void, Void>()
        {
            @Override
            protected Void doInBackground(Void... params) {
                //Get the ascii value for each pixle in the image
                for (int y = 0; y < image.getHeight(); y++) {
                    if (sb.length() != 0) {
                        sb.append("\n");
                    }
                    for (int x = 0; x < image.getWidth(); x++) {

                        //get the RGB values
                        int red = Color.red(image.getPixel(x, y));
                        int green = Color.green(image.getPixel(x, y));
                        int blue = Color.blue(image.getPixel(x, y));

                        //convert RGV values to greyscale value
                        double gValue = (double) red * 0.2999 + (double) green * 0.587 + (double) blue * 0.114;

                        final char s = getAsciiCharFromGrayscale(gValue);

                        sb.append(s);
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result)
            {
                asciiImageTextView.setText(sb.toString());
                cardImage.setVisibility(View.INVISIBLE);
                asciiImageTextView.setVisibility(View.VISIBLE);
                progress.dismiss();
            }
        }.execute();
    }

    /**
     *
     * @param g the grayscale value
     * @return ascii character for the grayscale value
     */
    private char getAsciiCharFromGrayscale(double g)
    {
        final char str;

        if (g >= 230.0) {
            str = '_';
        } else if (g >= 200.0) {
            str = '#';
        } else if (g >= 180.0) {
            str = '8';
        } else if (g >= 160.0) {
            str = '&';
        } else if (g >= 130.0) {
            str = 'o';
        } else if (g >= 100.0) {
            str = ':';
        } else if (g >= 70.0) {
            str = '*';
        } else if (g >= 50.0) {
            str = '.';
        } else {
            str = ' ';
        }
        return str;
    }
}