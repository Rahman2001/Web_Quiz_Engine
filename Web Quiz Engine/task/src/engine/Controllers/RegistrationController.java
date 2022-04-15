package engine.Controllers;

import engine.Repositories.UserRepo;
import engine.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/register")
public class RegistrationController {

    private final UserRepo userRepo;
    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    public RegistrationController( UserRepo userRepo){
        this.userRepo = userRepo;
    }

    @PostMapping
    public ResponseStatusException responseStatus(@RequestBody @Valid User user){ //gets user's data (email, password)
        User temp = this.userRepo.findByEmail(user.getEmail());
        if(temp == null) {
            user.setPassword(this.encoder.encode(user.getPassword()));
            this.userRepo.save(user);
            throw new ResponseStatusException(HttpStatus.OK);
        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}