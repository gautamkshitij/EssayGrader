/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Semantics_EssayAddressTopic_2B;

import Grade.FinalGrade;
import StanfordToolkit.StanfordNLP;
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kshitijgautam
 */
public class GradeEssayAddressTopic {

    ArrayList<String> essayNounsSyns = new ArrayList<>(
            Arrays.asList("cars", "motorcar", "auto", "automobile", "machine", "vehicle", "wheel", "engine", "petrol", "diesel",
                    "use", "used", "consumption", "utilization", "purpose", "application", "exploitation", "usage", "function", "role",
                    "today", "present",
                    "years", "year", "days", "time"));
    double phraseMatch = 0.0;
    double highPhrase = 0.63880825;
    String essayTopic = "In twenty years, there will be fewer cars in use than there are today.";
    double STSValue, NSimilarity;
    int commonNouns;
    public static int average = 0;

    public GradeEssayAddressTopic(String essay) throws IOException {

        applyGrade(CalcuateSTS(essay), calculateCommonNouns(getNounList(essay)));
        // System.out.println(getNounList(essay));
        //average += calculateCommonNouns(getNounList(essay));

    }

    public int calculateCommonNouns(Set<String> nounSet) {
        commonNouns = 0;
        for (String noun : nounSet) {
            for (String nounSyn : essayNounsSyns) {
                if (noun.contains(nounSyn)) {
                    commonNouns++;
                }
//NSimilarity = countCommonNouns(noun, nounSyn);
                /* if (NSimilarity > 0.15) {
                 commonNouns++;
                 System.out.println("breaking now");
                 break;
                 }*/
            }
        }
        return commonNouns;
    }

    public double countCommonNouns(String noun, String essayNoun) {
        double NSimilarity = 0.0;
        try {

            String STS_URL = "http://swoogle.umbc.edu/SimService/GetSimilarity?operation=api&phrase1="
                    + noun + "&phrase2=" + essayNoun;

            URL oracle = new URL(STS_URL);
            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(oracle.openStream()))) {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    NSimilarity = Double.parseDouble(inputLine);
//                    System.out.println(NSimilarity);

                }
            }

        } catch (MalformedURLException ex) {
            System.err.println("Error! Connect to Internet please");
            Logger.getLogger(GradeEssayAddressTopic.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            System.err.println("Error! Connect to Internet please-1");
            Logger.getLogger(GradeEssayAddressTopic.class.getName()).log(Level.SEVERE, null, ex);
        }

        return NSimilarity;
    }

    public void applyGrade(double STSPercentage, int commonNouns) {
        double percentageNouns = 0.0;
        phraseMatch = (STSPercentage / highPhrase) * 2.50;
        percentageNouns = (commonNouns / 6) * 2.50; // 6 is average common nouns taken from training of high data set
        //  System.out.println(phraseMatch); 
        if (phraseMatch < 0.5) {
            FinalGrade.scoreaddressTopic = 0.5;
        }

        if (phraseMatch > 2.5) {
            FinalGrade.scoreaddressTopic = 2.5;
        } else {
            FinalGrade.scoreaddressTopic = phraseMatch;
        }
        if (percentageNouns < 0.5) {
            FinalGrade.scoreaddressTopic += 0.5;
        }

        if (percentageNouns > 2.5) {
            FinalGrade.scoreaddressTopic += 2.5;
        } else {
            FinalGrade.scoreaddressTopic += percentageNouns;
        }

    }
//Compute Semantic Textual Similarity between essay topic and essay http://swoogle.umbc.edu/SimService/index.html

    public double CalcuateSTS(String essay) {
        try {
            STSValue = 0.0;

            String STS_URL = "http://swoogle.umbc.edu/StsService/GetStsSim?operation=api&"
                    + "phrase1=" + essayTopic.replaceAll("[^a-zA-Z ]", "").replace(" ", "%20")
                    + "&phrase2=" + essay.replaceAll("[^a-zA-Z ]", "").replace(" ", "%20");

            URL oracle = new URL(STS_URL);
            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(oracle.openStream()))) {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    STSValue = Double.parseDouble(inputLine);

                }
            }
            return STSValue;
        } catch (MalformedURLException ex) {
            System.err.println("Error! Connect to Internet please");
            Logger
                    .getLogger(GradeEssayAddressTopic.class
                            .getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            System.err.println("Error! Connect to Internet please-1");
            Logger
                    .getLogger(GradeEssayAddressTopic.class
                            .getName()).log(Level.SEVERE, null, ex);
        }
        return STSValue;
    }

    public List<String> PartofspeechTag(String essay) {
        return new StanfordNLP().posTagging(essay);
    }

    public List<String[]> essayBroken(String essay) {
        return null;
    }

    public List<String> getStringbyWords(String sentenceOrPOS) {

        return Arrays.asList(sentenceOrPOS.toLowerCase().split("[^A-Za-z0-9]"));
    }

    public String[] getPOS(String POS) {

        return POS.split(",");
    }

    public List<String> sentenceSPlit(String essay) {
        return new StanfordNLP().sentenceSplit(essay);
    }

    public Set<String> getNounList(String essay) {

        List<String> nounList = new ArrayList<>();
        List<String> wordList = new ArrayList<>();
        List<String> sentences = sentenceSPlit(essay);
        String[] posArray;
        for (String sentence : sentences) {
            List<String> pos = PartofspeechTag(sentence);
            //System.out.println(pos);
            //System.out.println(sentence);

            wordList = getStringbyWords(sentence);
            //wordList.add();
            pos.remove(pos.size() - 1);
            //System.out.println(wordList);
            if (pos.size() == wordList.size()) {
                for (int i = 0; i < wordList.size(); i++) {
                    if (pos.get(i).contains("NN")) {
                        nounList.add(wordList.get(i));
                    }
                }
            } else {
                //  System.out.println("Not Equal");
            }

        }
        //System.out.println("\n\n" + sentences);
        Set<String> nounset = new HashSet<String>(nounList);
        return nounset;
    }

}
