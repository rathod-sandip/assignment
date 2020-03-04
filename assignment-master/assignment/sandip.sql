

CREATE TABLE `users` (
  `id` bigint(20) NOT NULL,
  `email` varchar(50) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `username` varchar(50) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

INSERT INTO `users` (`id`,  `email`, `name`, `password`, `username`,`status`) VALUES
(1, 'pgdandare@gmail.com', 'Pratik', '$2a$10$bSxff4W0XESFQnGOKOMzLectj4ZfwZV79crR/17PPNx3K0kKErhwm', 'Gaurav','ACTIVATED');


CREATE TABLE `roles` (
  `id` bigint(20) NOT NULL,
  `name` varchar(60) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

INSERT INTO `roles` (`id`, `name`) VALUES
(1, 'ROLE_USER'),
(2, 'ROLE_PM'),
(3, 'ROLE_ADMIN');

CREATE TABLE `user_roles` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

INSERT INTO `user_roles` (`user_id`, `role_id`) VALUES
(1, 1),
(1, 3);


===================================================================

localhost:8080/api/auth/signin

{
  "username":"Gaurav",
  "password":"password"
}




localhost:8080/api/auth/signup

{
  "name":"Sandip",
  "username":"sandy",
  "email":"sandy@gmail.com",
  "role":["admin","user"],
  "password":"password"
}


http://localhost:8080/assignement/saveusers

{
  "name":"Sandip",
  "username":"sandy",
  "email":"sandy@gmail.com",
  "role":["admin","user"],
  "password":"password",
  "status":"ACTIVATED"
}