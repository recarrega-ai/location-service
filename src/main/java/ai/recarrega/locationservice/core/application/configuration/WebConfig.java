package ai.recarrega.locationservice.core.application.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        log.info("Registering converter for StatusTomadaConverter");
        registry.addConverter(new StatusTomadaConverter());
        WebMvcConfigurer.super.addFormatters(registry);
    }
}
