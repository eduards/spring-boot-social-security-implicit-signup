package myapp.config;

import myapp.security.SecurityImplicitConnectionSignUp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.security.AuthenticationNameUserIdSource;
import org.springframework.social.config.annotation.SocialConfigurer;

import javax.sql.DataSource;

/**
 * Implementation of {@link SocialConfigurer} extending
 * {@link SocialConfigurerAdapter} to provide the application specific
 * Spring Social configuration.
 */
@Configuration
@EnableSocial
public class SocialConfig extends SocialConfigurerAdapter {

  @Autowired
  private DataSource dataSource;

  /**
   * Implementation of {@link #getUsersConnectionRepository(ConnectionFactoryLocator)}
   * that creates and JDBC repository
   */
  @Override
  public UsersConnectionRepository getUsersConnectionRepository(
      ConnectionFactoryLocator connectionFactoryLocator) {
    JdbcUsersConnectionRepository repository =
        new JdbcUsersConnectionRepository(
            dataSource,
            connectionFactoryLocator,
            Encryptors.noOpText());
    repository.setConnectionSignUp(
        new SecurityImplicitConnectionSignUp(userDetailsManager()));
    return repository;
  }

  /**
   * Implementation of {@link #getUserIdSource()} that returns a
   * {@link AuthenticationNameUserIdSource}
   */
  @Override
  public UserIdSource getUserIdSource() {
    return new AuthenticationNameUserIdSource();
  }

  /**
   * Exposes a {@link JdbcUserDetailsManager}
   * @return - a JdbcUserDetailsManager
   */
  @Bean
  public JdbcUserDetailsManager userDetailsManager() {
    JdbcUserDetailsManager manager = new JdbcUserDetailsManager();
    manager.setDataSource(dataSource);
    manager.setEnableAuthorities(true);
    return manager;
  }

}
