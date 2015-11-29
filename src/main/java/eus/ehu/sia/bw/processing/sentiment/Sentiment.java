package eus.ehu.sia.bw.processing.sentiment;

import java.math.BigDecimal;

/**
 * Created by Haritz on 25/11/2015.
 */
public class Sentiment {
    private BigDecimal pos;
    private BigDecimal neg;
    private BigDecimal neutral;
    private String result;

    public Sentiment(String result) {
        this.result = result;
    }

    public BigDecimal getPos() {
        return pos;
    }

    public void setPos(BigDecimal pos) {
        this.pos = pos;
    }

    public BigDecimal getNeg() {
        return neg;
    }

    public void setNeg(BigDecimal neg) {
        this.neg = neg;
    }

    public BigDecimal getNeutral() {
        return neutral;
    }

    public void setNeutral(BigDecimal neutral) {
        this.neutral = neutral;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "Sentiment{" +
                "pos=" + pos +
                ", neg=" + neg +
                ", neutral=" + neutral +
                ", result='" + result + '\'' +
                '}';
    }

    public Sentiment(BigDecimal pos, BigDecimal neg, BigDecimal neutral, String result) {

        this.pos = pos;
        this.neg = neg;
        this.neutral = neutral;
        this.result = result;
    }

    public Sentiment() {

    }
}
