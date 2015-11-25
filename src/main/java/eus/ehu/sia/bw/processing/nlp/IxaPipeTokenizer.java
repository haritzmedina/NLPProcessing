package eus.ehu.sia.bw.processing.nlp;

import eus.ixa.ixa.pipe.tok.Annotate;
import ixa.kaflib.KAFDocument;

import java.io.*;
import java.util.Properties;

/**
 * Created by Haritz on 25/11/2015.
 */
public class IxaPipeTokenizer {

    public static final String LANGUAGE_SPANISH = "es";
    public static final String LANGUAGE_ENGLISH = "en";
    public static final String LANGUAGE_FRENCH = "fr";
    public static final String LANGUAGE_GALICIAN = "gl";

    private final Properties annotateProperties;

    public IxaPipeTokenizer(String language){
        this.annotateProperties = new Properties();
        annotateProperties.setProperty("language", language);
        annotateProperties.setProperty("normalize", "default");
        annotateProperties.setProperty("untokenizable", "no");
        annotateProperties.setProperty("hardParagraph", "no");
    }

    public KAFDocument tokenize(String text){
        // Convert String into InputStream
        InputStream is = new ByteArrayInputStream(text.getBytes());
        // Read it with BufferedReader
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        Annotate annotate = new Annotate(br, annotateProperties);
        KAFDocument kaf = new KAFDocument("es", "v1.naf");
        try {
            annotate.tokenizeToKAF(kaf);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return kaf;
    }

}
