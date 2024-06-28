package med.gomez.api.domain.query;

import jakarta.validation.constraints.NotNull;

public record DataCancellationConsultation(
        @NotNull
        Long idQuery,

        @NotNull
        ReasonCancellation reason
) {
}
