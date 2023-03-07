package reactive;

import com.mongodb.rx.client.MongoClient;
import com.mongodb.rx.client.MongoClients;
import com.mongodb.rx.client.MongoDatabase;
import org.bson.Document;
import rx.Observable;

public class Database {

    private static final MongoClient client = createMongoClient();
    private final MongoDatabase database = client.getDatabase("db");

    private static void getPrintln(User user) {
        System.out.println(user);
    }

    public void addUser(int id, String name, String login, String currency_str) {
        Currency currency = switch (currency_str) {
            case "eur" -> Currency.EUR;
            case "rub" -> Currency.RUB;
            default -> Currency.USD;
        };
        database.getCollection("users")
                .insertOne(
                        new Document("_id", id)
                                .append("name", name)
                                .append("login", login)
                                .append("currency", currency)
                )
                .subscribe();
    }

    public void addItem(String name, double price_usd) {
        database.getCollection("items")
                .insertOne(
                        new Document("name", name)
                                .append("price_usd", price_usd)
                )
                .subscribe();
    }

    public Observable<User> getAllUsers() {
        return database.getCollection("users")
                .find().toObservable().map(User::new);
    }

    public Observable<Item> getAllItems() {
        return database.getCollection("items")
                .find().toObservable().map(Item::new);
    }

    private static MongoClient createMongoClient() {
        return MongoClients.create("mongodb://localhost:27017");
    }
}
