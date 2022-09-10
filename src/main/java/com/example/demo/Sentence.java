package com.example.demo;

import java.io.Serializable;
import java.util.ArrayList;

public class Sentence implements Serializable {
    public ArrayList<String> badSentence;
    public ArrayList<String> goodSentence;

    public Sentence() {
        this(null, null);
    }

    public Sentence(ArrayList<String> badSentence, ArrayList<String> goodSentence) {
        this.badSentence = badSentence;
        this.goodSentence = goodSentence;
    }

    public ArrayList<String> getBadSentence() {
        return badSentence;
    }

    public void setBadSentence(ArrayList<String> badSentence) {
        this.badSentence = badSentence;
    }

    public ArrayList<String> getGoodSentence() {
        return goodSentence;
    }

    public void setGoodSentence(ArrayList<String> goodSentence) {
        this.goodSentence = goodSentence;
    }
}
