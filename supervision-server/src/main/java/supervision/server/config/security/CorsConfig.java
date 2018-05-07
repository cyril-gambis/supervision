package supervision.server.config.security;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


/**
 * Méthode non standard Spring de gestion CORS car le filtre Spring Security se fait
 * avant le filtre standard CORS de SecurityConfig.configure
 * 
 * @date 07/05/2018
 * @author Cyril Gambis
 */
@Configuration
public class CorsConfig {
//IMPORTANT: it has to be a normal configuration class, 
//not extending WebMvcConfigurerAdapter or other Spring Security class
    @Bean
    public FilterRegistrationBean corsCustomFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        //config.addAllowedOrigin("http://localhost:3000");
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*"); // "x-requested-with", "authorization"
        config.addAllowedMethod("*"); // "POST", "GET", "OPTIONS", "DELETE"
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }
}

/*
Autre possibilité, à tester éventuellement (mais à priori, cela ne fonctionne pas, notamment du fait des request preflight OPTION):

https://docs.spring.io/spring-security/site/docs/current/reference/html/cors.html

set your own CorsConfigurationSource in your Spring Security Config.
e.g. @Override protected void configure(HttpSecurity http) throws Exception { http.cors().configurationSource(new CustomConfigSource()); }

The CorsConfigurationSource offers a method CorsConfiguration
getCorsConfiguration(HttpServletRequest request); in which you can handle your specific CORS Handling.

*/