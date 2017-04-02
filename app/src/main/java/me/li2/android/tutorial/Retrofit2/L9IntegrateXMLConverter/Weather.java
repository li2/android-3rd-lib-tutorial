package me.li2.android.tutorial.Retrofit2.L9IntegrateXMLConverter;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Test XML file
 * http://samples.openweathermap.org/data/2.5/weather?q=London&mode=xml&appid=b1b15e88fa797225412429c1c50c122a1
 *
 * Using Retrofit 2.x as REST client - Tutorial
 * 5. Exercise: Using Retrofit to convert XML response from an RSS feed
 * http://www.vogella.com/tutorials/Retrofit/article.html#exercise-using-retrofit-to-convert-xml-response-from-an-rss-feed
 *
 * Retrofit — How to Integrate XML Converter
 * https://futurestud.io/tutorials/retrofit-how-to-integrate-xml-converter
 *
 * Simple XML Tutorial
 * http://simple.sourceforge.net/download/stream/doc/tutorial/tutorial.php
 *
 * Created by weiyi on 30/03/2017.
 * https://github.com/li2
 */

@Root(name = "current", strict = false)
public class Weather
{
    /*
    <current>
        <city id="2643743" name="London">
            <country>GB</country>
            <sun rise="2017-01-30T07:40:36" set="2017-01-30T16:47:56"/>
        </city>
        <temperature value="280.15" min="278.15" max="281.15" unit="kelvin"/>
        <humidity value="81" unit="%"/>
        <wind>
            <speed value="4.6" name="Gentle Breeze"/>
            <direction value="90" code="E" name="East"/>
        </wind>
        <clouds value="90" name="overcast clouds"/>
        <lastupdate value="2017-01-30T15:50:00"/>
    </current>
     */
    @Element(name = "city")
    public CityBean city;

    @Element(name = "temperature")
    public TemperatureBean temperature;

    @Element(name = "humidity")
    public HumidityBean humidity;

    @Element(name = "wind")
    public WindBean wind;

    @Element(name = "clouds")
    public CloudsBean clouds;

    @Element(name = "lastupdate")
    public LastUpdate lastUpdate;

    @Override
    public String toString() {
        return "city " + city.toString() + "\n"
                + "temperature " + temperature.toString() + "\n"
                + "humidity " + humidity.toString() + "\n"
                + "wind " + wind.toString() + "\n"
                + "clouds " + clouds.toString() + "\n"
                + "last update " + lastUpdate.toString();
    }

    /*
         <city id="2643743" name="London">
           <country>GB</country>
           <sun rise="2017-01-30T07:40:36" set="2017-01-30T16:47:56"/>
         </city>
         */
    @Root(name = "city", strict = false)
    public static class CityBean {
        @Attribute(name = "name")
        public String name;

        @Element(name = "sun")
        public Sun sun;

        @Override
        public String toString() {
            return "name=" + name + "\n"
                    + " sun " + sun.toString();
        }
    }

    /*
    <sun rise="2017-01-30T07:40:36" set="2017-01-30T16:47:56"/>
     */
    @Root(name = "sun")
    public static class Sun {
        @Attribute(name = "rise")
        public String rise;

        @Attribute(name = "set")
        public String set;

        @Override
        public String toString() {
            return "rise=" + rise + " set=" + set;
        }
    }

    /*
    <temperature value="280.15" min="278.15" max="281.15" unit="kelvin"/>
     */
    @Root(name = "temperature", strict = false)
    public static class TemperatureBean {
        @Attribute(name = "value")
        public float value;

        @Attribute(name = "min")
        public float min;

        @Attribute(name = "max")
        public float max;

        @Attribute(name = "unit")
        public String unit;

        @Override
        public String toString() {
            return "value=" + value + "" + unit + " min=" + min + " max=" + max;
        }
    }

    /*
    <humidity value="81" unit="%"/>
     */
    @Root(name = "humidity", strict = false)
    public static class HumidityBean {
        @Attribute(name = "value")
        public int value;

        @Attribute(name = "unit")
        public String unit;

        @Override
        public String toString() {
            return "value=" + value + "" + unit;
        }
    }

    /*
    <wind>
      <speed value="4.6" name="Gentle Breeze"/>
      <gusts/>
      <direction value="90" code="E" name="East"/>
    </wind>
     */
    @Root(name = "wind", strict = false)
    public static class WindBean {
        @Element(name = "speed")
        public WindSpeedBean speed;

        @Element(name = "direction")
        public WindDirectionBean direction;

        @Override
        public String toString() {
            return "speed " + speed.toString() + "\n"
                    + "direction " + direction.toString();
        }
    }

    /*
    <speed value="4.6" name="Gentle Breeze"/>
     */
    @Root(name = "speed", strict = false)
    public static class WindSpeedBean {
        @Attribute(name = "value")
        public float value;

        @Attribute(name = "name")
        public String name;

        @Override
        public String toString() {
            return "value=" + value + " name=" + name;
        }
    }

    /*
    <direction value="90" code="E" name="East"/>
     */
    @Root(name = "direction", strict = false)
    public static class WindDirectionBean {
        @Attribute(name = "value")
        public int value;

        @Attribute(name = "code")
        public String code;

        @Attribute(name = "name")
        public String name;

        @Override
        public String toString() {
            return "value=" + value + " code=" + code + " name=" + name;
        }
    }

    /*
    <clouds value="90" name="overcast clouds"/>
     */
    @Root(name = "clouds")
    public static class CloudsBean {
        @Attribute(name = "value")
        public int value;

        @Attribute(name = "name")
        public String name;

        @Override
        public String toString() {
            return "value=" + value + " name=" + name;
        }
    }

    /*
    <lastupdate value="2017-01-30T15:50:00"/>
     */
    @Root(name = "lastupdate")
    public static class LastUpdate {
        @Attribute(name = "value")
        public String value;

        @Override
        public String toString() {
            return "value=" + value;
        }
    }
}

