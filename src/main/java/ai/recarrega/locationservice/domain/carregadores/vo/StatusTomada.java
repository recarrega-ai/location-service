package ai.recarrega.locationservice.domain.carregadores.vo;

import com.fasterxml.jackson.annotation.JsonValue;

public enum StatusTomada {
    LIVRE("livre"), EM_USO("em_uso");

    final String label;

    StatusTomada(String label) {
        this.label = label;
    }

    @JsonValue
    public String getLabel() {
        return label;
    }
}
