package myapp.signin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * A {@link Controller} for signin related actions
 */
@Controller
public class SigninController {

  @RequestMapping(value="/signin", method=RequestMethod.GET)
  public String signin() {
    return "authentication/signin";
  }

}