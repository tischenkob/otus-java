package ru.otus.model;

public class GameResult {
    private static final String RESULT_PATTERN = "Уважаемый: %s. Всего было примеров: %d, отвечено верно: %d";

    private final Player player;
    private int total;
    private int rightAnswers;

    public GameResult(Player player) {
        this.player = player;
    }

    public void incrementAnswers(boolean answerIsRight) {
        total++;
        if (answerIsRight) {
            ++rightAnswers;
        }
    }

    @Override
    public String toString() {
        return String.format(RESULT_PATTERN, player.getName(),total, rightAnswers);
    }
}
