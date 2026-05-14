# Esquema de infraestructura — F1 Manager Web

## Arquitectura objetivo

```text
┌─────────────────────────────────────────────────────────────┐
│                         GitHub                              │
│  Organización: f1-manager-dam-daw                           │
│  Repositorio: f1-manager-web                                │
│  Ramas: main / develop / feature/*                          │
│  Pull Requests + Issues + Tags + Release 1.0                │
└─────────────────────────────────────────────────────────────┘
                              │
                              │ git clone / pull / push
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                  Equipo de desarrollo                       │
│  IntelliJ / VS Code / Terminal                              │
│  Java Web App                                               │
│  HTML + CSS + Bootstrap + JavaScript                        │
└─────────────────────────────────────────────────────────────┘
                              │
                              │ JDBC
                              │ jdbc:mysql://<rds-endpoint>:3306/f1_manager
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                           AWS                               │
│                                                             │
│  ┌───────────────────────────────────────────────────────┐  │
│  │                    Amazon RDS                         │  │
│  │  Motor: MariaDB/MySQL                                 │  │
│  │  Base de datos: f1_manager                            │  │
│  │  Usuario app: f1_app                                  │  │
│  │  Tablas: drivers, constructors, races, results        │  │
│  └───────────────────────────────────────────────────────┘  │
│                                                             │
│  ┌───────────────────────────────────────────────────────┐  │
│  │                  Security Group                       │  │
│  │  Permite puerto 3306 solo desde IPs autorizadas       │  │
│  │  No abrir la base de datos a todo Internet            │  │
│  └───────────────────────────────────────────────────────┘  │
└─────────────────────────────────────────────────────────────┘
```

## Versión inicial para la práctica

En la primera fase, la aplicación Java puede ejecutarse en los ordenadores del equipo y conectarse a la base de datos en AWS RDS.

```text
Navegador local
     │
     ▼
Java Web App local
     │
     │ JDBC
     ▼
AWS RDS MariaDB/MySQL
```

Esta opción permite empezar rápido y ya demuestra uso de base de datos cloud.

## Versión ampliada si da tiempo

Si se quiere cubrir mejor el punto de despliegue, se puede desplegar también la aplicación Java en AWS.

```text
Usuario / Navegador
        │
        ▼
┌──────────────────────┐
│ AWS EC2              │
│ Java Web App         │
│ Tomcat/Spring Boot   │
└──────────────────────┘
        │
        │ JDBC interno AWS
        ▼
┌──────────────────────┐
│ AWS RDS              │
│ MariaDB/MySQL        │
│ f1_manager           │
└──────────────────────┘
```

## Componentes principales

### 1. GitHub

Uso:

- Organización del equipo.
- Repositorio central.
- Issues para repartir tareas.
- Ramas `feature/*` para cada funcionalidad.
- Pull Requests revisadas por otro compañero.
- Tags intermedios.
- Release final `1.0`.

### 2. Aplicación Java

Responsabilidades:

- Mostrar listados y detalles.
- Formularios de alta, modificación y borrado.
- Validar datos.
- Conectarse a la base de datos.
- Ejecutar consultas SQL.
- Mostrar relaciones entre pilotos, escuderías, carreras y resultados.

### 3. AWS RDS

Responsabilidades:

- Alojar la base de datos relacional.
- Mantener tablas y relaciones.
- Guardar datos importados del dataset de Fórmula 1.

Motor recomendado:

```text
MariaDB/MySQL
```

Base de datos:

```text
f1_manager
```

Usuario de aplicación:

```text
f1_app
```

### 4. Security Group de AWS

Reglas recomendadas:

```text
Entrada:
- TCP 3306 desde IPs concretas del equipo/profesores si hace falta.

Salida:
- Permitida por defecto.
```

Importante:

```text
No abrir 3306 a 0.0.0.0/0 salvo prueba temporal muy controlada.
```

## Flujo de datos

```text
1. Usuario entra en la web.
2. La app Java recibe la petición.
3. La app Java consulta AWS RDS mediante JDBC.
4. AWS RDS devuelve datos de F1.
5. La app Java renderiza HTML.
6. El navegador muestra la respuesta al usuario.
```

## Tablas previstas

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

## Configuración sensible

No se debe subir al repositorio:

```text
DB_HOST
DB_USER
DB_PASSWORD
DB_NAME
```

Se usará un archivo local ignorado por Git o variables de entorno.

Ejemplo:

```text
DB_HOST=<aws-rds-endpoint>
DB_PORT=3306
DB_NAME=f1_manager
DB_USER=f1_app
DB_PASSWORD=<secret>
```

## Resumen para explicar en la presentación

Nuestra infraestructura separa el código de la base de datos. El código se gestiona en GitHub mediante Git Flow y Pull Requests. La aplicación Java se conecta a una base de datos MariaDB/MySQL alojada en AWS RDS. Esto permite trabajar con una arquitectura más realista, segura y cercana a un entorno profesional, evitando depender de una base de datos local durante la demo.
