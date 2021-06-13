package com.gmail.vx.rnd_news_generator.config;

import com.gmail.vx.rnd_news_generator.model.Source;
import com.gmail.vx.rnd_news_generator.model.roles.UserRole;
import com.gmail.vx.rnd_news_generator.services.SourceService;
import com.gmail.vx.rnd_news_generator.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(1000)
public class AppConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer{
    public static final String ADMIN = "admin";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/static/**")
                .addResourceLocations("/WEB-INF/static/");
    }

    @Autowired
    private SourceService sourceService;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CommandLineRunner demo(final UserService userService,
                                  final PasswordEncoder encoder) {
        return new CommandLineRunner() {
            @Override
            public void run(String... strings) throws Exception {
                userService.addUser(ADMIN,
                        encoder.encode("1234"),
                        UserRole.ADMIN);
                userService.addUser("user",
                        encoder.encode("password"),
                        UserRole.USER);
                userService.addUser("moder", encoder.encode("777"), UserRole.MODERATOR );

                /*Source source = new Source("Radio Svoboda");
                sourceService.addSource(source);
                Source source1 = new Source("Ukrainska Pravda");
                sourceService.addSource(source1);
                Source source2 = new Source("Ukrinform");
                sourceService.addSource(source2);*/

            }
        };
    }

}
