CREATE TABLE employee (
    id INT,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    phone_number VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    birth_date TIMESTAMP WITH TIME ZONE NOT NULL,
    position VARCHAR(255) NOT NULL,

    PRIMARY KEY (id)
);