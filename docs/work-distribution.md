# Distribución del trabajo — Guillermo y Héctor

Proyecto: **F1 Manager Web**

Repositorio: https://github.com/f1-manager-dam-daw/f1-manager-web

## Objetivo de la distribución

Repartir el trabajo de forma que ambos participen en partes técnicas importantes y que se cumpla Git Flow: cada funcionalidad se trabaja en una rama, se abre Pull Request y la revisa el otro compañero antes de fusionar.

## Regla general de trabajo

```text
Nadie fusiona su propia Pull Request sin revisión del otro.
```

Flujo recomendado:

```text
Issue → feature branch → commits → Pull Request → revisión del compañero → merge a develop
```

## Reparto principal

### Guillermo — Base de datos, AWS e integración backend

Responsabilidades principales:

- Modelo Entidad/Relación.
- Modelo relacional.
- Script SQL y vistas del proyecto.
- AWS RDS.
- Security Group.
- Conexión JDBC.
- Configuración por variables de entorno.
- Importación del dataset F1DB.
- Documentación técnica de base de datos/AWS.

Issues recomendadas para Guillermo:

```text
#1  Define database model
#3  Create SQL schema
#5  Import initial F1 dataset
#9  Connect Java app to database
#30 Configure AWS RDS database
#31 Create AWS security group for database access
#32 Document AWS RDS setup in README
#33 Add environment-based database configuration
#34 Prepare AWS architecture diagram
```

Ramas sugeridas:

```text
feature/database-model
feature/sql-schema
feature/import-f1db-dataset
feature/aws-rds-setup
feature/jdbc-connection
feature/env-db-config
feature/aws-docs
```

Pull Requests de Guillermo revisadas por:

```text
Héctor
```

---

### Héctor — Frontend, vistas, Bootstrap y experiencia de usuario

Responsabilidades principales:

- Layout HTML.
- Bootstrap.
- Responsive design.
- Listados.
- Vistas detalle.
- Formularios.
- JavaScript/fetch.
- Búsquedas y filtros.
- Paginación.
- Presentación visual de la aplicación.

Issues recomendadas para Héctor:

```text
#11 Create drivers listing and detail
#13 Create constructors listing and detail
#15 Add create/delete forms
#16 Translate UI to English
#18 Add Bootstrap layout
#20 Add search filters
#21 Add pagination
#22 Add JavaScript fetch action
```

Ramas sugeridas:

```text
feature/bootstrap-layout
feature/drivers-views
feature/constructors-views
feature/create-delete-forms
feature/search-filters
feature/pagination
feature/fetch-delete
feature/responsive-design
```

Pull Requests de Héctor revisadas por:

```text
Guillermo
```

## Tareas compartidas

Estas tareas deberían hacerse entre los dos:

```text
#7  Setup Java project
#24 Write README startup instructions
#26 Prepare final presentation and demo video
#28 Create final release 1.0
```

### Setup Java project

Responsable inicial: Guillermo  
Revisor: Héctor

Motivo: afecta a conexión con BD y estructura base.

### README

Responsable inicial: Guillermo  
Revisor: Héctor

Héctor debe comprobar que las instrucciones se entienden desde cero.

### Presentación y vídeo demo

Responsabilidad compartida.

Propuesta:

- Guillermo explica arquitectura, AWS, BD y Git Flow.
- Héctor explica interfaz, funcionalidades, Bootstrap y demo de uso.

### Release final

Responsabilidad compartida.

Checklist antes de release:

```text
[ ] Todas las PR importantes fusionadas a develop
[ ] develop probado
[ ] PR final develop → main creada
[ ] PR final revisada por ambos
[ ] Tag v1.0-final-release creado
[ ] Release 1.0 publicada
[ ] README actualizado
[ ] Presentación y demo listas
```

## Orden recomendado de ejecución

### Día 1 — Base y organización

Guillermo:

1. Terminar modelo ER.
2. Preparar AWS RDS.
3. Importar F1DB.
4. Crear vistas `app_drivers`, `app_constructors`, `app_races`, `app_results`.

Héctor:

1. Revisar plan y GitHub.
2. Preparar propuesta de diseño Bootstrap.
3. Crear wireframe sencillo de pantallas.
4. Revisar PRs de base de datos.

Ambos:

1. Confirmar stack Java.
2. Crear proyecto base.
3. Acordar naming de paquetes, rutas y vistas.

### Día 2 — Funcionalidades obligatorias

Guillermo:

1. Implementar conexión JDBC.
2. Crear DAO/repositorios.
3. Crear servicios/controladores base.

Héctor:

1. Implementar listados.
2. Implementar detalles.
3. Implementar formularios.
4. Aplicar Bootstrap.

Ambos:

1. Revisar PRs cruzadas.
2. Probar CRUD mínimo.

### Día 3 — Extras y pulido

Guillermo:

1. Queries de estadísticas/dashboard.
2. Documentar AWS.
3. Revisar seguridad/configuración.

Héctor:

1. Búsqueda/filtros.
2. Paginación.
3. JavaScript/fetch.
4. Responsive.

Ambos:

1. README final.
2. Presentación.
3. Vídeo demo.
4. Release 1.0.

## Matriz rápida de revisión

| Área | Responsable | Revisor |
|---|---|---|
| Base de datos | Guillermo | Héctor |
| AWS RDS | Guillermo | Héctor |
| Backend conexión BD | Guillermo | Héctor |
| Layout Bootstrap | Héctor | Guillermo |
| Vistas/listados | Héctor | Guillermo |
| Formularios | Héctor | Guillermo |
| Búsqueda/paginación/fetch | Héctor | Guillermo |
| README | Guillermo | Héctor |
| Presentación | Ambos | Ambos |
| Release final | Ambos | Ambos |

## Reglas de Pull Request

Cada Pull Request debe incluir:

```text
- Qué issue cierra
- Qué se ha cambiado
- Cómo se ha probado
- Captura si cambia interfaz
- Scripts SQL si cambia base de datos
```

Checklist mínimo:

```text
[ ] Compila
[ ] Funciona manualmente
[ ] No rompe funcionalidades anteriores
[ ] Textos en inglés
[ ] Código claro
[ ] Documentación actualizada si aplica
[ ] Revisado por el otro compañero
```
