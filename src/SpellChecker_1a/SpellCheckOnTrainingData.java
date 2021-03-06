/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SpellChecker_1a;

import lengthOfEssay_3a.*;
import StanfordToolkit.StanfordNLPExamples;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import org.apache.lucene.search.spell.PlainTextDictionary;
import org.apache.lucene.search.spell.SpellChecker;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

/**
 *
 * @author itikagupta
 */
public class SpellCheckOnTrainingData {

    static String punctutations = ".,:;'";
    static String text;
    static float size, count;
    static List<Float> essayLength; //stores number of sentences in each essay
    static List<Float> essayWords;  //stores number of words in each essay
    static List<Float> essayWordsPerSentence; //stores words per sentence
    static List<Float> essayAvgWordsPerSentence;
    static List<String> essaySentences; //stores sentences after split
    static Multimap<String, Integer> length = HashMultimap.create();
    static HashMap<String, Float> avgsentence = new HashMap<>();
    static HashMap<String, Float> avgwords = new HashMap<>();
    static HashMap<String, Float> avgwordsinsentence = new HashMap<>();
    static int countSpell = 0;
    static List<Float> spellchecker;

    public static void main(String args[]) throws IOException {
        // readFolder("/Users/kshitijgautam/NetBeansProjects/igupta5_kgauta3/src/input/training/original/high", "high");
        //readFolder("/Users/kshitijgautam/NetBeansProjects/igupta5_kgauta3/src/input/training/original/medium", "medium");
        readFolder("/Users/kshitijgautam/NetBeansProjects/igupta5_kgauta3/src/input/training/original/low", "low");
//        System.out.println(avgsentence);
//        System.out.println(avgwords);
//        System.out.println(avgwordsinsentence);
    }

    static String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    static synchronized void readFolder(String path, String type) throws IOException {
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        //System.out.println("LIST OF FILE"+listOfFiles.si);
        size = 0;
        count = 0;
        essayLength = new ArrayList<>();
        essayWords = new ArrayList<>();
        essaySentences = new ArrayList<>();
        essayAvgWordsPerSentence = new ArrayList<>();
        essayWordsPerSentence = new ArrayList<>();
        for (File file : listOfFiles) {
            essayWordsPerSentence = new ArrayList<>();
            if (file.isFile()) {
                numberOfSentences(file);
                SpellCheckEssays(new StanfordToolkit.StanfordNLP().tokenize(text));

            }
        }
        System.err.println(countSpell + "-" + countSpell / 20.0);
        avgsentence.put(type, average(count, essayLength));
        avgwords.put(type, average(count, essayWords));
        avgwordsinsentence.put(type, average(count, essayAvgWordsPerSentence));
    }

    static void numberOfSentences(File file) throws IOException {
        count++;
        text = readFile(file.getCanonicalPath(), StandardCharsets.UTF_8);
        essaySentences = StanfordNLPExamples.sentenceSplit(text);
        essayLength.add((float) essaySentences.size());//number of sentences in each essay
        essayWords.add((float) StanfordNLPExamples.tokenize(text).size()); //total words in an essay
        essaySentences.stream().forEach((essaySentence) -> {
            essayWordsPerSentence.add((float) StanfordNLPExamples.tokenize(essaySentence).size());
        });
        essayAvgWordsPerSentence.add(average((float) essaySentences.size(), essayWordsPerSentence));
    }

    static float average(float count, List<Float> essayLength) {
        essayLength.stream().forEach((essayLength1) -> {
            size += essayLength1;
        });
        size = size / count;
        return size;
    }

    static File dir;
    static Directory directory;
    static SpellChecker spellChecker;

    static void initilize() {
        dir = new File("/Users/kshitijgautam/NetBeansProjects/igupta5_kgauta3");

        try {
            directory = FSDirectory.open(dir);
            spellChecker = new SpellChecker(directory);
            spellChecker.indexDictionary(
                    new PlainTextDictionary(new File("/Users/kshitijgautam/NetBeansProjects/igupta5_kgauta3/lib/fulldictionary00.txt")));

        } catch (IOException ex) {
            Logger.getLogger(GradeSpellCheck.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void SpellCheckEssays(List<String> wordsPerEssay) throws IOException {

        initilize();

        for (String word : wordsPerEssay) {
            if (word.length() > 2 && !word.contains(punctutations)) {
                if (!spellChecker.exist(word.toLowerCase().trim())) {
                    System.out.println(word);
                    countSpell++;
                }
            }
        }

    }
}
