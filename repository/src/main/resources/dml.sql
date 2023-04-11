insert into gift_certificate (name, description, price, duration, created_date, last_updated_date) values ('Happy birthday!', 'May your birthday be filled with the warmth of good friends, whether they be old or new. May the stars align and bring you good fortune, and may happiness fill your heart and soul.', 8.0, 10, '2023-01-20 03:16:42', '2023-01-24 03:00:43');
insert into gift_certificate (name, description, price, duration, created_date, last_updated_date) values ('Happy Navruz!', 'May the joyous celebration of Navruz bring new beginnings and fresh opportunities to your life. May the arrival of spring fill your heart with warmth and your soul with happiness. Wishing you and your loved ones a very happy Navruz, filled with love, peace, and prosperity', 6.0, 20, '2023-01-23 01:48:17', '2023-02-21 05:52:33');
insert into gift_certificate (name, description, price, duration, created_date, last_updated_date) values ('Project leader!', 'Congratulations on earning the ''Project Leader'' badge! Your exceptional leadership skills have made a significant impact on the project''s success. Keep up the fantastic work!', 30.0, 50, '2023-02-04 06:04:31', '2023-02-15 09:33:01');
insert into gift_certificate (name, description, price, duration, created_date, last_updated_date) values ('Happy vacation!', 'Have a fantastic ''Happy Vacation''! Enjoy your well-deserved break, create lasting memories, and come back rejuvenated. Safe travels and we can''t wait to hear about your adventures!', 35.0, 40, '2023-01-02 22:39:16', '2023-01-03 17:56:06');
insert into gift_certificate (name, description, price, duration, created_date, last_updated_date) values ('Volunteer hero!', 'Thank you for being a ''Volunteer Hero'' and making a positive difference. Your selflessness and dedication are truly admirable. Keep up the fantastic work!', 20.0, 40, '2023-02-21 05:52:33', '2023-02-25 17:53:52');
insert into gift_certificate (name, description, price, duration, created_date, last_updated_date) values ('Level up!', 'Congratulations on your new title! One step closer to becoming EPAM''s legend!', 10.0, 10, '2023-02-21 05:55:33', '2023-02-25 17:59:52');

insert into tag (name) values ('family');
insert into tag (name) values ('job');
insert into tag (name) values ('travelling');
insert into tag (name) values ('love');
insert into tag (name) values ('birthday');
insert into tag (name) values ('holiday');

insert into certificate_tag (certificate_id, tag_id) values (1, 1);
insert into certificate_tag (certificate_id, tag_id) values (1, 4);
insert into certificate_tag (certificate_id, tag_id) values (1, 5);
insert into certificate_tag (certificate_id, tag_id) values (1, 6);
insert into certificate_tag (certificate_id, tag_id) values (2, 6);
insert into certificate_tag (certificate_id, tag_id) values (2, 4);
insert into certificate_tag (certificate_id, tag_id) values (3, 2);
insert into certificate_tag (certificate_id, tag_id) values (4, 2);
insert into certificate_tag (certificate_id, tag_id) values (4, 3);
insert into certificate_tag (certificate_id, tag_id) values (5, 2);
insert into certificate_tag (certificate_id, tag_id) values (5, 3);
insert into certificate_tag (certificate_id, tag_id) values (6, 2);