package myapp.config;

import myapp.security.SimpleSocialUsersDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.social.security.SpringSocialConfigurer;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;

import javax.sql.DataSource;

/**
 * Implementation of {@link WebSecurityConfigurer} extending
 * {@link WebSecurityConfigurerAdapter} to provide the application specific
 * Spring Security configuration.
 */
@Configuration
@EnableWebMvcSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private ApplicationContext context;

  @Autowired
  private DataSource dataSource;

  /**
   * Configures {@link AuthenticationManagerBuilder}.
   */
  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth)
      throws Exception {
    auth
        .jdbcAuthentication()
        .dataSource(dataSource)
        .withDefaultSchema();
  }

  /**
   * Overrides {@link #configure(WebSecurity)} to {@link WebSecurity}.
   */
  @Override
  public void configure(WebSecurity web) throws Exception {
    web
        .ignoring()
        .antMatchers("/**/*.css", "/**/*.png", "/**/*.gif", "/**/*.jpg");
  }

  /**
   * Overrides {@link #configure(HttpSecurity)} to configure
   * {@link HttpSecurity}.
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
          .authorizeRequests()
            .antMatchers("/", "/resources/**", "/auth/**", "/signin/**")
              .permitAll()
            .antMatchers("/**")
              .authenticated()
        .and()
          .formLogin()
            .loginPage("/signin")
            .loginProcessingUrl("/signin/authenticate")
            .failureUrl("/signin?param.error=bad_credentials")
            .permitAll()
        .and()
          .logout()
            .logoutUrl("/signout")
            .deleteCookies("JSESSIONID")
        .and()
          .rememberMe()
        .and()
          .apply(new SpringSocialConfigurer());
  }

  /**
   * Exposes a {@link SocialUserDetailsService}
   * @return - a SocialUserDetailsService
   */
  @Bean
  public SocialUserDetailsService socialUsersDetailService() {
    return new SimpleSocialUsersDetailService(userDetailsService());
  }

}
