package br.com.eduardotoriani.todolist.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/task")
public class TaskController {
    
    @Autowired
    private ITaskRepository repository;

    public ResponseEntity create(@RequestBody TaskModel task) {
        var taskCreated = this.repository.save(task);
        return ResponseEntity.ok().body(taskCreated);
    }

}
