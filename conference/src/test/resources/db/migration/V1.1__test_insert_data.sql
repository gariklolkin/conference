INSERT INTO hall (id, name, places) VALUES (11, 'test11', 10);
INSERT INTO hall (id, name, places) VALUES (12, 'test12', 12);
INSERT INTO hall (id, name, places) VALUES (13, 'test13', 13);

INSERT INTO presentation (id, start_time, end_time, author, title, hall_id)
VALUES (1001, '10:00', '11:15', 'Andy Wilkinson', 'Spring Data REST', 11);
INSERT INTO presentation (id, start_time, end_time, author, title, hall_id)
VALUES (1002, '11:45', '15:00', 'Mikalai Alimenkou', 'Microservices in practice', 11);
INSERT INTO presentation (id, start_time, end_time, author, title, hall_id)
VALUES (1003, '10:00', '15:15', 'Ivan Ivanou', 'All about Spring workshop', 12);
