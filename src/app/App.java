package app;


import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class App {
    static List<Shop> shops = Arrays.asList(new Shop("Best price"),
            new Shop("LetsSaveBig"),
            new Shop("LetsSaveBig"),
            new Shop("LetsSaveBig1"),
            new Shop("LetsSaveBig2"),
            new Shop("LetsSaveBig3"),
            new Shop("LetsSaveBig4"),
            new Shop("LetsSaveBig5"),
            new Shop("LetsSaveBig6"),
            new Shop("LetsSaveBig7"),
            new Shop("LetsSaveBig8"),
            new Shop("LetsSaveBig8"),
            new Shop("LetsSaveBig8"),
            new Shop("LetsSaveBig8"),
            new Shop("LetsSaveBig8"),
            new Shop("LetsSaveBig8"),
            new Shop("LetsSaveBig8"),
            new Shop("LetsSaveBig8"),
            new Shop("LetsSaveBig8"),
            new Shop("LetsSaveBig8"),
            new Shop("LetsSaveBig8"),
            new Shop("LetsSaveBig8"),
            new Shop(" BuyItAll"));

    public final static Executor executor = Executors.newFixedThreadPool(Math.min(shops.size(), 100), thread -> {
        Thread t = new Thread(thread);
        t.setDaemon(true);
        return t;
    });

    public static void main(String[] args) {
//        Shop shop = new Shop();
//        long start = System.nanoTime();
//        Future<Double> futurePrice = shop.getPriceAsync("ny favorite product");
//        long invocationTime = ((System.nanoTime() - start) / 1_000_000);
//        System.out.println("Invocation Time: " + invocationTime + " msecs");
//        System.out.println("Do something......");
//        try {
//            double price = futurePrice.get();
//            System.out.println("Do something v2......");
//            System.out.println("Price is " + price);
//        } catch (Exception e){
//            throw new RuntimeException(e);
//        }
//        long retrievalTime = ((System.nanoTime() -start)/1_000_000);
//        System.out.println("Price return after "+retrievalTime+" msecs");

        long start = System.nanoTime();
//        System.out.println(findPrices("myPhone27S"));
        CompletableFuture[] futures = findPricesStream("myPhone")
                .map(f -> f.thenAccept(s -> System.out.println(s + " done in " + ((System.nanoTime() -start)/1_000_000) + " msecs")))
                .toArray(CompletableFuture[]::new);
        CompletableFuture.allOf(futures).join();
        long duration = (System.nanoTime() - start) / 1000000;
        System.out.println("Done in " + duration + " msecs");
    }

    public static Stream<CompletableFuture<String>> findPricesStream(String product) {
        return shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(() -> shop.getPrice(product), executor))
                .map(future -> future.thenApply(Quote::parse))
                .map(future -> future.thenCompose(quote ->
                        CompletableFuture.supplyAsync(() -> Discount.applyDiscount(quote), executor)));

    }

    public static List<String> findPrices(String product) {
        List<CompletableFuture<String>> priceFutures =
                shops.stream()
                        .map(shop -> CompletableFuture.supplyAsync(
                                () -> shop.getPrice(product), executor
                        ))
                        .map(future -> future.thenApply(Quote::parse))
                        .map(future -> future.thenCompose(quote -> CompletableFuture.supplyAsync(
                                () -> Discount.applyDiscount(quote), executor
                        )))
                        .collect(Collectors.toList());

        return priceFutures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }

    public static List<String> findPrices_2(String product) {
        return shops.stream()
                .map(shop -> shop.getPrice(product))
                .map(Quote::parse)
                .map(Discount::applyDiscount)
                .collect(Collectors.toList());
    }

    public static List<String> findPrices_(String product) {
        List<CompletableFuture<String>> priceFutures = shops.stream()
                .map(shop ->
                        CompletableFuture.supplyAsync(() -> shop.getName() + " price is " + shop.getPrice(product), executor))
                .collect(Collectors.toList());

        return priceFutures.stream().map(CompletableFuture::join)
                .collect(Collectors.toList());
    }

    public static List<String> findPrices_old(String product) {
        return shops.parallelStream()
                .map(shop -> String.format("%s price is %.2f",
                        shop.name, shop.getPrice(product)))
                .collect(Collectors.toList());
    }
}