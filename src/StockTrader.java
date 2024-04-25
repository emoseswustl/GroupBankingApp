public class StockTrader {
	
    public static  void tradeStock(String symbol, double currentPrice, double targetBuyPrice, double targetSellPrice, int quantity) {
        if (currentPrice <= targetBuyPrice) {
            buyStock(symbol, currentPrice, quantity);
        } else if (currentPrice >= targetSellPrice) {
            sellStock(symbol, currentPrice, quantity);
        } else {
            holdStock(symbol);
        }
    }

    private static  void buyStock(String symbol, double price, int quantity) {
        System.out.println("Bought " + quantity + " shares of " + symbol + " at $" + price + " each.");
    }

    private static  void sellStock(String symbol, double price, int quantity) {
        System.out.println("Sold " + quantity + " shares of " + symbol + " at $" + price + " each.");
    }

    private static void holdStock(String symbol) {
        System.out.println("Holding shares of " + symbol);
    }
}