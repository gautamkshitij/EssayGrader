/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package subectVerbAgreement_1b;

import java.util.List;
import StanfordToolkit.StanfordNLP;
import Grade.FinalGrade;
import com.google.common.primitives.Booleans;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author kshitijgautam
 */
public class GradeSubjectVerb {

    static StanfordNLP toolkit = new StanfordNLP();
    static int mistkaes = 0;
    public static List<String> EssaySentences;

    public GradeSubjectVerb(String essay) {

        //List<String> posTaggedPerSentence = PartofspeechTag(essay);
        mistkaes = 0;
        EssaySentences = toolkit.sentenceSplit(essay);
        EvaluateSubjectVerbAgreement(EssaySentences);
    }

    public List<String> PartofspeechTag(String essay) {
        return toolkit.posTagging(essay);
    }

    public void EvaluateSubjectVerbAgreement(List<String> sentences) {
        Collection lexParse;
        List<String> subject = new ArrayList<>();
        String typedep;
        List<String> sentencePosTagg;
        for (String sentence : sentences) {
            lexParse = toolkit.lexParse(sentence);
            // System.out.println("Sentence"+sentence);
            sentencePosTagg = toolkit.posTagging(sentence);
            // System.out.println("SentencePosTagg"+sentencePosTagg);
            //System.out.println(lexParse);
            Iterator i = lexParse.iterator();
            while (i.hasNext()) {
                typedep = i.next().toString();
                //if (typedep.contains("nsubj")) {
                subject = extractSubstring(typedep);
                // System.out.println(subject);
                evaluate(sentencePosTagg, subject, sentence);

                //}
            }
        }
        CalculateGrade(mistkaes);
    }

    void CalculateGrade(int misktaes) {
        FinalGrade.scoreSubjectverbAgreement = 5 - misktaes * 0.05;
        if (FinalGrade.scoreSubjectverbAgreement < 1) {
            FinalGrade.scoreSubjectverbAgreement = 1;
        }
    }

    public void evaluate(List<String> posTaggedSentence, List<String> typeDependecySplitted, String sentence) {
        //  System.out.println("PosTagg" + posTaggedSentence + "\n Type Dependecy" + typeDependecySplitted);

        if (isNumeric(typeDependecySplitted.get(3)) && isNumeric(typeDependecySplitted.get(1)) && !isNumeric(typeDependecySplitted.get(0)) && isNumeric(typeDependecySplitted.get(2)) && Integer.parseInt(typeDependecySplitted.get(3)) != 0 && Integer.parseInt(typeDependecySplitted.get(1)) != 0) {

            String temp2 = posTaggedSentence.get(Integer.parseInt(typeDependecySplitted.get(3)) - 1);
            String temp1 = posTaggedSentence.get(Integer.parseInt(typeDependecySplitted.get(1)) - 1);

            // System.out.println("Temp1" + temp1 + "\ntemp2" + temp2);
            if (temp1.equals("NN") || temp1.equals("NNP") || temp1.equals("NNS") || temp1.equals("NNPS")
                    || temp1.equals("PRP") || temp1.equals("PRP$")) {
                if (temp2.equals("VB") || temp2.equals("VBP") || temp2.equals("VBD") || temp2.equals("VBZ")
                        || temp2.equals("VBG") || temp2.equals("VBN")) {
                    checkRules(temp1, typeDependecySplitted.get(0), temp2, typeDependecySplitted.get(2));
                }
            } else if (temp1.equals("VB") || temp1.equals("VBP") || temp1.equals("VBD") || temp1.equals("VBZ")
                    || temp1.equals("VBG") || temp1.equals("VBN")) {
                if (temp2.equals("NN") || temp2.equals("NNP") || temp2.equals("NNS") || temp2.equals("NNPS")
                        || temp2.equals("PRP") || temp2.equals("PRP$")) {
                    checkRules(temp2, typeDependecySplitted.get(2), temp1, typeDependecySplitted.get(0));
                }
            } else {
                sentence = sentence.toLowerCase().trim();
                String postTaggString = posTaggedSentence.toString().toLowerCase().trim();
                if ((sentence.contains("he") || sentence.contains("she") || sentence.contains("it")
                        || postTaggString.contains("nn") || postTaggString.contains("nnp")) && postTaggString.contains("vbz")) {
                    //  System.out.println(1);
//SImple Present
                } else if ((postTaggString.contains("nns") || postTaggString.contains("nnps") || sentence.contains("they")
                        || sentence.contains("we") || sentence.contains("i") || sentence.contains("you"))
                        && postTaggString.contains("vbp")) {
                    // System.out.println(2);
//simple present

                } else {
                    mistkaes++;
                }
            }
        }
    }

    void checkRules(String nounpos, String word1, String verbpos, String word2) {

        if ((nounpos.equalsIgnoreCase("NN") && verbpos.equalsIgnoreCase("VBP"))
                || (nounpos.equalsIgnoreCase("NNP") && verbpos.equalsIgnoreCase("VBP"))
                || (nounpos.equalsIgnoreCase("NNS") && verbpos.equalsIgnoreCase("VBZ"))
                || (nounpos.equalsIgnoreCase("NNPS") && verbpos.equalsIgnoreCase("VBZ"))
                || (word1.equalsIgnoreCase("He") && verbpos.equalsIgnoreCase("VBP"))
                || (word1.equalsIgnoreCase("she") && verbpos.equalsIgnoreCase("VBP"))
                || (word1.equalsIgnoreCase("I") && verbpos.equalsIgnoreCase("VBP"))
                || (word1.equalsIgnoreCase("him") && verbpos.equalsIgnoreCase("VBP"))
                || (word1.equalsIgnoreCase("me") && verbpos.equalsIgnoreCase("VBP"))
                || (word1.equalsIgnoreCase("myself") && verbpos.equalsIgnoreCase("VBP"))
                || (word1.equalsIgnoreCase("it") && verbpos.equalsIgnoreCase("VBP"))
                || (word1.equalsIgnoreCase("they") && verbpos.equalsIgnoreCase("VBZ"))
                || (word1.equalsIgnoreCase("we") && verbpos.equalsIgnoreCase("VBZ"))
                || (word1.equalsIgnoreCase("them") && verbpos.equalsIgnoreCase("VBZ"))
                || (word1.equalsIgnoreCase("themselves") && verbpos.equalsIgnoreCase("VBZ"))
                || (word1.equalsIgnoreCase("us") && verbpos.equalsIgnoreCase("VBZ"))
                || (word1.equalsIgnoreCase("those") && verbpos.equalsIgnoreCase("VBZ"))) {
            mistkaes++;
        }

    }

    public List<String> extractSubstring(String s) {
        String[] requiredString = new String[2];
        String[] temp1 = new String[2];
        String[] temp2 = new String[2];
        List<String> temp = new ArrayList<>();
        requiredString = s.substring(s.indexOf("(") + 1, s.indexOf(")")).split(",");
        temp1 = requiredString[0].split("-");
        temp2 = requiredString[1].split("-");
        temp.add(temp1[0]);
        temp.add(temp1[1]);
        temp.add(temp2[0]);
        temp.add(temp2[1]);
        temp1 = null;
        temp2 = null;
        return temp;

    }

    public boolean isNumeric(String string) throws IllegalArgumentException {
        boolean isnumeric = false;

        if (string != null && !string.equals("")) {
            isnumeric = true;
            char chars[] = string.toCharArray();

            for (int d = 0; d < chars.length; d++) {
                isnumeric &= Character.isDigit(chars[d]);

                if (!isnumeric) {
                    break;
                }
            }
        }
        return isnumeric;
    }

    public static void main(String[] args) {
        new GradeSubjectVerb("This are man");
        //  System.err.println(GradeSubjectVerb.mistkaes);

    }

}
