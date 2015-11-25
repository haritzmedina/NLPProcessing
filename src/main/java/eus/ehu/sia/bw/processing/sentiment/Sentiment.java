package eus.ehu.sia.bw.processing.sentiment;

/**
 * Created by Haritz on 25/11/2015.
 */
public class Sentiment {
    private Long pos;
    private Long neg;
    private Long neutral;
    private String result;

    public Sentiment(String result) {
        this.result = result;
    }

    public Long getPos() {
        return pos;
    }

    public void setPos(Long pos) {
        this.pos = pos;
    }

    public Long getNeg() {
        return neg;
    }

    public void setNeg(Long neg) {
        this.neg = neg;
    }

    public Long getNeutral() {
        return neutral;
    }

    public void setNeutral(Long neutral) {
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

    public Sentiment(Long pos, Long neg, Long neutral, String result) {

        this.pos = pos;
        this.neg = neg;
        this.neutral = neutral;
        this.result = result;
    }

    public Sentiment() {

    }
}
