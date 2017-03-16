package journey.aux_android;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import common.action.ActionFactoryProducer;
import common.action.RecycleViewOnItemClick;
import common.constants.ConstantValues;
import common.view.AbstractView;
import journey.dto.JourneyDTO;
import journey.dto.JourneyFieldResearcherDTO;
import poc.servicedesigntoolkit.getpost.R;

/**
 * Created by longnguyen on 3/3/17.
 */

public class JourneyRecycleAdapter extends RecyclerView.Adapter<JourneyViewHolder> {
    private List<JourneyDTO> journeyDTOList;
    private AbstractView abstractView;

    public JourneyRecycleAdapter(List<JourneyDTO> journeyDTOList, AbstractView abstractView) {
        this.journeyDTOList = journeyDTOList;
        this.abstractView = abstractView;
    }

    @Override
    public JourneyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new JourneyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.journey_recycle_item, parent, false));
    }

    @Override
    public void onBindViewHolder(JourneyViewHolder holder, final int position) {
        JourneyDTO journeyDTO = journeyDTOList.get(position);
        holder.getJourneyTextView().setText(journeyDTO.getJourneyName());
        if (null != journeyDTO.getJourneyFieldResearcherListDTO() &&
                ConstantValues.OTHERS_STATUS_DONE.equals(journeyDTO.getJourneyFieldResearcherListDTO().getJourneyFieldResearcherDTOList().get(0).getStatus())) {
            holder.getJourneyButton().setText(ConstantValues.COMPONENT_JOURNEY_LIST_VIEW_BUTTON_LABEL_VIEW);
            Bundle extras = abstractView.getContext().getIntent().getExtras();
            JourneyFieldResearcherDTO journeyFieldResearcherDTO = (JourneyFieldResearcherDTO) extras.get(JourneyFieldResearcherDTO.class.toString());
            journeyDTO.getJourneyFieldResearcherListDTO().getJourneyFieldResearcherDTOList().get(0).setFieldResearcherDTO(journeyFieldResearcherDTO.getFieldResearcherDTO());
        }
        holder.getJourneyButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActionFactoryProducer.getFactory(RecycleViewOnItemClick.class.toString()).initRecycleViewOnItemClick(view, abstractView, position).onItemClick(view);
            }
        });
    }

    @Override
    public int getItemCount() {
        return journeyDTOList.size();
    }

    public List<JourneyDTO> getJourneyDTOList() {
        return journeyDTOList;
    }
}
