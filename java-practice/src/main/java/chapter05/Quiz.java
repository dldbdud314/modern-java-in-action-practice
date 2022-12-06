package chapter05;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Quiz {

    static Trader raoul = new Trader("Raoul", "Cambridge");
    static Trader mario = new Trader("Mario", "Milan");
    static Trader alan = new Trader("Alan", "Cambridge");
    static Trader brian = new Trader("Brian", "Cambridge");

    static List<Transaction> transactions = Arrays.asList(
            new Transaction(brian, 2011, 300),
            new Transaction(raoul, 2012, 1000),
            new Transaction(raoul, 2011, 400),
            new Transaction(mario, 2012, 710),
            new Transaction(mario, 2012, 700),
            new Transaction(alan, 2012, 950)
    );

    public static void main(String[] args) {

        System.out.println(solution1());
        System.out.println(solution2());
        System.out.println(solution3());
        System.out.println(solution4());
        System.out.println(solution5());
        solution6();
        System.out.println(solution7());
        System.out.println(solution8());
    }

    private static List<Transaction> solution1() {
        System.out.println("1. 2011년에 일어난 모든 트랜젝션을 찾아 값을 오름차순으로 정리하시오.");
        return transactions.stream()
                .filter(transaction -> transaction.getYear() == 2011)
                .sorted(Comparator.comparingInt(Transaction::getValue))
                .collect(Collectors.toList());
    }

    private static List<String> solution2() {
        System.out.println("\n2. 거래자가 근무하는 모든 도시를 중복 없이 나열하시오.");
        return transactions.stream()
                .map(transaction -> transaction.getTrader().getCity())
                .distinct()
                .collect(Collectors.toList());
    }

    private static List<Trader> solution3() {
        System.out.println("\n3. 케임브리지에서 근무하는 모든 거래자를 찾아서 이름순으로 정렬하시오.");
        return transactions.stream()
                .takeWhile(transaction -> transaction.getTrader().getCity().equals("Cambridge"))
                .map(Transaction::getTrader)
                .sorted(Comparator.comparing(Trader::getName))
                .collect(Collectors.toList());
    }

    private static List<String> solution4() {
        System.out.println("\n4. 모든 거래자의 이름을 알파벳 순으로 정렬해서 반환하시오.");
        return transactions.stream()
                .map(Transaction::getTrader)
                .map(Trader::getName)
                .sorted()
                .distinct()
                .collect(Collectors.toList());
    }

    private static boolean solution5() {
        System.out.println("\n5. 밀라노에 거래자가 있는가?");
        return transactions.stream()
                .map(Transaction::getTrader)
                .anyMatch(trader -> trader.getCity().equals("Milan"));
    }

    private static void solution6() {
        System.out.println("\n6. 케임브리지에 거주하는 거래자의 모든 트랜젝션 값을 출력하시오.");
        transactions.stream()
                .filter(transaction -> transaction.getTrader().getCity().equals("Cambridge"))
                .forEach(transaction -> System.out.println(transaction.getValue()));
    }

    private static int solution7() {
        System.out.println("\n7. 전체 트랜젝션 중 최댓값은 얼마인가?");
        return transactions.stream()
                .map(Transaction::getValue)
                .reduce(Integer::max)
                .orElse(-1);
    }

    private static int solution8() {
        System.out.println("\n8. 전체 트랜젝션 중 최솟값은 얼마인가?");
        return transactions.stream()
                .map(Transaction::getValue)
                .reduce(Integer::min)
                .orElse(9999);
    }


}
