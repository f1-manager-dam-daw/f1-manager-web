# Modelo relacional — F1 Manager Web

Basado en las tablas principales de F1DB que usa el proyecto Java y en las vistas creadas en `database/f1db_project_views.sql`.

## Tablas principales

- COUNTRY(**id**, alpha2_code, alpha3_code, ioc_code, name, demonym, *continent_id*)
- DRIVER(**id**, name, first_name, last_name, full_name, abbreviation, permanent_number, gender, date_of_birth, place_of_birth, *country_of_birth_country_id*, *nationality_country_id*, total_race_wins, total_podiums, total_points)
- CONSTRUCTOR(**id**, name, full_name, *country_id*, total_championship_wins, total_race_wins, total_podiums, total_points)
- SEASON(**year**)
- GRAND_PRIX(**id**, name, full_name, short_name, abbreviation, *country_id*)
- CIRCUIT(**id**, name, full_name, type, place_name, *country_id*, latitude, longitude, length, turns)
- CIRCUIT_LAYOUT(**id**, *circuit_id*, effective, length, turns)
- RACE(**id**, *year*, round, date, *grand_prix_id*, official_name, *circuit_id*, *circuit_layout_id*, laps, distance, drivers_championship_decider, constructors_championship_decider)
- RACE_DATA(**race_id**, **type**, **position_display_order**, position_number, position_text, driver_number, *driver_id*, *constructor_id*, race_grid_position_number, race_points, race_laps, race_reason_retired)

## Vistas usadas por la aplicación

- APP_DRIVERS(id, forename, surname, code, number, nationality, date_of_birth, total_points, total_race_wins, total_podiums)
- APP_CONSTRUCTORS(id, name, full_name, nationality, total_points, total_race_wins, total_podiums, total_championship_wins)
- APP_RACES(id, year, round, name, official_name, date, circuit_name, laps, distance, drivers_championship_decider, constructors_championship_decider)
- APP_RESULTS(race_id, driver_id, constructor_id, grid, position, position_text, points, laps, status)

## Relaciones clave

- COUNTRY 1:N DRIVER, CONSTRUCTOR, GRAND_PRIX y CIRCUIT.
- SEASON 1:N RACE.
- GRAND_PRIX 1:N RACE.
- CIRCUIT 1:N CIRCUIT_LAYOUT y CIRCUIT 1:N RACE.
- CIRCUIT_LAYOUT 1:N RACE.
- RACE 1:N RACE_DATA.
- DRIVER 1:N RACE_DATA.
- CONSTRUCTOR 1:N RACE_DATA.
