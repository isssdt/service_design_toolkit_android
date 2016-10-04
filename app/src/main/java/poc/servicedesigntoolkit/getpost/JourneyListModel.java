package poc.servicedesigntoolkit.getpost;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Gunjan Pathak on 24-Aug-16.
 */
public class JourneyListModel implements Parcelable{
    String journeyName;
    int numberOfResearcher;
    String isActive;
/*
    JourneyListModel(){

    }*/

    protected JourneyListModel(Parcel in) {
        journeyName = in.readString();
        numberOfResearcher = in.readInt();
        isActive = in.readString();
    }

    public static final Creator<JourneyListModel> CREATOR = new Creator<JourneyListModel>() {
        @Override
        public JourneyListModel createFromParcel(Parcel in) {
            return new JourneyListModel(in);
        }

        @Override
        public JourneyListModel[] newArray(int size) {
            return new JourneyListModel[size];
        }
    };

    public JourneyListModel() {
    }

    public String getJourneyName() {
        return journeyName;
    }

    public void setJourneyName(String journeyName) {
        this.journeyName = journeyName;
    }

    public int getNumberOfResearcher() {
        return numberOfResearcher;
    }

    public void setNumberOfResearcher(int numberOfResearcher) {
        this.numberOfResearcher = numberOfResearcher;
    }

    public String isActive() {
        return isActive;
    }

    public void setActive(String active) {
        isActive = active;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(journeyName);
        parcel.writeInt(numberOfResearcher);
        parcel.writeString(isActive);
    }
}
