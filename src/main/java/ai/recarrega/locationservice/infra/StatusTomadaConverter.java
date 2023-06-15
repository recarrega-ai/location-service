package ai.recarrega.locationservice.infra;

import org.springframework.core.convert.converter.Converter;

import ai.recarrega.locationservice.domain.carregadores.vo.StatusTomada;

public class StatusTomadaConverter implements Converter<String, StatusTomada> {
    @Override
    public StatusTomada convert(String s) {
        return StatusTomada.valueOf(s.toUpperCase());
    }
}
