package DFillFill;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Fillfill {
    public static void main(String[] args) throws IOException {
        // Объявляем переменную для хранения количества заказов
        int numOrders = 0;
        // Объявляем переменную для хранения количества запросов
        int numQueries = 0;
        // Объявляем коллекцию для хранения каждого заказа
        List<Order> orderList = new ArrayList<>();
        // Объявляем коллекцию для хранения каждого запроса
        List<Query> queryList = new ArrayList<>();
        // пытаемся прочитать файл "input" через try с ресурсами
        try (BufferedReader reader = new BufferedReader(new FileReader("input.txt"))) {
            String line = reader.readLine();
            // Парсим количество строк заказов
            numOrders = Integer.parseInt(line);
            // В цикле перебираем каждую строку заказа
            for (int i = 0; i < numOrders; i++) {
                // Читаем строку
                line = reader.readLine();
                // Разбиваем строку на массив подстрок с помощью разделителя " "
                String[] numbers = line.split(" ");
                // Если получилось 3 подстроки, то все ок (проверяем, чтобы не схватить NullPointerException)
                if (numbers.length == 3) {
                    orderList.add(new Order(
                            Integer.parseInt(numbers[0]),
                            Integer.parseInt(numbers[1]),
                            Integer.parseInt(numbers[2])
                    ));
                }
            }
            // Читаем строку
            line = reader.readLine();
            // Парсим количество строк запросов (проверяем, действительно ли там одно число)
            if (line.split(" ").length == 1) {
                numQueries = Integer.parseInt(line);
            }
            // В цикле перебираем каждую строку запроса и запоминаем в коллекцию
            for (int a = 0; a < numQueries; a++) {
                // Читаем строку
                line = reader.readLine();
                // Разбиваем строку на массив подстрок с помощью разделителя " "
                String[] numbers = line.split(" ");
                // Если получилось 3 подстроки, то все ок (проверяем, чтобы не схватить NullPointerException)
                if (numbers.length == 3) {
                    // Если тип запроса 1, то запоминаем его как false, если 2 - то true, если иной - то не запоминаем
                    if (numbers[2].equals("1")) {
                        queryList.add(new Query(Integer.parseInt(numbers[0]), Integer.parseInt(numbers[1]), false));
                    } else if (numbers[2].equals("2")) {
                        queryList.add(new Query(Integer.parseInt(numbers[0]), Integer.parseInt(numbers[1]), true));
                    }
                }
            }
        }

        // Перебираем прочитанные коллекции заказов и запросов и запоминаем в объект Query результаты запросов
        for (Query query : queryList) {
            for (Order order : orderList) {
                if (!query.isType() && order.getStart() >= query.getStart() && order.getStart() <= query.getEnd()) {
                    query.setResult(query.getResult() + order.getCost());
                } else if (query.isType() && order.getEnd() >= query.getStart() && order.getEnd() <= query.getEnd()) {
                    query.setResult(query.getResult() + order.getDuration());
                }
            }
        }

        // Выводим все результаты запросов в файл "output.txt"
        try (FileWriter writer = new FileWriter("output.txt")) {
            for (Query query : queryList) {
                writer.write(query.getResult() + " ");
            }
        }

    }
}

// Класс для хранения объектов "Заказ"
class Order {
    private int start;
    private int end;
    private int cost;
    public Order(int start, int end, int cost) {
        this.start = start;
        this.end = end;
        this.cost = cost;
    }
    public int getStart() {
        return start;
    }
    public int getEnd() {
        return end;
    }
    public int getCost() {
        return cost;
    }
    public int getDuration() {
        return end - start;
    }
}

// Класс для хранения объектов "Запрос"
class Query {
    private int start;
    private int end;
    // false соответствует типу запроса "1", true - типу запроса "2".
    private boolean type;
    private int result;

    public Query(int start, int end, boolean type) {
        this.start = start;
        this.end = end;
        this.type = type;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public boolean isType() {
        return type;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }
}

