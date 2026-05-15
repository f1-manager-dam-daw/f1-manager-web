# Base de Datos — F1 Manager Web

## 1. Resumen

El proyecto usa una base de datos **MariaDB** alojada en **AWS RDS**.

La base de datos contiene información histórica de Fórmula 1 importada desde el dataset **F1DB**. Para facilitar el uso desde Java y desde el frontend, además de las tablas originales se han creado varias vistas simplificadas con los campos más útiles para la aplicación.

---

## 2. Datos de conexión

> Importante: estos datos no deben subirse a repositorios públicos.

```text
Motor: MariaDB
Host: f1-manager-db.czam0qussyez.us-east-1.rds.amazonaws.com
Puerto: 3306
Base de datos: f1_manager
Región AWS: us-east-1
```

Usuario recomendado para la aplicación Java:

```text
Usuario: f1_app
Contraseña: F1a_P8gROcjZc8kFCItoT4_X
```

URL JDBC:

```text
jdbc:mysql://f1-manager-db.czam0qussyez.us-east-1.rds.amazonaws.com:3306/f1_manager
```

Ejemplo de conexión desde consola:

```bash
mariadb -h f1-manager-db.czam0qussyez.us-east-1.rds.amazonaws.com -P 3306 -u f1_app -p f1_manager
```

---

## 3. Estado actual de la base de datos

La base de datos ya está importada y contiene datos.

| Elemento | Registros |
|---|---:|
| `driver` | 915 |
| `constructor` | 187 |
| `race` | 1171 |
| `circuit` | 78 |
| `season` | 77 |
| `app_drivers` | 915 |
| `app_constructors` | 187 |
| `app_races` | 1171 |
| `app_results` | 27379 |

---

## 4. Tablas principales usadas

Aunque F1DB tiene muchas tablas, para este proyecto se recomienda empezar usando estas tablas principales:

| Tabla | Uso en el proyecto |
|---|---|
| `driver` | Información de pilotos |
| `constructor` | Información de escuderías/constructores |
| `race` | Información de carreras |
| `circuit` | Información de circuitos |
| `season` | Temporadas disponibles |

---

## 5. Tabla `driver`

Tabla principal de pilotos.

Campos más importantes:

| Campo | Tipo | Descripción |
|---|---|---|
| `id` | varchar(100) | Identificador del piloto |
| `name` | varchar(100) | Nombre corto |
| `first_name` | varchar(100) | Nombre |
| `last_name` | varchar(100) | Apellidos |
| `full_name` | varchar(100) | Nombre completo |
| `abbreviation` | varchar(3) | Código de tres letras |
| `permanent_number` | varchar(2) | Número permanente del piloto |
| `gender` | varchar(6) | Género |
| `date_of_birth` | date | Fecha de nacimiento |
| `nationality_country_id` | varchar(100) | País/nacionalidad |
| `total_race_wins` | int | Victorias totales |
| `total_podiums` | int | Podios totales |
| `total_points` | decimal(8,2) | Puntos totales |

Consulta de prueba:

```sql
SELECT *
FROM driver
LIMIT 10;
```

---

## 6. Tabla `constructor`

Tabla principal de escuderías o constructores.

Campos más importantes:

| Campo | Tipo | Descripción |
|---|---|---|
| `id` | varchar(100) | Identificador de la escudería |
| `name` | varchar(100) | Nombre corto |
| `full_name` | varchar(100) | Nombre completo |
| `country_id` | varchar(100) | País |
| `total_championship_wins` | int | Campeonatos ganados |
| `total_race_wins` | int | Victorias totales |
| `total_podiums` | int | Podios totales |
| `total_points` | decimal(8,2) | Puntos totales |

Consulta de prueba:

```sql
SELECT *
FROM constructor
LIMIT 10;
```

---

## 7. Tabla `race`

Tabla principal de carreras.

Campos más importantes:

| Campo | Tipo | Descripción |
|---|---|---|
| `id` | int | Identificador de la carrera |
| `year` | int | Año de la temporada |
| `round` | int | Número de carrera en la temporada |
| `date` | date | Fecha de la carrera |
| `grand_prix_id` | varchar(100) | Identificador del Gran Premio |
| `official_name` | varchar(100) | Nombre oficial |
| `circuit_id` | varchar(100) | Circuito asociado |
| `laps` | int | Vueltas de la carrera |
| `distance` | decimal(6,3) | Distancia total |

Consulta de prueba:

```sql
SELECT *
FROM race
ORDER BY year DESC, round ASC
LIMIT 10;
```

---

## 8. Tabla `circuit`

Tabla de circuitos.

Campos más importantes:

| Campo | Tipo | Descripción |
|---|---|---|
| `id` | varchar(100) | Identificador del circuito |
| `name` | varchar(100) | Nombre corto |
| `full_name` | varchar(100) | Nombre completo |
| `type` | varchar(6) | Tipo de circuito |
| `place_name` | varchar(100) | Lugar o ciudad |
| `country_id` | varchar(100) | País |
| `latitude` | decimal(10,6) | Latitud |
| `longitude` | decimal(10,6) | Longitud |
| `length` | decimal(6,3) | Longitud del circuito |
| `turns` | int | Número de curvas |

Consulta de prueba:

```sql
SELECT *
FROM circuit
LIMIT 10;
```

---

## 9. Tabla `season`

Tabla de temporadas disponibles.

| Campo | Tipo | Descripción |
|---|---|---|
| `year` | int | Año de la temporada |

Consulta de prueba:

```sql
SELECT *
FROM season
ORDER BY year DESC;
```

---

# 10. Vistas del proyecto

Las vistas son consultas guardadas que simplifican el trabajo del backend Java. En lugar de consultar muchas columnas o hacer joins complejos, el backend puede consultar estas vistas directamente.

Vistas principales:

```text
app_drivers
app_constructors
app_races
app_results
```

---

## 10.1 Vista `app_drivers`

Vista pensada para listar pilotos en el frontend.

Campos:

| Campo | Tipo | Descripción |
|---|---|---|
| `id` | varchar(100) | Identificador del piloto |
| `forename` | varchar(100) | Nombre |
| `surname` | varchar(100) | Apellidos |
| `code` | varchar(3) | Código de tres letras |
| `number` | varchar(2) | Número permanente |
| `nationality` | varchar(100) | Nacionalidad |
| `date_of_birth` | date | Fecha de nacimiento |
| `total_points` | decimal(8,2) | Puntos totales |
| `total_race_wins` | int | Victorias totales |
| `total_podiums` | int | Podios totales |

Consulta recomendada para backend:

```sql
SELECT *
FROM app_drivers
ORDER BY surname, forename;
```

Consulta con búsqueda:

```sql
SELECT *
FROM app_drivers
WHERE forename LIKE CONCAT('%', ?, '%')
   OR surname LIKE CONCAT('%', ?, '%')
   OR code LIKE CONCAT('%', ?, '%')
ORDER BY surname, forename;
```

Ejemplo de uso en DBeaver:

```sql
SELECT *
FROM app_drivers
LIMIT 10;
```

---

## 10.2 Vista `app_constructors`

Vista pensada para listar escuderías/constructores.

Campos:

| Campo | Tipo | Descripción |
|---|---|---|
| `id` | varchar(100) | Identificador de la escudería |
| `name` | varchar(100) | Nombre corto |
| `full_name` | varchar(100) | Nombre completo |
| `nationality` | varchar(100) | Nacionalidad/país |
| `total_points` | decimal(8,2) | Puntos totales |
| `total_race_wins` | int | Victorias totales |
| `total_podiums` | int | Podios totales |
| `total_championship_wins` | int | Campeonatos ganados |

Consulta recomendada para backend:

```sql
SELECT *
FROM app_constructors
ORDER BY name;
```

Consulta con búsqueda:

```sql
SELECT *
FROM app_constructors
WHERE name LIKE CONCAT('%', ?, '%')
   OR full_name LIKE CONCAT('%', ?, '%')
   OR nationality LIKE CONCAT('%', ?, '%')
ORDER BY name;
```

---

## 10.3 Vista `app_races`

Vista pensada para listar carreras.

Campos:

| Campo | Tipo | Descripción |
|---|---|---|
| `id` | int | Identificador de la carrera |
| `year` | int | Año |
| `round` | int | Número de carrera dentro de la temporada |
| `name` | varchar(100) | Nombre corto del Gran Premio |
| `official_name` | varchar(100) | Nombre oficial |
| `date` | date | Fecha |
| `circuit_name` | varchar(100) | Nombre del circuito |
| `laps` | int | Vueltas |
| `distance` | decimal(6,3) | Distancia total |
| `drivers_championship_decider` | tinyint(1) | Indica si decidió el campeonato de pilotos |
| `constructors_championship_decider` | tinyint(1) | Indica si decidió el campeonato de constructores |

Consulta recomendada para backend:

```sql
SELECT *
FROM app_races
ORDER BY year DESC, round ASC;
```

Consulta filtrando por temporada:

```sql
SELECT *
FROM app_races
WHERE year = ?
ORDER BY round ASC;
```

Ejemplo:

```sql
SELECT *
FROM app_races
ORDER BY year DESC, round ASC
LIMIT 10;
```

---

## 10.4 Vista `app_results`

Vista pensada para mostrar resultados de carreras.

Campos:

| Campo | Tipo | Descripción |
|---|---|---|
| `race_id` | int | Identificador de la carrera |
| `driver_id` | varchar(100) | Identificador del piloto |
| `constructor_id` | varchar(100) | Identificador de la escudería |
| `grid` | int | Posición de salida |
| `position` | int | Posición final numérica |
| `position_text` | varchar(4) | Posición final como texto |
| `points` | decimal(8,2) | Puntos conseguidos |
| `laps` | int | Vueltas completadas |
| `status` | varchar(100) | Estado final |

Consulta recomendada para backend:

```sql
SELECT *
FROM app_results
WHERE race_id = ?
ORDER BY position ASC;
```

Ejemplo:

```sql
SELECT *
FROM app_results
WHERE race_id = 1
ORDER BY position ASC;
```

---

# 11. Consultas útiles para el proyecto

## Total de pilotos

```sql
SELECT COUNT(*) AS total_drivers
FROM driver;
```

## Total de escuderías

```sql
SELECT COUNT(*) AS total_constructors
FROM constructor;
```

## Total de carreras

```sql
SELECT COUNT(*) AS total_races
FROM race;
```

## Rango de temporadas

```sql
SELECT MIN(year) AS first_season,
       MAX(year) AS last_season
FROM season;
```

## Top 10 pilotos por victorias

```sql
SELECT id,
       forename,
       surname,
       nationality,
       total_race_wins,
       total_podiums,
       total_points
FROM app_drivers
ORDER BY total_race_wins DESC
LIMIT 10;
```

## Top 10 escuderías por victorias

```sql
SELECT id,
       name,
       nationality,
       total_race_wins,
       total_podiums,
       total_points
FROM app_constructors
ORDER BY total_race_wins DESC
LIMIT 10;
```

## Carreras de una temporada

```sql
SELECT *
FROM app_races
WHERE year = 2026
ORDER BY round ASC;
```

## Resultados de una carrera

```sql
SELECT *
FROM app_results
WHERE race_id = 1
ORDER BY position ASC;
```

---

# 12. Relación con el backend Java

El backend Java debe conectarse a esta base de datos y consultar las vistas anteriores.

Ejemplo de rutas recomendadas:

| Ruta backend | Consulta base recomendada |
|---|---|
| `GET /api/drivers` | `SELECT * FROM app_drivers` |
| `GET /api/drivers/{id}` | `SELECT * FROM app_drivers WHERE id = ?` |
| `GET /api/constructors` | `SELECT * FROM app_constructors` |
| `GET /api/constructors/{id}` | `SELECT * FROM app_constructors WHERE id = ?` |
| `GET /api/races` | `SELECT * FROM app_races` |
| `GET /api/races/{id}/results` | `SELECT * FROM app_results WHERE race_id = ?` |
| `GET /api/stats/summary` | Consultas de totales sobre `driver`, `constructor`, `race`, `season` |

---

# 13. Recomendaciones

1. Para listados del frontend, usar preferentemente las vistas `app_*`.
2. Evitar consultar tablas internas de MariaDB como `mysql.user` con el usuario `f1_app`.
3. No subir contraseñas a GitHub.
4. Si DBeaver no muestra las tablas, hacer clic derecho sobre la conexión y pulsar **Refresh**.
5. Si aparece un error de permisos sobre `mysql.user`, no significa que la base esté mal: solo indica que el usuario de aplicación no tiene permisos administrativos.
6. Para el proyecto Java, usar `f1_app`, no `f1_admin`.

---

# 14. Comprobación rápida en DBeaver

Ejecutar estas consultas:

```sql
SELECT COUNT(*) AS total_drivers FROM driver;
SELECT COUNT(*) AS total_constructors FROM constructor;
SELECT COUNT(*) AS total_races FROM race;
```

Después probar las vistas:

```sql
SELECT * FROM app_drivers LIMIT 10;
SELECT * FROM app_constructors LIMIT 10;
SELECT * FROM app_races LIMIT 10;
SELECT * FROM app_results LIMIT 10;
```

Si estas consultas devuelven datos, la base de datos está lista para ser usada desde el backend Java.
