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

    private static final String FILE3_INPUT_URI = "data/input/TwitterEspanolPosterior.xml";
    private static final String FILE3_OUTPUT_URI = "data/output/TwitterEspanolPosterior.json";

    private static final String FILE4_INPUT_URI = "data/input/DepuradoEspanolAnteriorYou.xml";
    private static final String FILE4_OUTPUT_URI = "data/output/YoutubeEspanolAnterior.json";

    private static final String FILE5_INPUT_URI = "data/input/DepuradoEspanolPosteriorYou.xml";
    private static final String FILE5_OUTPUT_URI = "data/output/YoutubeEspanolPosterior.json";

    private static final String FILE6_INPUT_URI = "data/input/DepuradoInglesAnteriorYou.xml";
    private static final String FILE6_OUTPUT_URI = "data/output/YoutubeInglesAnterior.json";

    private static final String FILE7_INPUT_URI = "data/input/DepuradoInglesPosteriorYou.xml";
    private static final String FILE7_OUTPUT_URI = "data/output/YoutubeInglesPosterior.json";

    private static final String FILE8_INPUT_URI = "data/input/TwitterInglesPosterior.xml";
    private static final String FILE8_OUTPUT_URI = "data/output/TwitterInglesPosterior.json";

    public static void main(String[] args){
        //process(FILE1_INPUT_URI, FILE1_OUTPUT_URI, true, false);
        //process(FILE2_INPUT_URI, FILE2_OUTPUT_URI, true, false);
        //process(FILE3_INPUT_URI, FILE3_OUTPUT_URI, true, false);
        //process(FILE4_INPUT_URI, FILE4_OUTPUT_URI, true, false);
        //process(FILE5_INPUT_URI, FILE5_OUTPUT_URI, true, false);
        //process(FILE6_INPUT_URI, FILE6_OUTPUT_URI, true, true);
        //process(FILE7_INPUT_URI, FILE7_OUTPUT_URI, true, true);
        process(FILE1_INPUT_URI, FILE1_OUTPUT_URI, true, true);
    }

    public static void process(String fileInputUri, String fileOutputUri, boolean entitiesRecognition, boolean sentimentAnalysis){

        // Retrieve input data
        File inputFile = new File(fileInputUri);
        XMLReader xmlReader = new XMLReader();
        List<Item> items = xmlReader.read(inputFile);

        System.out.println("Input file readed.");

        // Retrieve entities
        if(entitiesRecognition){
            IxaPipeWrapper ixaPipeWrapperSpanish = new IxaPipeWrapper(IxaPipeWrapper.LANGUAGE_SPANISH);
            IxaPipeWrapper ixaPipeWrapperEnglish = new IxaPipeWrapper(IxaPipeWrapper.LANGUAGE_ENGLISH);

            System.out.println("Models loaded.");

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

        System.out.println("Entities recognized.");

        // Retrieve sentiment analysis (only english supported in NLTK)
        if(sentimentAnalysis){
            SentimentAnalyser sentimentAnalyser = new NLTKSentimentAnalysis(NLTKSentimentAnalysis.LANGUAGE_ENGLISH);
            for(Item item : items){
                if(Item.LANGUAGE_ENGLISH.compareTo(item.getLanguage())==0){
                    sentimentAnalyser.addAnalysis(item);
                }
            }
        }

        System.out.println("Sentiment analysis done.");

        // Write output data
        FileWriter outputFile = null;
        try {
            outputFile = new FileWriter(fileOutputUri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONWriter jsonWriter = new JSONWriter();
        jsonWriter.write(items, outputFile);

        System.out.println("File output written.");
    }

}
