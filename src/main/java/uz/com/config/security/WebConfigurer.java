package uz.com.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import uz.com.config.ApplicationContextProvider;

import java.util.Locale;

@Configuration
@EnableWebMvc
@EnableSpringDataWebSupport
@ComponentScan(value = {"uz.com.controller", "uz.com.service", "uz.com.hibernate.dao"})
@NoRepositoryBean
public class WebConfigurer implements WebMvcConfigurer {

    @Bean
    public ApplicationContextProvider applicationContextProvider() {
        return new ApplicationContextProvider();
    }

//    @Lazy
//    @Bean
//    public ViewResolver viewResolver() {
//        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
//        viewResolver.setViewClass(JstlView.class);
//        viewResolver.setPrefix("/WEB-INF/views/");
//        viewResolver.setSuffix(".html");
//        return viewResolver;
//    }

    @Bean
    public FreeMarkerConfigurationFactoryBean getFreeMarkerConfiguration() {
        FreeMarkerConfigurationFactoryBean bean = new FreeMarkerConfigurationFactoryBean();
        bean.setTemplateLoaderPath("classpath:/templates/");
        return bean;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");

//        registry.addResourceHandler("/js/**")
//                .addResourceLocations("classpath:/src/main/webapp/resources/js/").setCachePeriod(3600).resourceChain(true);
//        registry.addResourceHandler("/css/**")
//                .addResourceLocations("classpath:/src/main/webapp/resources/css/").setCachePeriod(3600).resourceChain(true);
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Lazy
    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    @Lazy
    @Bean
    public SessionLocaleResolver localeResolver() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(Locale.forLanguageTag("uz"));
        return localeResolver;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }

    @Lazy
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        return localeChangeInterceptor;
    }
}
