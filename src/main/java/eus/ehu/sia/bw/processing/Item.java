package eus.ehu.sia.bw.processing;

import eus.ehu.sia.bw.processing.nlp.Entity;
import eus.ehu.sia.bw.processing.sentiment.Sentiment;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Haritz on 27/11/2015.
 */
public class Item {

    public static final String LANGUAGE_ENGLISH = "en";
    public static final String LANGUAGE_SPANISH = "es";

    private String commentary;
    private String id;
    private String source;
    private Date date;
    private String language;
    private Map<String, Entity> entities;
    private Sentiment sentiment;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        return id.equals(item.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Item{" +
                "commentary='" + commentary + '\'' +
                ", id='" + id + '\'' +
                ", source='" + source + '\'' +
                ", date=" + date +
                ", language='" + language + '\'' +
                ", entities=" + entities +
                ", sentiment=" + sentiment +
                '}';
    }

    public String getCommentary() {
        return commentary;
    }

    public void setCommentary(String commentary) {
        this.commentary = commentary;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Map<String, Entity> getEntities() {
        return entities;
    }

    public void setEntities(Map<String, Entity> entities) {
        this.entities = entities;
    }

    public Sentiment getSentiment() {
        return sentiment;
    }

    public void setSentiment(Sentiment sentiment) {
        this.sentiment = sentiment;
    }

    public Item(String commentary, String id, String source, Date date, String language) {

        this.commentary = commentary;
        this.id = id;
        this.source = source;
        this.date = date;
        this.language = language;
        this.sentiment = new Sentiment();
        this.entities = new HashMap<>();
    }

    public Item() {
        this.sentiment = new Sentiment();
        this.entities = new HashMap<>();
    }
}
