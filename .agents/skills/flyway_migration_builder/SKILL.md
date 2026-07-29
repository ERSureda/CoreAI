---
name: flyway_migration_builder
description: "ACTIVAR CUANDO: Se solicite compilar, empaquetar o construir scripts de migración de Flyway a partir de esquemas SQL modulares en db/schema/ (ej. 'construye la migración de taxai', 'genera la versión V1 para Flyway'). HACE: Lee ficheros en schema/<domain>/, resuelve la jerarquía de dependencias y genera un único archivo V<X>__create_<domain>.sql en db/migration/."
version: 2.0.0
---

# SKILL: FLYWAY_MIGRATION_BUILDER

## 1. ACTIVACIÓN Y ALCANCE
- **Scope / Directorios**:
    - **Origen (Read-Only)**: `src/main/resources/db/schema/<domain>/<module>/*.sql`
    - **Destino (Write)**: `src/main/resources/db/migration/`
- **Inputs válidos**: Peticiones para compilar un dominio entero (ej. `taxai`) o un conjunto específico de módulos dentro de un dominio.
- **Output Contract**: Crear un único script ejecutable en UTF-8 en la carpeta de migración respetando la convención de Flyway `V<X>__create_<domain>.sql`. Cero modificaciones en los archivos fuente.

## 2. REGLAS INVARIABLES (STRICT CONSTRAINTS)
> ⚠️ **Atención IA**: Las siguientes reglas son de estricto cumplimiento y tienen máxima prioridad.

- **[MUST-01]**: El archivo compilado DEBE guardarse en `src/main/resources/db/migration/` nombrado obligatoriamente como `V<X>__create_<domain>.sql`, donde `<X>` es el número consecutivo de la versión Flyway disponible.
- **[MUST-02]**: La concatenación de módulos DEBE respetar el orden del grafo de dependencias:
    1. Esquemas base y módulos compartidos (`common`, utilidades).
    2. Tipos personalizados (`CREATE TYPE ... AS ENUM`) y funciones globales.
    3. Tablas e índices con claves foráneas cruzadas.
    4. Triggers complejos, bloques `DO` y políticas RLS.
- **[MUST-03]**: Cada bloque de módulo unificado DEBE separarse con un salto de línea limpio (`\n`) manteniendo los comentarios de cabecera de módulo (`-- Module: <module_name>`).
- **[MUST-04]**: El archivo resultante DEBE ser generado con codificación **UTF-8** estricta.
- **[NEVER-01]**: NUNCA modificar, mover, editar ni eliminar archivos dentro del directorio `schema/`. Toda lectura del origen es **estrictamente Read-Only**.
- **[NEVER-02]**: NUNCA usar comandos ciegos de terminal (como `cat * > file.sql`) que vulneren el orden de dependencias o alteren codificaciones.
- **[NEVER-03]**: NUNCA alterar la sintaxis interna ni inyectar saltos de línea arbitrarios dentro de los bloques SQL que se extraen de la fuente.

## 3. ALGORITMO DE EJECUCIÓN
Ejecuta la tarea en esta secuencia estricta:

1. **Parse & Dependency Mapping**:
    - Inspeccionar el directorio `src/main/resources/db/schema/<domain>/` buscando todos los submódulos.
    - Analizar dependencias entre archivos (`CREATE TYPE`, `REFERENCES`, llamadas a funciones) para determinar el orden de concatenación.
2. **Flyway Version Resolution**:
    - Escanear `src/main/resources/db/migration/` para encontrar la última versión registrada (ej. si existe `V2__...`, la nueva será `V3`).
    - Si no existen migraciones anteriores, iniciar en `V1`.
3. **Emit & File Generation**:
    - Crear el directorio de destino si no existe (`mkdir -p src/main/resources/db/migration/`).
    - Unificar los módulos en la secuencia resuelta en la Fase 1 aplicando las reglas de formato `[MUST-03]`.
    - Escribir el resultado en `src/main/resources/db/migration/V<X>__create_<domain>.sql`.

## 4. EDGE CASES & FALLBACKS
- **Si hay dependencias circulares entre módulos**: Detener el proceso inmediatamente e informar al usuario identificando las tablas o tipos que causan el ciclo.
- **Si se pide compilar un submódulo aislado**: Asegurar que las dependencias globales (`common`) se incluyan al inicio del script resultante para evitar errores de ejecución en Flyway.
- **Si el archivo de migración destino ya existe**: Incrementar el número de versión `<X>` para nunca sobrescribir una migración ya aplicada por Flyway.

## 5. CHECKLIST DE AUTO-VERIFICACIÓN (Pre-Flight Checks)
Verifica mentalmente cada punto antes de finalizar:

- [ ] ¿Los archivos en `src/main/resources/db/schema/` están intactos (`[NEVER-01]`)?
- [ ] ¿Ninguna tabla o clave foránea se intenta crear antes que el tipo o la tabla a la que referencia (`[MUST-02]`)?
- [ ] ¿La versión `V<X>` en el nombre del archivo es consecutiva a la última existente en `db/migration/` (`[MUST-01]`)?
- [ ] ¿Se conservan las cabeceras de los módulos y el formato UTF-8 (`[MUST-03]`, `[MUST-04]`)?

## 6. FEW-SHOT EXAMPLES

### ❌ ORDEN DE CONCATENACIÓN INCORRECTO
```sql
-- Fichero compilado con errores de orden
-- Module: booking
CREATE TABLE booking.rides (
    id UUID PRIMARY KEY,
    status common.ride_status NOT NULL -- ❌ ERROR: common.ride_status aún no se ha creado
);

-- Module: common
CREATE SCHEMA IF NOT EXISTS common;
CREATE TYPE common.ride_status AS ENUM ('REQUESTED', 'COMPLETED');
```

### ✅ ORDEN DE CONCATENACIÓN CORRECTO
```sql
-- Module: common (Base)
CREATE SCHEMA IF NOT EXISTS common;

CREATE TYPE common.ride_status AS ENUM ('REQUESTED', 'COMPLETED');

-- Module: booking (Dependiente)
CREATE SCHEMA IF NOT EXISTS booking;

CREATE TABLE booking.rides (
    id UUID PRIMARY KEY,
    status common.ride_status NOT NULL
);
```