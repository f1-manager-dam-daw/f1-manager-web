# Plan de Verificación — Defensa Proyecto AA

Este documento sirve como guía para la presentación del proyecto y asegura que todos los puntos de la rúbrica han sido verificados.

## 1. Requisitos Obligatorios (100% Completados)

| Requisito | Estado | Evidencia / Cómo verificar |
|---|---|---|
| **Base de Datos** | ✅ OK | Ver `docs/BaseDeDatos.md` con el script SQL y el modelo ER. |
| **Listado y Detalle** | ✅ OK | Tablas de Pilotos, Escuderías y Carreras con enlaces a su detalle. |
| **Registro y Borrado** | ✅ OK | Formularios en Pilotos (y próximamente Escuderías). |
| **Inglés** | ✅ OK | Toda la interfaz y el código (Servlets, DAOs) están en inglés. |
| **Git Flow** | ✅ OK | Uso de ramas (ej. `integration`), tags y repositorio en GitHub. |

## 2. Otras Funcionalidades (Puntos Extra)

| Funcionalidad | Estado | Evidencia |
|---|---|---|
| **Modificar** | ⏳ Proc. | Se habilitará el botón "Edit" en la vista detalle. |
| **Búsqueda** | ✅ OK | Buscador en tiempo real en los listados de Pilotos y Escuderías. |
| **Relaciones** | ✅ OK | Ver detalle de una carrera muestra los resultados asociados. |
| **Javascript (Fetch)** | ✅ OK | Todas las acciones (Cargar, Añadir, Borrar) usan Fetch API. |
| **Paginación** | ✅ OK | Implementada en todos los listados largos (20 por página). |
| **Bootstrap** | ✅ OK | Maquetación completa con Bootstrap 5. |
| **Responsive** | ✅ OK | Adaptable a móviles (menú hamburguesa y tablas scroll). |
| **Despliegue** | ✅ OK | Configuración de Docker lista para despliegue en AWS. |

## 3. Pruebas Automáticas
- **Unitarias:** `mvn test` ejecuta las pruebas de los DAOs.
- **Conectividad:** `TestConnection.java` verifica el acceso a AWS RDS.
