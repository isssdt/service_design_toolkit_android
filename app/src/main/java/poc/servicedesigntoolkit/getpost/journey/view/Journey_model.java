package poc.servicedesigntoolkit.getpost.journey.view;

import java.util.Date;

/**
 * Created by Gunjan Pathak on 23-Dec-16.
 */

public class Journey_model
{
    String journeyName;
    Date startDate;
    Date endDate;

    public Journey_model(){

    }
    public Journey_model(String journeyName, Date startDate, Date endDate) {
        this.journeyName = journeyName;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getJourneyName() {
        return journeyName;
    }

    public void setJourneyName(String journeyName) {
        this.journeyName = journeyName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }


}
