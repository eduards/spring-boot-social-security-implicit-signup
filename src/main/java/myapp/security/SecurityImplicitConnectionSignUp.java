package myapp.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;

import java.util.Arrays;

/**
 * Implementation of {@link ConnectionSignUp} that allows the implicitly
 * creation of a local user profile in a Spring Security context
 */
public class SecurityImplicitConnectionSignUp implements ConnectionSignUp {

  public UserDetailsManager userDetailsManager;

  /**
   * Constructor of {@link SecurityImplicitConnectionSignUp}
   * @param userDetailsManager - used to create new user
   */
  public SecurityImplicitConnectionSignUp(UserDetailsManager userDetailsManager) {
    this.userDetailsManager = userDetailsManager;
  }

  /**
   * Implementation of {@link #execute(Connection)} that utilize
   * Spring Securities {@link UserDetailsManager} to sign up the new user with
   * the "USER" authority granted
   */
  @Override
  public String execute(Connection<?> connection) {
    String providerUserId = connection.getKey().getProviderUserId();
    User newUser = new User(
        providerUserId, "", Arrays.asList(new SimpleGrantedAuthority("USER")));
    userDetailsManager.createUser(newUser);
    return providerUserId;
  }

}