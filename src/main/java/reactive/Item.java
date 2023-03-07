package reactive;

import org.bson.Document;

public class Item {
    String name;
    double price_usd;

    public Item(Document doc) {
        this(doc.getString("name"), doc.getDouble("price_usd"));
    }

    public Item(String name, double price_usd) {
        this.name = name;
        this.price_usd = price_usd;
    }

    private double toCurrency(Currency currency) {
        switch (currency) {
            case USD -> {
                return price_usd;
            }
            case EUR -> {
                return price_usd * 0.944015;
            }
            case RUB -> {
                return price_usd * 75.728901;
            }
            default -> {
                return 0;
            }
        }
    }

    public String toString(Currency currency) {
        return "name: " + name + ", price: " + toCurrency(currency) + currency;
    }
}
