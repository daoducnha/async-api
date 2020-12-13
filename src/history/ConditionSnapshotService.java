package history;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ConditionSnapshotService {


    public List<String> getSnapshotsUrl(String type) {
        randomDelay();
        if ("L1".equals(type)) {
            return Arrays.asList("example.com/L1/1",
                    "example.com/L1/2",
                    "example.com/L1/33",
                    "example.com/L1/333",
                    "example.com/L1/3qwer",
                    "example.com/L1/33",
                    "example.com/L1/3re",
                    "example.com/L1/3ewq",
                    "example.com/L1/3",
                    "example.com/L1/3weqr",
                    "example.com/L1/3",
                    "example.com/L1/3wqe",
                    "example.com/L1/3wqerqwer",
                    "example.com/L1/3",
                    "example.com/L1/3qwer",
                    "example.com/L1/3",
                    "example.com/L1/3",
                    "example.com/L1/qwer3",
                    "example.com/L1/3",
                    "example.com/L1/3erwq32",
                    "example.com/L1/3",
                    "example.com/L1/qwe31",
                    "example.com/L1/3",
                    "example.com/L1/3qwer",
                    "example.com/L1/3",
                    "example.com/L1/4");
        }

        return Arrays.asList("example.com/A1/1",
                "example.com/A1/2",
                "example.com/A1/2",
                "example.com/A1/2",
                "example.com/A1/2",
                "example.com/A1/2",
                "example.com/A1/2",
                "example.com/A1/2",
                "example.com/A1/2",
                "example.com/A1/2",
                "example.com/A1/2",
                "example.com/A1/2",
                "example.com/A1/2",
                "example.com/A1/2",
                "example.com/A1/2",
                "example.com/A1/2",
                "example.com/A1/2",
                "example.com/A1/2",
                "example.com/A1/2",
                "example.com/A1/2",
                "example.com/A1/2",
                "example.com/A1/2",
                "example.com/A1/2",
                "example.com/A1/2",
                "example.com/A1/2",
                "example.com/A1/2",
                "example.com/A1/2",
                "example.com/A1/2",
                "example.com/A1/2",
                "example.com/A1/2",
                "example.com/A1/2",
                "example.com/A1/2",
                "example.com/A1/2",
                "example.com/A1/3",
                "example.com/A1/4",
                "example.com/A1/5");
    }


    private void randomDelay() {
        int delay = 1000 ;
//                + new Random().nextInt(2000);
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}