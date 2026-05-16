-- Optional project-friendly views over the official F1DB schema.
-- Run this after importing f1db-sql-mysql-single-inserts.sql.
-- These views expose names closer to the project plan: drivers, constructors, races, results.

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
  d.full_name AS driver_name,
  d.abbreviation AS driver_code,
  rr.constructor_id,
  c.name AS constructor_name,
  c.full_name AS constructor_full_name,
  rr.grid_position_number AS grid,
  rr.position_number AS position,
  rr.position_text,
  rr.points,
  rr.laps,
  rr.reason_retired AS status
FROM race_result rr
LEFT JOIN driver d ON d.id = rr.driver_id
LEFT JOIN constructor c ON c.id = rr.constructor_id;
