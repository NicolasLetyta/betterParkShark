SET search_path TO parkshark;




--MEMBER
INSERT INTO member (id, first_name, last_name, phone, email, password, license_plate, registration_date, address_id, membership_level_id)
VALUES (
           nextval('member_seq'),
           'John',
           'Doe',
           '+3283289392',
           'john@gmail.com',
           'random123',
           'ERG123B',
           '2022-05-08',
           null,
           1
       );

INSERT INTO member (id, first_name, last_name, phone, email, password, license_plate, registration_date, address_id, membership_level_id)
VALUES (
           nextval('member_seq'),
           'Jane',
           'Foo',
           '+3283232392',
           'jane@yahoo.com',
           'random123',
           'ARG232B',
           '2023-04-08',
           null,
           2
       );

INSERT INTO member (id, first_name, last_name, phone, email, password, license_plate, registration_date, address_id, membership_level_id)
VALUES (
           nextval('member_seq'),
           'Bob',
           'Sponge',
           '+3232838232',
           'bob@krusty.com',
           'random123',
           'ABC789C',
           '2024-12-01',
           null,
           3
       );




--EMPLOYEE
INSERT INTO employee (id, first_name, last_name, mobile_phone, telephone, email, password, type_employee, address_id)
VALUES (
           nextval('employee_seq'),
           'Squid',
           'Ward',
           '+3283289392',
           null,
           'squidward@gmail.com',
           'random123',
           'DIRECTOR',
           1
       );

INSERT INTO employee (id, first_name, last_name, mobile_phone, telephone, email, password, type_employee, address_id)
VALUES (
           nextval('employee_seq'),
           'Mister',
           'Krabs',
           '+3283289392',
           null,
           'krabs@gmail.com',
           'random123',
           'DIRECTOR',
           2
       );

INSERT INTO employee (id, first_name, last_name, mobile_phone, telephone, email, password, type_employee, address_id)
VALUES (
           nextval('employee_seq'),
           'Patrick',
           'Star',
           '+3283289392',
           null,
           'patrick@gmail.com',
           'random123',
           'ADMIN',
           3
       );

INSERT INTO employee (id, first_name, last_name, mobile_phone, telephone, email, password, type_employee, address_id)
VALUES (
           nextval('employee_seq'),
           'Jake',
           'Dont',
           '+3283289392',
           null,
           'jake@gmail.com',
           'random123',
           'CONTACT_PERSON',
           4
       );




--DIVISION
INSERT INTO division (id, name, original_name, director_id, parent_id)
VALUES (
           nextval('division_seq'),
           'Apple',
           'Apple',
           1,
           null
       );

INSERT INTO division (id, name, original_name, director_id, parent_id)
VALUES (
           nextval('division_seq'),
           'X',
           'Twitter',
           2,
           1
       );


--PARKING LOT
INSERT INTO parking_lot (id, name, lot_category, max_capacity, price_hour, contact_person, address_id, division_id)
VALUES (
           nextval('parking_lot_seq'),
           'parking1',
           'UNDERGROUND_BUILDING',
           350,
           3.5,
           4,
           5,
           1
       );

INSERT INTO parking_lot (id, name, lot_category, max_capacity, price_hour, contact_person, address_id, division_id)
VALUES (
           nextval('parking_lot_seq'),
           'parking2',
           'GROUND_BUILDING',
           250,
           2.5,
           4,
           6,
           2
       );


--ALLOCATION
INSERT INTO allocation (id, member_id, license_plate, parking_id, start_time, end_time)
VALUES (
           nextval('allocation_seq'),
           1,
           'ERG123B',
           1,
           TIMESTAMP '2024-12-01 15:35:40',
           TIMESTAMP '2024-12-01 17:35:40'
       );

INSERT INTO allocation (id, member_id, license_plate, parking_id, start_time, end_time)
VALUES (
           nextval('allocation_seq'),
           1,
           'ERG123B',
           1,
           TIMESTAMP '2024-12-01 18:35:40',
           null
       );