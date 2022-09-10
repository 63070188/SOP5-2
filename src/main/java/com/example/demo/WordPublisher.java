package com.example.demo;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class WordPublisher {

    protected Word words;
    private ArrayList<String> keepBad, keepGood;

    public WordPublisher() {
        words = new Word();
        keepBad = new ArrayList<>();
        keepGood = new ArrayList<>();
        keepGood.add("happy");
        keepGood.add("enjoy");
        keepGood.add("life");
        keepBad.add("fuck");
        keepBad.add("olo");
        words.setGoodWords(keepGood);
        words.setBadWord(keepBad);
    }

    @RequestMapping(value = "/addBad/{word}", method = RequestMethod.POST)
    public ArrayList<String> addBadWord(@PathVariable("word") String word){
        keepBad.add(String.valueOf(word));
        words.setBadWord(keepBad);
        return words.getBadWord();
    }

    @RequestMapping(value = "/delBad/{word}", method = RequestMethod.POST)
    public ArrayList<String> deleteBadWord(@PathVariable("word") String word){
        for(int i = 0; i < this.keepBad.size() ; i++){
            if(this.keepBad.get(i).equals(word)){
                this.keepBad.remove(this.keepBad.get(i));
            }
        }
        return words.getBadWord();
    }

    @RequestMapping(value = "/addGood/{word}", method = RequestMethod.POST)
    public ArrayList<String> addGoodWord(@PathVariable("word") String word){
        keepGood.add(String.valueOf(word));
        words.setGoodWords(keepGood);
        return words.getGoodWords();
    }

    @RequestMapping(value = "/delGood/{word}", method = RequestMethod.POST)
    public ArrayList<String> deleteGoodWord(@PathVariable("word") String word){
        for(int i = 0; i < this.keepGood.size() ; i++){
            if(this.keepGood.get(i).equals(word)){
                this.keepGood.remove(this.keepGood.get(i));
            }
        }
        return words.getGoodWords();
    }
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RequestMapping(value = "/proof/{sentence}", method = RequestMethod.POST)
    public String proofSentence(@PathVariable("sentence") String sentence){
        //rabbitTemplate.convertAndSend("Direct","BadWordQueue",sentence);
        //List<String> ansWord = new ArrayList<String>(Arrays.asList(sentence.split(" ")));
        //boolean goodWord = keepGood.containsAll(ansWord);
        //boolean badWord = keepBad.containsAll(ansWord);
        boolean goodWord = false;
        boolean badWord = false;


        for (String check : keepGood){
            goodWord = sentence.contains(check);
            //System.out.println("Check :" + goodWord);
            if (goodWord){
                break;
            }
        }
        for (String check2 : keepBad) {
            badWord = sentence.contains(check2);
            if (badWord){
                break;
            }
        }

        //System.out.println(ansWord);
        //System.out.println(keepBad);
        //System.out.println(keepGood);
        //System.out.println(goodWord);
        //System.out.println(badWord);
        if(goodWord && badWord){
            //System.out.println("Found Bad & Good Word");
            rabbitTemplate.convertAndSend("Fanout","",sentence);
            return  "Found Bad & Good Word";
        }else if(goodWord){
            //System.out.println("Found Good Word");
            rabbitTemplate.convertAndSend("Direct","good",sentence);
            return  "Found Good Word";
        }else if(badWord){
            //System.out.println("Found Bad Word");
            rabbitTemplate.convertAndSend("Direct","bad",sentence);
            return  "Found Bad Word";
        }else{
            return  null;
        }
    }

    @RequestMapping(value = "/getSentence", method = RequestMethod.GET)
    public Sentence getSentence(){
        Object out = rabbitTemplate.convertSendAndReceive("Direct", "queue", "");
        return (Sentence) out;
    }

}

