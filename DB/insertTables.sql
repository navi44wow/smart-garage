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


INSERT INTO vehicles (VIN, brand, creation_year, license_plate, model, user_id)
VALUES ('dlaksjdlak9089809', 'audi', 2000, 'CB1000AA', 'a4', '49db55d4-68fc-4f34-9458-42dcb8684b75'),
       ('dlaksjdlak9089810', 'bmw', 2001, 'CB1001AA', '320d', '49db55d4-68fc-4f34-9458-42dcb8684b75'),
       ('dlaksjdlak9089811', 'mercedes', 2002, 'CB1002AA', 'eclass', '49db55d4-68fc-4f34-9458-42dcb8684b75'),
       ('dlaksjdlak9089812', 'subaru', 2003, 'CB1003AA', 'b2f', '49db55d4-68fc-4f34-9458-42dcb8684b75'),
       ('dlaksjdlak9089813', 'audi', 2004, 'CB1004AA', 'a4', '49db55d4-68fc-4f34-9458-42dcb8684b75'),
       ('dlaksjdlak9089814', 'bmw', 2005, 'CB1005AA', '320d', '49db55d4-68fc-4f34-9458-42dcb8684b75'),
       ('dlaksjdlak9089815', 'mercedes', 2006, 'CB1006AA', 'eclass', '27c0a0c0-866b-4413-8494-8739e487c1ab'),
       ('dlaksjdlak9089816', 'subaru', 2007, 'CB1007AA', 'b2f', '27c0a0c0-866b-4413-8494-8739e487c1ab'),
       ('dlaksjdlak9089817', 'audi', 2008, 'CB1008AA', 'a4', '27c0a0c0-866b-4413-8494-8739e487c1ab'),
       ('dlaksjdlak9089818', 'bmw', 2009, 'CB1009AA', '320d', '27c0a0c0-866b-4413-8494-8739e487c1ab'),
       ('dlaksjdlak9089819', 'mercedes', 2010, 'CB1010AA', 'eclass', '27c0a0c0-866b-4413-8494-8739e487c1ab'),
       ('dlaksjdlak9089820', 'subaru', 2011, 'CB1011AA', 'b2f', '27c0a0c0-866b-4413-8494-8739e487c1ab');


INSERT INTO visit_status(id, name)
VALUES (1, 'Not started'),
       (2, 'In progress'),
       (3, 'Completed');

INSERT INTO visits(user_id, vehicle_id, start_date, end_date, status_id)
VALUES
    ('27c0a0c0-866b-4413-8494-8739e487c1ab', 3, '2023-04-09', '2023-04-10', 1),
       ('27c0a0c0-866b-4413-8494-8739e487c1ab', 4, '2023-04-09', '2023-04-10', 2),
       ('27c0a0c0-866b-4413-8494-8739e487c1ab', 1, '2023-04-08', '2023-04-10', 3),
       ('27c0a0c0-866b-4413-8494-8739e487c1ab', 6, '2023-04-08', '2023-04-10', 1),
       ('27c0a0c0-866b-4413-8494-8739e487c1ab', 7, '2023-04-07', '2023-04-10', 2),
       ('27c0a0c0-866b-4413-8494-8739e487c1ab', 8, '2023-04-08', '2023-04-10', 3),
       ('49db55d4-68fc-4f34-9458-42dcb8684b75', 9, '2023-04-10', '2023-04-11', 1),
       ('49db55d4-68fc-4f34-9458-42dcb8684b75', 10, '2023-04-09', '2023-04-11', 2),
       ('49db55d4-68fc-4f34-9458-42dcb8684b75', 11, '2023-04-08', '2023-04-11', 3),
       ('49db55d4-68fc-4f34-9458-42dcb8684b75', 12, '2023-04-04', '2023-04-11', 1),
       ('49db55d4-68fc-4f34-9458-42dcb8684b75', 1, '2023-04-05', '2023-04-11', 2);


