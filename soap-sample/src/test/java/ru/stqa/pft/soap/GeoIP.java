package ru.stqa.pft.soap;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("GeoIP")
public class GeoIP {

    private String country;
    private String state;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
