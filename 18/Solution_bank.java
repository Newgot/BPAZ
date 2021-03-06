import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

class Solution {

    /* HackerLand National Bank имеет простую политику предупреждения клиентов о
     * возможных мошеннических действиях на счете. Если сумма, потраченная клиентом
     * в определенный день, больше или равна медиане расходов клиента за конечное
     * число дней, они отправляют клиенту уведомление о потенциальном мошенничестве.
     * Банк не отправляет клиенту никаких уведомлений до тех пор, пока у него нет,
     * по крайней мере, этого конечного числа транзакций за предыдущие дни. Учитывая
     * количество завершающих дней и общие ежедневные расходы клиента за период,
     * найдите и распечатайте количество дней, когда клиент получит уведомление.
     *
     * 1 <= n <= 2*10e5, n - количество учтенных расходов
     * 1 <= d <= n, d - количество дней для расчета медианы
     * 0 <= exp[i] <= 200
     *
     * Пример: 9 5
     * 2 3 4 2 3 6 8 4 5
     * Ответ: 2
     * {2 2 3 3 4}, m = 3, 2 * 3 <= 6 1
     * {2 3 3 4 6}, m = 3, 2 * 3 <= 8 1
     * {3 3 4 6 8}, m = 4, 2 * 4 > 4 0
     * {3 4 4 6 8}, m = 4, 2 * 4 > 5 0

     * Пример: 5 4
     * 1 2 3 4 4
     * {1 2 3 4}, m = 2.5, 2 * 2.5 > 4 0
     * Ответ: 0
     */
    // Пузырьковая сортировка
    public static void sort(double[] arr) {
        for (int i = arr.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (arr[j] > arr[j + 1]) {
                    double tmp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = tmp;

                }
            }
        }
    }

    private static final int MAX_EXPENDITURE = 200; // Лимит по колличеству дней

    // Complete the activityNotifications function below.
    static int activityNotifications(int[] expenditure, int d) {
        int n = expenditure.length;
        if (n > d) {
            double[] period = new double[d]; // Массив для подсчкета медианы
            int count = 0;
            for (int i = 0; i + d < n; i++) {
                for (int j = 0; j < d; j++) {
                    period[j] = expenditure[i + j];
                }
                sort(period);
                double median; // Медиана
                if (d % 2 == 0) { // Медиана у массива с четным колличеством элементов
                    median = period[d / 2];
                    median += period[d / 2 + 1];
                } else median = period[d / 2 + 1] * 2; // Медиана у массива с нечетным колличеством элементов
                System.out.println(Arrays.toString(period) + " | " + expenditure[d + 1] + " больше (или равен)" + median + " ?");
                if (expenditure[d + i] >= median) {
                    System.out.println("Да");
                    count++;
                } else System.out.println("Нет");
            }
            System.out.println("Ответ : " + count);
            return count;
        } else return 0;
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("buffer.txt")); // Выдовал ошибку, потому сменил на файл

        String[] nd = scanner.nextLine().split(" ");

        String[] expenditureItems = scanner.nextLine().split(" ");
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        AtomicInteger r = new AtomicInteger(); // Добавил внешнюю перемененную, чтоб вытягивать значение из потока


        Thread tr = new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + "  - поток запущен");

                int n = Integer.parseInt(nd[0]);

                int d = Integer.parseInt(nd[1]); // Длинна проверочного периода

                int[] expenditure = new int[n];

                for (int i = 0; i < n; i++) {
                    int expenditureItem = Integer.parseInt(expenditureItems[i]);
                    expenditure[i] = expenditureItem;
                }

                r.set(activityNotifications(expenditure, d));
                System.out.println(Thread.currentThread().getName() + "  - поток остановлен");
            }
            catch (Exception exception){
                System.out.println("Error!");
            }
        });
        tr.start();


        bufferedWriter.write(String.valueOf(r.get()));
        bufferedWriter.newLine();

        bufferedWriter.close();

        scanner.close();
    }

}
//@FunctionalInterface
//interface Bank{
//    int method(int d, int[] expenditure);
//}
//
//class MyThread extends Thread{
//
//    public int[] expenditure;
//    public int d;
//
//    public void setD(int d){ this.d = d;} // Прочитать переменную d
//    public void setExpenditure(int[] expenditure){ this.expenditure = expenditure;} // Прочитать массив
//
//    @Override
//    public void run() {
//        super.run();
//        Bank bank;
//    }
//
//
//
//}