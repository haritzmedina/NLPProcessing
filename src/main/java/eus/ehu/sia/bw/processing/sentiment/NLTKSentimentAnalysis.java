package eus.ehu.sia.bw.processing.sentiment;

import eus.ehu.sia.bw.processing.Item;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Haritz on 25/11/2015.
 */
public class NLTKSentimentAnalysis implements SentimentAnalyser {

    public static final String LANGUAGE_ENGLISH = "english";

    private final String language;
    private HttpClient client;

    public NLTKSentimentAnalysis(String language) {
        this.client = HttpClientBuilder.create().build();
        this.language = language;
    }

    public Sentiment analyse(String text) {
        List nameValuePairs = new ArrayList(1);
        nameValuePairs.add(new BasicNameValuePair("language", language));
        nameValuePairs.add(new BasicNameValuePair("text", text));
        HttpPost post = new HttpPost("http://text-processing.com/api/sentiment/");
        try {
            post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Sentiment sentiment = null;
        try {
            HttpResponse response = client.execute(post);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String responseString = IOUtils.toString(rd);
            System.out.println(responseString);
            JSONObject resultJson = new JSONObject(responseString);
            String result = resultJson.getString("label");
            JSONObject probability = (JSONObject) resultJson.get("probability");
            BigDecimal pos = probability.getBigDecimal("pos");
            BigDecimal neg = probability.getBigDecimal("neg");
            BigDecimal neutral = probability.getBigDecimal("neutral");
            sentiment = new Sentiment(pos, neg, neutral, result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sentiment;
    }

    @Override
    public void addAnalysis(Item item) {
        Sentiment sentiment = this.analyse(item.getCommentary());
        item.setSentiment(sentiment);
    }

    public static void main(String[] args){
        SentimentAnalyser analyser = new NLTKSentimentAnalysis(NLTKSentimentAnalysis.LANGUAGE_ENGLISH);
        System.out.println(analyser.analyse("The film was bad."));
    }
}
