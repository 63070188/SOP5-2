package com.example.demo;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;


@Route(value = "/index2")
public class MyView2 extends HorizontalLayout {
    private TextField addWord, addSen;
    private TextArea goodSen, badSen;
    private Button addGoodWord, addBadWord, addSentence, showSentence;

    public MyView2(){
        VerticalLayout v = new VerticalLayout();
        v.setAlignItems(FlexComponent.Alignment.STRETCH);
        addWord = new TextField("Add Word");
        addGoodWord = new Button("Add Good Word");
        addBadWord = new Button("Add Bad Word");
        ComboBox<String> goodWords = new ComboBox<>("Good Words");
        ComboBox<String> badWords = new ComboBox<>("Bad Words");
        v.add(addWord, addGoodWord, addBadWord, goodWords, badWords);

        VerticalLayout v2 = new VerticalLayout();
        v2.setAlignItems(FlexComponent.Alignment.STRETCH);
        addSen = new TextField("Add Sentence");
        goodSen = new TextArea("Good Sentences");
        badSen = new TextArea("Bad Sentences");
        addSentence = new Button("Add Sentence");
        showSentence = new Button("showSentence");
        v2.add(addSen, addSentence, goodSen, badSen, showSentence);
        add(v,v2);


        addGoodWord.addClickListener(event -> {
            String word = addWord.getValue();

            ArrayList out = WebClient.create()
                    .post()
                    .uri("http://localhost:8080/addGood/"+word)
                    .retrieve()
                    .bodyToMono(ArrayList.class)
                    .block();

            ArrayList<String> keep = new ArrayList<>();
            for(Object text: out){
                keep.add((String) text);

            }
            goodWords.setItems(out);
            //badWords.setItems(out);
            //System.out.println(String.join(", ", out));
            //ans.setValue(out);

        });

        addBadWord.addClickListener(event -> {
            String word = addWord.getValue();

            ArrayList out = WebClient.create()
                    .post()
                    .uri("http://localhost:8080/addBad/"+word)
                    .retrieve()
                    .bodyToMono(ArrayList.class)
                    .block();

            ArrayList<String> keep = new ArrayList<>();
            for(Object text: out){
                keep.add((String) text);

            }
            badWords.setItems(out);
            //badWords.setItems(out);
            //System.out.println(String.join(", ", out));
            //ans.setValue(out);

        });

        addSentence.addClickListener(event -> {
            String sentence = addSen.getValue();

            String out = WebClient.create()
                    .post()
                    .uri("http://localhost:8080/proof/"+sentence)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            Notification notification = Notification.show(out);

    });
        showSentence.addClickListener(event -> {
            //String sentence = addSen.getValue();

            Sentence out = WebClient.create()
                    .get()
                    .uri("http://localhost:8080/getSentence/")
                    .retrieve()
                    .bodyToMono(Sentence.class)
                    .block();

            goodSen.setValue("[" + String.join(", ", out.getGoodSentence() )+"]");
            badSen.setValue("[" + String.join(", ", out.getBadSentence() )+"]");

        });
    }


}
