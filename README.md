# F1 Manager Web

Proyecto Java para gestionar datos de Fórmula 1 con una base de datos relacional.

## Stack previsto

- Java
- HTML/CSS
- Bootstrap
- MySQL/MariaDB en AWS RDS

## Datos

Se usará F1DB como dataset inicial.

El ZIP SQL está en:

```text
database/vendor/f1db/f1db-sql-mysql-single-inserts.zip
```

Vistas auxiliares del proyecto:

```text
database/f1db_project_views.sql
```

## Ramas

- `main`: versión final estable
- `develop`: trabajo integrado
- `feature/*`: funcionalidades

## Configuración de Base de Datos (AWS RDS)

El proyecto utiliza una base de datos alojada en AWS RDS. Para conectarse, es necesario configurar las credenciales localmente.

1. Crea el directorio `src/main/resources` si no existe.
2. Crea un archivo llamado `application-local.properties` dentro de ese directorio.
3. Añade el siguiente contenido, reemplazando la contraseña por la proporcionada por el equipo:

```properties
db.url=jdbc:mysql://f1-manager-db.czam0qussyez.us-east-1.rds.amazonaws.com:3306/f1_manager
db.user=f1_app
db.password=TU_PASSWORD_AQUI
```

> **Nota:** Este archivo está ignorado en `.gitignore` para no subir contraseñas a GitHub.
Alternativamente, se pueden utilizar las variables de entorno `F1_DB_URL`, `F1_DB_USER` y `F1_DB_PASSWORD`.

## Ejecución

Pendiente de completar cuando esté finalizado el setup del servidor web.
