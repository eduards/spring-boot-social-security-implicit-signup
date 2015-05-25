package myapp.fabook;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

/**
 * A {@link Controller} for facebook profile related actions
 */
@Controller
public class ProfileController {

  private final Facebook facebook;

  /**
   * Constructor of {@link ProfileController}
   * @param facebook - interface to interact with the Facebook API
   */
  @Inject
  public ProfileController(Facebook facebook) {
    this.facebook = facebook;
  }

  @RequestMapping(value="/facebook/profile", method= RequestMethod.GET)
  public String profile(Model model) {
    model.addAttribute("profile", facebook.userOperations().getUserProfile());
    model.addAttribute("friends", facebook.friendOperations().getTaggableFriends());
    return "facebook/profile";
  }

}
