CREATE TABLE pacientes (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    document VARCHAR(6) NOT NULL UNIQUE,
    street VARCHAR(100) NOT NULL,
    district VARCHAR(100) NOT NULL,
    complement VARCHAR(100),
    number VARCHAR(20),
    city VARCHAR(100) NOT NULL,
    phone varchar(20) not null,
    active tinyint not null,
    PRIMARY KEY (id)
);
