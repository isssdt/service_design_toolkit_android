package poc.servicedesigntoolkit.getpost;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import main.controller.MainController;
import main.view.MainView;
import user.dto.SdtUserDTO;


public class MainActivity extends AppCompatActivity {
    private MainController mainController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainController = new MainController(new MainView(this));
    }
}