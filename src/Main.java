import java.io.IOException;
import java.util.Scanner;

public class Main {
    final static String arabic = "0123456789", roman = "IVX", signs = "+-*/";

    public static void main(String[] args) throws IOException {
        System.out.println("Ведите выражение для расчета, например 2 + 4, VII * IX... и нажмите Enter");
        Scanner console = new Scanner(System.in);
        System.out.println(calc(console.nextLine()));
        console.close();
    }

    public static String calc(String input) throws IOException {
        String[] operation = checkForConditions(input);

        //проверяем, являются ли символы римскими или арабскими
        boolean numbersAreRoman = operation[0].charAt(0) >= 'I' && operation[2].charAt(0) >= 'I';
        boolean numbersAreArabic = operation[0].charAt(0) <= '9' && operation[2].charAt(0) <= '9';
        if (!(numbersAreArabic || numbersAreRoman)) throw new IOException("Числа должны быть в одной системе счисления!");

        //производим вычисления
        int number1 = convertStringToNumber(operation[0]);
        int number2 = convertStringToNumber(operation[2]);
        if (number1 == 0 || number2 == 0) throw new IOException("Числа должны быть больше 0!");
        int result = 0;
        switch (operation[1]) {
            case "+" : result = number1 + number2;
                break;
            case "-" : result = number1 - number2;
                break;
            case "*" : result = number1 * number2;
                break;
            case "/" : result = number1 / number2;
                break;
        }

        //проверяем, чтобы результат вычисления с римскими числами был положительным
        if ((numbersAreRoman) && result < 1) throw new IOException("Результат в римской системе не может быть меньше 1");

        System.out.println("Результат:");
        if (numbersAreRoman)
            return convertToRoman(result);
        return result + "";
    }

    public static String[] checkForConditions(String input) throws IOException {
        // Убираем пробелы в введенной строке
        String expression = input.replace(" ", "");

        // Проверяем длину введенной строки (без пробелов). Длина строки может быть от 3 до 9 символов (от 1+1 до VIII+VIII).
        if (expression.length() < 3 || expression.length() > 9) throw new IOException("Введенные данные не соответствуют условиям!");

        int count = 0; // счетчик для арифметических знаков
        int signPosition = 0; // позиция арифметического знака в введенном выражении

        // проходим циклом по выражению для проверки введенных символов
        for (int i = 0; i < expression.length(); i++) {
            String currentSymbol = expression.substring(i, i + 1);

            // Проверяем, чтобы в строке были только допустимые символы
            if (!arabic.contains(currentSymbol) && !roman.contains(currentSymbol) && !signs.contains(currentSymbol)) throw new IOException("Введенные данные не соответствуют условиям!");

            // считаем арифметические знаки и определяем расположение арифметического знака
            if (signs.contains(currentSymbol)) {
                count++;
                signPosition = i;
            }
        }

        // Проверяем, чтобы был только один арифметический знак в введенной строке и не по краям строки
        if (count != 1 || signPosition == 0 || signPosition == expression.length() - 1) throw new IOException("Введенные данные не соответствуют условиям!");

        // результат работы метода
        String[] operation = {expression.substring(0, signPosition), //первый операнд
                           expression.substring(signPosition, signPosition + 1), //оператор
                           expression.substring(signPosition + 1)}; //второй операнд
        return operation;
    }

    static int convertStringToNumber(String string) { //метод переводит строки в числа для дальнейших вычислений
        int num = 0;
        switch (string) {
            case "1": case "I": num = 1;
                break;
            case "2": case "II": num = 2;
                break;
            case "3": case "III": num = 3;
                break;
            case "4": case "IV": num = 4;
                break;
            case "5": case "V": num = 5;
                break;
            case "6": case "VI": num = 6;
                break;
            case "7": case "VII": num = 7;
                break;
            case "8": case "VIII": num =8;
                break;
            case "9": case "IX": num = 9;
                break;
            case "10": case "X": num = 10;
                break;
        }
        return num;
    }

    static String convertToRoman(int arabicNumber) {
        String[] tens = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
        String[] ones = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};
        String romanNumber = "";
        if (arabicNumber == 100)
            return "C";
        if (arabicNumber >= 10)
            romanNumber =  tens[arabicNumber / 10] + ones[arabicNumber % 10];
        if (arabicNumber < 10)
            romanNumber =  ones[arabicNumber];

        return romanNumber;
    }
}