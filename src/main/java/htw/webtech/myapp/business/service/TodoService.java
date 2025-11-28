package htw.webtech.myapp.business.service;

import htw.webtech.myapp.persistence.entity.TodoEntry;
import htw.webtech.myapp.persistence.repository.TodoEntryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class TodoService {

    private final TodoEntryRepository todoEntryRepository;

    public TodoService(TodoEntryRepository todoEntryRepository) {
        this.todoEntryRepository = todoEntryRepository;
    }

    public List<TodoEntry> getAllTodo() {
        return todoEntryRepository.findAll();
    }

    public List<TodoEntry> createTestTodo() {
        TodoEntry entry = new TodoEntry("Test-Todo " + UUID.randomUUID());
        todoEntryRepository.save(entry);
        return todoEntryRepository.findAll();
    }
}
