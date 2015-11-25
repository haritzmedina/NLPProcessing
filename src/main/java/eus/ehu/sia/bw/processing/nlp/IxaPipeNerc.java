package eus.ehu.sia.bw.processing.nlp;

import eus.ixa.ixa.pipe.nerc.Annotate;
import eus.ixa.ixa.pipe.nerc.train.Flags;
import ixa.kaflib.KAFDocument;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by Haritz on 25/11/2015.
 */
public class IxaPipeNerc {

    public static final String LANGUAGE_SPANISH = "es";
    public static final String LANGUAGE_ENGLISH = "en";

    public static final String MODEL_ENGLISH = "models/nerc/en-clark-conll03.bin";
    public static final String MODEL_SPANISH = "models/nerc/es-clusters-conll02.bin";

    private final Properties annotateProperties;

    private Annotate annotator;

    public IxaPipeNerc(String language){
        this.annotateProperties = new Properties();
        annotateProperties.setProperty("ruleBasedOption", Flags.DEFAULT_LEXER);
        annotateProperties.setProperty("dictTag", Flags.DEFAULT_DICT_OPTION);
        annotateProperties.setProperty("dictPath", Flags.DEFAULT_DICT_PATH);
        annotateProperties.setProperty("clearFeatures", Flags.DEFAULT_FEATURE_FLAG);
        annotateProperties.setProperty("language", language);
        annotateProperties.setProperty("model", getModel(language));

        try {
            annotator = new Annotate(annotateProperties);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public KAFDocument nerc(KAFDocument kaf) throws LanguageDoesNotMatch {
        // Check if language of kaf and preset is the same
        String language = kaf.getLang();
        if(language.compareTo(this.annotateProperties.getProperty("language"))!=0){
            throw new LanguageDoesNotMatch();
        }

        try {
            annotator.annotateNEs(kaf);
        } catch (IOException e) {
            e.printStackTrace();
        }

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
