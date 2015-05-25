package myapp.security;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;

/**
 * Default implementation of {@link SocialUserDetailsService}
 */
public class SimpleSocialUsersDetailService
    implements SocialUserDetailsService {

  private UserDetailsService userDetailsService;

  /**
   * Constructor of {@link SimpleSocialUsersDetailService}
   * @param userDetailsService - used to load the user
   */
  public SimpleSocialUsersDetailService(UserDetailsService userDetailsService) {
    this.userDetailsService = userDetailsService;
  }

  /**
   * Implementation of {@link #loadUserByUserId(String)} to load the user from
   * the provided {@link UserDetailsService}
   */
  @Override
  public SocialUserDetails loadUserByUserId(String userId)
      throws UsernameNotFoundException, DataAccessException {
    UserDetails userDetails = userDetailsService.loadUserByUsername(userId);
    return new SocialUser(
        userDetails.getUsername(),
        userDetails.getPassword(),
        userDetails.getAuthorities());
  }

}
