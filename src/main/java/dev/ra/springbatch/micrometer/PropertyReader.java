package dev.ra.springbatch.micrometer;

import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {

    Properties props;

    public PropertyReader(){
        props = new Properties();

        InputStream resourceAsStream = this.getClass().getResourceAsStream("/application.properties");

        try{ props.load(resourceAsStream); }
        catch (Exception e){ e.printStackTrace(); }

    }

    public Properties getProperties(){return props; }
}
