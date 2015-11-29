package eus.ehu.sia.bw.processing.sentiment;

import eus.ehu.sia.bw.processing.Item;

/**
 * Created by Haritz on 25/11/2015.
 */
public interface SentimentAnalyser {
    public Sentiment analyse(String text);
    public void addAnalysis(Item item);
}
