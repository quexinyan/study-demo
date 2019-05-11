package demo.validation;


import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.*;
import java.util.List;

@RestController
@RequestMapping("/validate")
public class StudentTest {

    @RequestMapping("/validatedDemo")
    public String validatedDemo(@Valid Student student, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            allErrors.stream().forEach(error -> System.out.println(error.getCode()+",msg:"+error.getDefaultMessage()));
            return "error";
        }
        return "OK";
    }


}
