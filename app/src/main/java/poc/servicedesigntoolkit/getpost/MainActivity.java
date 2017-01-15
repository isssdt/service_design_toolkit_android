package poc.servicedesigntoolkit.getpost;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import main.controller.MainController;
import main.view.MainView;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new MainController(new MainView(this));
    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder exit = new AlertDialog.Builder(
                MainActivity.this);
        exit.setMessage(" Thank you for your participation. See you soon");
        exit.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        exit.setNegativeButton("No", null);
        exit.show();
    }
}