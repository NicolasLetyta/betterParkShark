CREATE SCHEMA IF NOT EXISTS parkshark;

ALTER SCHEMA parkshark owner TO student;

SET search_path TO parkshark;

CREATE TABLE address
(
    id int NOT NULL,
    street varchar(25) NOT NULL,
    street_number int,
    postal_code varchar(25) NOT NULL,
    city varchar(25) NOT NULL,
    country varchar(25) NOT NULL,

    CONSTRAINT pk_address PRIMARY KEY (id)
);

CREATE SEQUENCE address_seq start with 1 increment by 1;

CREATE TABLE membership_level
(
    id int NOT NULL,
    monthly_price decimal NOT NULL,
    reduction_hour int NOT NULL ,
    max_hour int NOT NULL,

    CONSTRAINT pk_membership_level PRIMARY KEY (id)
);

CREATE SEQUENCE membership_level_seq start with 1 increment by 1;

CREATE TABLE employee
(
    id int NOT NULL,
    first_name varchar(25) NOT NULL,
    last_name varchar(25) NOT NULL,
    mobile_phone int,
    telephone int,
    email varchar(25) NOT NULL UNIQUE,
    password varchar(25) NOT NULL,
    type_employee varchar(25) NOT NULL,
    address_id int NOT NULL,
    CONSTRAINT pk_employee PRIMARY KEY (id),

    CONSTRAINT fk_employee_address FOREIGN KEY (address_id) REFERENCES address(id)
);

CREATE SEQUENCE employee_seq start with 1 increment by 1;

CREATE TABLE division
(
    id int NOT NULL,
    name varchar(25) NOT NULL UNIQUE,
    original_name varchar(25) NOT NULL,
    director_id int NOT NULL,
    parent_id int,
    CONSTRAINT pk_division PRIMARY KEY (id),

    CONSTRAINT fk_division_director FOREIGN KEY (director_id) REFERENCES employee (id)
);

CREATE SEQUENCE division_seq start with 1 increment by 1;

CREATE TABLE member
(
    id int NOT NULL,
    first_name varchar(25) NOT NULL,
    last_name varchar(25) NOT NULL,
    phone int,
    email varchar(25) NOT NULL UNIQUE,
    password varchar(25) NOT NULL,
    license_plate varchar(25) NOT NULL UNIQUE,
    registration_date date NOT NULL,
    address_id int,
    membership_level_id int NOT NULL,
    CONSTRAINT pk_member PRIMARY KEY (id),

    CONSTRAINT fk_member_address FOREIGN KEY (address_id) REFERENCES address(id),
    CONSTRAINT fk_member_membership_level FOREIGN KEY (membership_level_id) REFERENCES membership_level(id)
);

CREATE SEQUENCE member_seq start with 1 increment by 1;

CREATE TABLE parking_lot
(
    id int NOT NULL,
    name varchar(25) NOT NULL UNIQUE,
    lot_category varchar(25) NOT NULL,
    max_capacity int NOT NULL,
    price_hour int,
    contact_person int NOT NULL,
    address_id int NOT NULL,
    division_id int NOT NULL,
    CONSTRAINT pk_parking_lot PRIMARY KEY (id),

    CONSTRAINT fk_parking_lot_contact_person FOREIGN KEY (contact_person) REFERENCES employee (id),
    CONSTRAINT fk_parking_lot_address FOREIGN KEY (address_id) REFERENCES address(id),
    CONSTRAINT fk_parking_lot_division FOREIGN KEY (division_id) REFERENCES division(id)
);

CREATE SEQUENCE parking_lot_seq start with 1 increment by 1;

CREATE TABLE allocation
(
    id int NOT NULL,
    member_id int NOT NULL,
    license_plate varchar(25) NOT NULL,
    parking_id int NOT NULL,
    start_time timestamp NOT NULL,
    end_time timestamp,
    CONSTRAINT pk_allocation PRIMARY KEY (id),

    CONSTRAINT fk_allocation_member FOREIGN KEY (member_id) REFERENCES member(id),
    CONSTRAINT fk_allocation_parking FOREIGN KEY (parking_id) REFERENCES parking_lot(id)
);

CREATE SEQUENCE allocation_seq start with 1 increment by 1;