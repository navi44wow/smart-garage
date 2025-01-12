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


INSERT INTO visit_status(id, name)
VALUES (1, 'Not started'),
       (2, 'In progress'),
       (3, 'Completed');



INSERT INTO `car-garage`.brands (brand_id, brand_name)
VALUES (1, 'audi'),
       (2, 'vw'),
       (3, 'bmw'),
       (4, 'mercedes-benz');

INSERT INTO `car-garage`.car_models (model_id, model_name, brand_id)
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
#        (3, '2023-04-08', '2023-04-10', 3, true),
#        (4, '2023-04-08', '2023-04-10', 1, false),
#        (5, '2023-04-07', '2023-04-10', 2, false),
#        (6, '2023-04-08', '2023-04-10', 3, true),
#        (7, '2023-04-10', '2023-04-11', 1, false),
#        (8, '2023-04-09', '2023-04-11', 2, false),
#        (9, '2023-04-08', '2023-04-11', 3, true),
#        (10, '2023-04-04', '2023-04-11', 1, false),
#        (11, '2023-04-05', '2023-04-11', 2, false);


INSERT INTO vehicles (VIN, creation_year, license_plate, model_id, user_id)
VALUES
    ('dlaksjdlak9089809', 2000, 'CB1000AA', 17, 'c3215d2d-c76e-41fa-be32-cd507c911bb4'),
    ('dlaksjdlak9089808', 2000, 'CB1000AA', 17, 'd75f5d1c-d550-4fba-be88-4b04ae4a7adc'),
    ('dlaksjdlak9089888', 2000, 'CB1000AA', 17, 'dac76825-dd6b-43fc-a33a-0ce33c574a97');



INSERT INTO visits(vehicle_id, start_date, due_date, status_id, is_archived)
VALUES
#     (1, '2023-04-09', '2023-04-10', 1, false),
       (1, '2023-04-09', '2023-04-10', 2, false),
       (2, '2023-04-01', '2023-04-10', 2, false),
       (2, '2023-03-09', '2023-04-10', 2, false);

INSERT INTO list_of_services(id, service_id, service_name, service_price, visit_id)
VALUES
    (1, 1, 'Change filter', 25, 1),
    (2, 2, 'Oil change', 50, 1),
    (3, 3, 'Brake pad replacement', 100, 1),
    (4, 4, 'Tire rotation', 35, 2),
    (5, 5, 'Air conditioning recharge', 75, 2),
    (6, 6, 'Transmission flush', 150, 2),
    (7, 7, 'Battery replacement', 125, 3),
    (8, 8, 'Windshield wiper replacement', 20, 3),
    (9, 9, 'Coolant flush', 80, 3),
    (10, 10, 'Fuel injector cleaning', 100, 1),
    (11, 11, 'Engine tune-up', 200, 1),
    (12, 12, 'Headlight restoration', 50, 1),
    (13, 13, 'Alternator replacement', 175, 2),
    (14, 14, 'Ignition system repair', 150, 3),
    (15, 15, 'Suspension repair', 250, 2),
    (16, 16, 'Power steering fluid flush', 100, 2),
    (17, 17, 'Serpentine belt replacement', 75, 1),
    (18, 18, 'Spark plug replacement', 75, 2),
    (19, 19, 'Exhaust system repair', 200, 3),
    (20, 20, 'Wheel alignment', 80, 3),
    (21, 1, 'Change filter', 25, 1),
    (22, 2, 'Oil change', 50, 2),
    (23, 3, 'Brake pad replacement', 100, 3),
    (24, 4, 'Tire rotation', 35, 2),
    (25, 5, 'Air conditioning recharge', 75, 1),
    (26, 6, 'Transmission flush', 150, 2);