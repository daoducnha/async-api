package history;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class App {


    public static void main(String[] args) throws InterruptedException, ExecutionException {
        System.out.println("Starting parallel task.......................");
        long start = System.nanoTime();

        List<Condition> conditions_parallel = getConditions_v2();
        conditions_parallel.add(new Condition(100, "Currnet condition"));
        conditions_parallel.forEach(System.out::println);

        long end = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Task sequence done in: " + end + " msecs");
        System.out.println("===============================================");
        System.out.println("Starting parallel_v1 task..........................");
        start = System.nanoTime();

        List<Condition> conditions = getConditions_v1();
        if (!conditions.isEmpty()) {
            conditions.add(new Condition(100, "Currnet condition"));
        }
        conditions.forEach(System.out::println);

        end = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Task parallel done in: " + end + " msecs");

    }


    private static CompletableFuture<List<String>> getSnapshotsByType(String type) {
        return CompletableFuture.supplyAsync(() -> ConditionSnapshotService.getSnapshotsUrl(type));
    }

    public static List<Condition> getConditions_v2() throws InterruptedException, ExecutionException {
        Executor executor = getExecutor();
        List<String> urls = new ArrayList<>();
        List<Condition> conditions = new ArrayList<>();

        List<String> types = Arrays.asList("A1", "L1");
        List<CompletableFuture<List<String>>> completableFutures = types.stream()
                .map(type -> CompletableFuture.supplyAsync(() -> ConditionSnapshotService.getSnapshotsUrl(type)))
                .collect(Collectors.toList());

        CompletableFuture<List<String>> allSnapshotLinksFutures = CompletableFuture
                .allOf(completableFutures.toArray(CompletableFuture[]::new)).thenApply(v ->
                        completableFutures.stream()
                                .map(CompletableFuture::join)
                                .flatMap(List::stream)
                                .collect(Collectors.toList())
                );


        CompletableFuture<List<Condition>> cf = allSnapshotLinksFutures.thenApply(strings ->
                strings.stream()
                        .map(s -> CompletableFuture.supplyAsync(() -> ConditionService.getConditionByUrl(s)))
                        .map(CompletableFuture::join)
                        .collect(Collectors.toList())
        );

        return cf.get();


//                .stream()
//                .map(url -> CompletableFuture.supplyAsync(() -> ConditionService.getConditionByUrl(url)))
//                .collect(Collectors.toList());

//        CompletableFuture<Void> allFutures = CompletableFuture.allOf(conditionFutures.toArray(CompletableFuture[]::new));
//
//        CompletableFuture<List<Condition>> allConditionFuture = allFutures.thenApply(v ->
//                conditionFutures.stream()
//                        .map(CompletableFuture::join)
//                        .collect(Collectors.toList()))
//                .thenApply(conditionList ->
//                        conditionList.stream()
//                                .sorted(Comparator.comparing(Condition::getId))
//                                .collect(Collectors.toList()));
//        return allConditionFuture.get();
    }


    public static List<Condition> getConditions_v1_1() {
        List<Condition> rs = new ArrayList<>();
        Executor executor = getExecutor();

        List<CompletableFuture<Condition>> conditionFutures = ConditionSnapshotService.getSnapshotsAllUrls().stream()
                .map(url -> CompletableFuture.supplyAsync(
                        () -> ConditionService.getConditionByUrl(url), executor))

                .collect(Collectors.toList());

        return conditionFutures.stream()
                .map(future -> future.whenComplete((condition, throwable) -> {
                    if (throwable != null) {
                        System.out.println("Error: " + throwable.getMessage());
                    }
                }))
                .map(CompletableFuture::join)
                .sorted(Comparator.comparing(Condition::getId))
                .collect(Collectors.toList());
    }

    public static List<Condition> getConditions_v1() throws InterruptedException, ExecutionException {

        List<String> types = Arrays.asList("A1", "L1");

        Executor executor = getExecutor();

        List<CompletableFuture<List<String>>> urlsFutures = types.stream()
                .map(s -> CompletableFuture.supplyAsync(() -> ConditionSnapshotService.getSnapshotsUrl(s), executor))
                .collect(Collectors.toList());

        List<String> urls = urlsFutures.stream()
                .map(CompletableFuture::join)
                .flatMap(List::stream)
                .collect(Collectors.toList());


        List<CompletableFuture<Condition>> conditionFuture = urls.stream()
                .map(s -> CompletableFuture.supplyAsync(() -> ConditionService.getConditionByUrl(s), executor))
                .collect(Collectors.toList());

        return conditionFuture.stream()
                .map(CompletableFuture::join)
                .sorted(Comparator.comparing(Condition::getId))
                .collect(Collectors.toList());
    }

    private static Stream<CompletableFuture<List<String>>> getUrlsStream(Executor executor) {
        List<String> types = Arrays.asList("A1", "L1");
        return types.stream()
                .map(type -> CompletableFuture.supplyAsync(
                        () -> ConditionSnapshotService.getSnapshotsUrl(type), executor));
    }

    private static Stream<CompletableFuture<Condition>> getConditionsStream(List<String> urls, Executor executor) {
        return urls.stream()
                .map(s -> CompletableFuture.supplyAsync(
                        () -> ConditionService.getConditionByUrl(s), executor
                ));
    }

    public static List<Condition> getConditions_parallel() {

        List<String> types = Arrays.asList("A1", "L1");
        List<String> rs = new ArrayList<>();

        List<String> urls = types.parallelStream()
                .map(ConditionSnapshotService::getSnapshotsUrl)
                .flatMap(List::stream).collect(Collectors.toList());

        return urls.parallelStream()
                .map(ConditionService::getConditionByUrl)
                .sorted(Comparator.comparing(Condition::getId))
                .collect(Collectors.toList());

    }


    private static Executor getExecutor() {
        int numOfCores = Runtime.getRuntime().availableProcessors();
        int poolSize = (int) (numOfCores * 1 * (1 + 50 / 5));
        System.out.println("pool size: " + poolSize);
        return Executors.newFixedThreadPool(50, r -> {
            Thread t = new Thread(r);
            t.setDaemon(true);
            return t;
        });
    }
}