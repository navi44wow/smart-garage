INSERT INTO services (name, price)
VALUES ('Change filter', 25),
       ('Oil change', 50),
       ('Brake pad replacement', 100),
       ('Tire rotation', 35),
       ('Air conditioning recharge', 75),
       ('Transmission flush', 150),
       ('Battery replacement', 125),
       ('Windshield wiper replacement', 20),
       ('Coolant flush', 80),
       ('Fuel injector cleaning', 100),
       ('Engine tune-up', 200),
       ('Headlight restoration', 50),
       ('Alternator replacement', 175),
       ('Ignition system repair', 150),
       ('Suspension repair', 250),
       ('Power steering fluid flush', 100),
       ('Serpentine belt replacement', 75),
       ('Spark plug replacement', 75),
       ('Exhaust system repair', 200),
       ('Wheel alignment', 80);


INSERT INTO vehicles (VIN, creation_year, license_plate, model_id, user_id)
VALUES ('dlaksjdlak9089809', 2000, 'CB1000AA', 17, 'e24b6d5f-a52f-4a54-9f69-b8cbb40fdbce'),
       ('dlaksjdlak9089810', 2001, 'CB1001AA', 18, 'e24b6d5f-a52f-4a54-9f69-b8cbb40fdbce'),
       ('dlaksjdlak9089811', 2002, 'CB1002AA', 19, 'e24b6d5f-a52f-4a54-9f69-b8cbb40fdbce'),
       ('dlaksjdlak9089812', 2003, 'CB1003AA', 20, 'e24b6d5f-a52f-4a54-9f69-b8cbb40fdbce'),
       ('dlaksjdlak9089813', 2004, 'CB1004AA', 21, 'e24b6d5f-a52f-4a54-9f69-b8cbb40fdbce'),
       ('dlaksjdlak9089814', 2005, 'CB1005AA', 22, 'e24b6d5f-a52f-4a54-9f69-b8cbb40fdbce'),
       ('dlaksjdlak9089815', 2006, 'CB1006AA', 23, '37d6a043-d5f6-4c61-99a0-b1826eba85e4'),
       ('dlaksjdlak9089816', 2007, 'CB1007AA', 24, '37d6a043-d5f6-4c61-99a0-b1826eba85e4'),
       ('dlaksjdlak9089817', 2008, 'CB1008AA', 25, '37d6a043-d5f6-4c61-99a0-b1826eba85e4'),
       ('dlaksjdlak9089818', 2009, 'CB1009AA', 26, '37d6a043-d5f6-4c61-99a0-b1826eba85e4'),
       ('dlaksjdlak9089819', 2010, 'CB1010AA', 27, '37d6a043-d5f6-4c61-99a0-b1826eba85e4'),
       ('dlaksjdlak9089820', 2011, 'CB1011AA', 28, '37d6a043-d5f6-4c61-99a0-b1826eba85e4');


INSERT INTO visit_status(id, name)
VALUES (1, 'Not started'),
       (2, 'In progress'),
       (3, 'Completed');

INSERT INTO visits(vehicle_id, start_date, end_date, status_id)
VALUES
    (1, '2023-04-09', '2023-04-10', 1),
       (2, '2023-04-09', '2023-04-10', 2),
       (3, '2023-04-08', '2023-04-10', 3),
       (4, '2023-04-08', '2023-04-10', 1),
       (5, '2023-04-07', '2023-04-10', 2),
       (6, '2023-04-08', '2023-04-10', 3),
       (7, '2023-04-10', '2023-04-11', 1),
       (8, '2023-04-09', '2023-04-11', 2),
       (9, '2023-04-08', '2023-04-11', 3),
       (10, '2023-04-04', '2023-04-11', 1),
       (11, '2023-04-05', '2023-04-11', 2);


INSERT INTO `car-garage`.brands (brand_id, brand_name)
VALUES (1, 'audi'),
       (2, 'vw'),
       (3, 'bmw'),
       (4, 'mercedes-benz');



INSERT INTO `car-garage`.models (model_id, model_name, brand_id)
VALUES (17, 'q3', 1),
       (18, 'q5', 1),
       (19, 'q7', 1),
       (20, 'q8', 1),
       (21, '320d', 3),
       (22, '320i', 3),
       (23, '500d', 3),
       (24, '500i', 3),
       (25, '500xd', 3),
       (26, '500xi', 3),
       (27, 'e500', 4),
       (28, 'e280', 4),
       (29, 'e320', 4),
       (30, 's500', 4),
       (31, 's320', 4),
       (32, 's400', 4),
       (33, 'c320', 4),
       (34, 'c500', 4),
       (35, 'c400', 4),
       (38, 'golf', 2),
       (39, 'passat', 2),
       (40, 'jetta', 2),
       (41, 'polo', 2),
       (42, 'a3', 1),
       (43, 'a4', 1),
       (44, 'a5', 1),
       (45, 'a6', 1),
       (46, 'a8', 1);

INSERT INTO list_of_services(id, service_id, service_name, service_price, visit_id)
VALUES
    (1, 1, 'Change filter', 25, 12),
    (2, 2, 'Oil change', 50, 12),
    (3, 3, 'Brake pad replacement', 100, 12),
    (4, 4, 'Tire rotation', 35, 13),
    (5, 5, 'Air conditioning recharge', 75, 13),
    (6, 6, 'Transmission flush', 150, 13),
    (7, 7, 'Battery replacement', 125, 14),
    (8, 8, 'Windshield wiper replacement', 20, 14),
    (9, 9, 'Coolant flush', 80, 14),
    (10, 10, 'Fuel injector cleaning', 100, 15),
    (11, 11, 'Engine tune-up', 200, 15),
    (12, 12, 'Headlight restoration', 50, 16),
    (13, 13, 'Alternator replacement', 175, 16),
    (14, 14, 'Ignition system repair', 150, 17),
    (15, 15, 'Suspension repair', 250, 17),
    (16, 16, 'Power steering fluid flush', 100, 18),
    (17, 17, 'Serpentine belt replacement', 75, 18),
    (18, 18, 'Spark plug replacement', 75, 19),
    (19, 19, 'Exhaust system repair', 200, 19),
    (20, 20, 'Wheel alignment', 80, 20),
    (21, 1, 'Change filter', 25, 21),
    (22, 2, 'Oil change', 50, 21),
    (23, 3, 'Brake pad replacement', 100, 21),
    (24, 4, 'Tire rotation', 35, 22),
    (25, 5, 'Air conditioning recharge', 75, 22),
    (26, 6, 'Transmission flush', 150, 22);