package br.com.eduardotoriani.todolist.task;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity(name = "tb_tasks")
@Data
public class TaskModel {
    
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    private UUID idUser;

    @Column(length = 50)
    private String title;
    private String description;
    private LocalDateTime begins;
    private LocalDateTime ends;
    private String priority;

    public void setTitle(String title) throws Exception {
        if (title.length() > 50) {
            throw new Exception("O titulo deve conter menos de 50 caracteres.");
        }
        this.title = title;
    }

}
