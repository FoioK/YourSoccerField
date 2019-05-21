INSERT INTO address (id, apartment_number, city, street)
VALUES (1, '1', 'city', 'street');

INSERT INTO surface
(id, name)
VALUES (1, 'name');

INSERT INTO open_hour (id, e1, e2, e3, e4, e5, e6, e7, s1, s2, s3, s4, s5, s6, s7)
VALUES (1, '20:00:00', '20:00:00', '20:00:00', '20:00:00', '20:00:00', '20:00:00', '20:00:00', '08:00:00', '08:00:00',
        '08:00:00', '08:00:00', '08:00:00', '08:00:00', '08:00:00');

INSERT INTO soccer_field
(id, description, is_fenced, is_lighting, is_locker_room, length, name, price, width, address_id, surface_id,
 open_hour_id)
VALUES (1, 'description', true, true, true, 100, 'name', '150.00', 60, 1, 1, 1);