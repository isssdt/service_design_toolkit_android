package poc.servicedesigntoolkit.getpost.Touchpoint;

/**
 * Created by Gunjan Pathak on 08-Nov-16.
 */

public class Touchpoint_model {

    String name;
    String status;
    String channel;
    String channel_desc;
    Integer id;
    String action;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getChannel_desc() {
        return channel_desc;
    }

    public void setChannel_desc(String channel_desc) {
        this.channel_desc = channel_desc;
    }

    public Touchpoint_model(String name, String status, String channel) {
        this.name = name;
        this.status = status;
        this.channel = channel;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Touchpoint_model{" +
                "name='" + name + '\'' +
                ", status='" + status + '\'' +
                ", channel='" + channel + '\'' +
                '}';
    }
}
