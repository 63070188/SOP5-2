package com.example.demo;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.ArrayList;

public class Word implements Serializable {
    public ArrayList<String> badWord;
    public ArrayList<String> goodWords;

    public Word() {
        this(null,null);
    }

    public Word(ArrayList<String> badWord, ArrayList<String> goodWords) {
        this.badWord = badWord;
        this.goodWords = goodWords;
    }

    public ArrayList<String> getGoodWords() {
        return goodWords;
    }

    public void setGoodWords(ArrayList<String> goodWords) {
        this.goodWords = goodWords;
    }

    public ArrayList<String> getBadWord() {
        return badWord;
    }

    public void setBadWord(ArrayList<String> badWord) {
        this.badWord = badWord;
    }
}
