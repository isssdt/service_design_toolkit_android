package touchpoint.extension.android;

/**
 * Created by Gunjan Pathak on 28-Oct-16.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import poc.servicedesigntoolkit.getpost.R;
import touchpoint.dto.TouchPointFieldResearcherDTO;
import touchpoint.dto.TouchPointFieldResearcherListDTO;
import touchpoint.view.TouchPointHolder;

public class TouchPointListAdapter extends RecyclerView.Adapter<TouchPointHolder> {
    private Context context;
    private TouchPointFieldResearcherListDTO touchPointFieldResearcherListDTO;

    public TouchPointListAdapter(TouchPointFieldResearcherListDTO touchPointFieldResearcherListDTO, Context context) {
        super();
        this.touchPointFieldResearcherListDTO = touchPointFieldResearcherListDTO;
        this.context = context;
    }

    @Override
    public TouchPointHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TouchPointHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.touchpoint_item_recycle, parent, false));
    }

    @Override
    public void onBindViewHolder(TouchPointHolder touchPointHolder, int position) {
        TouchPointFieldResearcherDTO touchPointFieldResearcherDTO = touchPointFieldResearcherListDTO.getTouchPointFieldResearcherDTOList().get(position);
        touchPointHolder.getNameTextView().setText(touchPointFieldResearcherDTO.getTouchpointDTO().getTouchPointDesc());
        touchPointHolder.getChannelTextView().setText(touchPointFieldResearcherDTO.getTouchpointDTO().getChannelDTO().getChannelName());
        touchPointHolder.getStausTextView().setText(touchPointFieldResearcherDTO.getStatus());
    }

    @Override
    public int getItemCount() {
        return touchPointFieldResearcherListDTO.getTouchPointFieldResearcherDTOList().size();
    }

    public TouchPointFieldResearcherListDTO getTouchPointFieldResearcherListDTO() {
        return touchPointFieldResearcherListDTO;
    }

    public void setTouchPointFieldResearcherListDTO(TouchPointFieldResearcherListDTO touchPointFieldResearcherListDTO) {
        this.touchPointFieldResearcherListDTO = touchPointFieldResearcherListDTO;
    }
}