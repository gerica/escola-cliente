package com.escola.client.model.response;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = false)
public class MunicipioResponse {
    String codigo;
    String descricao;
    String uf;
    EstadoResponse estado;

    public String getUf() {
        if (uf != null) {
            return uf;
        }
        if (estado != null) {
            return estado.sigla();
        }
        return "indetermidado";
    }
}
