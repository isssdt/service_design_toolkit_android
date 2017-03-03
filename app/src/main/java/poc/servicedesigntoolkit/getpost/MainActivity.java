package poc.servicedesigntoolkit.getpost;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import common.action.ActionFactoryProducer;
import common.action.ActionOnBackClick;
import main.view.MainView;


public class MainActivity extends AppCompatActivity {
    private MainView mainView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainView = new MainView(this);
    }
    @Override
    public void onBackPressed() {
        ActionFactoryProducer.getFactory(ActionOnBackClick.class.toString()).initOnBackClickAction(mainView).onBackClick();
    }
}