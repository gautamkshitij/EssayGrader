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
import edu.stanford.nlp.trees.Dependencies;
import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.GrammaticalStructureFactory;
import edu.stanford.nlp.trees.PennTreebankLanguagePack;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
import edu.stanford.nlp.trees.TreebankLanguagePack;
import edu.stanford.nlp.trees.TypedDependency;
import edu.stanford.nlp.util.CoreMap;
import java.util.Collection;
import java.util.function.Consumer;

public class StanfordNLPExamples {

    public static List<String> tokenize(String text) {
        Properties props = new Properties();

        props.setProperty("annotators", "tokenize");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        // create an empty Annotation just with the given text
        Annotation document = new Annotation(text);

        // run all Annotators on this text
        pipeline.annotate(document);
        List<CoreLabel> tokens = document.get(TokensAnnotation.class);

        List<String> result = new ArrayList<>();

        for (CoreLabel token : tokens) {

            String word = token.get(TextAnnotation.class);

            result.add(word);

        }
        return result;
    }

    public synchronized static List<String> sentenceSplit(String text) {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

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

    public static List<String> posTagging(String text) {
        Properties props = new Properties();
        props.put("annotators", "tokenize, ssplit, pos");
        props.put("tagger", "edu/stanford/nlp/models/pos-tagger/wsj-bidirectional/wsj-0-18-bidirectional-distsim.tagger");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        // create an empty Annotation just with the given text
        Annotation document = new Annotation(text);

        // run all Annotators on this text
        pipeline.annotate(document);
        List<CoreLabel> tokens = document.get(TokensAnnotation.class);

        List<String> result = new ArrayList<>();
        tokens.stream().map((token) -> token.get(PartOfSpeechAnnotation.class)).forEach((pos) -> {
            result.add(pos);
        });

        return result;
    }

    public static List<String> lemmatize(String text) {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos, lemma");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

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

    public static List<String> ner(String text) {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

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

    public static List<Tree> parse(String text) {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos, lemma, parse");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

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

    public static Collection lexParse(String text) {
        // List<Collection> collection = new ArrayList<>();
        Collection tdl = null;
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos, lemma, parse");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

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

    public static Map<Integer, CorefChain> coreferenceResolution(String text) {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        // create an empty Annotation just with the given text
        Annotation document = new Annotation(text);

        // run all Annotators on this text
        pipeline.annotate(document);

        return document.get(CorefChainAnnotation.class);
    }

    public static void main(String[] args) {
        String text
                = "Pierre Vinken, 61 years old, will join the board as a nonexecutive director Nov. 29. "
                + "Mr. Vinken is chairman of Elsevier N.V., the Dutch publishing group. "
                + "Rudolph Agnew, 55 years old and former chairman of Consolidated Gold Fields PLC, "
                + "was named a director of this British industrial conglomerate.";

        List<String> tokens = tokenize(text);
        tokens.stream().forEach((token) -> {
            System.out.print("<" + token + "> ");
        });
        System.out.println();
        System.out.println("=====================================================");

        List<String> sentences = sentenceSplit(text);
        sentences.stream().forEach((sentence) -> {
            System.out.println(sentence);
        });
        System.out.println("=====================================================");

        List<String> posTags = posTagging(text);
        posTags.stream().forEach((posTag) -> {
            System.out.print(posTag + " ");
        });
        System.out.println();
        System.out.println("=====================================================");

        List<String> lemmas = lemmatize(text);
        lemmas.stream().forEach((lemma) -> {
            System.out.print("<" + lemma + "> ");
        });
        System.out.println();
        System.out.println("=====================================================");

        List<String> nerTags = ner(text);
        nerTags.stream().forEach((nerTag) -> {
            System.out.print("<" + nerTag + "> ");
        });
        System.out.println();
        System.out.println("=====================================================");

        List<Tree> trees = parse(text);
        trees.stream().forEach((tree) -> {
            System.out.println(tree);
        });
        System.out.println("=====================================================");

        text
                = "Victoria Chen, Chief Financial Officer of Megabucks Banking Corp since 2004, saw her pay jump 20%, to $1.3 million, as the 37-year-old also became the Denver-based financial-services company’s president. It has been ten years since she came to Megabucks from rival Lotsabucks.";
        Map<Integer, CorefChain> graph = coreferenceResolution(text);
        graph.entrySet().stream().forEach((entry) -> {
            System.out.println(entry);
        });
    }
}
