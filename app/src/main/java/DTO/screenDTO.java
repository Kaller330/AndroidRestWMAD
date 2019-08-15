
package DTO;


public class screenDTO
{
    private String screenID;
    private cinemaDTO cinema;

    public screenDTO(String screenID, cinemaDTO cinema) {
        this.screenID = screenID;
        this.cinema = cinema;
    }

    @Override
    public String toString(){
        return getScreenID();
    }

    public String getScreenID() {
        return screenID;
    }

    public cinemaDTO getCinema() {
        return cinema;
    }

    public void setScreenID(String screenID) {
        this.screenID = screenID;
    }

    public void setCinema(cinemaDTO cinema) {
        this.cinema = cinema;
    }
}
