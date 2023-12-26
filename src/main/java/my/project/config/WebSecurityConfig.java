package my.project.config;

import my.project.service.Impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //Метод принимающий объект HttpSecurity
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //Описание доступа к конечным точкам
        http
                .authorizeRequests()
                    .antMatchers("/",
                            "/search",
                            "/registration",
                            "/contacts",
                            "/img/**",
                            "/uploads/**",
                            "/static/**",
                            "/activate/*",
                            "/product/*",
                            "/menu/**").permitAll()
                //Разрешение доступа к вышеперечисленым конечным точкам
                // не авторизованного пользователя
                    .anyRequest().authenticated()
                .and()
                //Передаем собственную форму для авторизации
                    .formLogin()
                    .loginPage("/login")
                    .permitAll()
                .and()
                //Обработка выхода пользователя из аккаунта
                    .logout()
                    .permitAll()
                //Обработка выхода пользователя из аккаунта
                .and().csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //Шифрование пользовательских паролей
        auth.userDetailsService(userService)
                .passwordEncoder(passwordEncoder);
    }
}