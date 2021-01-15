package otus.tisch;

import com.google.common.base.Joiner;

public class HelloOtus {
    public static void main(String[] args) {
        Joiner joiner = Joiner.on("!");
        String[] greetingWords = {"Hello", "Nice", "To", "Meet", "You"};
        String[] words = (args.length == 0) ? greetingWords : args;
        String result = joiner.join(words);

        System.out.println(result);
    }
}