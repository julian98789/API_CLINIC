CREATE TABLE consultas (
    id BIGINT NOT NULL AUTO_INCREMENT,
    medic_id BIGINT NOT NULL,
    patient_id BIGINT NOT NULL,
    fecha datetime NOT NULL,

    PRIMARY KEY (id),

    CONSTRAINT fk_consultas_medic_id FOREIGN KEY (medic_id) REFERENCES medicos(id),
    CONSTRAINT fk_consultas_patient_id FOREIGN KEY (patient_id) REFERENCES pacientes(id)
);
