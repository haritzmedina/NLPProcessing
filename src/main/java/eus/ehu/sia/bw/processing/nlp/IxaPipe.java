package eus.ehu.sia.bw.processing.nlp;

import ixa.kaflib.KAFDocument;

/**
 * Created by Haritz on 25/11/2015.
 */
public class IxaPipe {
    public static void main(String[] args){
        String text1 = "Karim Benzema, delantero internacional frances del Real Madrid, fue el unico de los titulares del equipo blanco en el clasico frente al barcelona que acudió a entrenarse a la ciudad deportiva de valdebebas en el dia libre concedido por el tecnico raf...leer la noticia completaBenzema trabaja en valdebebas en su dia libre";
        String text2 = "el mundo se rie del Real Madrid de Florentino Perez y Rafa Benitez. en Brasil, el Corinthians, flamante campeon de liga, ha utilizado su cuenta oficial en twitter, seguida por mas de 3,8 millones de torcedores  para mofar...leer la noticia completael corinthians se mofa del batacazo del madrid";
        IxaPipeTokenizer tokenizer = new IxaPipeTokenizer(IxaPipeWrapper.LANGUAGE_SPANISH);
        IxaPipePostagger postagger = new IxaPipePostagger(IxaPipeWrapper.LANGUAGE_SPANISH);
        IxaPipeNerc nerc = new IxaPipeNerc(IxaPipeWrapper.LANGUAGE_SPANISH);
        try {
            KAFDocument kaf1 = nerc.nerc(postagger.postagging(tokenizer.tokenize(text1)));
            kaf1.getEntities().iterator().forEachRemaining((a)->{
                System.out.println(a.getStr());
            });
        } catch (LanguageDoesNotMatch languageDoesNotMatch) {
            languageDoesNotMatch.printStackTrace();
        }
        try {
            KAFDocument kaf2 = nerc.nerc(postagger.postagging(tokenizer.tokenize(text2)));
            kaf2.getEntities().iterator().forEachRemaining((a)->{
                System.out.println(a.getStr());
            });
        } catch (LanguageDoesNotMatch languageDoesNotMatch) {
            languageDoesNotMatch.printStackTrace();
        }
    }
}
