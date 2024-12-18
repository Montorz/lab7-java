import java.util.*;

class InvalidBalanceException extends RuntimeException {
    public InvalidBalanceException() {
        super("Недопустимый баланс!");
    }
}

// Базовый класс User
class User {
    protected int id;
    protected String name;
    protected float balance;

    public User(int id, String name, float balance) {
        if (balance < 0) {
            throw new InvalidBalanceException();
        }
        this.id = id;
        this.name = name;
        this.balance = balance;
    }

    public void displayInfo() {
        System.out.println("ID: " + id);
        System.out.println("Имя: " + name);
        System.out.println("Баланс: " + balance);
    }

    public boolean isVIP() {
        return false;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getBalance() {
        return balance;
    }
}

// Производный класс VIPUser
class VIPUser extends User {
    private float cashbackRate;

    public VIPUser(int id, String name, float balance, float cashbackRate) {
        super(id, name, balance);
        this.cashbackRate = cashbackRate;
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Кэшбэк: " + (cashbackRate * 100) + "%");
    }

    @Override
    public boolean isVIP() {
        return true;
    }

    public void addCashback() {
        balance += balance * cashbackRate;
    }
}

public class Main {

    // Функция для сортировки пользователей по статусу
    public static void sortUsersByStatus(List<User> users) {
        users.sort((a, b) -> Boolean.compare(b.isVIP(), a.isVIP())); // VIP-пользователи идут первыми
    }

    // Функция для поиска пользователя по ID
    public static User findUserById(List<User> users, int id) {
        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        try {
            // Создаем пользователей
            User user1 = new User(1, "Анна", 1200.0f);
            User user2 = new User(2, "Иван", 800.0f);
            VIPUser vip1 = new VIPUser(3, "Мария", 5000.0f, 0.05f);
            VIPUser vip2 = new VIPUser(4, "Петр", 3000.0f, 0.1f);

            // Контейнер для хранения пользователей
            List<User> allUsers = new ArrayList<>(Arrays.asList(user1, user2, vip1, vip2));

            // Сортировка пользователей по статусу
            sortUsersByStatus(allUsers);

            // Выводим отсортированных пользователей
            System.out.println("Пользователи после сортировки по статусу (VIP -> обычные):");
            for (User user : allUsers) {
                user.displayInfo();
                System.out.println();
            }

            // Интерактивный поиск по ID
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.print("\nВведите ID для поиска пользователя (или введите 0 для выхода): ");
                int searchId = scanner.nextInt();

                if (searchId == 0) {
                    System.out.println("Выход из программы.");
                    break;
                }

                User foundUser = findUserById(allUsers, searchId);
                if (foundUser != null) {
                    System.out.println("\nНайден пользователь:");
                    foundUser.displayInfo();
                } else {
                    System.out.println("Пользователь с ID " + searchId + " не найден");
                }
            }

        } catch (Exception e) {
            System.err.println("Ошибка: " + e.getMessage());
        }
    }
}
