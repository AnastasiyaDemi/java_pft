package ru.stqa.pft.soap;

import com.lavasoft.GeoIPService;
import com.thoughtworks.xstream.XStream;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class GeoIpServiceTests {

    @Test
    public void testMyIp () {
        String countryAndStateXml = new GeoIPService().getGeoIPServiceSoap12().getIpLocation("91.231.45.200");

        XStream xstream = new XStream();
        xstream.processAnnotations(GeoIP.class);

        xstream.aliasField("Country", GeoIP.class, "country");
        xstream.aliasField("State", GeoIP.class, "state");

        GeoIP geoIP = (GeoIP) xstream.fromXML(countryAndStateXml);

        String ipLocation = geoIP.getCountry();

        assertEquals(ipLocation, "PL");
    }
}
