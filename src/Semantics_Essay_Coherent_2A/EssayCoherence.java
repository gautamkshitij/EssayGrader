/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Semantics_Essay_Coherent_2A;

import Grade.FinalGrade;
import StanfordToolkit.StanfordNLP;
import edu.stanford.nlp.dcoref.CorefChain;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 *
 * @author itikagupta
 */
public class EssayCoherence {

    public List<String> PartofspeechTag(String essay) {
        return new StanfordNLP().posTagging(essay);
    }

    public List<String> sentenceSPlit(String essay) {
        return new StanfordNLP().sentenceSplit(essay);
    }

    public void applyGrade(int wronSentencess) {

        FinalGrade.scorecoherent = 5 - 0.1 * wronSentencess;
        if (FinalGrade.scorecoherent < 1) {
            FinalGrade.scorecoherent = 1;
        }

        if (FinalGrade.scorecoherent > 5) {
            FinalGrade.scorecoherent = 5;
        }
    }

    public EssayCoherence(String essay) {

        List<String> sentences = new ArrayList<>();
        List<String> words = new ArrayList<>();
        List<String> thirdpronoun = new ArrayList<>();
        List<Integer> sentenceno = new ArrayList<>();
        Map<Integer, CorefChain> coref;
        int wrongref = 0;

        sentences = sentenceSPlit(essay);
        for (int i = 0; i < sentences.size(); i++) {
            words = Arrays.asList(sentences.get(i).toLowerCase().split("[^A-Za-z0-9]"));
            // System.out.println(words);
            //System.out.println(thirdpronoun);
            if (!words.isEmpty()) {
                for (String temp : words) {
                    if (temp.equals("he")) {
                        sentenceno.add(i);
                        thirdpronoun.add("he");
                    } else if (temp.equals("she")) {
                        sentenceno.add(i);
                        thirdpronoun.add("she");
                    } else if (temp.equals("it")) {
                        sentenceno.add(i);
                        thirdpronoun.add("it");
                    } else if (temp.equals("him")) {
                        sentenceno.add(i);
                        thirdpronoun.add("him");
                    } else if (temp.equals("her")) {
                        sentenceno.add(i);
                        thirdpronoun.add("her");
                    } else if (temp.equals("his")) {
                        sentenceno.add(i);
                        thirdpronoun.add("his");
                    } else if (temp.equals("hers")) {
                        sentenceno.add(i);
                        thirdpronoun.add("hers");
                    } else if (temp.equals("its")) {
                        sentenceno.add(i);
                        thirdpronoun.add("its");
                    } else if (temp.equals("they")) {
                        sentenceno.add(i);
                        thirdpronoun.add("they");
                    } else if (temp.equals("them")) {
                        sentenceno.add(i);
                        thirdpronoun.add("them");
                    } else if (temp.equals("their")) {
                        sentenceno.add(i);
                        thirdpronoun.add("their");
                    } else if (temp.equals("theirs")) {
                        sentenceno.add(i);
                        thirdpronoun.add("theirs");
                    }
                }

            }

            //System.out.println(words);
        }
        List<String> tempList;
        String[] arr, temparr;
        String temp, tempp = "", temporary = "";
        if (!thirdpronoun.isEmpty()) {
            coref = new StanfordNLP().coreferenceResolution(essay);

            arr = coref.toString().split("],");
            for (int j = 0; j < thirdpronoun.size(); j++) {
                for (int k = 0; k < arr.length; k++) {
                    if (arr[k].contains("\"" + thirdpronoun.get(j) + "\""))//exit when satisfy for one
                    {
                        temp = arr[k].substring(arr[k].indexOf("[") + 1, arr[k].length());
                        temparr = temp.split("[0-9],");//what if in oe paranthesis
                        for (int l = 0; l < temparr.length; l++) {
                            if (temparr.length > 2) {
                                //System.out.println(temparr.length + "-" + temparr[l]);

                                if (temparr[l].indexOf("\"") + 1 < temparr[l].length() && temparr[l].lastIndexOf("\"") < temparr[l].length()
                                        && (temparr[l].indexOf("\"") + 1 != temparr[l].lastIndexOf("\""))) {
                                    //System.out.println("hello");

                                    temparr[l] = temparr[l].substring(temparr[l].indexOf("\"") + 1, temparr[l].lastIndexOf("\""));
                                    tempp += temparr[l] + " ";
                                    //System.err.println(temparr[l]);
                                }
                            }
                            tempList = PartofspeechTag(tempp);
                            for (int m = 0; m < tempList.size(); m++) {
                                temporary += tempList.get(m) + " ";
                            }
                            if (thirdpronoun.get(j).equals("he") || thirdpronoun.get(j).equals("she")
                                    || thirdpronoun.get(j).equals("his") || thirdpronoun.get(j).equals("her")
                                    || thirdpronoun.get(j).equals("hers") || thirdpronoun.get(j).equals("him")
                                    || thirdpronoun.get(j).equals("it") | thirdpronoun.get(j).equals("its")) {
                                if (!temporary.contains(" NN ") && !temporary.contains(" NNP ")) {
                                    wrongref++;
                                }
                            } else if (thirdpronoun.get(j).equals("they") || thirdpronoun.get(j).equals("them")
                                    || thirdpronoun.get(j).equals("their") || thirdpronoun.get(j).equals("theirs")) {
                                if (!temporary.contains("NNS") && !temporary.contains("NNPS")) {
                                    wrongref++;
                                }
                            }

                            //System.out.println("arr[k]");
                            //System.out.println("third"+thirdpronoun.get(j));
                            //System.out.println("YEs");
                        }
                    }
                }

                /*System.out.println(coref.values());
                 if(coref.values().contains("\""+thirdpronoun.get(j)+"\" "+ "Sentence "+ sentenceno.get(j)))
                 System.out.println("\""+thirdpronoun.get(j)+"\" "+ "Sentence "+ sentenceno.get(j));*/
            }

            // System.out.println("contains");
            //System.out.println(coref);
            //System.out.println(coref.values());
               /* for(int j=0;j<thirdpronoun.size();j++)
             {
             thirdpronoun.get(j);
             //if(coref.containsValue("=CHAINS[\""+thirdpronoun.get(j)+"\" sentence"+" "+sentenceno.get(j)+"]"))
             //System.err.println("No reference");
             }*/
        }
        //   System.out.println(wrongref);
        applyGrade(wrongref);
    }
}
