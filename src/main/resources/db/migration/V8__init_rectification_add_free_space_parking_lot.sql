SET search_path TO parkshark;

ALTER TABLE parking_lot
ADD space_available integer;

UPDATE parking_lot
SET space_available = 349
WHERE id = 1;

UPDATE parking_lot
SET space_available = 250
WHERE id = 2;