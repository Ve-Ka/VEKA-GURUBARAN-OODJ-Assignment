package Main;

import java.util.List;

public interface Task {
    void search(String ID);
    void add();
    void modify(Object object);
    void remove(String ID);
    List<String> defaultDetails(String ID);
    String generateID();
}
