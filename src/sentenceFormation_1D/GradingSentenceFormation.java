/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sentenceFormation_1D;

/**
 *
 * @author kshitijgautam
 */

/*
 1. First word should not be main verb
 2. 


 */
import Grade.FinalGrade;
import StanfordToolkit.StanfordNLP;
import edu.stanford.nlp.trees.Tree;
import java.util.List;

public class GradingSentenceFormation {

    static int wrongSentences;

    public GradingSentenceFormation(String essay) {
        GradeSentences(essay);

        //   System.err.println(wrongSentences / 20);
        applyGrade(wrongSentences);
    }

    public void GradeSentences(String essay) {
        wrongSentences = 0;
        List<String> EssaySplit = sentenceSPlit(essay);
        List<String> posEssay;
        List<Tree> parseEssay;
        for (String sentence : EssaySplit) {
            posEssay = PartofspeechTag(sentence);
            if (posEssay.size() > 0 && (posEssay.get(0).contains("VBD")
                    || posEssay.get(0).contains("VBN") || posEssay.get(0).contains("VBP")
                    || posEssay.get(0).contains("VBZ") || posEssay.get(0).contains("VB")) && !posEssay.contains("VBG")) {
                wrongSentences++;

            }
        }

        parseEssay = parseTree(essay);
        int j;
        String substring;
        for (int i = 0; i < parseEssay.size(); i++) {
            if (parseEssay.get(i).toString().contains("FRAG")) {
                wrongSentences++;
            }

            if (parseEssay.get(i).toString().contains("SBAR")) {

                j = parseEssay.get(i).toString().indexOf("SBAR");
                substring = parseEssay.get(i).toString().substring(0, j - 1);
                if (!substring.contains("(VP ") && !substring.contains("(S ") && !substring.contains("(SINV ")) {
                    wrongSentences++;
                    //System.err.println(parseEssay.get(i));

                } else {

                    if (EssaySplit.get(i).contains("after") || EssaySplit.get(i).contains("how") || EssaySplit.get(i).contains("till") || EssaySplit.get(i).contains("although")
                            || EssaySplit.get(i).contains("if") || EssaySplit.get(i).contains("unless") || EssaySplit.get(i).contains("as")
                            || EssaySplit.get(i).contains("much") || EssaySplit.get(i).contains("until") || EssaySplit.get(i).contains("as if") || EssaySplit.get(i).contains("in order that")
                            || EssaySplit.get(i).contains("when") || EssaySplit.get(i).contains("as long as") || EssaySplit.get(i).contains("") || EssaySplit.get(i).contains("whenever") || EssaySplit.get(i).contains("as much as")
                            || EssaySplit.get(i).contains("now that") || EssaySplit.get(i).contains("where") || EssaySplit.get(i).contains("as soon as") || EssaySplit.get(i).contains("provided")
                            || EssaySplit.get(i).contains("wherever") || EssaySplit.get(i).contains("as though") || EssaySplit.get(i).contains("since") || EssaySplit.get(i).contains("while")
                            || EssaySplit.get(i).contains("bceause") || EssaySplit.get(i).contains("so that") || EssaySplit.get(i).contains("han") || EssaySplit.get(i).contains("before")
                            || EssaySplit.get(i).contains("ven if") || EssaySplit.get(i).contains("that") || EssaySplit.get(i).contains("even though")
                            || EssaySplit.get(i).contains("even")) {

                        if (!parseEssay.get(i).contains("VB")) {
                            wrongSentences++;
                        } else {
                            if (!parseEssay.get(i).toString().substring(parseEssay.get(i).toString().indexOf("VB") + 1, parseEssay.get(i).toString().length()).contains("VB")) {
                                wrongSentences++;
                            }
                        }
                        //if(!split till vb and find another vb in rest
                        //{}

                    }
                    /*substring = parse.toString().substring(i + 4, parse.toString().length());
                    
                     if (substring.contains("(IN ")) {
                            
                     }*/
                }
            }
        }

        // System.err.println(wrongSentences);
    }

    public void applyGrade(int wronSentencess) {

        FinalGrade.scoresentenceformation = 5 - 0.01 * wronSentencess;
        if (FinalGrade.scoresentenceformation < 1) {
            FinalGrade.scoresentenceformation = 1;
        }

        if (FinalGrade.scoresentenceformation > 5) {
            FinalGrade.scoresentenceformation = 5;
        }
    }

    public List<String> PartofspeechTag(String essay) {
        return new StanfordNLP().posTagging(essay);
    }

    public List<String> sentenceSPlit(String essay) {
        return new StanfordNLP().sentenceSplit(essay);
    }

    public List<Tree> parseTree(String essay) {
        return new StanfordNLP().parse(essay);
    }

}
