-- Script SQL resumido — estructura principal usada por F1 Manager Web
-- Motor: MariaDB / MySQL
-- Nota: el proyecto real importa el dataset F1DB completo y después ejecuta database/f1db_project_views.sql.
-- Este script recoge las tablas y vistas principales para documentar/defender el modelo de BD del proyecto.

CREATE DATABASE IF NOT EXISTS f1_manager
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE f1_manager;

CREATE TABLE country (
  id VARCHAR(100) PRIMARY KEY,
  alpha2_code VARCHAR(2) NOT NULL UNIQUE,
  alpha3_code VARCHAR(3) NOT NULL UNIQUE,
  ioc_code VARCHAR(3),
  name VARCHAR(100) NOT NULL UNIQUE,
  demonym VARCHAR(100),
  continent_id VARCHAR(100) NOT NULL
);

CREATE TABLE driver (
  id VARCHAR(100) PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  first_name VARCHAR(100) NOT NULL,
  last_name VARCHAR(100) NOT NULL,
  full_name VARCHAR(100) NOT NULL,
  abbreviation VARCHAR(3) NOT NULL,
  permanent_number VARCHAR(2),
  gender VARCHAR(6) NOT NULL,
  date_of_birth DATE NOT NULL,
  place_of_birth VARCHAR(100) NOT NULL,
  country_of_birth_country_id VARCHAR(100) NOT NULL,
  nationality_country_id VARCHAR(100) NOT NULL,
  total_championship_wins INT NOT NULL DEFAULT 0,
  total_race_wins INT NOT NULL DEFAULT 0,
  total_podiums INT NOT NULL DEFAULT 0,
  total_points DECIMAL(8,2) NOT NULL DEFAULT 0,
  CONSTRAINT fk_driver_birth_country FOREIGN KEY (country_of_birth_country_id) REFERENCES country(id),
  CONSTRAINT fk_driver_nationality FOREIGN KEY (nationality_country_id) REFERENCES country(id)
);

CREATE TABLE constructor (
  id VARCHAR(100) PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  full_name VARCHAR(100) NOT NULL,
  country_id VARCHAR(100) NOT NULL,
  total_championship_wins INT NOT NULL DEFAULT 0,
  total_race_wins INT NOT NULL DEFAULT 0,
  total_podiums INT NOT NULL DEFAULT 0,
  total_points DECIMAL(8,2) NOT NULL DEFAULT 0,
  CONSTRAINT fk_constructor_country FOREIGN KEY (country_id) REFERENCES country(id)
);

CREATE TABLE season (
  year INT PRIMARY KEY
);

CREATE TABLE grand_prix (
  id VARCHAR(100) PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  full_name VARCHAR(100) NOT NULL,
  short_name VARCHAR(100) NOT NULL,
  abbreviation VARCHAR(3) NOT NULL,
  country_id VARCHAR(100),
  CONSTRAINT fk_grand_prix_country FOREIGN KEY (country_id) REFERENCES country(id)
);

CREATE TABLE circuit (
  id VARCHAR(100) PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  full_name VARCHAR(100) NOT NULL,
  type VARCHAR(6) NOT NULL,
  place_name VARCHAR(100) NOT NULL,
  country_id VARCHAR(100) NOT NULL,
  latitude DECIMAL(10,6) NOT NULL,
  longitude DECIMAL(10,6) NOT NULL,
  length DECIMAL(6,3) NOT NULL,
  turns INT NOT NULL,
  CONSTRAINT fk_circuit_country FOREIGN KEY (country_id) REFERENCES country(id)
);

CREATE TABLE circuit_layout (
  id VARCHAR(100) PRIMARY KEY,
  circuit_id VARCHAR(100) NOT NULL,
  effective BOOLEAN NOT NULL,
  length DECIMAL(6,3) NOT NULL,
  turns INT NOT NULL,
  CONSTRAINT fk_layout_circuit FOREIGN KEY (circuit_id) REFERENCES circuit(id)
);

CREATE TABLE race (
  id INT PRIMARY KEY,
  year INT NOT NULL,
  round INT NOT NULL,
  date DATE NOT NULL,
  grand_prix_id VARCHAR(100) NOT NULL,
  official_name VARCHAR(100) NOT NULL,
  circuit_id VARCHAR(100) NOT NULL,
  circuit_layout_id VARCHAR(100) NOT NULL,
  laps INT NOT NULL,
  distance DECIMAL(6,3) NOT NULL,
  drivers_championship_decider BOOLEAN NOT NULL DEFAULT FALSE,
  constructors_championship_decider BOOLEAN NOT NULL DEFAULT FALSE,
  UNIQUE (year, round),
  CONSTRAINT fk_race_season FOREIGN KEY (year) REFERENCES season(year),
  CONSTRAINT fk_race_grand_prix FOREIGN KEY (grand_prix_id) REFERENCES grand_prix(id),
  CONSTRAINT fk_race_circuit FOREIGN KEY (circuit_id) REFERENCES circuit(id),
  CONSTRAINT fk_race_layout FOREIGN KEY (circuit_layout_id) REFERENCES circuit_layout(id)
);

CREATE TABLE race_data (
  race_id INT NOT NULL,
  type VARCHAR(50) NOT NULL,
  position_display_order INT NOT NULL,
  position_number INT,
  position_text VARCHAR(4) NOT NULL,
  driver_number VARCHAR(3) NOT NULL,
  driver_id VARCHAR(100) NOT NULL,
  constructor_id VARCHAR(100) NOT NULL,
  race_grid_position_number INT,
  race_laps INT,
  race_reason_retired VARCHAR(100),
  race_points DECIMAL(8,2),
  PRIMARY KEY (race_id, type, position_display_order),
  CONSTRAINT fk_race_data_race FOREIGN KEY (race_id) REFERENCES race(id),
  CONSTRAINT fk_race_data_driver FOREIGN KEY (driver_id) REFERENCES driver(id),
  CONSTRAINT fk_race_data_constructor FOREIGN KEY (constructor_id) REFERENCES constructor(id)
);

CREATE OR REPLACE VIEW app_drivers AS
SELECT
  d.id,
  d.first_name AS forename,
  d.last_name AS surname,
  d.abbreviation AS code,
  d.permanent_number AS number,
  nc.demonym AS nationality,
  d.date_of_birth,
  d.total_points,
  d.total_race_wins,
  d.total_podiums
FROM driver d
LEFT JOIN country nc ON nc.id = d.nationality_country_id;

CREATE OR REPLACE VIEW app_constructors AS
SELECT
  c.id,
  c.name,
  c.full_name,
  co.demonym AS nationality,
  c.total_points,
  c.total_race_wins,
  c.total_podiums,
  c.total_championship_wins
FROM constructor c
LEFT JOIN country co ON co.id = c.country_id;

CREATE OR REPLACE VIEW app_races AS
SELECT
  r.id,
  r.year,
  r.round,
  gp.name AS name,
  r.official_name,
  r.date,
  ci.name AS circuit_name,
  r.laps,
  r.distance,
  r.drivers_championship_decider,
  r.constructors_championship_decider
FROM race r
LEFT JOIN grand_prix gp ON gp.id = r.grand_prix_id
LEFT JOIN circuit ci ON ci.id = r.circuit_id;

CREATE OR REPLACE VIEW app_results AS
SELECT
  rr.race_id,
  rr.driver_id,
  rr.constructor_id,
  rr.race_grid_position_number AS grid,
  rr.position_number AS position,
  rr.position_text,
  rr.race_points AS points,
  rr.race_laps AS laps,
  rr.race_reason_retired AS status
FROM race_data rr
WHERE rr.type = 'RACE_RESULT';

CREATE INDEX idx_driver_name ON driver(last_name, first_name);
CREATE INDEX idx_constructor_name ON constructor(name);
CREATE INDEX idx_race_year_round ON race(year, round);
CREATE INDEX idx_race_data_driver ON race_data(driver_id);
CREATE INDEX idx_race_data_constructor ON race_data(constructor_id);
