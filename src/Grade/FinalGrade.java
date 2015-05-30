/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Grade;

/**
 *
 * @author kshitijgautam
 */
public class FinalGrade {

    //part1
    public static double scorespellcheck = 0.0;
    public static double scoreSubjectverbAgreement = 0.0;
    public static double scorelengthOfEssay = 0.0;
    public static double scoremissingVerb = 0.0;
    //part 2
    public static double scorecoherent = 0.0;
    public static double scoreaddressTopic = 0.0;
    public static double scoresentenceformation = 0.0;

    public static double finalScore;
    //(float) (1 * scorespellcheck + 1 * scoreSubjectverbAgreement + 1 * scoremissingVerb
    //+ 2 * scorecoherent + 2 * scoresentenceformation + 3 * scoreaddressTopic + 2 * scorelengthOfEssay);
    public static String finalGrade = "unknown"; //low, medium or high

}
