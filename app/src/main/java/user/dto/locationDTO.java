package user.dto;

/**
 * Created by Gunjan Pathak on 31/01/2017.
 */

public class locationDTO {
    private double Latitude;
    private double Longitude;
    private SdtUserDTO sdtUserDTO;


    public double getLatitude() { return Latitude; }

    public void setLatitude(double latitude) { Latitude = latitude; }

    public double getLongitude() { return Longitude; }

    public void setLongitude(double longitude) { Longitude = longitude;}

    public SdtUserDTO getSdtUserDTO() {
        return sdtUserDTO;
    }

    public void setSdtUserDTO(SdtUserDTO sdtUserDTO) {
        this.sdtUserDTO = sdtUserDTO;
    }
}
