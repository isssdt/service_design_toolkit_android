package touchpoint.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;

import poc.servicedesigntoolkit.getpost.journey.view.JourneyDTO;

/**
 * Created by longnguyen on 11/6/16.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TouchPointDTO {
    private Integer id;
    private String touchPointDesc;
    private String latitude;
    private String longitude;
    private String radius;
    private String action;
    private String channelDescription;
    private BigDecimal duration;
    private ChannelDTO channelDTO;
    private JourneyDTO journeyDTO;
    private Integer no_like;
    private Integer no_dislike;
    private Integer no_neutral;

    public TouchPointDTO() {

    }

    public TouchPointDTO(Integer id, String touchPointDesc, String latitude, String longitude, String radius,
                         String action, String channelDescription, ChannelDTO channelDTO, JourneyDTO journeyDTO) {
        this.id = id;
        this.touchPointDesc = touchPointDesc;
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;
        this.action = action;
        this.channelDescription = channelDescription;
        this.channelDTO = channelDTO;
        this.journeyDTO = journeyDTO;
    }

    public Integer getNo_like() {
        return no_like;
    }

    public void setNo_like(Integer no_like) {
        this.no_like = no_like;
    }

    public Integer getNo_dislike() {
        return no_dislike;
    }

    public void setNo_dislike(Integer no_dislike) {
        this.no_dislike = no_dislike;
    }

    public Integer getNo_neutral() {
        return no_neutral;
    }

    public void setNo_neutral(Integer no_neutral) {
        this.no_neutral = no_neutral;
    }

    public BigDecimal getDuration() {
        return duration;
    }

    public void setDuration(BigDecimal duration) {
        this.duration = duration;
    }

    public JourneyDTO getJourneyDTO() {
        return journeyDTO;
    }

    public void setJourneyDTO(JourneyDTO journeyDTO) {
        this.journeyDTO = journeyDTO;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ChannelDTO getChannelDTO() {
        return channelDTO;
    }

    public void setChannelDTO(ChannelDTO channelDTO) {
        this.channelDTO = channelDTO;
    }


    public String getTouchPointDesc() {
        return touchPointDesc;
    }

    public void setTouchPointDesc(String touchPointDesc) {
        this.touchPointDesc = touchPointDesc;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getRadius() {
        return radius;
    }

    public void setRadius(String radius) {
        this.radius = radius;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getChannelDescription() {
        return channelDescription;
    }

    public void setChannelDescription(String channelDescription) {
        this.channelDescription = channelDescription;
    }

}