package harel.todo.security;


import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;




@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true )
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/h2-console/**")
						.antMatchers(HttpMethod.POST,"/account/user");
	}
	
	@Override
	protected void configure(HttpSecurity http)throws Exception{
		
		http.httpBasic();
		http.csrf().disable();
		http.cors().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS );
		http.authorizeRequests()
								.antMatchers(HttpMethod.PUT,"/task/{login}").access("@customWebSecurity.checkAuthorityAndloginInPass(#login,authentication)")	
								.antMatchers(HttpMethod.GET,"/task/{login}").access("@customWebSecurity.checkAuthorityAndloginInPass(#login,authentication)")
								.antMatchers(HttpMethod.PUT,"/task/update/{login}").access("@customWebSecurity.checkAuthorityAndloginInPass(#login,authentication)")	
								.antMatchers(HttpMethod.DELETE,"/task/{login}/**").access("@customWebSecurity.checkAuthorityAndloginInPass(#login,authentication)")							
								.antMatchers(HttpMethod.POST,"/task/clear/tasks/{login}").access("@customWebSecurity.checkAuthorityAndloginInPass(#login,authentication)")																										
//								.antMatchers(HttpMethod.PUT,"/account/user").authenticated()
								.antMatchers(HttpMethod.DELETE,"/account/user/{login}").access("@customWebSecurity.checkAuthorityAndloginInPass(#login,authentication)")
								.antMatchers(HttpMethod.POST,"/account/login").authenticated()
								.antMatchers(HttpMethod.POST,"/account/user/{login}/role/{role}").hasRole("ADMINISTRATOR")
								.antMatchers(HttpMethod.DELETE,"/account/user/{login}/role/{role}").hasRole("ADMINISTRATOR")
								.antMatchers(HttpMethod.PUT,"/account/user/password/{login}").access("@customWebSecurity.checkAuthorityChangePassword(#idUser,authentication) or hasRole('ADMINISTRATOR')");
								
	}
	

}
