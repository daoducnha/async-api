package history;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class App {

    private final static Executor executor = Executors.newFixedThreadPool(100, r -> {
        Thread t = new Thread(r);
        t.setDaemon(true);
        return t;
    });

    public static void main(String[] args) {
        System.out.println("Starting sequence task.......................");
        long start = System.nanoTime();

        getConditions_sequence().forEach(System.out::println);

        long end = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Task sequence done in: " + end + " msecs");
        System.out.println("===============================================");
        System.out.println("Starting parallel task..........................");
        start = System.nanoTime();

        List<Condition> conditions = getConditions_v1();
        if(!conditions.isEmpty()){
            conditions.add(new Condition("100", "Currnet condition"));
        }
        conditions.forEach(System.out::println);

        end = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Task parallel done in: " + end + " msecs");

    }

    public static List<Condition> getConditions() {
        List<String> urls = new ArrayList<>();
        List<Condition> conditions = new ArrayList<>();

        CompletableFuture[] futures = findSnapshotUrls()
                .map(f -> f.thenAccept(urls::addAll))
                .toArray(CompletableFuture[]::new);
        CompletableFuture.allOf(futures).join();

        CompletableFuture[] futures1 = findConditionByUrls(urls)
                .map(f -> f.thenAccept(conditions::add))
                .toArray(CompletableFuture[]::new);

        CompletableFuture.allOf(futures1).join();

        return conditions;
    }

    private static Stream<CompletableFuture<List<String>>> findSnapshotUrls() {
        List<String> types = Arrays.asList("A1", "L1");
        ConditionSnapshotService conditionSnapshotService = new ConditionSnapshotService();
        return types.stream()
                .map(s -> CompletableFuture.supplyAsync(
                        () -> conditionSnapshotService.getSnapshotsUrl(s), executor
                ));
    }

    private static Stream<CompletableFuture<Condition>> findConditionByUrls(List<String> urls) {
        ConditionService conditionService = new ConditionService();
        return urls.stream()
                .map(s -> CompletableFuture.supplyAsync(
                        () -> conditionService.getConditionByUrl(s), executor)
                );
    }


    public static List<Condition> getConditions_v1() {
        ConditionSnapshotService conditionSnapshotService = new ConditionSnapshotService();
        ConditionService conditionService = new ConditionService();

        List<String> types = Arrays.asList("A1", "L1");
        List<String> rs = new ArrayList<>();


        List<CompletableFuture<List<String>>> urlsFuture = types.stream()
                .map(s -> CompletableFuture.supplyAsync(
                        () -> conditionSnapshotService.getSnapshotsUrl(s)
                )).collect(Collectors.toList());


        List<String> urls = urlsFuture.stream()
                .map(CompletableFuture::join)
                .flatMap(List::stream)
                .collect(Collectors.toList());


        List<CompletableFuture<Condition>> conditionFuture = urls.stream()
                .map(s -> CompletableFuture.supplyAsync(
                        () -> conditionService.getConditionByUrl(s)
                )).collect(Collectors.toList());

        return conditionFuture.stream().map(CompletableFuture::join)
                .sorted(Comparator.comparing(Condition::getId))
                .collect(Collectors.toList());
    }


    public static List<Condition> getConditions_sequence() {
        ConditionSnapshotService conditionSnapshotService = new ConditionSnapshotService();
        ConditionService conditionService = new ConditionService();

        List<String> types = Arrays.asList("A1", "L1");
        List<String> rs = new ArrayList<>();

        List<String> urls = types.stream()
                .map(conditionSnapshotService::getSnapshotsUrl)
                .flatMap(List::stream).collect(Collectors.toList());

        return urls.parallelStream()
                .map(conditionService::getConditionByUrl)
                .sorted(Comparator.comparing(Condition::getId))
                .collect(Collectors.toList());

    }


}