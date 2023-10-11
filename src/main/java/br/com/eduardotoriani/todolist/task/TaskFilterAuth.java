package br.com.eduardotoriani.todolist.task;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.eduardotoriani.todolist.user.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class TaskFilterAuth extends OncePerRequestFilter{

    @Autowired
    private IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException 
    {
        var authEncoded        = request.getHeader("Authorization").substring("Basic".length()).trim();
        var authDecodedInBytes = Base64.getDecoder().decode(authEncoded);
        var authCredentials    = new String(authDecodedInBytes).split(":");
        String username = authCredentials[0];
        String password = authCredentials[1];

        var userFounded = this.userRepository.findByUsername(username);

        if (userFounded == null) {
            response.sendError(HttpStatus.UNAUTHORIZED.value());
            return; 
        }
        var verify = BCrypt.verifyer().verify(password.toCharArray(), userFounded.getPassword());
        if (!verify.verified) {
            response.sendError(HttpStatus.UNAUTHORIZED.value());
            return;   
        }
        filterChain.doFilter(request, response);
            
    }
    
}
