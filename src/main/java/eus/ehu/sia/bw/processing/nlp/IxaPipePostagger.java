package eus.ehu.sia.bw.processing.nlp;

import eus.ixa.ixa.pipe.pos.Annotate;
import ixa.kaflib.KAFDocument;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by Haritz on 25/11/2015.
 */
public class IxaPipePostagger {

    private final Properties annotateProperties;

    public static final String MODEL_ENGLISH = "models/pos/en-maxent-100-c5-baseline-dict-penn.bin";
    public static final String MODEL_SPANISH = "models/pos/es-maxent-100-c5-baseline-autodict01-ancora.bin";

    private Annotate annotator;

    public IxaPipePostagger(String language){
        this.annotateProperties = new Properties();
        this.annotateProperties.setProperty("multiwords", "true");
        this.annotateProperties.setProperty("dictag", "false");
        this.annotateProperties.setProperty("language", language);
        this.annotateProperties.setProperty("model", this.getModel(language));

        try {
            annotator = new Annotate(this.annotateProperties);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public KAFDocument postagging(KAFDocument kaf) throws LanguageDoesNotMatch {
        // Check if language of kaf and preset is the same
        String language = kaf.getLang();
        if(language.compareTo(this.annotateProperties.getProperty("language"))!=0){
            throw new LanguageDoesNotMatch("Postagger language: "+this.annotateProperties.getProperty("language")+
                    " KAF document language: "+language);
        }

        annotator.annotatePOSToKAF(kaf);
        return kaf;
    }

    private String getModel(String language){
        if(language.compareTo("en")==0){
            return MODEL_ENGLISH;
        }
        else{
            return MODEL_SPANISH;
        }
    }
}
