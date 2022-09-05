package com.congee.doc2html.properties;

import java.io.IOException;
import java.util.Properties;

public class SingleDoc2HtmlProperties {

    public static Properties prop = null;


    private SingleDoc2HtmlProperties() {
    }

    public static Properties getInstance() {
        if (prop == null) {
            prop = new Properties();
            try {
                prop.load(SingleDoc2HtmlProperties.class.getResourceAsStream("/config.properties"));
            } catch (IOException e) {
                System.out.println("loading error");
            }
        }
        return prop;
    }

    public static String docLocation() {
        return getInstance().getProperty("doc2html.doc.location");
    }

    public static void main(String[] args) {
        System.out.println(SingleDoc2HtmlProperties.docLocation());
    }


}
