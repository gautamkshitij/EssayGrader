/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lengthOfEssay_3a;

import Grade.FinalGrade;
import StanfordToolkit.StanfordNLP;

/**
 *
 * @author kshitijgautam
 */
public class GradingLength {
//Learned from Sentence_Length_Training file

    static float numberofSentencesHigh = (float) 18.315968;
    static float numberofSentencesMedium = (float) 17.364721;
    static float numberofSentencesLow = (float) 12.573172;

    static float numberofWordssHigh = (float) 422.96582;
    static float numberofWordsMedium = (float) 343.81824;
    static float numberofWordsLow = (float) 244.87866;

    public GradingLength(String essay) {

        gradeLengths(getNumberofSentences(essay), getNumberofWords(essay));
    }

    /*  public void gradeLenghts(int numberofSentences, int numberofWords) {

     float essaySentenceGrade = (float) ((numberofSentences / numberofSentencesHigh) * 2.50);
     float essayWordGrade = (float) ((numberofWords / numberofWordssHigh) * 2.50);

     if (essaySentenceGrade > 2.5) {
     essaySentenceGrade = (float) 2.5;
     }
     if (essaySentenceGrade < 0.5) {
     essaySentenceGrade = (float) 0.5;
     }
     if (essayWordGrade > 2.5) {
     essayWordGrade = (float) 2.5;
     }
     if (essayWordGrade < 0.5) {
     essayWordGrade = (float) 0.5;
     }
     FinalGrade.scorelengthOfEssay = essayWordGrade + essaySentenceGrade;

     }*/
    public int getNumberofSentences(String essay) {

        return new StanfordNLP().countSentence(essay);

    }

    public int getNumberofWords(String essay) {

        return new StanfordNLP().countTokens(essay);
    }

    public static void main(String args[]) {
        new GradingLength("Sentence formation - are the sentences formed properly? i.e. beginning and ending\n"
                + "properly, is the word order correct, are the constituents formed properly? are there\n"
                + "missing words or constituents (prepositions, subject, object etc.)?");

    }

    private void gradeLengths(int numberofSentences, int numberofWords) {
        float essaySentenceGrade = (float) ((numberofSentences / numberofSentencesHigh) * 2.50);
        float essayWordGrade = (float) ((numberofWords / numberofWordssHigh) * 2.50);

        if (essaySentenceGrade > 2.5) {
            essaySentenceGrade = (float) 2.5;
        }
        if (essaySentenceGrade < 0.5) {
            essaySentenceGrade = (float) 0.5;
        }
        if (essayWordGrade > 2.5) {
            essayWordGrade = (float) 2.5;
        }
        if (essayWordGrade < 0.5) {
            essayWordGrade = (float) 0.5;
        }

        FinalGrade.scorelengthOfEssay = essayWordGrade + essaySentenceGrade;
        if (FinalGrade.scorelengthOfEssay < 1) {
            FinalGrade.scorelengthOfEssay = 1;
        }
        if (FinalGrade.scorelengthOfEssay > 5) {
            FinalGrade.scorelengthOfEssay = 5F;
        }

    }

}
