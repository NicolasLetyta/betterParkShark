SET search_path TO parkshark;

-- MEMBERSHIP LEVEL
INSERT INTO membership_level (id, monthly_price, reduction_hour, max_hour, name)
VALUES (
        nextval('membership_level_seq'),
        0,
        0,
        4,
        'bronze'
       );

INSERT INTO membership_level (id, monthly_price, reduction_hour, max_hour, name)
VALUES (
           nextval('membership_level_seq'),
           10,
           20,
           6,
           'silver'
       );

INSERT INTO membership_level (id, monthly_price, reduction_hour, max_hour, name)
VALUES (
           nextval('membership_level_seq'),
           40,
           30,
           24,
           'gold'
       );


-- ADDRESS
INSERT INTO address (id, street, street_number, postal_code, city, country)
VALUES (
           nextval('address_seq'),
           'Sesame Street',
           42,
           '2048',
           'Brussels',
           'Belgium'
       );

INSERT INTO address (id, street, street_number, postal_code, city, country)
VALUES (
           nextval('address_seq'),
           'Sesame Street',
           42,
           '2048',
           'Brussels',
           'Belgium'
       );

INSERT INTO address (id, street, street_number, postal_code, city, country)
VALUES (
           nextval('address_seq'),
           'Sesame Street',
           24,
           '2048',
           'Brussels',
           'Belgium'
       );

INSERT INTO address (id, street, street_number, postal_code, city, country)
VALUES (
           nextval('address_seq'),
           'Sesame Street',
           43,
           '2048',
           'Brussels',
           'Belgium'
       );

INSERT INTO address (id, street, street_number, postal_code, city, country)
VALUES (
           nextval('address_seq'),
           'Sesame Street',
           13,
           '2048',
           'Brussels',
           'Belgium'
       );

INSERT INTO address (id, street, street_number, postal_code, city, country)
VALUES (
           nextval('address_seq'),
           'Sesame Street',
           102,
           '2048',
           'Brussels',
           'Belgium'
       );

