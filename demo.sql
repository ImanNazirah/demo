-- -------------------------------------------------------------------------
-----------------------------------------CREATE-----------------------------
----------------------------------------------------------------------------
----------------------------------------------------------------------------

create table spotify_song (
	id BIGINT NOT NULL AUTO_INCREMENT,
	track_name VARCHAR(45) NOT NULL,
	artist_name VARCHAR(45) NOT NULL,
	genre VARCHAR(45) NOT NULL,
	popularity INT NOT NULL,
	 PRIMARY KEY ( id )
)


-- -------------------------------------------------------------------------
-----------------------------------------SEED DATA--------------------------
----------------------------------------------------------------------------
----------------------------------------------------------------------------

INSERT INTO `spotify_song` (`track_name`,`artist_name`,`genre`,`popularity`) VALUES
('Senorita','Shawn Mendes','canadian pop',79),
('China','Anuel AA','reggaeton flow',92),
('boyfriend (with Social House)','Ariana Grande','dance pop',85),
('Beautiful People (feat. Khalid)','Ed Sheeran','pop',86),
('Goodbyes (Feat. Young Thug)','Post Malone','dfw rap',94),
('I Dont Care (with Justin Bieber)','Ed Sheeran','pop',84),
('bad guy','Billie Eilish','electropop',95),
('Callaita','Bad Bunny','reggaeton',93),
('Ransom','Lil Tecca','trap music',92),
('How Do You Sleep?','Sam Smith','pop',90),
('Old Town Road - Remix','Lil Nas X','country rap',87),
('Loco Contigo (feat. J. Balvin & Tyga)','DJ Snake','dance pop',86),
('Someone You Loved','Lewis Capaldi','pop',88),
('Otro Trago - Remix','Sech','panamanian pop',87),
('Money In The Grave (Drake ft. Rick Ross)','Drake','canadian hip hop',92);

CREATE TABLE `user` (
  `id` varchar(100) NOT NULL,
  `email` varchar(300) DEFAULT NULL,
  `username` varchar(30) DEFAULT NULL,
  `password` varchar(256) DEFAULT NULL,
  `created` timestamp NULL DEFAULT NULL,
  `updated` timestamp NULL DEFAULT NULL,
  `full_name` varchar(250) DEFAULT NULL,
  `is_enable` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `User_un_email` (`email`),
  UNIQUE KEY `User_un_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO demo.`user`
(id,email, username, password, full_name, is_enable,created)
VALUES('9afb2853-7e37-498f-a7f5-08199e3964f6','test1@gmail.com', 'demo_test1', '$2a$10$/DlV8jJGrPXqDeoFNi.IDOwpLGkHh1QicjhIvwpInCJMWv.SpfrRy', 'Demo Test1', 1, NOW());