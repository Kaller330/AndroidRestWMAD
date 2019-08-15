
package DTO;


import java.io.Serializable;

public class filmDTO implements Serializable
{
    private int filmID;
    private String title;
    private String bbfcAge;
    private String runtime;
    private String lead;

    public filmDTO(){

    }
    public filmDTO(int filmID, String title, String bbfcAge, String runtime, String lead)
    {
        this.filmID = filmID;
        this.title = title;
        this.bbfcAge = bbfcAge;
        this.runtime = runtime;
        this.lead = lead;
    }

    @Override
    public String toString(){
        String output = "FilmID: "  + this.getFilmID() + ", Title: " + this.getTitle();
        return output;
    }

    public int getFilmID()
    {
        return filmID;
    }

    public String getTitle()
    {
        return title;
    }

    public String getBbfcAge()
    {
        return bbfcAge;
    }

    public String getRuntime()
    {
        return runtime;
    }

    public String getLead()
    {
        return lead;
    }

    public void setFilmID(int filmID) {
        this.filmID = filmID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBbfcAge(String bbfcAge) {
        this.bbfcAge = bbfcAge;
    }

    public void setRuntime(String runtime) {
        runtime = runtime;
    }

    public void setLead(String lead) {
        lead = lead;
    }
}
