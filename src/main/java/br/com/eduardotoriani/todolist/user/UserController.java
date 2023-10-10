package br.com.eduardotoriani.todolist.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserRepository repository;
  
    @PostMapping("/")
    public ResponseEntity create(@RequestBody UserModel user) {
        var userFounded = repository.findByUsername(user.getUsername());
        if (userFounded != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ops, já existe um usuário com este nome!");
        }
        var userCreated = repository.save(user);
        return ResponseEntity.ok().body(userCreated);
    }

}
