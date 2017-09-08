import java.util.List; 
import java.util.*; 
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;
import java.awt.*; //for dimensions
import java.awt.event.*; //for events 
import javax.swing.*; //for guis components
import javax.swing.event.*; // for mouse events
import java.awt.event.ActionListener;

public class MeanTweets {
   
   public static void main(String[] args) throws TwitterException {
   
      System.out.println("Welcome to #MeanTweets"); 
      Scanner console = new Scanner (System.in);
      
      ConfigurationBuilder cb = new ConfigurationBuilder();  
         cb.setDebugEnabled(true) 
            .setOAuthConsumerKey("********************")        
            .setOAuthConsumerSecret("M76p6PB6xPhEawwJJOy73Vv6zxVC97U7kVzzM3EmuA4zIeDjtz")
            .setOAuthAccessToken("737440271076491264-bqWBljSXKCm8qlAIMGTJ5JaAd4qQTMB")
            .setOAuthAccessTokenSecret("i4RTAj4LdsASRKJTgu6lFvt6kON46vCqVCehNyCF34jnv");      
   
         TwitterFactory tf = new TwitterFactory(cb.build());      
         Twitter twitter = tf.getInstance();  
      /*List<Status> status = twitter.getHomeTimeline();      
        for(Status st: status) {        
            System.out.println(st.getUser().getName()+ ": "+ st.getText());
      }*/
      
      System.out.println("Who do you want to search?");      
      String name = console.next() + " " + console.next();
      String tweet = getTweet(name,twitter);
      if(tweet.isEmpty()) {
         System.out.println("No mean tweets about " + name + " at this time");
      } else {
         String[] words = tweet.split("\\s+");
         for (int i = 0; i < words.length; i++) {
            words[i] = words[i].replaceAll("[^\\w]", "");
         }
         String user = "@" + words[1];
         String tweetdisplay = "";
         for(int i = 2; i < words.length; i++) {
            tweetdisplay += words[i] + " ";
         }
         
         System.out.println();
         System.out.println("Would you like to view the tweet Jimmy Kimmel style? (y/n)");
         if(console.next().equals("y")) {
            createDisplay(user,tweetdisplay);   
         } else {
            System.exit(0);
         }
     }
   }
  
   public static String getTweet(String name, Twitter twitter) {
   
   String negWords[] = {"sucks","bad", "terrible", "useless",":(",
                        "hate", "lol","ugly","sh*t","was","not","f***", "b****"};
   String display = "";
   int counter = 0;
   try {      
      Query query = new Query(name);      
      QueryResult result;      
      result = twitter.search(query);    
      List<Status> tweets = result.getTweets();
      for (Status tweet : tweets) {
         String tweetJ = tweet.getText();         
         for(int i = 0 ; i < negWords.length; i++) {
             if (tweetJ.contains(negWords[i])) {              
               if(counter == 0) { 
                  display = tweet.getText();
                  counter++;
               }
               System.out.println("@" + tweet.getUser().getScreenName() + " - " + tweet.getText() + " Tweeted At: " + tweet.getCreatedAt());   

            }
         }
      }
    } catch (TwitterException te) { 
         te.printStackTrace();
         System.out.println("There seem to be no mean tweets on " + name + " at the time. :("); 
  } 
      return display;
  }
      
  public static void createDisplay(String user, String tweetdisplay) {
      JFrame frame = new JFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(new Dimension(800,300));
      frame.setBackground(Color.BLACK);
      frame.setTitle("#MEANTWEETS");
      frame.setResizable(true);
      frame.setVisible(true);
      frame.setLayout(new FlowLayout());
      
      JLabel logo = new JLabel();
      logo.setPreferredSize(new Dimension(120,120));
      logo.setIcon(new ImageIcon("kimmel.jpg"));
      frame.add(logo);
      
      JLabel bird = new JLabel(); 
      bird.setPreferredSize(new Dimension(173,143));
      bird.setIcon(new ImageIcon("bird.jpg"));
      frame.add(bird);
      
      JPanel tweet = new JPanel(new FlowLayout());
      JLabel userdisplay = new JLabel(user);
      JLabel display = new JLabel(tweetdisplay);
      tweet.add(userdisplay);
      tweet.add(display);
      frame.add(tweet);
   
 }
}
