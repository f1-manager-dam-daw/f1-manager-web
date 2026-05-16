# F1 Manager Web

Proyecto Java para gestionar datos de Fórmula 1 con una base de datos relacional.

## Stack previsto

- Java
- HTML/CSS
- Bootstrap
- MySQL/MariaDB en AWS RDS

## Datos

Se usará F1DB como dataset inicial, el cual ya se encuentra importado en AWS RDS.

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

## Requisitos del Entorno

- Docker y Docker Compose
- Maven (Opcional, para lanzar pruebas locales)
- Java 17 (Opcional, para lanzar pruebas locales)

## Ejecución (Despliegue)

El proyecto está dockerizado para un despliegue rápido y sencillo.

1. Asegúrate de tener las variables de entorno configuradas (ej. en tu `.bashrc` o inyectadas).
   - `F1_DB_URL`
   - `F1_DB_USER`
   - `F1_DB_PASSWORD`
2. En la raíz del proyecto, ejecuta:
   ```bash
   docker compose up -d --build
   ```
3. La aplicación backend y frontend estará disponible en `http://localhost:8080/`.

## Tests Unitarios y Funcionales

Para verificar las operaciones DAO contra la base de datos:

```bash
mvn test
```

> **Nota:** Las pruebas de Integración y Funcionales están documentadas paso a paso en `docs/tests/FunctionalTests.md` para facilitar la verificación de la rúbrica.
