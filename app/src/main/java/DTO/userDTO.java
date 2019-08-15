
package DTO;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class userDTO
{

    private String username;
    private String password;
    private String address1;
    private String address2;
    private String town;
    private String county;
    private String postcode;
    private String regdate;
    private boolean isAdmin;

    public userDTO(String username, String password, String address1, String address2, String town, String county, String postcode, String regdate, boolean isAdmin)
    {

        this.username = username;
        this.password = password;
        this.address1 = address1;
        this.address2 = address2;
        this.town = town;
        this.county = county;
        this.postcode = postcode;

        this.regdate = regdate;

        this.isAdmin = isAdmin;
    }



    public String getUsername()
    {
        return username;
    }

    public String getPassword()
    {
        return password;
    }

    public String getAddress1()
    {
        return address1;
    }

    public String getAddress2()
    {
        return address2;
    }

    public String getTown()
    {
        return town;
    }

    public String getCounty()
    {
        return county;
    }

    public String getPostcode()
    {
        return postcode;
    }

    public String getRegdate() {
        return regdate;
    }


    public boolean getIsAdmin()
    {
        return isAdmin;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString(){
        String result;

        if (this.getIsAdmin() == true){
            result = this.getUsername() + " [Admin]";
        }else{
            result = this.getUsername() + " [User]";
        }

        return result;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public void setRegdate(String regdate) {
        this.regdate = regdate;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
