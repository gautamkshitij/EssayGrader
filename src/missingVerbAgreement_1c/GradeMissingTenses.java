/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package missingVerbAgreement_1c;

import java.util.List;
import StanfordToolkit.StanfordNLP;
import Grade.FinalGrade;

/**
 *
 * @author kshitijgautam
 */
public class GradeMissingTenses {

    static int wrongTenseSentences = 0;

    public GradeMissingTenses(String essay) {
        GradeTenses(essay);
        // System.out.println("WrongTensese" + wrongTenseSentences);
        applyGrade(wrongTenseSentences);

    }

    public void applyGrade(int wrongTenseSentences) {
        FinalGrade.scoremissingVerb = 5 - 0.05 * wrongTenseSentences;
        if (FinalGrade.scoremissingVerb < 1) {
            FinalGrade.scoremissingVerb = 1;
        }

        if (FinalGrade.scoremissingVerb > 5) {
            FinalGrade.scoremissingVerb = 5;
        }

    }

    public List<String> PartofspeechTag(String essay) {
        return new StanfordNLP().posTagging(essay);
    }

    public List<String> sentenceSPlit(String essay) {
        return new StanfordNLP().sentenceSplit(essay);
    }

    public void GradeTenses(String essay) {

        for (String sentence : sentenceSPlit(essay)) {
            String postTaggString = PartofspeechTag(sentence).toString().toLowerCase().trim();

            sentence = sentence.toLowerCase().trim();
            // System.out.println("All \t" + sentence);
            //System.out.println("AllPost \t" + postTaggString);
            if ((sentence.contains("he") || sentence.contains("she") || sentence.contains("it")
                    || postTaggString.contains("nn") || postTaggString.contains("nnp")) && postTaggString.contains("vbz")) {
                //  System.out.println(1);
//SImple Present
            } else if ((postTaggString.contains("nns") || postTaggString.contains("nnps") || sentence.contains("they")
                    || sentence.contains("we") || sentence.contains("i") || sentence.contains("you"))
                    && postTaggString.contains("vbp")) {
                // System.out.println(2);
//simple present

            } else if ((postTaggString.contains("nns") || postTaggString.contains("nnps") || sentence.contains("they")
                    || sentence.contains("we") || sentence.contains("i") || sentence.contains("you")
                    || sentence.contains("he") || sentence.contains("she") || sentence.contains("it")
                    || postTaggString.contains("nn") || postTaggString.contains("nnp")) && postTaggString.contains("vbd")) {
                // System.out.println(3);
//simple past                

            } else if ((postTaggString.contains("nns") || postTaggString.contains("nnps") || sentence.contains("they")
                    || sentence.contains("we") || sentence.contains("i") || sentence.contains("you")
                    || sentence.contains("he") || sentence.contains("she") || sentence.contains("it")
                    || postTaggString.contains("nn") || postTaggString.contains("nnp")) && (sentence.contains("will")
                    || sentence.contains("shall")) && postTaggString.contains("vb")) {
                // System.out.println(4);
//simple future                

            } else if ((sentence.contains("he") || sentence.contains("she") || sentence.contains("it")
                    || postTaggString.contains("nn") || postTaggString.contains("nnp")) && sentence.contains("is")
                    && postTaggString.contains("vbg")) {
                //  System.out.println(5);
//Present Continuous            
            } else if ((postTaggString.contains("nns") || postTaggString.contains("nnps") || sentence.contains("they")
                    || sentence.contains("we") || sentence.contains("you")) && sentence.contains("are")
                    && postTaggString.contains("vbg")) {
                //System.out.println(6);
//Present Continuous            
            } else if (sentence.contains("I") && sentence.contains("am") && postTaggString.contains("vbg")) {
//Present Continuous   
                // System.out.println(7);

            } else if ((sentence.contains("he") || sentence.contains("she") || sentence.contains("it")
                    || postTaggString.contains("nn") || postTaggString.contains("nnp") || sentence.contains("I"))
                    && sentence.contains("was") && postTaggString.contains("vbg")) {
                //  System.out.println(8);
                //Past continuous           
            } else if ((postTaggString.contains("nns") || postTaggString.contains("nnps") || sentence.contains("they")
                    || sentence.contains("we") || sentence.contains("you"))
                    && sentence.contains("were") && postTaggString.contains("vbg")) {

                //Past continuous           
            } else if ((postTaggString.contains("nns") || postTaggString.contains("nnps") || sentence.contains("they")
                    || sentence.contains("we") || sentence.contains("i") || sentence.contains("you")
                    || sentence.contains("he") || sentence.contains("she") || sentence.contains("it")
                    || postTaggString.contains("nn") || postTaggString.contains("nnp")) && (sentence.contains("will")
                    || sentence.contains("shall")) && sentence.contains("be") && postTaggString.contains("vbg")) {
//future continuous               

            } else if ((sentence.contains("he") || sentence.contains("she") || sentence.contains("it")
                    || postTaggString.contains("nn") || postTaggString.contains("nnp")) && sentence.contains("has")
                    && postTaggString.contains("vbn")) {

// Present Perfect
            } else if ((postTaggString.contains("nns") || postTaggString.contains("nnps") || sentence.contains("they")
                    || sentence.contains("we") || sentence.contains("i") || sentence.contains("you"))
                    && sentence.contains("have") && postTaggString.contains("vbn")) {
//present perfect

            } else if ((postTaggString.contains("nns") || postTaggString.contains("nnps") || sentence.contains("they")
                    || sentence.contains("we") || sentence.contains("i") || sentence.contains("you")
                    || sentence.contains("he") || sentence.contains("she") || sentence.contains("it")
                    || postTaggString.contains("nn") || postTaggString.contains("nnp")) && (sentence.contains("had"))
                    && postTaggString.contains("vbn")) {
//past perfect              

            } else if ((postTaggString.contains("nns") || postTaggString.contains("nnps") || sentence.contains("they")
                    || sentence.contains("we") || sentence.contains("i") || sentence.contains("you")
                    || sentence.contains("he") || sentence.contains("she") || sentence.contains("it")
                    || postTaggString.contains("nn") || postTaggString.contains("nnp")) && (sentence.contains("will")
                    || sentence.contains("shall")) && (sentence.contains("have"))
                    && postTaggString.contains("vbn")) {
//future perfect              

            } else if ((sentence.contains("he") || sentence.contains("she") || sentence.contains("it")
                    || postTaggString.contains("nn") || postTaggString.contains("nnp")) && sentence.contains("has")
                    && sentence.contains("been") && postTaggString.contains("vbg")) {

// Present Perfect continuous
            } else if ((postTaggString.contains("nns") || postTaggString.contains("nnps") || sentence.contains("they")
                    || sentence.contains("we") || sentence.contains("i") || sentence.contains("you"))
                    && sentence.contains("have") && sentence.contains("been") && postTaggString.contains("vbg")) {
//present perfect continuous

            } else if ((postTaggString.contains("nns") || postTaggString.contains("nnps") || sentence.contains("they")
                    || sentence.contains("we") || sentence.contains("i") || sentence.contains("you")
                    || sentence.contains("he") || sentence.contains("she") || sentence.contains("it")
                    || postTaggString.contains("nn") || postTaggString.contains("nnp")) && sentence.contains("had")
                    && sentence.contains("been") && postTaggString.contains("vbg")) {
//past perfect   continuous           

            } else if ((postTaggString.contains("nns") || postTaggString.contains("nnps") || sentence.contains("they")
                    || sentence.contains("we") || sentence.contains("i") || sentence.contains("you")
                    || sentence.contains("he") || sentence.contains("she") || sentence.contains("it")
                    || postTaggString.contains("nn") || postTaggString.contains("nnp")) && (sentence.contains("will")
                    || sentence.contains("shall")) && sentence.contains("have") && sentence.contains("been")
                    && postTaggString.contains("vbg")) {
//future perfect  continuous            

            } else if ((sentence.contains("can") || sentence.contains("could") || sentence.contains("may")
                    || sentence.contains("might") || sentence.contains("must") || sentence.contains("ought to")
                    || sentence.contains("shall") || sentence.contains("should") || sentence.contains("will")
                    || sentence.contains("would")) && postTaggString.contains("vb")) {
//Modal + main verb               
            } else if ((sentence.contains("can") || sentence.contains("could") || sentence.contains("may")
                    || sentence.contains("might") || sentence.contains("must") || sentence.contains("ought to")
                    || sentence.contains("shall") || sentence.contains("should") || sentence.contains("will")
                    || sentence.contains("would")) && sentence.contains("be") && postTaggString.contains("vbg")) {
//modal+be+present participle               
            } else if ((sentence.contains("can") || sentence.contains("could") || sentence.contains("may")
                    || sentence.contains("might") || sentence.contains("must") || sentence.contains("ought to")
                    || sentence.contains("shall") || sentence.contains("should") || sentence.contains("will")
                    || sentence.contains("would")) && sentence.contains("have") && postTaggString.contains("vbn")) {
//modal+have+past participle               
            } else {
                //System.out.println("Wrong \t" + sentence);
                wrongTenseSentences++;
            }

        }
    }
}
