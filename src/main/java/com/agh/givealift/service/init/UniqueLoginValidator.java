package com.agh.givealift.service.init;

import com.agh.givealift.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Service
public class UniqueLoginValidator implements ConstraintValidator<UniqueLogin, String> {
    
  
    private UserRepository userRepository;

    @Autowired
    public UniqueLoginValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void initialize(UniqueLogin constraint) {
   }

   public boolean isValid(String login, ConstraintValidatorContext context){
        return login != null && userRepository.findByEmail(login) == null;               
   }
   
}
