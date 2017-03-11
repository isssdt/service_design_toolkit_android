package touchpoint.action;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.AdapterView;

import com.google.gson.Gson;

import common.action.BaseAction;
import common.utils.Utils;
import common.view.AbstractView;
import touchpoint.activity.TouchPointDetailsActivity;
import touchpoint.dto.TouchPointFieldResearcherDTO;

/**
 * Created by longnguyen on 3/4/17.
 */

public class ACTION_LIST_VIEW_TOUCH_POINT_LIST extends BaseAction implements AdapterView.OnItemClickListener {
    public ACTION_LIST_VIEW_TOUCH_POINT_LIST(AbstractView abstractView) {
        super(abstractView);
    }

    @Override
    public boolean validation(View view) {
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        TouchPointFieldResearcherDTO touchPointFieldResearcherDTO = (TouchPointFieldResearcherDTO) adapterView.getItemAtPosition(position);

        Context context = abstractView.getContext();
        SharedPreferences sharedPref = context.getSharedPreferences("Trial", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = gson.toJson(touchPointFieldResearcherDTO);
        //SharedPreferences.Editor editor = sharedPref.edit();
        //editor.putString("TouchPointFieldResearcherDTO",json);
        //editor.commit();

        Utils.forwardToScreen(abstractView.getContext(), TouchPointDetailsActivity.class, touchPointFieldResearcherDTO.getClass().toString(), touchPointFieldResearcherDTO);
    }
}
