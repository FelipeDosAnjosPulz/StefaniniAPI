package stefanini;

import com.sun.org.apache.xerces.internal.parsers.SecurityConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.jms.support.destination.DestinationResolver;
import org.springframework.jms.support.destination.JndiDestinationResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import javax.jms.ConnectionFactory;
import java.io.IOException;
import java.util.Properties;

@EnableJms
@SpringBootApplication(exclude = {SecurityConfiguration.class})
@Configuration
@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    @Autowired
    private Environment env;

    @PostConstruct
    public void initApplication() throws IOException {
        if (this.env.getActiveProfiles().length == 0) {
            Application.log.info("No Spring profile configured, running with default configuration");
        } else {
            Application.log.info("Running with Spring profile(s) : {}", String.join(",", (CharSequence[]) this.env.getActiveProfiles()));
        }
    }

    @Bean
    public JmsListenerContainerFactory<?> myFactory(
            ConnectionFactory connectionFactory,
            DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        return factory;
    }

    @Bean
    public MessageConverter jacksonJmsMessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }

    @Bean
    @ConditionalOnMissingBean(DestinationResolver.class)
    public JndiDestinationResolver destinationResolver() {
        JndiDestinationResolver resolver = new JndiDestinationResolver();
        resolver.setFallbackToDynamicDestination(true);
        return resolver;
    }

    protected SpringApplicationBuilder configure(final SpringApplicationBuilder application) {
        return application.properties(this.addDefaultProperties()).sources(new Class[]{Application.class});
    }

    private Properties addDefaultProperties() {
        Properties defaultProperties = new Properties();
        defaultProperties.put("spring.jmx.enabled", "false");
        defaultProperties.put("spring.mvc.locale", "pt_BR");
        defaultProperties.put("spring.resources.cachePeriod", "0");
        defaultProperties.put("multipart.maxFileSize", "1024mb");
        defaultProperties.put("multipart.maxRequestSize", "1024mb");
        defaultProperties.put("management.contextPath", "/api/management");
        defaultProperties.put("management.security.enabled", "false");
        return defaultProperties;
    }

}
