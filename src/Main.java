import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        String csvFile = "returns.csv";
        String line;
        String csvSplitBy = ",";

        // Load data
        Map<String, List<Double>> stockReturns = new LinkedHashMap<>();
        List<String> tickers = new ArrayList<>();
        List<String> dates = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            // Read headers (tickers)
            String[] headers = br.readLine().split(csvSplitBy);
            tickers.addAll(Arrays.asList(headers).subList(1, headers.length)); // Skip the date column

            // Read data
            while ((line = br.readLine()) != null) {
                String[] row = line.split(csvSplitBy);
                dates.add(row[0]);

                for (int i = 1; i < row.length; i++) {
                    String ticker = tickers.get(i - 1);
                    double value = row[i].isEmpty() ? 0 : Double.parseDouble(row[i]);
                    stockReturns.computeIfAbsent(ticker, k -> new ArrayList<>()).add(value);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Calculate moving averages and signals
        int shortWindow = 20;
        int longWindow = 40;
        Map<String, List<Integer>> signals = new LinkedHashMap<>();
        Map<String, List<Double>> strategyReturns = new LinkedHashMap<>();

        for (String ticker : tickers) {
            List<Double> returns = stockReturns.get(ticker);
            List<Double> shortMA = calculateMovingAverage(returns, shortWindow);
            List<Double> longMA = calculateMovingAverage(returns, longWindow);
            List<Integer> signal = new ArrayList<>();
            List<Double> stratReturn = new ArrayList<>();

            for (int i = 0; i < returns.size(); i++) {
                if (i > 0) {
                    int previousSignal = signal.get(i - 1);
                    stratReturn.add(previousSignal * returns.get(i));
                } else {
                    stratReturn.add(0.0);
                }

                if (shortMA.get(i) > longMA.get(i)) {
                    signal.add(1);
                } else {
                    signal.add(0);
                }
            }
            signals.put(ticker, signal);
            strategyReturns.put(ticker, stratReturn);
        }

        // Portfolio analysis
        List<Double> totalReturns = new ArrayList<>();
        for (int i = 0; i < dates.size(); i++) {
            double sum = 0.0;
            for (String ticker : tickers) {
                sum += strategyReturns.get(ticker).get(i);
            }
            totalReturns.add(sum / tickers.size());
        }

        // Metrics: Average Return, Volatility, Sharpe Ratio, Max Drawdown
        double averageReturn = totalReturns.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
        double volatility = calculateStandardDeviation(totalReturns);
        double sharpeRatio = (averageReturn * 252 - 0.01) / (volatility * Math.sqrt(252));
        double maxDrawdown = calculateMaxDrawdown(totalReturns);

        // Output results
        System.out.printf("Average Return: %.2f%%\n", averageReturn * 100);
        System.out.printf("Volatility: %.2f%%\n", volatility * 100);
        System.out.printf("Sharpe Ratio: %.2f\n", sharpeRatio);
        System.out.printf("Maximum Drawdown: %.2f%%\n", maxDrawdown * 100);
    }

    // Helper functions
    private static List<Double> calculateMovingAverage(List<Double> data, int window) {
        List<Double> movingAverage = new ArrayList<>(Collections.nCopies(data.size(), 0.0));
        for (int i = window - 1; i < data.size(); i++) {
            double sum = 0.0;
            for (int j = 0; j < window; j++) {
                sum += data.get(i - j);
            }
            movingAverage.set(i, sum / window);
        }
        return movingAverage;
    }

    private static double calculateStandardDeviation(List<Double> data) {
        double mean = data.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
        double variance = data.stream().mapToDouble(x -> Math.pow(x - mean, 2)).sum() / data.size();
        return Math.sqrt(variance);
    }

    private static double calculateMaxDrawdown(List<Double> cumulativeReturns) {
        double peak = cumulativeReturns.get(0);
        double maxDrawdown = 0.0;
        for (double value : cumulativeReturns) {
            peak = Math.max(peak, value);
            double drawdown = (peak - value) / peak;
            maxDrawdown = Math.max(maxDrawdown, drawdown);
        }
        return maxDrawdown;
    }
}
