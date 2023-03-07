package reactive;

import io.reactivex.netty.protocol.http.server.HttpServer;
import rx.Observable;

import java.util.Arrays;
import java.util.List;

public class Server {
    Database database;

    public Server (Database database) {
        this.database = database;
    }

    public void run() {
        HttpServer
                .newServer(27017)
                .start((req, resp) -> {

                    List<String> request = Arrays.stream(req.getDecodedPath().substring(1).split("/")).toList();
                    String command = request.get(0);

                    Observable<String> response;

                    switch (command) {
                        case "users" -> {
                            Observable<User> users = database.getAllUsers();
                            response = users.map(user -> "Hello, " + user + "!");
                        }
                        case "items" -> {
                            Observable<Item> items = database.getAllItems();
                            response = items.map(item -> "Hello, " + item + "!");
                        }
                        case "add_user" -> {
                            int id = Integer.parseInt(request.get(1));
                            String name = request.get(2);
                            String login = request.get(3);
                            String currency = request.get(4);

                            database.addUser(id, name, login, currency);

                            response = Observable
                                    .just("User " + name + " added!");
                        }
                        case "add_item" -> {
                            String name = request.get(1);
                            double price = Double.parseDouble(request.get(2));

                            database.addItem(name, price);

                            response = Observable
                                    .just("Item " + name + " added!");
                        }
                        default -> response = Observable.just("Invalid command!");
                    }

                    return resp.writeString(response);
                })
                .awaitShutdown();
    }
}
