DROP TABLE IF EXISTS npc;
DROP TABLE IF EXISTS player;
DROP TABLE IF EXISTS game;

CREATE TABLE game (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  number_of_rounds INT NOT NULL
);

CREATE TABLE npc (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  id_game INT NOT NULL,
  name VARCHAR(30) NOT NULL,
  score INT NOT NULL,
  round INT NOT NULL,
  throwing_order INT NOT NULL,
  difficulty VARCHAR(10) NOT NULL
);

CREATE TABLE player (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  id_game INT NOT NULL,
  name VARCHAR(30) NOT NULL,
  score INT NOT NULL,
  round INT NOT NULL,
  throwing_order INT NOT NULL
);

INSERT INTO npc (name, id_game, score, round, throwing_order, difficulty) VALUES
    ('npc1', 1, 10, 1, 1, 'Easy'),
    ('npc2', 2, 12, 2, 2, 'Medium');

INSERT INTO player (name, id_game, score, round, throwing_order) VALUES
    ('laur', 1, 30, 3, 1),
    ('mario', 1, 40, 4, 2);

INSERT INTO game (number_of_rounds) VALUES
    (12),
    (10);