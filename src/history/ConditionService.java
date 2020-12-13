package history;

import java.util.Random;

public class ConditionService {

    public Condition getConditionByUrl(String url) {
        randomDelay();
        String[] split = url.split("/");

        return new Condition(split[split.length - 1], url);
    }

    private void randomDelay() {
        int delay = 1000;
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}