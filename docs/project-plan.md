# Plan de proyecto — F1 Manager Web

Proyecto para las Prácticas Presenciales de Mayo — 1º DAM/DAW.

## 0. Idea general

**Nombre provisional:** F1 Manager Web

Aplicación web en Java para gestionar información de Fórmula 1 usando una base de datos relacional alojada en AWS, preferiblemente mediante Amazon RDS.

La aplicación permitirá consultar, registrar, modificar, eliminar y relacionar datos de pilotos, escuderías, carreras y resultados.

## 1. Organización de GitHub

### 1.1 Objetivo

Crear una organización de GitHub para centralizar el trabajo del equipo y cumplir correctamente el requisito de Git Flow, ramas, Pull Requests, revisión entre compañeros, tags y release final.

### 1.2 Organización propuesta

**Nombre posible de la organización:**

- `f1-manager-dam-daw`
- `f1-java-project-2026`
- `dam-daw-f1-manager`

**Repositorio principal:**

```text
f1-manager-web
```

### 1.3 Repositorio

El repositorio contendrá todo el material entregable:

```text
f1-manager-web/
├── backend/                 # Proyecto Java
├── database/                # Scripts SQL y datos iniciales
├── docs/                    # Documentación técnica y funcional
├── presentation/            # Presentación final
├── demo/                    # Vídeos o capturas de demo
├── README.md
└── .gitignore
```

Si el proyecto Java se crea directamente en la raíz, se puede simplificar:

```text
f1-manager-web/
├── src/
├── database/
├── docs/
├── presentation/
├── demo/
├── pom.xml
├── README.md
└── .gitignore
```

### 1.4 Ramas principales

```text
main       → versión estable / entregable
develop    → integración de trabajo diario
feature/*  → nuevas funcionalidades
fix/*      → correcciones
release/*  → preparación de entrega
```

### 1.5 Flujo de trabajo obligatorio

1. Cada tarea se crea como Issue en GitHub.
2. Cada persona trabaja en una rama propia.
3. Cada rama se fusiona mediante Pull Request hacia `develop`.
4. Otro compañero revisa la Pull Request antes de fusionar.
5. La versión final se fusiona a `main`.
6. Se crea tag `1.0` y una Release final en GitHub.

### 1.6 Ejemplo de ramas

```text
feature/database-schema
feature/import-f1-data
feature/drivers-list
feature/constructors-crud
feature/results-search
feature/bootstrap-layout
feature/responsive-design
feature/javascript-fetch-delete
fix/readme-startup-instructions
release/1.0
```

### 1.7 Tags intermedios requeridos

El enunciado pide al menos 4 tags intermedios. Propuesta:

```text
v0.1-database-schema
v0.2-basic-listings
v0.3-crud-forms
v0.4-relations-search
v1.0-final-release
```

## 2. Distribución del trabajo

La distribución debe permitir que cada integrante tenga tareas claras y que las Pull Requests puedan revisarse entre compañeros.

### Rol A — Base de datos e importación

Responsabilidades:

- Diseñar modelo Entidad/Relación.
- Crear modelo relacional.
- Crear scripts SQL.
- Preparar datos iniciales del dataset de Fórmula 1.
- Documentar cómo importar la base de datos.

Ramas posibles:

```text
feature/database-schema
feature/import-f1-data
```

Entregables:

```text
database/schema.sql
database/seed.sql
database/README.md
docs/entity-relationship-model.md
```

### Rol B — Backend Java / lógica de datos

Responsabilidades:

- Configurar proyecto Java.
- Crear entidades/modelos.
- Crear repositorios/DAOs/servicios.
- Conectar con la base de datos remota alojada en AWS RDS.
- Implementar operaciones CRUD.

Ramas posibles:

```text
feature/java-project-setup
feature/driver-service
feature/constructor-service
feature/result-service
```

### Rol C — Frontend HTML/CSS/Bootstrap

Responsabilidades:

- Crear layout común.
- Crear listados.
- Crear vistas detalle.
- Crear formularios.
- Aplicar Bootstrap.
- Hacer diseño responsive.

Ramas posibles:

```text
feature/bootstrap-layout
feature/drivers-views
feature/constructors-views
feature/responsive-design
```

### Rol D — Funcionalidades extra, GitHub y presentación

Responsabilidades:

- Búsqueda/filtros.
- Paginación.
- Funcionalidad con JavaScript/fetch.
- README.
- Presentación final.
- Vídeo de demo de emergencia.
- Revisar Pull Requests.

Ramas posibles:

```text
feature/search-filters
feature/pagination
feature/javascript-fetch-delete
feature/project-documentation
feature/final-presentation
```

### Revisiones de Pull Requests

Regla recomendada:

- Nadie fusiona su propia Pull Request sin revisión.
- Cada PR debe tener al menos 1 aprobación de otro compañero.
- La revisión comprueba:
  - que compila
  - que no rompe funcionalidades previas
  - que el código es entendible
  - que los textos están en inglés
  - que la rama cumple el objetivo de la Issue

## 3. Requisitos obligatorios del enunciado

### 3.1 Base de datos — obligatorio

Se debe entregar:

- Modelo Entidad/Relación.
- Modelo relacional.
- Script SQL de creación de tablas.
- Datos iniciales importados o insertados.

Entidades propuestas:

```text
drivers
constructors
races
results
```

Relaciones:

```text
drivers 1 --- N results
constructors 1 --- N results
races 1 --- N results
```

Esto permite tener más de 2 entidades y relaciones reales.

### 3.2 Listado y detalle — obligatorio

Listados mínimos:

- Listado de pilotos.
- Listado de escuderías.

Detalles mínimos:

- Detalle de piloto.
- Detalle de escudería.

Detalles recomendados:

- Ver resultados asociados a un piloto.
- Ver resultados asociados a una escudería.

### 3.3 Registro y borrado — obligatorio

Formularios mínimos:

- Registrar piloto.
- Eliminar piloto.
- Registrar escudería.
- Eliminar escudería.

Idealmente también:

- Registrar carrera.
- Registrar resultado.

### 3.4 Inglés — obligatorio

Toda la aplicación debe mostrarse en inglés.

Ejemplos:

```text
Drivers
Constructors
Races
Results
Create driver
Delete
Edit
Search
Details
Nationality
Date of birth
Team
```

### 3.5 Git Flow — obligatorio

Se cumplirá mediante:

- Organización de GitHub.
- Repositorio común.
- Rama `main`.
- Rama `develop`.
- Ramas `feature/*`.
- Pull Requests revisadas por compañeros.
- README completo.
- Al menos 4 tags intermedios.
- Release final `1.0`.

## 4. Funcionalidades extra para subir nota

Orden recomendado de prioridad:

### 4.1 Modificar datos

Permitir modificar:

- Pilotos.
- Escuderías.
- Carreras/resultados si da tiempo.

### 4.2 Búsqueda y filtrado

Filtros recomendados:

- Buscar piloto por nombre.
- Filtrar pilotos por nacionalidad.
- Buscar escudería por nombre.
- Filtrar resultados por carrera, piloto o escudería.

### 4.3 Relaciones entre tablas

Funcionalidad clara:

- En el detalle de un piloto, mostrar sus resultados.
- En el detalle de una escudería, mostrar sus resultados.
- En el detalle de una carrera, mostrar clasificación/resultados.

### 4.4 JavaScript/fetch

Acción asíncrona recomendada:

- Borrar un piloto con `fetch` sin recargar toda la página.
- O buscar pilotos con `fetch`.

### 4.5 Paginación

Aplicar paginación en:

- Listado de pilotos.
- Listado de resultados.

### 4.6 Bootstrap

Usar Bootstrap para:

- Navbar.
- Cards.
- Tablas.
- Formularios.
- Botones.
- Alertas.

### 4.7 Responsive design

Comprobar en:

- Desktop.
- Tablet.
- Móvil.

### 4.8 Despliegue

El enunciado menciona AWS como extra. Si no da tiempo, priorizar funcionalidad y Git Flow.

Alternativa técnica para demo:

- Amazon RDS como base de datos principal.
- Aplicación ejecutada localmente o desplegada en AWS para presentación.

Si se quiere aspirar al punto de despliegue:

- Desplegar backend en AWS EC2 o Elastic Beanstalk.
- Usar una base de datos en AWS RDS.

## 5. Arquitectura general del proyecto

### 5.1 Arquitectura recomendada

```text
Browser
  |
  v
Java Web App
  |
  v
AWS RDS Database
  |
  v
MariaDB/MySQL or PostgreSQL
```

### 5.2 Servidor / base de datos

Decisión actualizada: la base de datos principal del proyecto se alojará en **AWS**, preferiblemente con **Amazon RDS**.

Opción recomendada para el proyecto:

```text
Amazon RDS for MariaDB/MySQL
```

Motivos:

- Encaja mejor con el extra de despliegue/cloud del enunciado.
- Evita depender de la red doméstica o Tailscale durante la presentación.
- Permite documentar una arquitectura más profesional.
- Facilita capturas y explicación de infraestructura en AWS.

Tailscale queda como alternativa de apoyo para desarrollo local o contingencia, pero no como arquitectura principal.

### 5.3 Base de datos recomendada

Para simplicidad en 1º DAM/DAW:

```text
MariaDB/MySQL
```

URL JDBC prevista:

```text
jdbc:mysql://<aws-rds-endpoint>:3306/f1_manager
```

El endpoint real se documentará cuando se cree la instancia RDS.

Usuario recomendado:

```text
f1_app
```

No usar usuario root/admin en la aplicación.

### 5.4 Stack Java recomendado

Opción preferida:

```text
Java + Spring Boot + Thymeleaf + JDBC/JPA + Bootstrap
```

Opción alternativa si en clase no permiten Spring Boot:

```text
Java Servlets/JSP + JDBC + Bootstrap
```

Pendiente de confirmar con el profesor/equipo.

## 6. Modelo de datos inicial

### 6.1 Tabla `drivers`

```text
id
forename
surname
code
number
nationality
date_of_birth
url
```

### 6.2 Tabla `constructors`

```text
id
name
nationality
url
```

### 6.3 Tabla `races`

```text
id
year
round
name
date
circuit_name
country
```

### 6.4 Tabla `results`

```text
id
race_id
driver_id
constructor_id
grid
position
points
laps
status
```

### 6.5 Relaciones

```text
results.driver_id      → drivers.id
results.constructor_id → constructors.id
results.race_id        → races.id
```

## 7. Dataset de Fórmula 1

Dataset recomendado:

```text
Formula 1 World Championship 1950-2020
https://www.kaggle.com/datasets/rohanrao/formula-1-world-championship-1950-2020
```

Archivos útiles:

```text
drivers.csv
constructors.csv
races.csv
results.csv
status.csv
circuits.csv
```

Para no complicar demasiado, empezar solo con:

```text
drivers.csv
constructors.csv
races.csv
results.csv
```

## 8. Plan paso a paso de ejecución

### Fase 1 — Preparación GitHub

1. Crear organización de GitHub.
2. Crear repositorio `f1-manager-web`.
3. Crear `README.md` inicial.
4. Crear ramas `main` y `develop`.
5. Crear Issues iniciales.
6. Definir reglas de Pull Request.

Issues iniciales sugeridas:

```text
#1 Define database model
#2 Create SQL schema
#3 Import initial F1 dataset
#4 Setup Java project
#5 Connect Java app to database
#6 Create drivers listing and detail
#7 Create constructors listing and detail
#8 Add create/delete forms
#9 Translate UI to English
#10 Add Bootstrap layout
#11 Add search filters
#12 Add pagination
#13 Add JavaScript fetch action
#14 Write README startup instructions
#15 Prepare final presentation and demo video
#16 Create final release 1.0
```

### Fase 2 — Base de datos

1. Descargar dataset.
2. Elegir tablas definitivas.
3. Crear modelo ER.
4. Crear script `schema.sql`.
5. Crear script `seed.sql` o importador CSV.
6. Crear base de datos `f1_manager` en AWS RDS.
7. Crear usuario específico `f1_app`.
8. Probar conexión desde Fedora contra el endpoint de AWS RDS.

### Fase 3 — Proyecto Java

1. Crear proyecto Java.
2. Configurar dependencias.
3. Configurar conexión JDBC.
4. Crear modelos/entidades.
5. Crear capa de acceso a datos.
6. Crear controladores/rutas.
7. Crear templates HTML.

### Fase 4 — Funcionalidades obligatorias

1. Listado de pilotos.
2. Detalle de piloto.
3. Alta de piloto.
4. Borrado de piloto.
5. Listado de escuderías.
6. Detalle de escudería.
7. Alta de escudería.
8. Borrado de escudería.
9. Toda la interfaz en inglés.
10. README con instrucciones.

### Fase 5 — Funcionalidades extra

1. Modificar piloto/escudería.
2. Búsqueda por nombre/nacionalidad/equipo.
3. Relaciones: resultados por piloto/escudería/carrera.
4. JavaScript/fetch para borrado o búsqueda.
5. Paginación.
6. Bootstrap.
7. Responsive.

### Fase 6 — Revisión y entrega

1. Revisar que todas las PR estén fusionadas.
2. Revisar que `develop` funciona.
3. Fusionar `develop` en `main` mediante PR.
4. Crear tag `v1.0-final-release`.
5. Crear Release `1.0` en GitHub.
6. Preparar presentación.
7. Grabar vídeo de demo por si falla la demo en directo.
8. Comprobar que la rama por defecto sea la correcta.
9. Entregar URL del repositorio.

## 9. Criterios de calidad para cada Pull Request

Antes de pedir revisión:

- El proyecto compila.
- No hay errores visibles en consola.
- La funcionalidad se ha probado manualmente.
- Los textos están en inglés.
- El código tiene nombres claros.
- La PR explica qué cambia.
- Si cambia BD, incluye script SQL actualizado.
- Si cambia UI, incluye captura o breve explicación.

Checklist de revisión:

```text
[ ] Compila
[ ] Funciona manualmente
[ ] No rompe vistas anteriores
[ ] Textos en inglés
[ ] Código entendible
[ ] README/docs actualizados si aplica
[ ] Aprobado por otro compañero
```

## 10. README mínimo obligatorio

El `README.md` debe incluir:

```text
# F1 Manager Web

## Description
Brief project description in English.

## Technologies
Java, HTML, CSS, Bootstrap, MySQL/MariaDB or PostgreSQL.

## Features
List of implemented features.

## Database setup
How to create and populate the database.

## Backend setup
How to configure and run the Java app.

## Git Flow
Explanation of branches, PRs, tags and release.

## Team members
Names and responsibilities.

## Final release
Link to release 1.0.
```

## 11. Decisiones pendientes

Antes de empezar a ejecutar, confirmar:

1. Nombre final de la organización de GitHub.
2. Nombre final del repositorio.
3. Integrantes del equipo y usuarios de GitHub.
4. Stack Java permitido por el profesor: Spring Boot o Servlets/JSP.
5. Base de datos final: MariaDB/MySQL o PostgreSQL.
6. Si se desplegará solo la base de datos en AWS RDS o también la aplicación Java en AWS.
7. Cuántas funcionalidades extra se intentarán implementar.

## 12. Recomendación de alcance

Para maximizar nota sin dispersarse:

### Imprescindible

- BD relacional bien diseñada.
- Drivers + Constructors + Results + Races.
- CRUD de Drivers y Constructors.
- Listado y detalle.
- Inglés.
- Git Flow real.
- README y Release.

### Extras prioritarios

1. Bootstrap.
2. Responsive.
3. Modificar datos.
4. Búsqueda/filtro.
5. Relaciones entre tablas.
6. Paginación.
7. Fetch para borrar.

### Extras opcionales

- Dashboard de estadísticas.
- Importador CSV desde la aplicación.
- Despliegue AWS.

## 13. Primeras acciones concretas

1. Confirmar equipo y usuarios de GitHub.
2. Crear organización GitHub.
3. Crear repositorio.
4. Crear estructura inicial.
5. Crear Issues.
6. Crear rama `develop`.
7. Empezar con PR `feature/database-schema`.
8. Crear base de datos `f1_manager` en AWS RDS.
9. Crear script SQL inicial.
10. Crear proyecto Java base.

---

Última actualización: 2026-05-14.

## 14. Ajuste según contexto de asignaturas de 1º

Revisión basada en `/home/guillermo/8-CONTEXT`, especialmente:

- `Programación__808792488756`
- `Bases De Datos__730703833871`
- `Entornos de Desarrollo__730703769257`
- `Prácticas Presenciales Curso 2025-2026__793453898130`

### 14.1 Lo que parece valorar el profesorado

Aunque el enunciado de prácticas presenciales de mayo pide un mínimo relativamente contenido, el contexto de 1º muestra que en trabajos similares se valora especialmente:

- Modelo Entidad/Relación claro.
- Modelo relacional bien derivado.
- Base de datos MariaDB/MySQL con varias tablas.
- Uso de patrón DAO o una separación clara entre acceso a datos y vistas.
- CRUD completo, no solo altas y bajas.
- Validación de formularios.
- Búsqueda con varios criterios.
- Relaciones visibles en la aplicación.
- GitHub con Issues, ramas, Pull Requests, revisiones, tags y release.
- README con instrucciones reales de puesta en marcha.
- Bootstrap/diseño cuidado.
- Demo preparada y vídeo de respaldo.
- Capacidad de explicar el código, especialmente si se usa IA.

### 14.2 Ajuste recomendado para F1 Manager

Para quedar por encima del mínimo, el objetivo real debería ser:

```text
4 tablas principales:
- drivers
- constructors
- races
- results
```

Y, si da tiempo, añadir:

```text
users
roles
```

para login básico con 2 roles:

```text
ADMIN  → puede crear, modificar y borrar
VIEWER → puede consultar y buscar
```

### 14.3 Tipos de datos exigibles por experiencia previa

En la asignatura de Programación se pedía que aparecieran estos tipos:

- String / texto
- int / entero
- float-decimal
- boolean
- LocalDate / fecha

En F1 Manager se cubrirían así:

```text
String: driver name, constructor name, nationality
int: race year, round, grid position, laps
float/decimal: points
boolean: active, legendary, editable, or current_season
LocalDate/date: date_of_birth, race_date
```

### 14.4 Validaciones recomendadas

Cada formulario importante debería validar al menos 2 campos:

#### Driver

- `forename` obligatorio.
- `surname` obligatorio.
- `date_of_birth` debe ser una fecha válida.
- `number` debe ser positivo si se informa.

#### Constructor

- `name` obligatorio.
- `nationality` obligatoria.

#### Race

- `year` entre 1950 y año actual.
- `round` positivo.
- `date` válida.

#### Result

- `points` mayor o igual que 0.
- `grid` y `position` mayor o igual que 0.
- `driver_id`, `constructor_id` y `race_id` obligatorios.

### 14.5 Funcionalidades de alto impacto para la presentación

Priorizar estas porque encajan con lo que se ha pedido en asignaturas previas:

1. Dashboard con estadísticas.
2. Búsqueda de pilotos por nombre + nacionalidad.
3. Filtro de resultados por carrera + escudería.
4. Vista detalle de piloto con resultados relacionados.
5. Vista detalle de escudería con resultados relacionados.
6. Confirmación JavaScript antes de borrar.
7. Paginación en listados grandes.
8. Bootstrap responsive.

### 14.6 Consultas SQL interesantes

Para demostrar nivel de Bases de Datos:

```sql
-- COUNT: total de pilotos por nacionalidad
SELECT nationality, COUNT(*) AS total
FROM drivers
GROUP BY nationality;

-- SUM: puntos totales por piloto
SELECT d.forename, d.surname, SUM(r.points) AS total_points
FROM drivers d
JOIN results r ON r.driver_id = d.id
GROUP BY d.id, d.forename, d.surname;

-- INNER JOIN: resultados con piloto, escudería y carrera
SELECT d.surname, c.name AS constructor, ra.name AS race, r.position, r.points
FROM results r
JOIN drivers d ON d.id = r.driver_id
JOIN constructors c ON c.id = r.constructor_id
JOIN races ra ON ra.id = r.race_id;

-- LEFT JOIN: pilotos aunque no tengan resultados importados
SELECT d.forename, d.surname, COUNT(r.id) AS result_count
FROM drivers d
LEFT JOIN results r ON r.driver_id = d.id
GROUP BY d.id, d.forename, d.surname;
```

### 14.7 Documentación adicional recomendada

Además del README, crear:

```text
docs/technical-reflection.md
docs/ai-prompts-annex.md
docs/entity-relationship-model.md
docs/database-design.md
docs/git-flow-evidence.md
```

Esto cubre la expectativa vista en prácticas previas sobre reflexión tecnológica, decisiones de diseño y trazabilidad del trabajo.

### 14.8 Conclusión de alcance

El mínimo oficial se puede cumplir con 2 entidades, pero el contexto de 1º indica que conviene presentar algo más robusto. Recomendación definitiva:

- **Base mínima:** drivers + constructors + races + results.
- **CRUD completo:** drivers + constructors.
- **CRUD parcial o consulta:** races + results.
- **Extra fuerte:** dashboard SQL + búsqueda + relaciones + Bootstrap + PRs reales.
- **Si da tiempo:** login básico con roles.


## 15. Decisión actualizada: base de datos en AWS

El proyecto pasa a usar **AWS como alojamiento principal de la base de datos**, en lugar del servidor doméstico por Tailscale.

### 15.1 Servicio recomendado

```text
Amazon RDS for MariaDB/MySQL
```

MariaDB/MySQL encaja mejor con el contexto de Programación y Bases de Datos de 1º, donde ya aparecen MariaDB, SQL, DDL, DML, joins y modelo relacional.

### 15.2 Arquitectura actualizada

```text
Developer laptop / IntelliJ
        |
        | JDBC over TLS / restricted security group
        v
Amazon RDS MariaDB/MySQL

Optional later:
Browser → Java Web App on AWS EC2/Elastic Beanstalk → Amazon RDS
```

### 15.3 Seguridad mínima

- Crear una base de datos llamada `f1_manager`.
- Crear usuario específico `f1_app`.
- No usar el usuario administrador de RDS en la aplicación.
- Limitar el Security Group para permitir conexión solo desde IPs necesarias durante desarrollo/presentación.
- Guardar credenciales fuera del repositorio, por ejemplo en `.env` o `application-local.properties`, ambos ignorados por Git.
- Documentar variables de entorno necesarias sin publicar contraseñas.

### 15.4 Impacto en el plan

Cambios respecto al plan anterior:

- Tailscale deja de ser requisito para la base de datos.
- AWS RDS pasa a ser la fuente principal de datos.
- El README deberá incluir sección `AWS RDS setup`.
- La presentación podrá explicar arquitectura cloud y justificar la decisión.
- Si da tiempo, se podrá desplegar también la aplicación Java en AWS para acercarnos más al punto extra de despliegue.

### 15.5 Nuevas tareas GitHub recomendadas

Añadir Issues:

```text
Configure AWS RDS database
Create AWS security group for database access
Document AWS RDS setup in README
Add environment-based database configuration
Prepare AWS architecture diagram
```
