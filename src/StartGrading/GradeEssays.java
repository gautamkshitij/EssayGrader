/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package StartGrading;

import Grade.FinalGrade;
import static Grade.FinalGrade.scoreSubjectverbAgreement;
import static Grade.FinalGrade.scoreaddressTopic;
import static Grade.FinalGrade.scorecoherent;
import static Grade.FinalGrade.scorelengthOfEssay;
import static Grade.FinalGrade.scoremissingVerb;
import static Grade.FinalGrade.scoresentenceformation;
import static Grade.FinalGrade.scorespellcheck;
import Semantics_EssayAddressTopic_2B.GradeEssayAddressTopic;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.apache.commons.io.FileUtils;
import sentenceFormation_1D.GradingSentenceFormation;
import Semantics_Essay_Coherent_2A.EssayCoherence;
import SpellChecker_1a.GradeSpellCheck;
import lengthOfEssay_3a.GradingLength;
import missingVerbAgreement_1c.GradeMissingTenses;
import subectVerbAgreement_1b.GradeSubjectVerb;

/**
 *
 * @author kshitijgautam
 */
public class GradeEssays {

    static PrintWriter writer;

    public static void StartGrading(String type) throws IOException {
        //String essay = " Hello this are mac Hello  monkey kshitij heelo this is mac book pro 15 inch better than any other laptop in world.Hello this is mac book pro 15 inch better than any other laptop in world.Hello this is mac book pro 15 inch better than any other laptop in world.Hello this is mac book pro 15 inch better than any other laptop in world.Hello this is mac book pro 15 inch better than any other laptop in world.Hello this is mac book pro 15 inch better than any other laptop in world.Hello this is mac book pro 15 inch better than any other laptop in world.Hello this is mac book pro 15 inch better than any other laptop in world.Hello this is mac book pro 15 inch better than any other laptop in world.Hello this is mac book pro 15 inch better than any other laptop in world.Hello this is mac book pro 15 inch better than any other laptop in world.Hello this is mac book pro 15 inch better than any other laptop in world.Hello this is mac book pro 15 inch better than any other laptop in world.Hello this is mac book pro 15 inch better than any other laptop in world.Hello this is mac book pro 15 inch better than any other laptop in world.Hello this is mac book pro 15 inch better than any other laptop in world.Hello this is mac book pro 15 inch better than any other laptop in world.Hello this is mac book pro 15 inch better than any other laptop in world.Hello this is mac book pro 15 inch better than any other laptop in world.Hello this is mac book pro 15 inch better than any other laptop in world.";
       //Change URL Here
        File folder = new File("/Users/kshitijgautam/NetBeansProjects/igupta5_kgauta3/input/test/original/" + type);
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            File file = listOfFiles[i];
            if (file.isFile() && file.getName().endsWith(".txt")) {
                String essay = FileUtils.readFileToString(file);
                //System.out.println(essay);
//                new GradingLength(essay);
                new GradeSpellCheck(essay);
//                new GradeSubjectVerb(essay);
//                new GradeMissingTenses(essay);
//                new GradingSentenceFormation(essay); //1D
//                new GradeEssayAddressTopic(essay); //2B
//                new EssayCoherence(essay);
                WriteShowgrades(file.getName());
            }
        }

    }

    public static void WriteShowgrades(String fileName) throws FileNotFoundException, UnsupportedEncodingException {
        //    System.out.println(fileName + "\t\t" + FinalGrade.scorespellcheck + "\t\t" + FinalGrade.scoreSubjectverbAgreement
        //          + "\t\t" + FinalGrade.scoremissingVerb + "\t\t" + FinalGrade.scoresentenceformation
        //        + "\t\t" + FinalGrade.scorecoherent + "\t" + FinalGrade.scoreaddressTopic
        //      + "\t\t" + FinalGrade.scorelengthOfEssay + "\t\t" + FinalGrade.finalScore + "\t\t" + FinalGrade.finalGrade);

        FinalGrade.finalScore = 0;
        // System.err.println("::" + FinalGrade.finalScore);
        FinalGrade.finalScore = (1 * round(scorespellcheck, 2) + round(scoreSubjectverbAgreement, 2) + round(scoremissingVerb, 2)
                + (2 * round(scorecoherent, 2)) + (2 * round(scoresentenceformation, 2)) + (3 * round(scoreaddressTopic, 2)) + (2 * round(scorelengthOfEssay, 2)));
        // System.err.println("--" + FinalGrade.finalScore);
        if (FinalGrade.finalScore >= 50) {
            FinalGrade.finalGrade = "high";
        }
        if (FinalGrade.finalScore < 50 && FinalGrade.finalScore >= 45) {

            FinalGrade.finalGrade = "medium";
        }

        if (FinalGrade.finalScore < 45 && FinalGrade.finalScore >= 12) {

            FinalGrade.finalGrade = "low";
        }
        writer.println(fileName + "\t\t" + round(FinalGrade.scorespellcheck, 2) + "\t\t" + round(FinalGrade.scoreSubjectverbAgreement, 2)
                + "\t\t" + round(FinalGrade.scoremissingVerb, 2) + "\t\t" + round(FinalGrade.scoresentenceformation, 2)
                + "\t\t" + round(FinalGrade.scorecoherent, 2) + "\t" + round(FinalGrade.scoreaddressTopic, 2)
                + "\t\t" + round(FinalGrade.scorelengthOfEssay, 2) + "\t\t" + round(FinalGrade.finalScore, 2) + "\t\t" + FinalGrade.finalGrade);

    }

    public static double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static void main(String[] args) throws IOException {
        //change URL here
        writer = new PrintWriter("/Users/kshitijgautam/NetBeansProjects/igupta5_kgauta3/output/" + "FinalGrades.txt", "UTF-8");
        StartGrading("high"); //6 //AddressEssay average = 0.5813621680000001, lowest 52, highest 0.63880825 //average 6 highest 11 //average 11 wrong sentences
        StartGrading("medium"); //13 //addressEssay average = 0.560997474 // 27 lowest, 67 highest // average 5
        StartGrading("low"); //18 //addressEssay average = 0.5678095145000001 //  26 lowest 70 // average 3
        writer.close();
    }

}
