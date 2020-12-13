package history;

public class ConditionService {

    public static  Condition getConditionByUrl(String url) {
        randomDelay();
        String[] split = url.split("/");
        return new Condition(Integer.parseInt(split[split.length - 1]), url);
    }

    private static void randomDelay() {
        int delay = 1000;
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}