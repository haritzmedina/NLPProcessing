package eus.ehu.sia.bw.processing.io;

import eus.ehu.sia.bw.processing.Item;
import eus.ehu.sia.bw.processing.nlp.Entity;
import eus.ehu.sia.bw.processing.sentiment.Sentiment;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Haritz on 29/11/2015.
 */
public class JSONWriter {

    public static final String TAG_ROOT = "items";

    public static final String TAG_COMMENTARY = "comentario";
    public static final String TAG_DATE = "fecha";
    public static final String TAG_SOURCE = "fuente";
    public static final String TAG_ID = "id";
    public static final String TAG_LANGUAGE = "idioma";

    public static final String TAG_NERC = "nerc";
    public static final String TAG_NERC_NAME = "nombre";
    public static final String TAG_NERC_NUMBER = "numero";

    public static final String TAG_SENTIMENT = "sentimiento";
    public static final String TAG_SENTIMENT_RESULT = "resultado";
    public static final String TAG_SENTIMENT_POS = "pos";
    public static final String TAG_SENTIMENT_NEG = "neg";
    public static final String TAG_SENTIMENT_NEUTRAL = "neutral";

    public void write(List<Item> items, FileWriter file){

        // ISO-8601 date formatter
        TimeZone timeZone = TimeZone.getTimeZone("UTC");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mmZ");
        dateFormat.setTimeZone(timeZone);

        JSONObject root = new JSONObject();

        JSONArray itemArray = new JSONArray();
        items.forEach((item)->{
            // Create json object to insert
            JSONObject itemObject = new JSONObject();

            // Add elements
            itemObject.put(JSONWriter.TAG_COMMENTARY, item.getCommentary());
            itemObject.put(JSONWriter.TAG_DATE, dateFormat.format(item.getDate()));
            itemObject.put(JSONWriter.TAG_ID, item.getId());
            itemObject.put(JSONWriter.TAG_LANGUAGE, item.getLanguage());
            itemObject.put(JSONWriter.TAG_SOURCE, item.getSource());

            // Add NERCs array
            JSONArray nercArray = new JSONArray();
            item.getEntities().forEach((key, entity)->{
                // Create nerc object for each entity
                JSONObject nercObject = new JSONObject();
                // Add name and value
                nercObject.put(JSONWriter.TAG_NERC_NAME, entity.getName());
                nercObject.put(JSONWriter.TAG_NERC_NUMBER, entity.getValue());
                // Add to nerc array
                nercArray.put(nercObject);
            });
            itemObject.put(JSONWriter.TAG_NERC, nercArray);

            // Add sentiment object
            JSONObject sentimentObject = new JSONObject();
            sentimentObject.put(JSONWriter.TAG_SENTIMENT_RESULT, item.getSentiment().getResult());
            sentimentObject.put(JSONWriter.TAG_SENTIMENT_POS, item.getSentiment().getPos());
            sentimentObject.put(JSONWriter.TAG_SENTIMENT_NEG, item.getSentiment().getNeg());
            sentimentObject.put(JSONWriter.TAG_SENTIMENT_NEUTRAL, item.getSentiment().getNeutral());
            itemObject.put(JSONWriter.TAG_SENTIMENT, sentimentObject);

            // Insert into the container the object
            itemArray.put(itemObject);
        });

        // Add items array to the root
        root.put(JSONWriter.TAG_ROOT, itemArray);

        // Write json to the file
        root.write(file);
        try {
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args){
        List<Item> items = new Vector<>();
        Item item = new Item();
        item.setCommentary("Hola estoy muy feliz pero tranquilo.");
        item.setDate(new Date());
        Map<String, Entity> entities = new HashMap<>();
        entities.put("Ronaldo", new Entity("Ronaldo", 1));
        entities.put("Messi", new Entity("Messi", 1));
        item.setEntities(entities);
        item.setId("T-000001");
        item.setLanguage("es");
        item.setSentiment(new Sentiment(new BigDecimal(0.7), new BigDecimal(0.4), new BigDecimal(0.1), "pos"));
        item.setSource("@pepito");
        items.add(item);
        try {
            new JSONWriter().write(items, new FileWriter("example.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
