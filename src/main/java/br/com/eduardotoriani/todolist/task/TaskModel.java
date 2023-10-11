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
    private Integer priority;

}
