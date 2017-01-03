package main.view;

import android.app.Activity;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import common.actionlistener.ViewController;
import common.actionlistener.ViewOfScreen;
import common.constants.ConstantValues;
import journey.dto.JourneyFieldResearcherDTO;
import main.controller.MainController;
import poc.servicedesigntoolkit.getpost.R;
import user.dto.FieldResearcherDTO;
import user.dto.SdtUserDTO;

/**
 * Created by longnguyen on 12/30/16.
 */

public class MainView extends ViewOfScreen {
    private Button register;
    private TextView textView;
    private EditText username;

    public MainView(Activity context) {
        super(context);
        register = (Button) context.findViewById(R.id.researchList);
        textView = (TextView) context.findViewById(R.id.textView);
        username = (EditText) context.findViewById(R.id.username);
    }

    public Button getRegister() {
        return register;
    }

    public TextView getTextView() {
        return textView;
    }

    public EditText getUsername() {
        return username;
    }

    @Override
    public void bind(ViewController viewActionListener) {
        register.setOnClickListener((MainController)viewActionListener);
    }
}
