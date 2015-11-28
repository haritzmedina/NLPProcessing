package eus.ehu.sia.bw.processing;

import eus.ehu.sia.bw.processing.io.XMLReader;
import eus.ehu.sia.bw.processing.nlp.IxaPipeWrapper;

import java.io.File;
import java.util.List;

/**
 * Created by Haritz on 27/11/2015.
 */
public class Main {

    private static final String FILE1_URI = "data/input/RSSEspanolAnterior.xml";
    private static final String FILE2_URI = "data/input/RSSEspanolPosterior.xml";

    public static void main(String[] args){
        process(FILE1_URI, true, false);
        //process(FILE2_URI);
    }

    public static void process(String fileUri, boolean entitiesRecognition, boolean sentimentAnalysis){

        // Retrieve input data
        File file = new File(FILE1_URI);
        XMLReader xmlReader = new XMLReader();
        List<Item> items = xmlReader.read(file);

        // Retrieve entities
        IxaPipeWrapper ixaPipeWrapperSpanish = new IxaPipeWrapper(IxaPipeWrapper.LANGUAGE_SPANISH);
        IxaPipeWrapper ixaPipeWrapperEnglish = new IxaPipeWrapper(IxaPipeWrapper.LANGUAGE_ENGLISH);

        for(Item item : items){
            if(IxaPipeWrapper.LANGUAGE_SPANISH.compareTo(item.getLanguage())==0){
                ixaPipeWrapperSpanish.addEntities(item);
            }
            else if(IxaPipeWrapper.LANGUAGE_ENGLISH.compareTo(item.getLanguage())==0){
                ixaPipeWrapperEnglish.addEntities(item);
            }
        }

        for (Item item : items) {
            System.out.println(item);
        }

        // TODO Retrieve sentiment analysis

        // TODO Write output data

    }

}
