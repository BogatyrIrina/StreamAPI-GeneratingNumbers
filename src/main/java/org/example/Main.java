package org.example;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<Order> orders = List.of(
                new Order("Laptop", 1200.0),
                new Order("Smartphone", 800.0),
                new Order("Laptop", 1500.0),
                new Order("Tablet", 500.0),
                new Order("Smartphone", 900.0)
        );

        // 1. Группируем заказы по продуктам
        Map<String, List<Order>> ordersByProduct = orders.stream()
                .collect(Collectors.groupingBy(Order::getProduct));

        // 2. Для каждого продукта находим общую стоимость всех заказов
        Map<String, Double> totalCostByProduct = ordersByProduct.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue().stream()
                                .mapToDouble(Order::getCost)
                                .sum()
                ));

        // 3. Сортируем продукты по убыванию общей стоимости
        List<Map.Entry<String, Double>> sortedProducts = totalCostByProduct.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toList());

        // 4. Выбираем три самых дорогих продукта
        List<Map.Entry<String, Double>> topThreeProducts = sortedProducts.stream()
                .limit(3)
                .collect(Collectors.toList());

        // 5. Выводим результат
        System.out.println("Три самых дорогих продукта и их общая стоимость:");
        topThreeProducts.forEach(p -> System.out.printf("%s: $%.2f%n", p.getKey(), p.getValue()));
    }
}