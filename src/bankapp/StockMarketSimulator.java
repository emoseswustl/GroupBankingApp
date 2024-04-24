package bankapp;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import bankapp.Menu.StockTrader;

public class StockMarketSimulator {

    private Map<String, Double> stockPrices = new HashMap<>();
    private Random random = new Random();

    public void updateMarketPrices() {
        String[] symbols = {"AAPL", "GOOGL", "AMZN", "MSFT", "TSLA"};
        for (String symbol : symbols) {
            double randomPrice = 100 + (1000 - 100) * random.nextDouble(); // Prices between 100 and 1000
            stockPrices.put(symbol, randomPrice);
        }
    }

    public void tradeAtMarketPrices() {
        for (Map.Entry<String, Double> entry : stockPrices.entrySet()) {
            String symbol = entry.getKey();
            double currentPrice = entry.getValue();
            double targetBuyPrice = currentPrice - 10;
            double targetSellPrice = currentPrice + 10;
            int quantity = 10;

            StockTrader.tradeStock(symbol, currentPrice, targetBuyPrice, targetSellPrice, quantity);
        }
    }
}


