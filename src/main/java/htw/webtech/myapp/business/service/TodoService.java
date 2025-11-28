package htw.webtech.myapp.business.service;

import htw.webtech.myapp.persistence.entity.TodoEntry;
import htw.webtech.myapp.persistence.repository.TodoEntryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TodoService {

    private final TodoEntryRepository todoEntryRepository;

    public TodoService(TodoEntryRepository todoEntryRepository) {
        this.todoEntryRepository = todoEntryRepository;
    }

    public List<TodoEntry> getAllTodo() {
        return todoEntryRepository.findAll();
    }

    public TodoEntry createTodo(String name) {
        TodoEntry entry = new TodoEntry(name);
        // Beispiel: Fällig morgen
        entry.setDue(LocalDateTime.now().plusDays(1));
        return todoEntryRepository.save(entry);
    }
}
