package history;

import java.util.Arrays;
import java.util.List;

public class ConditionSnapshotService {

    public static List<String> getSnapshotsAllUrls() {
        randomDelay();
        return Arrays.asList("example.com/L1/1",
                "example.com/L1/2",
                "example.com/L1/3",
                "example.com/L1/4",
                "example.com/L1/5",
                "example.com/L1/6",
                "example.com/L1/7",
                "example.com/L1/8",
                "example.com/L1/9",
                "example.com/L1/12",
                "example.com/L1/13",
                "example.com/L1/14",
                "example.com/L1/15",

                "example.com/L1/20",
                "example.com/L1/21",
                "example.com/L1/22",
                "example.com/L1/33",
                "example.com/L1/34",
                "example.com/L1/35",
                "example.com/L1/36",
                "example.com/L1/23",
                "example.com/L1/24",
                "example.com/L1/25",
                "example.com/L1/26",
                "example.com/L1/27",
                "example.com/L1/28",
                "example.com/L1/29",
                "example.com/L1/30",
                "example.com/L1/31",
                "example.com/L1/32",
                "example.com/L1/37",
                "example.com/L1/38",
                "example.com/L1/39",
                "example.com/L1/40",
                "example.com/L1/41",
                "example.com/L1/42",
                "example.com/L1/43",
                "example.com/L1/44",
                "example.com/A1/3",
                "example.com/A1/4",
                "example.com/A1/5",
                "example.com/A1/6",
                "example.com/L1/10",
                "example.com/L1/11",
                "example.com/L1/16",
                "example.com/L1/17",
                "example.com/L1/18",
                "example.com/L1/19",
                "example.com/A1/7",
                "example.com/A1/8",
                "example.com/A1/9",
                "example.com/L1/45",
                "example.com/L1/46",
                "example.com/L1/47",
                "example.com/L1/48",
                "example.com/L1/49",
                "example.com/L1/50",
                "example.com/A1/1",
                "example.com/A1/2",
                "example.com/A1/10",
                "example.com/A1/11",
                "example.com/A1/17",
                "example.com/A1/18",
                "example.com/A1/19",
                "example.com/A1/20",
                "example.com/A1/21",
                "example.com/A1/22",
                "example.com/A1/23",
                "example.com/A1/24",
                "example.com/A1/25",
                "example.com/A1/26",
                "example.com/A1/12",
                "example.com/A1/13",
                "example.com/A1/14",
                "example.com/A1/15",
                "example.com/A1/16",
                "example.com/A1/27",
                "example.com/A1/28",
                "example.com/A1/29",
                "example.com/A1/30",
                "example.com/A1/31",
                "example.com/A1/36",
                "example.com/A1/37",
                "example.com/A1/38",
                "example.com/A1/32",
                "example.com/A1/33",
                "example.com/A1/34",
                "example.com/A1/35",
                "example.com/A1/39",
                "example.com/A1/40",
                "example.com/A1/41",
                "example.com/A1/42",
                "example.com/A1/43",
                "example.com/A1/44",
                "example.com/A1/45",
                "example.com/A1/46",
                "example.com/A1/47",
                "example.com/A1/48",
                "example.com/A1/49",
                "example.com/A1/50"
        );
    }

    public static List<String> getSnapshotsUrl(String type) {
        randomDelay();
        if ("L1".equals(type)) {
            return Arrays.asList("example.com/L1/1",
                    "example.com/L1/2",
                    "example.com/L1/3",
                    "example.com/L1/4",
                    "example.com/L1/5",
                    "example.com/L1/6",
                    "example.com/L1/7",
                    "example.com/L1/8",
                    "example.com/L1/9",
                    "example.com/L1/10",
                    "example.com/L1/11",
                    "example.com/L1/12",
                    "example.com/L1/13",
                    "example.com/L1/14",
                    "example.com/L1/15",
                    "example.com/L1/16",
                    "example.com/L1/17",
                    "example.com/L1/18",
                    "example.com/L1/19",
                    "example.com/L1/20",
                    "example.com/L1/21",
                    "example.com/L1/22",
                    "example.com/L1/23",
                    "example.com/L1/24",
                    "example.com/L1/25",
                    "example.com/L1/26",
                    "example.com/L1/27",
                    "example.com/L1/28",
                    "example.com/L1/29",
                    "example.com/L1/30",
                    "example.com/L1/31",
                    "example.com/L1/32",
                    "example.com/L1/33",
                    "example.com/L1/34",
                    "example.com/L1/35",
                    "example.com/L1/36",
                    "example.com/L1/37",
                    "example.com/L1/38",
                    "example.com/L1/39",
                    "example.com/L1/40",
                    "example.com/L1/41",
                    "example.com/L1/42",
                    "example.com/L1/43",
                    "example.com/L1/44",
                    "example.com/L1/45",
                    "example.com/L1/46",
                    "example.com/L1/47",
                    "example.com/L1/48",
                    "example.com/L1/49",
                    "example.com/L1/50"
            );
        }

        return Arrays.asList("example.com/A1/1",
                "example.com/A1/2",
                "example.com/A1/3",
                "example.com/A1/4",
                "example.com/A1/5",
                "example.com/A1/6",
                "example.com/A1/7",
                "example.com/A1/8",
                "example.com/A1/9",
                "example.com/A1/10",
                "example.com/A1/11",
                "example.com/A1/12",
                "example.com/A1/13",
                "example.com/A1/14",
                "example.com/A1/15",
                "example.com/A1/16",
                "example.com/A1/17",
                "example.com/A1/18",
                "example.com/A1/19",
                "example.com/A1/20",
                "example.com/A1/21",
                "example.com/A1/22",
                "example.com/A1/23",
                "example.com/A1/24",
                "example.com/A1/25",
                "example.com/A1/26",
                "example.com/A1/27",
                "example.com/A1/28",
                "example.com/A1/29",
                "example.com/A1/30",
                "example.com/A1/31",
                "example.com/A1/32",
                "example.com/A1/33",
                "example.com/A1/34",
                "example.com/A1/35",
                "example.com/A1/36",
                "example.com/A1/37",
                "example.com/A1/38",
                "example.com/A1/39",
                "example.com/A1/40",
                "example.com/A1/41",
                "example.com/A1/42",
                "example.com/A1/43",
                "example.com/A1/44",
                "example.com/A1/45",
                "example.com/A1/46",
                "example.com/A1/47",
                "example.com/A1/48",
                "example.com/A1/49",
                "example.com/A1/50"
        );
    }


    private static void randomDelay() {
        int delay = 1000;
//                + new Random().nextInt(2000);
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}