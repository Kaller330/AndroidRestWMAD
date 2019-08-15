
package DTO;


public class cinemaDTO
{
    private int cinemaID;
    private String name;
    private String location;

    public cinemaDTO(int cinemaID, String name, String location)
    {
        this.cinemaID = cinemaID;
        this.name = name;
        this.location = location;
    }

    public int getCinemaID()
    {
        return cinemaID;
    }

    public String getName()
    {
        return name;
    }

    public String getLocation()
    {
        return location;
    }

    public void setCinemaID(int cinemaID) {
        this.cinemaID = cinemaID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
