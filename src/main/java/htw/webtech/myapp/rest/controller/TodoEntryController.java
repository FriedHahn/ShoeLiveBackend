package htw.webtech.myapp.rest.controller;

import htw.webtech.myapp.business.service.TodoService;
import htw.webtech.myapp.persistence.entity.TodoEntry;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class TodoEntryController {

    private final TodoService todoService;

    public TodoEntryController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("/todo")
    public ResponseEntity<List<TodoEntry>> getToDo() {
        return ResponseEntity.ok(todoService.getAllTodo());
    }

    // Einfacher POST zum Anlegen eines Todos
    @PostMapping("/todo/test-create")
    public ResponseEntity<TodoEntry> createTodo(@RequestBody TodoEntry body) {
        TodoEntry saved = todoService.createTodo(body.getName());
        return ResponseEntity.ok(saved);
    }
}
