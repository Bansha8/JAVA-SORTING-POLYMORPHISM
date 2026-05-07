import java.util.Arrays;
import java.util.Random;
// --- 1. АБСТРАКЦІЯ ---
interface SortStrategy {
    void sort(int[] array);
}

// --- 2. РЕАЛІЗАЦІЇ АЛГОРИТМІВ (4 МЕТОДИ) ---

// 2.1. Сортування бульбашкою (Bubble Sort)
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

// 2.2. Сортування вставками (Insertion Sort)
class InsertionSort implements SortStrategy {
    public void sort(int[] array) {
        int n = array.length;
        for (int i = 1; i < n; i++) {
            int key = array[i];
            int j = i - 1;
            while (j >= 0 && array[j] > key) {
                array[j + 1] = array[j];
                j = j - 1;
            }
            array[j + 1] = key;
        }
    }
}

// 2.3. Сортування вибором (Selection Sort)
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

// 2.4. Швидке сортування (Quick Sort)
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

// --- 3. КОНТЕКСТ (Використання поліморфізму) ---
class Sorter {
    private SortStrategy strategy;
    public Sorter(SortStrategy strategy) { this.strategy = strategy; }
    public void setStrategy(SortStrategy strategy) { this.strategy = strategy; }
    public void performSort(int[] array) { strategy.sort(array); }
}

// --- 4. ГОЛОВНИЙ МОДУЛЬ (Бенчмарк та тестування) ---
public class SortMaster {
    public static void main(String[] args) {
        System.out.println("=== Java Polymorphic Sorting Suite ===");
        int[] data = {34, 12, 5, 78, 1, 45, 23};
        System.out.println("Тестовий масив: " + Arrays.toString(data));

        Sorter sorter = new Sorter(new BubbleSort());

        // Демонстрація роботи кожного методу
        SortStrategy[] allStrategies = {
            new BubbleSort(), 
            new InsertionSort(), 
            new SelectionSort(), 
            new QuickSort()
        };
        
        String[] names = {"Bubble", "Insertion", "Selection", "Quick"};

        for (int i = 0; i < allStrategies.length; i++) {
            int[] testCopy = data.clone();
            sorter.setStrategy(allStrategies[i]);
            sorter.performSort(testCopy);
            System.out.println(names[i] + " Sort Result: " + Arrays.toString(testCopy));
        }

        // Запуск порівняльного аналізу
        runBenchmark();
    }

    public static void runBenchmark() {
        int[] sizes = {100, 1000, 5000};
        Random rand = new Random();
        
        for (int size : sizes) {
            System.out.println("\n--- Аналіз продуктивності (N = " + size + ") ---");
            int[] base = new int[size];
            for (int i = 0; i < size; i++) base[i] = rand.nextInt(10000);

            SortStrategy[] strategies = {new BubbleSort(), new InsertionSort(), new SelectionSort(), new QuickSort()};
            String[] names = {"Bubble", "Insertion", "Selection", "Quick"};
            Sorter s = new Sorter(strategies[0]);

            for (int i = 0; i < strategies.length; i++) {
                s.setStrategy(strategies[i]);
                int[] copy = base.clone();
                long start = System.nanoTime();
                s.performSort(copy);
                long end = System.nanoTime();
                System.out.printf("%s: %.5f сек\n", names[i], (end - start) / 1_000_000_000.0);
            }
        }
    }
}
