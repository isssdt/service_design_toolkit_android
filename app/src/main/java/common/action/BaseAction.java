package common.action;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

import common.view.AbstractView;

/**
 * Created by longnguyen on 3/3/17.
 */

public abstract class BaseAction extends AppCompatActivity {
    protected AbstractView abstractView;
    protected int position;

    public BaseAction(AbstractView abstractView, int position) {
        this.abstractView = abstractView;
        this.position = position;
    }

    public BaseAction(AbstractView abstractView) {
        this.abstractView = abstractView;
    }

    public abstract boolean validation(View view);
}
