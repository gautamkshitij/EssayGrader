/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package StanfordToolkit;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import edu.stanford.nlp.dcoref.CorefChain;
import edu.stanford.nlp.dcoref.CorefCoreAnnotations.CorefChainAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.GrammaticalStructureFactory;
import edu.stanford.nlp.trees.PennTreebankLanguagePack;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
import edu.stanford.nlp.trees.TreebankLanguagePack;
import edu.stanford.nlp.util.CoreMap;
import java.util.Collection;

/**
 *
 * @author kshitijgautam
 */
public class StanfordNLP {

    static StanfordCoreNLP pipeline = null;

    public static void initialize(Properties props) {
        pipeline = new StanfordCoreNLP(props);
    }

    public List<String> tokenize(String text) {
        Properties props = new Properties();

        props.setProperty("annotators", "tokenize");
        initialize(props);

        // create an empty Annotation just with the given text
        Annotation document = new Annotation(text);

        // run all Annotators on this text
        pipeline.annotate(document);
        List<CoreLabel> tokens = document.get(TokensAnnotation.class);

        List<String> result = new ArrayList<>();
        tokens.stream().map((token) -> token.get(TextAnnotation.class)).forEach((word) -> {
            result.add(word);
        });

        return result;
    }

    public int countTokens(String text) {
        Properties props = new Properties();

        props.setProperty("annotators", "tokenize");
        initialize(props);

        // create an empty Annotation just with the given text
        Annotation document = new Annotation(text);

        // run all Annotators on this text
        pipeline.annotate(document);
        List<CoreLabel> tokens = document.get(TokensAnnotation.class);

        List<String> result = new ArrayList<>();
        tokens.stream().map((token) -> token.get(TextAnnotation.class)).forEach((word) -> {
            result.add(word);
        });

        return result.size();
    }

    public List<String> sentenceSplit(String text) {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit");
        initialize(props);

        // create an empty Annotation just with the given text
        Annotation document = new Annotation(text);

        // run all Annotators on this text
        pipeline.annotate(document);
        List<CoreMap> sentences = document.get(SentencesAnnotation.class);

        List<String> result = new ArrayList<>();
        sentences.stream().map((sentence) -> {
            String sentenceString = sentence.get(TextAnnotation.class);
            result.add(sentenceString);
            // see tokenize(String) method
            List<CoreLabel> tokens = sentence.get(TokensAnnotation.class);
            return tokens;
        }).forEach((tokens) -> {
            tokens.stream().forEach((token) -> {
                String word = token.get(TextAnnotation.class);
                // ...
            });
        });

        return result;
    }

    public int countSentence(String text) {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit");
        initialize(props);

        // create an empty Annotation just with the given text
        Annotation document = new Annotation(text);

        // run all Annotators on this text
        pipeline.annotate(document);
        List<CoreMap> sentences = document.get(SentencesAnnotation.class);

        List<String> result = new ArrayList<>();
        sentences.stream().map((sentence) -> {
            String sentenceString = sentence.get(TextAnnotation.class);
            result.add(sentenceString);
            // see tokenize(String) method
            List<CoreLabel> tokens = sentence.get(TokensAnnotation.class);
            return tokens;
        }).forEach((tokens) -> {
            tokens.stream().forEach((token) -> {
                String word = token.get(TextAnnotation.class);
                // ...
            });
        });

        return result.size();
    }

    public List<String> posTagging(String essay) {
        Properties props = new Properties();
        props.put("annotators", "tokenize, ssplit, pos");
        props.put("tagger", "edu/stanford/nlp/models/pos-tagger/wsj-bidirectional/wsj-0-18-bidirectional-distsim.tagger");
        initialize(props);

        // create an empty Annotation just with the given text
        Annotation document = new Annotation(essay);

        // run all Annotators on this text
        pipeline.annotate(document);
        List<CoreLabel> tokens = document.get(TokensAnnotation.class);

        List<String> result = new ArrayList<>();
        tokens.stream().map((token) -> token.get(PartOfSpeechAnnotation.class)).forEach((pos) -> {
            result.add(pos);
        });

        return result;
    }

    public List<String> lemmatize(String text) {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos, lemma");
        initialize(props);

        // create an empty Annotation just with the given text
        Annotation document = new Annotation(text);

        // run all Annotators on this text
        pipeline.annotate(document);
        List<CoreLabel> tokens = document.get(TokensAnnotation.class);

        List<String> result = new ArrayList<>();
        tokens.stream().map((token) -> token.get(LemmaAnnotation.class)).forEach((lemma) -> {
            result.add(lemma);
        });

        return result;
    }

    public List<String> ner(String text) {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner");
        initialize(props);

        // create an empty Annotation just with the given text
        Annotation document = new Annotation(text);

        // run all Annotators on this text
        pipeline.annotate(document);
        List<CoreLabel> tokens = document.get(TokensAnnotation.class);

        List<String> result = new ArrayList<>();
        tokens.stream().map((token) -> token.get(NamedEntityTagAnnotation.class)).forEach((nerTag) -> {
            result.add(nerTag);
        });

        return result;
    }

    public List<Tree> parse(String text) {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos, lemma, parse");
        initialize(props);

        // create an empty Annotation just with the given text
        Annotation document = new Annotation(text);

        // run all Annotators on this text
        pipeline.annotate(document);
        List<CoreMap> sentences = document.get(SentencesAnnotation.class);

        List<Tree> result = new ArrayList<>();
        sentences.stream().map((sentence) -> sentence.get(TreeAnnotation.class)).forEach((tree) -> {
            result.add(tree);
        });

        return result;
    }

    public Collection lexParse(String text) {
        // List<Collection> collection = new ArrayList<>();
        Collection tdl = null;
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos, lemma, parse");
        initialize(props);

        // create an empty Annotation just with the given text
        Annotation document = new Annotation(text);

        // run all Annotators on this text
        pipeline.annotate(document);
        List<CoreMap> sentences = document.get(SentencesAnnotation.class);

        List<Tree> result = new ArrayList<>();
        sentences.stream().map((sentence) -> sentence.get(TreeAnnotation.class)).forEach((tree) -> {
            result.add(tree);
        });

        TreebankLanguagePack tlp = new PennTreebankLanguagePack();
        GrammaticalStructureFactory gsf = tlp.grammaticalStructureFactory();
        for (int i = 0; i < result.size(); i++) {
            GrammaticalStructure gs = gsf.newGrammaticalStructure(result.get(i));
            //collection.add(gs.typedDependenciesCollapsed());
            tdl = gs.typedDependenciesCollapsed();
        }

        return tdl;
    }

    public Map<Integer, CorefChain> coreferenceResolution(String text) {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
        initialize(props);

        // create an empty Annotation just with the given text
        Annotation document = new Annotation(text);

        // run all Annotators on this text
        pipeline.annotate(document);

        return document.get(CorefChainAnnotation.class);
    }

}
