package reactive;

public class Main {
    public static void main(String[] args) {
        Database database = new Database();
        Server server = new Server(database);
        server.run();
    }
}
