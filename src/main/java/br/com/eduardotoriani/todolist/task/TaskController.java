package br.com.eduardotoriani.todolist.task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.eduardotoriani.todolist.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/task")
public class TaskController {
    
    @Autowired
    private ITaskRepository repository;

    @PostMapping("/")
    public ResponseEntity create(@RequestBody TaskModel task, HttpServletRequest request) {
        task.setIdUser((UUID) request.getAttribute("idUser"));
        
        //Validações:
        LocalDateTime currentDate = LocalDateTime.now();
        if (currentDate.isAfter(task.getBegins()) || currentDate.isAfter(task.getEnds())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("As datas de início e término da tarefa são inválidas para com a data atual.");
        }
        if (task.getBegins().isAfter(task.getEnds())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A data inicial da tarefa deve ser menor que a data de término da tarefa.");
        }

        var taskCreated = this.repository.save(task);
        return ResponseEntity.ok().body(taskCreated);
    }

    @GetMapping("/")
    public List<TaskModel> read(HttpServletRequest request) {
        UUID user = (UUID) request.getAttribute("idUser");
        List<TaskModel> tasks = this.repository.findByIdUser(user);
        return tasks;
    }

    @PutMapping("/{id}")
    public TaskModel update(@RequestBody TaskModel task, @PathVariable UUID id) {
        TaskModel taskToUpdate = this.repository.findById(id).orElse(null);
        Utils.beanNonNullProps(task, taskToUpdate);
        return this.repository.save(taskToUpdate);
    }

}
