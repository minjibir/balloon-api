CREATE TABLE IF NOT EXISTS political_party
(
  id           SERIAL UNIQUE PRIMARY KEY,
  name         VARCHAR(100) NOT NULL UNIQUE,
  abbreviation VARCHAR(10)  NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS geo_political_zone
(
  id           SERIAL UNIQUE PRIMARY KEY,
  name         VARCHAR(45) NOT NULL UNIQUE,
  abbreviation VARCHAR(5)  NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS state
(
  id      SERIAL UNIQUE PRIMARY KEY,
  name    VARCHAR(45) NOT NULL UNIQUE,
  zone_id INT         NOT NULL,
  CONSTRAINT fk_states_geo_political_zones1
    FOREIGN KEY (zone_id)
      REFERENCES geo_political_zone (id)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS local_government
(
  id       SERIAL UNIQUE PRIMARY KEY,
  name     VARCHAR(100) NOT NULL UNIQUE,
  state_id INT          NOT NULL,
  CONSTRAINT fk_local_governments_states1
    FOREIGN KEY (state_id)
      REFERENCES state (id)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS question
(
  id       SERIAL UNIQUE PRIMARY KEY,
  text     TEXT NOT NULL,
  choices  TEXT NOT NULL,
  added_at TIMESTAMP DEFAULT (NOW())
);

CREATE TABLE IF NOT EXISTS participant
(
  id                  SERIAL UNIQUE PRIMARY KEY,
  first_name          VARCHAR(45) NOT NULL,
  last_name           VARCHAR(45) NOT NULL,
  middle_name         VARCHAR(45) NULL,
  gender              VARCHAR(45) NOT NULL,
  marital_status      VARCHAR(45) NOT NULL,
  phone_number        VARCHAR(45) NULL,
  email_address       VARCHAR(45) NULL,
  employment_status   VARCHAR(45) NOT NULL,
  party_id            INT         NOT NULL,
  local_government_id INT         NOT NULL,
  CONSTRAINT fk_people_local_governments1
    FOREIGN KEY (local_government_id)
      REFERENCES local_government (id)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS survey
(
  id             SERIAL UNIQUE PRIMARY KEY,
  taken_at       TIMESTAMP DEFAULT (NOW()),
  participant_id INT       DEFAULT (NULL),
  CONSTRAINT fk_surveys_people1
    FOREIGN KEY (participant_id)
      REFERENCES participant (id)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS response
(
  id          SERIAL UNIQUE,
  survey_id   INT         NOT NULL,
  question_id INT         NOT NULL,
  response    VARCHAR(45) NOT NULL,
  PRIMARY KEY (id, survey_id),
  CONSTRAINT fk_surveysquestions_questions1
    FOREIGN KEY (id)
      REFERENCES question (id)
      ON DELETE CASCADE
      ON UPDATE CASCADE,
  CONSTRAINT fk_surveysquestions_surveys1
    FOREIGN KEY (survey_id)
      REFERENCES survey (id)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS users
(
  first_name    VARCHAR(45)  NOT NULL,
  last_name     VARCHAR(45)  NOT NULL,
  email_address VARCHAR(255) NOT NULL PRIMARY KEY UNIQUE,
  password      TEXT         NOT NULL,
  token         TEXT         NOT NULL,
  role          VARCHAR(45)  NOT NULL DEFAULT 'agent'
);
