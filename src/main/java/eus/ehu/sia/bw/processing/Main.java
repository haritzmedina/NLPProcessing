package eus.ehu.sia.bw.processing;

import eus.ehu.sia.bw.processing.io.JSONWriter;
import eus.ehu.sia.bw.processing.io.XMLReader;
import eus.ehu.sia.bw.processing.nlp.IxaPipeWrapper;
import eus.ehu.sia.bw.processing.sentiment.NLTKSentimentAnalysis;
import eus.ehu.sia.bw.processing.sentiment.SentimentAnalyser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by Haritz on 27/11/2015.
 */
public class Main {

    private static final String FILE1_INPUT_URI = "data/input/RSSEspanolAnterior.xml";
    private static final String FILE1_OUTPUT_URI = "data/output/RSSEspanolAnterior.json";

    private static final String FILE2_INPUT_URI = "data/input/RSSEspanolPosterior.xml";
    private static final String FILE2_OUTPUT_URI = "data/output/RSSEspanolPosterior.json";

    public static void main(String[] args){
        process(FILE1_INPUT_URI, FILE1_OUTPUT_URI, true, false);
        process(FILE2_INPUT_URI, FILE2_OUTPUT_URI, true, false);
    }

    public static void process(String fileInputUri, String fileOutputUri, boolean entitiesRecognition, boolean sentimentAnalysis){

        // Retrieve input data
        File inputFile = new File(fileInputUri);
        XMLReader xmlReader = new XMLReader();
        List<Item> items = xmlReader.read(inputFile);

        // Retrieve entities
        if(entitiesRecognition){
            IxaPipeWrapper ixaPipeWrapperSpanish = new IxaPipeWrapper(IxaPipeWrapper.LANGUAGE_SPANISH);
            IxaPipeWrapper ixaPipeWrapperEnglish = new IxaPipeWrapper(IxaPipeWrapper.LANGUAGE_ENGLISH);

            for(Item item : items){
                // If commentary is in spanish, it will use spanish wrapper
                if(Item.LANGUAGE_SPANISH.compareTo(item.getLanguage())==0){
                    ixaPipeWrapperSpanish.addEntities(item);
                }
                // If commentary is in english, it will use english wrapper
                else if(Item.LANGUAGE_ENGLISH.compareTo(item.getLanguage())==0){
                    ixaPipeWrapperEnglish.addEntities(item);
                }
            }
        }

        // Retrieve sentiment analysis (only english supported in NLTK)
        if(sentimentAnalysis){
            SentimentAnalyser sentimentAnalyser = new NLTKSentimentAnalysis(NLTKSentimentAnalysis.LANGUAGE_ENGLISH);
            for(Item item : items){
                if(Item.LANGUAGE_ENGLISH.compareTo(item.getLanguage())==0){
                    sentimentAnalyser.addAnalysis(item);
                }
            }
        }


        for (Item item : items) {
            System.out.println(item);
        }

        // Write output data
        FileWriter outputFile = null;
        try {
            outputFile = new FileWriter(fileOutputUri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONWriter jsonWriter = new JSONWriter();
        jsonWriter.write(items, outputFile);
    }

}
