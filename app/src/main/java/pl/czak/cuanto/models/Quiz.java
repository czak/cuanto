package pl.czak.cuanto.models;

import java.util.Random;

import pl.czak.num2words.Translator;

/**
 * Created by czak on 24.01.2015.
 */
public class Quiz {
    long seed;

    public Quiz() {
        this.seed = new Random().nextLong();
    }

    public long getSeed() {
        return seed;
    }

    public void setSeed(long seed) {
        this.seed = seed;
    }

    public Card getCard(int position) {
        int num = new Random(seed + position).nextInt(1001);
        return new Card(num);
    }

    public static class Card {
        int question;
        boolean isAnswered;

        public Card(int question) {
            this.question = question;
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
}