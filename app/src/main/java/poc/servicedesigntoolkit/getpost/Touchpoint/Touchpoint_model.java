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
    String rating;
    String reaction;
    String comment;

    public Touchpoint_model(String name, String status, String channel, String rating, String reaction, String comment) {
        this.name = name;
        this.status = status;
        this.channel = channel;
        this.rating = rating;
        this.reaction = reaction;
        this.comment = comment;
    }

    public Touchpoint_model(String name, String status, String channel) {
        this.name = name;
        this.status = status;
        this.channel = channel;
    }

    public Touchpoint_model() {

    }

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

    public String getChannel() {
        return channel;
    }

    public String getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getReaction() {
        return reaction;
    }

    public void setReaction(String reaction) {
        this.reaction = reaction;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
