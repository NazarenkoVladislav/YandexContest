package ATrickyCode;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class TrickyCode {

    // Объявляем латинский алфавит
    static String latinAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public static void main(String[] args) throws IOException {
        // Объявляем латинский алфавит
        String latinAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        // Читаем
        BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
        // Читаем первую строку, чтобы узнать количество строк
        String line1 = reader.readLine();
        // Из первой строки вычленяем Integer
        int numOfInterns = Integer.parseInt(line1);
        // Объявляем массив для храннеия шифров
        int[] shifr = new int[numOfInterns];
        // Объявляем буферную строку для хранения уникальных символов
        StringBuilder buffer = new StringBuilder();
        // Сумма букв
        int sumLetter = 0;
        // Сумма цифр
        int sumNumber = 0;

        // Перебираем каждую строку в файле с данными
        for(int i = 0; i < numOfInterns; i++) {
            String line = reader.readLine();
            if (line != null) {
                String[] internInfo = line.split(",");
                // Сумма букв
                sumLetter = 0;
                // Сумма цифр
                sumNumber = 0;
                // Ищем сумму уникальных букв последовательно в ФИО
                sumLetter = sumOfLetter(sumLetter, internInfo[0], buffer);
                sumLetter = sumOfLetter(sumLetter, internInfo[1], buffer);
                sumLetter = sumOfLetter(sumLetter, internInfo[2], buffer);
                // Очищаем buffer для последующего использования
                buffer.delete(0, buffer.length());
                // Считаем сумму цифр даты рождения
                sumNumber += sumOfNumber(Integer.parseInt(internInfo[3]));
                // Считаем сумму цифр месяца рождения и суммируем
                sumNumber += sumOfNumber(Integer.parseInt(internInfo[4]));
                // Ищем номер в алфавите первой буквы Фамилии
                int positionFirstLetter = latinAlphabet.indexOf(internInfo[0].charAt(0)) + 1;
                if (positionFirstLetter > 26) { positionFirstLetter -= 26; };
                // Считаем шифр для этой строки
                shifr[i] = sumLetter + sumNumber * 64 + positionFirstLetter * 256;
            }
        }

        // Выводим все в файл output.txt
        try (FileWriter writer = new FileWriter("output.txt")) {
            for (int i = 0; i < numOfInterns; i++) {
                if(i < numOfInterns - 1) {
                    writer.write(integerToHex(shifr[i]) + " ");
                } else {
                    writer.write(integerToHex(shifr[i]));
                }
            }
        }
    }

    // Перевод Integer to HEX
    public static String integerToHex(int number) {
        String s = Integer.toHexString(number);
        if (s.length() >= 3) {
            return s.substring(s.length()-3).toUpperCase();
        } else if (s.length() == 2) {
            return "0" + s.toUpperCase();
        } else if (s.length() == 1) {
            return "00" + s.toUpperCase();
        } else {
            return "000";
        }
    }


    // Метод для поиска количества символов
    public static int sumOfLetter(int sumLetter, String symbols, StringBuilder buffer) {
        // Перебираем все символы массива
        for (int c = 0; c < symbols.length(); c++) {
            char ch = symbols.charAt(c);
            String s = "" + ch;
            if (latinAlphabet.indexOf(ch) >= 0 && buffer.indexOf(s) == -1) {
                sumLetter++;
                buffer.append(s);
            }
        }
        return sumLetter;
    }

    // Метод для поиска суммы цифр числа
    public static int sumOfNumber(int number) {
        int sum = 0;
        do {
            sum += number % 10;
            number = number / 10;
        } while(number > 0);
        return sum;
    }

}
