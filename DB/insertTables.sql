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
VALUES ('dlaksjdlak9089809', 2000, 'CB1000AA', 17, 'ad6cca3a-6de5-4c47-847d-c8cb72cbcaf0'),
       ('dlaksjdlak9089810', 2001, 'CB1001AA', 18, 'ad6cca3a-6de5-4c47-847d-c8cb72cbcaf0'),
       ('dlaksjdlak9089811', 2002, 'CB1002AA', 19, 'ad6cca3a-6de5-4c47-847d-c8cb72cbcaf0'),
       ('dlaksjdlak9089812', 2003, 'CB1003AA', 20, 'ad6cca3a-6de5-4c47-847d-c8cb72cbcaf0'),
       ('dlaksjdlak9089813', 2004, 'CB1004AA', 21, 'ad6cca3a-6de5-4c47-847d-c8cb72cbcaf0'),
       ('dlaksjdlak9089814', 2005, 'CB1005AA', 22, 'ad6cca3a-6de5-4c47-847d-c8cb72cbcaf0'),
       ('dlaksjdlak9089815', 2006, 'CB1006AA', 23, 'ad6cca3a-6de5-4c47-847d-c8cb72cbcaf0'),
       ('dlaksjdlak9089816', 2007, 'CB1007AA', 24, 'ad6cca3a-6de5-4c47-847d-c8cb72cbcaf0'),
       ('dlaksjdlak9089817', 2008, 'CB1008AA', 25, 'ad6cca3a-6de5-4c47-847d-c8cb72cbcaf0'),
       ('dlaksjdlak9089818', 2009, 'CB1009AA', 26, 'ad6cca3a-6de5-4c47-847d-c8cb72cbcaf0'),
       ('dlaksjdlak9089819', 2010, 'CB1010AA', 27, 'ad6cca3a-6de5-4c47-847d-c8cb72cbcaf0'),
       ('dlaksjdlak9089820', 2011, 'CB1011AA', 28, 'ad6cca3a-6de5-4c47-847d-c8cb72cbcaf0');


INSERT INTO visit_status(id, name)
VALUES (1, 'Not started'),
       (2, 'In progress'),
       (3, 'Completed');

INSERT INTO visits(user_id, vehicle_id, start_date, end_date, status_id)
VALUES ('ad6cca3a-6de5-4c47-847d-c8cb72cbcaf0', 14, '2023-04-10', '2023-04-11', 1),
       ('ad6cca3a-6de5-4c47-847d-c8cb72cbcaf0', 14, '2023-04-09', '2023-04-11', 2),
       ('ad6cca3a-6de5-4c47-847d-c8cb72cbcaf0', 14, '2023-04-08', '2023-04-11', 3),
       ('ad6cca3a-6de5-4c47-847d-c8cb72cbcaf0', 15, '2023-04-04', '2023-04-11', 1),
       ('ad6cca3a-6de5-4c47-847d-c8cb72cbcaf0', 16, '2023-04-05', '2023-04-11', 2);




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