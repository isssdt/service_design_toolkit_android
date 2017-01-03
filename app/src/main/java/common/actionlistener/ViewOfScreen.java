package common.actionlistener;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by longnguyen on 12/31/16.
 */

public abstract class ViewOfScreen {
    private Activity context;

    public ViewOfScreen(Activity context) {
        this.context = context;
    }

    public Activity getContext() {
        return context;
    }

    public abstract void bind(ViewController bindControllerOnView);
}
