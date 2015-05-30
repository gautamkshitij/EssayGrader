/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SpellChecker_1a;

/**
 *
 * @author kshitijgautam
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import Grade.FinalGrade;
import org.apache.lucene.search.spell.PlainTextDictionary;
import org.apache.lucene.search.spell.SpellChecker;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import StanfordToolkit.StanfordNLP;
import java.util.List;
import java.util.Set;

public class GradeSpellCheck {

    static String punctutations = ".,:;'";
    static File dir;
    static Directory directory;
    static SpellChecker spellChecker;

    void initilize() {
        dir = new File("/Users/kshitijgautam/NetBeansProjects/igupta5_kgauta3/");

        try {
            directory = FSDirectory.open(dir);
            spellChecker = new SpellChecker(directory);
            spellChecker.indexDictionary(
                    new PlainTextDictionary(new File("/Users/kshitijgautam/NetBeansProjects/igupta5_kgauta3/lib/fulldictionary00.txt")));

        } catch (IOException ex) {
            Logger.getLogger(GradeSpellCheck.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public GradeSpellCheck(String essay) {
        initilize();
        gradeSpellings(essay);
    }

    public void gradeSpellings() {
    }

    public void gradeSpellings(String essay) {
        int countMistakes = 0;
        List<String> words = new StanfordNLP().tokenize(essay);
        for (String word : words) {

            try {
                if (word.length() > 2 && !word.contains(punctutations)) {
                    if (!spellChecker.exist(word.toLowerCase().trim())) {
                        //  System.out.println(word);
                        countMistakes++;
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(GradeSpellCheck.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        ApplyGrade(countMistakes);
    }

    public void ApplyGrade(int numberOfMistakes) {
        float score = (float) 0.0;
        //average mistakes in high =8.3, medium 12.7, high = 13.2
        if (numberOfMistakes <= 8 && numberOfMistakes > 0) {
            score = (float) (numberOfMistakes * 0.05);

        }
        if (numberOfMistakes > 8 && numberOfMistakes <= 12) {
            score = (float) ((8 * 0.05) + (numberOfMistakes - 8) * 0.1);
        }

        if (numberOfMistakes > 12) {
            score = (float) ((8 * 0.05) + (4 * 0.1) + (numberOfMistakes - 12) * 0.15);
        }
        if ((5 - score) < 1) {
            FinalGrade.scorespellcheck = 1;
        } else {
            FinalGrade.scorespellcheck = 5 - score;
        }

//System.err.println(score+"-"+numberOfMistakes);
    }

    public static void main(String[] args) {

        new GradeSpellCheck("Helluho").ApplyGrade(18);

    }

}
