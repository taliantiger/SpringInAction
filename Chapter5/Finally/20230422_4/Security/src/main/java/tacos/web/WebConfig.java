package tacos.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry){
//        If there "/home" in the WebMVC, there is no need to have HomeController Class
//        It will auto to find home.html in the templates folder
        registry.addViewController("/").setViewName("home");

//        If there "/login" in the WebMVC, there is no need to have LoginController Class
//        It will auto to find login.html in the templates folder
        registry.addViewController("/login");
    }
}



