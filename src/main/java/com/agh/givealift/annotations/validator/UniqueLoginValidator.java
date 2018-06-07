package com.agh.givealift.annotations.validator;

import com.agh.givealift.annotations.UniqueLogin;
import com.agh.givealift.repository.UserRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class UniqueLoginValidator implements ConstraintValidator<UniqueLogin, String> {
    
  
    private UserRepository userRepository;

   
    public UniqueLoginValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void initialize(UniqueLogin constraint) {
   }

   public boolean isValid(String login, ConstraintValidatorContext context){
        return login != null && userRepository.findByEmail(login) == null;               
   }
   
}
