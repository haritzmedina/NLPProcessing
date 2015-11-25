package eus.ehu.sia.bw.processing.nlp;

import ixa.kaflib.KAFDocument;

/**
 * Created by Haritz on 25/11/2015.
 */
public class IxaPipe {
    public static void main(String[] args){
        String text1 = "Hola soy Cristiano Ronaldo.";
        String text2 = "Hola mi nombre es Lionel Messi.";
        IxaPipeTokenizer tokenizer = new IxaPipeTokenizer(IxaPipeTokenizer.LANGUAGE_SPANISH);
        IxaPipePostagger postagger = new IxaPipePostagger(IxaPipePostagger.LANGUAGE_SPANISH);
        IxaPipeNerc nerc = new IxaPipeNerc(IxaPipeNerc.LANGUAGE_SPANISH);
        try {
            KAFDocument kaf1 = nerc.nerc(postagger.postagging(tokenizer.tokenize(text1)));
            System.out.println(kaf1.toString());
        } catch (LanguageDoesNotMatch languageDoesNotMatch) {
            languageDoesNotMatch.printStackTrace();
        }
        try {
            KAFDocument kaf2 = nerc.nerc(postagger.postagging(tokenizer.tokenize(text2)));
            System.out.println(kaf2.toString());
        } catch (LanguageDoesNotMatch languageDoesNotMatch) {
            languageDoesNotMatch.printStackTrace();
        }
    }
}
