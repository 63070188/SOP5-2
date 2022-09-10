package com.example.demo;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SentenceConsumer {
    protected Sentence sentence;
    protected ArrayList<String> keepBadSentence, keepGoodSentence;
    public SentenceConsumer() {
        sentence = new Sentence();
        keepBadSentence = new ArrayList<>();
        keepGoodSentence = new ArrayList<>();
    }

    @RabbitListener(queues = "BadWordQueue")
    public void addBadSentence(String s){
        keepBadSentence.add(String.valueOf(s));
        sentence.setBadSentence(keepBadSentence);
        System.out.println("In addBadSentence Method : "+sentence.getBadSentence());
    }

    @RabbitListener(queues = "GoodWordQueue")
    public void addGoodSentence(String s){
        keepGoodSentence.add(String.valueOf(s));
        sentence.setGoodSentence(keepGoodSentence);
        System.out.println("In addGoodSentence Method : "+sentence.getGoodSentence());
    }

    @RabbitListener(queues = "GetQueue")
    public Sentence getSentence() {
        return sentence;
    }
}
