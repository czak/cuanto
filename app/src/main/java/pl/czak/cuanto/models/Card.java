package pl.czak.cuanto.models;

import java.util.Random;

import pl.czak.num2words.Translator;

/**
 * Created by czak on 24.01.2015.
 */
public class Card {
    int question;
    boolean isAnswered;

    public Card(int seed, int position) {
        this.question = new Random(seed + position).nextInt(1001);
    }

    public int getQuestion() {
        return question;
    }

    public String getAnswer() {
        return new Translator().translate(getQuestion());
    }

    public boolean isAnswered() {
        return isAnswered;
    }

    public void setAnswered(boolean isAnswered) {
        this.isAnswered = isAnswered;
    }
}
