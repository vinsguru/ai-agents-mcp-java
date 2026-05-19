import java.util.ArrayList;
import java.util.List;

public class UserService {

    private List<String> users = new ArrayList<>();

    public void addUser(String name) {
        users.add(name.trim());
    }

    public String findUser(String name) {
        for (String u : users) {
            if (u.equalsIgnoreCase(name)) {
                return u;
            }
        }
        return null;
    }

    public void printUsers() {
        users.stream().forEach(System.out::println);
    }

}