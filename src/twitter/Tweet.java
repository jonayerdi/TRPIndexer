package twitter;

import com.twitter.Autolink;
import com.twitter.Extractor;

import java.util.*;

/**
 * Created by User on 28/09/2016.
 */
public class Tweet {

    public static final String[] TO_REMOVE_FROM_TERMS
            = {".",":",",",";","\n","\r","?","!","&","'","\"","(",")"};

    protected Extractor extractor;
    protected Autolink autolink;

    protected PorterStemmer stemmer;

    protected Integer id;

    protected String text;
    protected String processedText;

    protected List<String> users;
    protected List<String> hashtags;
    protected List<String> cashtags;
    protected List<String> terms;

    public Tweet(Integer id, String text) {
        this.id = id;
        this.text = text;
        this.processedText = text;
        this.extractor = new Extractor();
        this.autolink = new Autolink();
        this.stemmer = new PorterStemmer();
        this.users = new ArrayList<String>();
        this.hashtags = new ArrayList<String>();
        this.cashtags = new ArrayList<String>();
        this.terms = new ArrayList<String>();
    }

    public Tweet(String text) {
        this(null, text);
    }

    public static Tweet create(String text) {
        Tweet tweet = new Tweet(text);
        return tweet;
    }

    public void process() {
        this.processText();
        this.splitTerms();
    }

    public void processText() {
        List<Extractor.Entity> entities = extractor.extractEntitiesWithIndices(processedText);
        entities.sort(new Comparator<Extractor.Entity>() {
            public int compare(Extractor.Entity o1, Extractor.Entity o2) {
                return (o1.getStart() > o2.getStart() ? -1 : 1);
            }
        });
        for(Extractor.Entity entity : entities) {
            switch (entity.getType()) {
                case HASHTAG:
                    hashtags.add(entity.getValue());
                    processedText = processedText.replaceFirst("#" + entity.getValue(), "");
                    break;
                case MENTION:
                    users.add(entity.getValue());
                    processedText = processedText.replaceFirst("@" + entity.getValue(), "");
                    break;
                case CASHTAG:
                    cashtags.add(entity.getValue());
                    processedText = processedText.replaceFirst("$" + entity.getValue(), "");
                    break;
                case URL:
                    processedText = processedText.replaceFirst(entity.getValue(), "");
                    break;
            }
        }
    }

    public void splitTerms() {
        for(String term : processedText.split(" ")) {
            for(String remove : TO_REMOVE_FROM_TERMS)
                term = term.replace(remove, "");
            term = term.toLowerCase();
            term = stemmer.stem(term);
            if(!term.isEmpty())
                terms.add(term);
        }
    }

    public String toString() {
        String text = "";
        text += "Tweet ID = " + id + "\n";
        text += "Original text:\n";
        text += " " + this.text + "\n";
        text += "Processed text:\n";
        text += " " + this.processedText + "\n";
        text += "Users:\n";
        for(String user : users) {
            text += " " + user + "\n";
        }
        text += "Hashtags:\n";
        for(String hashtag : hashtags) {
            text += " " + hashtag + "\n";
        }
        text += "Cashtags:\n";
        for(String cashtag : cashtags) {
            text += " " + cashtag + "\n";
        }
        text += "Terms:\n";
        for(String term : terms) {
            text += " " + term + "\n";
        }
        return text;
    }

    public Integer getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getProcessedText() {
        return processedText;
    }

    public List<String> getUsers() {
        return users;
    }

    public List<String> getHashtags() {
        return hashtags;
    }

    public List<String> getCashtags() {
        return cashtags;
    }

    public List<String> getTerms() {
        return terms;
    }

}
