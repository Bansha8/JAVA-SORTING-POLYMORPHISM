import java.util.Arrays;
import java.util.Random;

// --- 1. АБСТРАКЦІЯ (Стратегія) ---
interface SortStrategy {
    void sort(int[] array);
}

// --- 2. РЕАЛІЗАЦІЇ АЛГОРИТМІВ ---

// Бульбашкове сортування
class BubbleSort implements SortStrategy {
    public void sort(int[] array) {
        int n = array.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
    }
}

// Сортування вибором
class SelectionSort implements SortStrategy {
    public void sort(int[] array) {
        int n = array.length;
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j++) {
                if (array[j] < array[minIdx]) minIdx = j;
            }
            int temp = array[minIdx];
            array[minIdx] = array[i];
            array[i] = temp;
        }
    }
}

// Швидке сортування (Quick Sort)
class QuickSort implements SortStrategy {
    public void sort(int[] array) {
        quickSort(array, 0, array.length - 1);
    }

    private void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    private int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) {
                i++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        return i + 1;
    }
}

// --- 3. КОНТЕКСТ (Клас, що використовує поліморфізм) ---
class Sorter {
    private SortStrategy strategy;

    public Sorter(SortStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(SortStrategy strategy) {
        this.strategy = strategy;
    }

    public void performSort(int[] array) {
        strategy.sort(array);
    }
}

// --- 4. ГОЛОВНИЙ КЛАС ТА БЕНЧМАРК ---
public class SortBenchmark {
    public static void main(String[] args) {
        // Базова перевірка логіки
        int[] data = {5, 2, 9, 1, 5, 6};
        System.out.println("Вхідний масив: " + Arrays.toString(data));

        Sorter sorter = new Sorter(new BubbleSort());
        
        int[] bubbleData = data.clone();
        sorter.performSort(bubbleData);
        System.out.println("Bubble Sort: " + Arrays.toString(bubbleData));

        sorter.setStrategy(new SelectionSort());
        int[] selectData = data.clone();
        sorter.performSort(selectData);
        System.out.println("Selection Sort: " + Arrays.toString(selectData));

        sorter.setStrategy(new QuickSort());
        int[] quickData = data.clone();
        sorter.performSort(quickData);
        System.out.println("Quick Sort: " + Arrays.toString(quickData));

        // Запуск експериментального дослідження (Бенчмарк)
        runBenchmark();
    }

    public static void runBenchmark() {
        int[] testSizes = {100, 1000, 5000, 10000};
        Random random = new Random();

        for (int size : testSizes) {
            System.out.println("\n--- Тестування масиву з " + size + " елементів ---");

            int[] baseArray = new int[size];
            for (int i = 0; i < size; i++) {
                baseArray[i] = random.nextInt(10000);
            }

            SortStrategy[] strategies = {new BubbleSort(), new SelectionSort(), new QuickSort()};
            String[] names = {"Bubble Sort", "Selection Sort", "Quick Sort"};

            Sorter sorter = new Sorter(strategies[0]);

            for (int i = 0; i < strategies.length; i++) {
                sorter.setStrategy(strategies[i]);
                int[] testArray = baseArray.clone();
