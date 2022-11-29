package chapter02;

import static chapter02.FilteringApples.Color.GREEN;
import static chapter02.FilteringApples.Color.RED;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FilteringApples {

    public static void main(String[] args) {
        List<Apple> inventory = Arrays.asList(
                new Apple(80, Color.GREEN),
                new Apple(155, Color.GREEN),
                new Apple(120, Color.RED));

        List<Apple> greenApples = filterApplesByColor(inventory, GREEN);
        List<Apple> redApples = filterApplesByColor(inventory, RED);

        System.out.println("greenApples = " + greenApples);
        System.out.println("redApples = " + redApples);

        // 개별로 !!
        List<Apple> greenApples2 = filterApples(inventory, GREEN, 0, true);
        List<Apple> heavyApples = filterApples(inventory, null, 150, false);

        System.out.println("greenApples2 = " + greenApples2);
        System.out.println("heavyApples = " + heavyApples);

        // 동작 파라미터화
        List<Apple> greenApples3 = filterApples(inventory, new AppleGreenColorPredicate());
        List<Apple> heavyApples2 = filterApples(inventory, new AppleHeavyWeightPredicate());

        System.out.println("greenApples3 = " + greenApples3);
        System.out.println("heavyApples2 = " + heavyApples2);

        prettyPrintApple(inventory, new AppleColorFormatter());
        prettyPrintApple(inventory, new AppleWeightFormatter());
    }

    /**
     * 녹색 사과를 필터링한다
     *
     * @param inventory
     * @return 필터링 된 사과들
     */
    public static List<Apple> filterGreenApples(List<Apple> inventory) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (GREEN.equals(apple.getColor())) {
                result.add(apple);
            }
        }
        return result;
    }

    /**
     * 색을 동작 파라미터화 하기 <br> - why? 다른 색을 필터링하는 경우가 추가될수도!
     *
     * @param inventory
     * @param color
     * @return 필터링 된 사과들
     */
    public static List<Apple> filterApplesByColor(List<Apple> inventory, Color color) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (color.equals(apple.getColor())) {
                result.add(apple);
            }
        }
        return result;
    }

    /**
     * 가능한 모든 속성으로 필터링하기
     *
     * @param inventory
     * @param color
     * @param weight
     * @param flag
     * @return
     */
    public static List<Apple> filterApples(List<Apple> inventory, Color color, int weight, boolean flag) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if ((flag && apple.getColor().equals(color)) ||
                    (!flag && apple.getWeight() > weight)) {
                result.add(apple);
            }
        }
        return result;
    }

    /**
     * 추상적인 조건으로 필터링하기 (<b>동작 파라미터화</b>)
     *
     * @param inventory
     * @param p
     * @return
     */
    public static List<Apple> filterApples(List<Apple> inventory, ApplePredicate p) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (p.test(apple)) {
                result.add(apple);
            }
        }
        return result;
    }

    /**
     * 실습 문제 -> 예쁘게 출력하는 동작을 파라미터화 해보자 !!
     *
     * @param inventory
     * @param f
     */
    public static void prettyPrintApple(List<Apple> inventory, AppleFormatter f) {
        for (Apple apple : inventory){
            String output = f.printInFormat(apple);
            System.out.println(output);
        }
    }

    enum Color {RED, GREEN}

    public static class Apple {

        private int weight = 0;
        private Color color;

        public Apple(int weight, Color color) {
            this.weight = weight;
            this.color = color;
        }

        public int getWeight() {
            return weight;
        }

        public Color getColor() {
            return color;
        }

        @Override
        public String toString() {
            return String.format("Apple{color=%s, weight=%d}", color, weight);
        }
    }

    interface ApplePredicate {
        boolean test(Apple apple);
    }

    static class AppleHeavyWeightPredicate implements ApplePredicate {

        @Override
        public boolean test(Apple apple) {
            return apple.getWeight() > 150;
        }
    }

    static class AppleGreenColorPredicate implements ApplePredicate {

        @Override
        public boolean test(Apple apple) {
            return GREEN.equals(apple.getColor());
        }
    }

    public interface AppleFormatter {
        String printInFormat(Apple apple);
    }

    static class AppleWeightFormatter implements AppleFormatter {

        @Override
        public String printInFormat(Apple apple) {
            return String.format("This is an apple of %d", apple.getWeight());
        }
    }

    static class AppleColorFormatter implements AppleFormatter{

        @Override
        public String printInFormat(Apple apple) {
            return String.format("This is a %s apple", apple.getColor());
        }
    }
}
