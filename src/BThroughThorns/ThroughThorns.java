package BThroughThorns;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class ThroughThorns {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
        String firstRow;
        // Считываем первую строку
        firstRow = reader.readLine();
        // Считываем количество строк в вводных данных
        int numOfRows = Integer.parseInt(firstRow);
        // Создаем коллекцию пар Ракета - суммарное время в заказах
        Map<Integer, Line> lines = new HashMap<>();
        // это абсолютно все записи вводных данных
        List<Line> list = new ArrayList<>();
        // Перебираем все записи вводных
        for (int i = 0; i < numOfRows; i++) {
            String line = reader.readLine();
            String[] data = line.split(" ");
            if (data[4].equals("C") || data[4].equals("S")) {
                list.add(new Line(Integer.parseInt(data[3]), (Integer.parseInt(data[2]) + Integer.parseInt(data[1]) * 60 + Integer.parseInt(data[0]) * 24 * 60), false, 0));
            } else if (data[4].equals("A") || data[4].equals("B")) {
                list.add(new Line(Integer.parseInt(data[3]), (Integer.parseInt(data[2]) + Integer.parseInt(data[1]) * 60 + Integer.parseInt(data[0]) * 24 * 60), true, 0));
            }
            lines.put(Integer.parseInt(data[3]), null);
        }
        // Сортируем полный список List, чтобы все записи были по времени
        Collections.sort(list, new Comparator<Line>() {
            @Override
            public int compare(Line o1, Line o2) {
                return Integer.compare(o2.time, o1.time);
            }
        });



        for (Line row : list) {
            if (lines.get(row.id) == null) {
                lines.put(row.id, row);
            } else if (lines.get(row.id).status && !row.status) {
                row.timeInOrder = lines.get(row.id).timeInOrder;
                lines.put(row.id, row);
            } else  {
                int timeInOrder = lines.get(row.id).timeInOrder + (lines.get(row.id).time - row.time);
                row.timeInOrder = timeInOrder;
                lines.put(row.id, row);
            }
        }


        // СОртируем коллекцию lines
        // Выводим все в файл output.txt
        try (FileWriter writer = new FileWriter("output.txt")) {
            SortedSet<Integer> keys = new TreeSet<>(lines.keySet());
            for (int key : keys) {
                writer.write(lines.get(key).timeInOrder + " ");
            }
        }

    }
}

class Line {
    int id;
    // minute + hour*60 + day*24*60
    int time;
    // A - 1; C,S - 0
    boolean status;
    int timeInOrder;

    @Override
    public String toString() {
        return String.valueOf(time) + "-" + String.valueOf(status);
    }

    public Line(int id, int time, boolean status, int timeInOrder) {
        this.id = id;
        this.time = time;
        this.status = status;
        this.timeInOrder = timeInOrder;
    }
}
