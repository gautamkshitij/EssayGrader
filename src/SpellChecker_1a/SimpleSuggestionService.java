/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SpellChecker_1a;

import java.io.File;

import org.apache.lucene.search.spell.PlainTextDictionary;
import org.apache.lucene.search.spell.SpellChecker;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class SimpleSuggestionService {

    public static void main(String[] args) throws Exception {

        File dir = new File("/Users/kshitijgautam/NetBeansProjects/igupta5_kgauta3/");

        Directory directory = FSDirectory.open(dir);

        SpellChecker spellChecker = new SpellChecker(directory);

        spellChecker.indexDictionary(
                new PlainTextDictionary(new File("/Users/kshitijgautam/NetBeansProjects/igupta5_kgauta3/fulldictionary00.txt")));

        String wordForSuggestions = "hwllo";

        int suggestionsNumber = 10;

        String[] suggestions = spellChecker.
                suggestSimilar(wordForSuggestions, suggestionsNumber);

        if (suggestions != null && suggestions.length > 0) {
            for (String word : suggestions) {
                System.out.println("Did you mean:" + word);
            }
        } else {
            System.out.println("No suggestions found for word:" + wordForSuggestions);
        }

    }

}
