INSERT INTO user_leave_management.lm_user
VALUES (1, 'Fariba', 'Chowdhury', 'fariba', MD5('therap'), 'HR_EXECUTIVE', 50000.00, 1, '2021-11-23 17:01:00', NULL, 0),
       (2, 'Ashraf', 'Hasan', 'ashraf', MD5('therap'), 'TEAM_LEAD', 50000.00, 1, '2021-11-23 17:01:00', NULL, 0),
       (3, 'Tanvir', 'Rifat', 'rifat', MD5('therap'), 'TEAM_LEAD', 50000.00, 1, '2021-11-23 17:01:00', NULL, 0),
       (4, 'Shahriar', 'Dipto', 'dipto', MD5('therap'), 'DEVELOPER', 30000.00, 1, '2021-11-23 17:01:00', NULL, 0),
       (5, 'Faiyaz', 'Khan', 'faiyaz', MD5('therap'), 'DEVELOPER', 30000.00, 1, '2021-11-23 17:01:00', NULL, 0),
       (6, 'Soumik', 'Sarker', 'soumik', MD5('therap'), 'TESTER', 30000.00, 1, '2021-11-23 17:01:00', NULL, 0);

INSERT INTO user_leave_management.lm_user_management
VALUES (1, 4, 2, '2021-11-23 17:01:00', NULL, 0),
       (2, 5, 3, '2021-11-23 17:01:00', NULL, 0),
       (3, 6, 2, '2021-11-23 17:01:00', NULL, 0);

INSERT INTO user_leave_management.lm_leave_stat
VALUES (1, 1, 0, 0, '2021-11-23 17:01:00', NULL, 0),
       (2, 2, 0, 0, '2021-11-23 17:01:00', NULL, 0),
       (3, 3, 0, 0, '2021-11-23 17:01:00', NULL, 0),
       (4, 4, 0, 0, '2021-11-23 17:01:00', NULL, 0),
       (5, 5, 0, 0, '2021-11-23 17:01:00', NULL, 0),
       (6, 6, 0, 0, '2021-11-23 17:01:00', NULL, 0);