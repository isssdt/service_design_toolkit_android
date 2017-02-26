package photo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.widget.ImageView;

import java.io.File;

import poc.servicedesigntoolkit.getpost.R;

/**
 * Created by dingyi on 6/2/17.
 */

public class UploadPhoto extends AppCompatActivity {

    ImageView imageView;
    String image;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showphoto);

        imageView = (ImageView) findViewById(R.id.imageView2);

        Bundle extras = getIntent().getExtras();
        image = (String) extras.get("image");

        File imgFile = new File(image);
        if(imgFile.exists()){

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            imageView.setImageBitmap(myBitmap);

        }
        //Bitmap bitmap = StringToBitMap(image);
        //imageView.setImageBitmap(bitmap);


    }

    public Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte= Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }
}
