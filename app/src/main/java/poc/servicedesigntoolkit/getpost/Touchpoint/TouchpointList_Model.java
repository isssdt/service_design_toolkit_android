package poc.servicedesigntoolkit.getpost.Touchpoint;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Gunjan Pathak on 21-Oct-16.
 */

public class TouchpointList_Model {


    String name;
    String status;
    String channel;
    String channel_desc;

    public TouchpointList_Model( String name, String channel, String channel_desc, String action, String status) {
        this.channel = channel;
        this.channel_desc = channel_desc;
        this.action = action;
        this.status = status;
        this.name = name;
    }

    String action;


/*    public TouchpointList_Model(String name, String status, String channel) {
        this.name = name;
        this.status = status;
        this.channel = channel;
    }*/

    public TouchpointList_Model(JSONObject object){
        try {
            this.name = object.getString("touchPointDesc");
            this.status = object.getString("latitude");
            this.channel = object.getString("radius");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public TouchpointList_Model() {

    }

    public static ArrayList<TouchpointList_Model> fromJson(JSONArray jsonObjects) {
        ArrayList<TouchpointList_Model> touchlist = new ArrayList<TouchpointList_Model>();
        for (int i = 0; i < jsonObjects.length(); i++) {
            try {
                touchlist.add(new TouchpointList_Model(jsonObjects.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return touchlist;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }


    public String getChannel_desc() { return channel_desc; }

    public void setChannel_desc(String channel_desc) {this.channel_desc = channel_desc ; }

    public String getAction() { return action; }

    public void setAction(String action) { this.action = action; }

    @Override
    public String toString() {
        return "TouchpointList_Model{" +
                "name='" + name + '\'' +
                ", status='" + status + '\'' +
                ", channel='" + channel + '\'' +
                '}';
    }
}
