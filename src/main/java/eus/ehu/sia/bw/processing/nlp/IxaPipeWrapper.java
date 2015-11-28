package eus.ehu.sia.bw.processing.nlp;

import eus.ehu.sia.bw.processing.Item;
import ixa.kaflib.KAFDocument;

import java.util.Map;

/**
 * Created by Haritz on 27/11/2015.
 */
public class IxaPipeWrapper {

    public static final String LANGUAGE_SPANISH = "es";
    public static final String LANGUAGE_ENGLISH = "en";
    public static final String LANGUAGE_FRENCH = "fr";
    public static final String LANGUAGE_GALICIAN = "gl";

    private IxaPipeTokenizer tokenizer;
    private IxaPipePostagger postagger;
    private IxaPipeNerc nerc;

    public IxaPipeWrapper(String language){
        if(language.compareTo(IxaPipeWrapper.LANGUAGE_SPANISH)==0){
            this.tokenizer = new IxaPipeTokenizer(IxaPipeWrapper.LANGUAGE_SPANISH);
            this.postagger = new IxaPipePostagger(IxaPipeWrapper.LANGUAGE_SPANISH);
            this.nerc = new IxaPipeNerc(IxaPipeWrapper.LANGUAGE_SPANISH);
        }
        else if(language.compareTo(IxaPipeWrapper.LANGUAGE_ENGLISH)==0){
            this.tokenizer = new IxaPipeTokenizer(IxaPipeWrapper.LANGUAGE_ENGLISH);
            this.postagger = new IxaPipePostagger(IxaPipeWrapper.LANGUAGE_ENGLISH);
            this.nerc = new IxaPipeNerc(IxaPipeWrapper.LANGUAGE_ENGLISH);
        }
    }

    public Map<String, Entity> retrieveEntities(Item item){
        try {
            KAFDocument kaf = nerc.nerc(postagger.postagging(tokenizer.tokenize(item.getCommentary())));
            kaf.getEntities().iterator().forEachRemaining((a)->{
                System.out.println(a.getStr());
            });
        } catch (LanguageDoesNotMatch languageDoesNotMatch) {
            languageDoesNotMatch.printStackTrace();
        }
        return null;
    }
}
