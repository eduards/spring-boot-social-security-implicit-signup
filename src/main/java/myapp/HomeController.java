package myapp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * A {@link Controller} for the home url
 */
@Controller
public class HomeController {

  @RequestMapping("/")
  public String home(HttpServletRequest request) {
    return "home";
  }

}
