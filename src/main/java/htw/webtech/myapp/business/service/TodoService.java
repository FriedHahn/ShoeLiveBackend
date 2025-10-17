package htw.webtech.myapp.business.service;

import htw.webtech.myapp.persistence.entity.TodoEntry;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TodoService {

    public List<TodoEntry> getAllTodo() {
        return List.of(
                new TodoEntry("Friedrich"),
                new TodoEntry("Nam")
        );
    }
}
