package ai.recarrega.locationservice.core.application.configuration;

import ai.recarrega.locationservice.core.domain.carregadores.vo.StatusTomada;
import org.springframework.core.convert.converter.Converter;

public class StatusTomadaConverter implements Converter<String, StatusTomada> {
    @Override
    public StatusTomada convert(String s) {
        return StatusTomada.valueOf(s.toUpperCase());
    }
}
