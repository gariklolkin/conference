CREATE TABLE hall
(
  id bigint NOT NULL PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL UNIQUE,
  places INT NOT NULL
);

CREATE TABLE presentation
(
  id bigint NOT NULL PRIMARY KEY AUTO_INCREMENT,
  end_time TIME NOT NULL,
  start_time TIME NOT NULL,
  author VARCHAR(100) NOT NULL,
  title VARCHAR(200) NOT NULL,
  hall_id bigint NOT NULL,
  CONSTRAINT unique_title_author UNIQUE (title, author),
  CONSTRAINT fk_presentation_hall FOREIGN KEY (hall_id) REFERENCES hall (id)
);
