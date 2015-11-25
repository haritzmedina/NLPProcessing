package eus.ehu.sia.bw.processing.sentiment;

/**
 * Created by Haritz on 25/11/2015.
 */
public interface SentimentAnalyser {
    public Sentiment analyse(String text);
}
