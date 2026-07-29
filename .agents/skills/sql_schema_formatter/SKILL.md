---
name: sql_schema_formatter
description: "ACTIVAR CUANDO: Se pida crear, refactorizar, formatear o modularizar archivos de esquema SQL (.sql) dentro de db/schema/ (ej. 'formatea este SQL raw', 'divide este DDL por módulos'). HACE: Parsea código SQL monolítico o sucio, crea la estructura de directorios modular y genera los archivos V1__create_<module_name>.sql aplicando reglas estrictas de sintaxis, tabulación, mayúsculas y espaciado vertical."
version: 2.0.0
---

# SKILL: SQL_SCHEMA_FORMATTER

## 1. ACTIVACIÓN Y ALCANCE
- **Scope / Directorios**: `src/main/resources/db/schema/<domain>/<module>/V1__create_<module_name>.sql`
- **Inputs válidos**: Código SQL DDL raw (monolítico o parcial), scripts sin formatear o peticiones de modularización de esquemas PostgreSQL.
- **Output Contract**: Generar de forma autónoma la carpeta del módulo y escribir el archivo `V1__create_<module_name>.sql` en UTF-8, perfectamente formateado y sin requerir confirmaciones intermedias.

## 2. REGLAS INVARIABLES (STRICT CONSTRAINTS)
> ⚠️ **Atención IA**: Las siguientes reglas de arquitectura, sintaxis y espaciado son de cumplimiento obligatorio e incondicional.

### A. Estructura y Cabecera
- **[MUST-01]**: Todo archivo de esquema DEBE comenzar con el bloque de comentario de cabecera descriptivo con el formato exacto:
    ```sql
    -- ========================================================
    --  V1
    --  Module: <module_name> (<Brief Title>)
    --  Goal: <A achieves clear explanation of schema this what>:
    --      - <Key 1 responsibility>
    --      - <Key 2 responsibility>
    -- ========================================================
    ```
* **[MUST-02]**: Inmediatamente después de la cabecera, DEBE incluirse la sentencia `CREATE SCHEMA IF NOT EXISTS <module_name>;`.
* **[MUST-03]**: Todos los tipos personalizados (`CREATE TYPE ... AS ENUM`) DEBEN ubicarse en la parte superior del archivo, justo después de la creación del esquema y extensiones.

### B. Tipos de Datos y ENUMs

* **[MUST-04]**: Todos los nombres de ENUMs y sus valores de texto internos DEBEN escribirse estrictamente en **MAYÚSCULAS** (ej. `CREATE TYPE registry.vehicle_status AS ENUM ('ACTIVE', 'INACTIVE');`).
* **[MUST-05]**: Todos los tipos de datos nativos de PostgreSQL (ej. `UUID`, `TEXT`, `BOOLEAN`, `TIMESTAMPTZ`, `SMALLINT`, `JSONB`, `BIGINT`) DEBEN estar completamente en **MAYÚSCULAS**.
* **[NEVER-01]**: NUNCA usar restricciones `CHECK (columna IN ('A', 'B'))` para simular un enum. DEBE crearse y utilizarse siempre un `CREATE TYPE ... AS ENUM` real de PostgreSQL.

### C. Indentación y Alineación

* **[MUST-06]**: La indentación de las columnas dentro de un bloque `CREATE TABLE` DEBE realizarse utilizando exactamente **un carácter de tabulación (`\t`)**.
* **[NEVER-02]**: NUNCA usar múltiples espacios para alinear verticalmente los tipos de datos o restricciones de las columnas. Debe haber un único espacio simple entre el nombre de la columna y su tipo de dato.

### D. Espaciado Vertical Estricto (Line Breaks)

* **[MUST-07]**: Debe haber exactamente **1 línea en blanco** entre el bloque de inicialización (schema/enums) y el primer `CREATE TABLE`.
* **[MUST-08]**: Dentro del bloque de una tabla:
* Exactamente **1 línea en blanco** entre el final del `CREATE TABLE` y su primer `CREATE INDEX` o `CREATE TRIGGER`.
* Exactamente **0 líneas en blanco** entre múltiples sentencias `CREATE INDEX` asociadas a la misma tabla.
* Exactamente **1 línea en blanco** entre el grupo de índices y la sentencia `CREATE TRIGGER`.


* **[MUST-09]**: Debe haber exactamente **2 líneas en blanco** entre el final de un bloque lógico de tabla (después de su último índice/trigger) y el inicio del siguiente `CREATE TABLE`.

## 3. ALGORITMO DE EJECUCIÓN

Sigue esta secuencia estricta al procesar SQL raw:

1. **Parse & Modularize**:
* Analizar el SQL de entrada e identificar las entidades pertenecientes a cada módulo lógico (ej. `common`, `registry`, `booking`).


2. **Directory Generation**:
* Crear autónomamente la estructura de directorios en el sistema de archivos: `src/main/resources/db/schema/<domain>/<module_name>/`.


3. **Format & Enforce Standards**:
* Generar la cabecera `[MUST-01]` y el `CREATE SCHEMA` `[MUST-02]`.
* Extraer todos los enums al inicio `[MUST-03]`, asegurando mayúsculas `[MUST-04]`.
* Formatear tablas aplicando tabulación `\t` `[MUST-06]`, tipos en mayúsculas `[MUST-05]` y eliminando alineaciones con espacios `[NEVER-02]`.
* Ajustar el espaciado vertical rigurosamente `[MUST-07]`, `[MUST-08]`, `[MUST-09]`.


4. **Emit Files**:
* Escribir los archivos `V1__create_<module_name>.sql` resultantes en codificación UTF-8.



## 4. EDGE CASES & FALLBACKS

* **Si un ENUM es compartido por varios módulos**: Colocar la definición del ENUM en el módulo `common` y referenciarlo como `common.<enum_name>` en los demás módulos.
* **Si el SQL raw contiene sentencias DML (`INSERT`, `UPDATE`)**: Extraerlas del esquema DDL y colocarlas en un archivo separado de semillas/datos si el usuario lo solicita, o informar que han sido omitidas del DDL.
* **Si no se especifica el nombre del dominio**: Asumir el dominio principal del proyecto (ej. `taxai`) para construir la ruta del sistema de archivos.

## 5. CHECKLIST DE AUTO-VERIFICACIÓN (Pre-Flight Checks)

Verifica mentalmente cada punto antes de dar la tarea por completada:

* [ ] ¿Cada archivo generado incluye la cabecera formal y la creación del esquema (`[MUST-01]`, `[MUST-02]`)?
* [ ] ¿Todos los ENUMs y tipos nativos (`UUID`, `TEXT`, etc.) están en MAYÚSCULAS (`[MUST-04]`, `[MUST-05]`)?
* [ ] ¿Se han reemplazado los constraints `CHECK` por tipos `ENUM` reales (`[NEVER-01]`)?
* [ ] ¿Las definiciones dentro de las tablas usan exactamente 1 tabulador (`\t`) y cero alineaciones con espacios (`[MUST-06]`, `[NEVER-02]`)?
* [ ] ¿Se han aplicado exactamente 2 líneas en blanco entre bloques de tablas y 0 entre índices consecutivos (`[MUST-08]`, `[MUST-09]`)?

## 6. FEW-SHOT EXAMPLES

### ❌ FORMATO INCORRECTO

```sql
-- Faltan comentarios de cabecera y el CREATE SCHEMA
create table registry.vehicles (
    id uuid primary key,               -- ❌ minúsculas en tipo y espacios en lugar de tabulador
    plate text not null,               -- ❌ espacios múltiples para alinear
    status varchar(20) check (status in ('active', 'inactive')) -- ❌ CHECK en vez de ENUM
);
create index idx_vehicles_plate on registry.vehicles(plate);

-- ❌ Solo 1 salto de línea entre tablas en lugar de 2
create table registry.drivers (
    id uuid primary key
);

```

### ✅ FORMATO CORRECTO

```sql
-- ========================================================
--  V1
--  Module: registry (Vehicle & Driver Registry)
--  Goal: Manage physical assets and authorized drivers:
--      - Vehicle registration and status tracking
--      - Driver profile management
-- ========================================================
CREATE SCHEMA IF NOT EXISTS registry;

CREATE TYPE registry.vehicle_status AS ENUM ('ACTIVE', 'INACTIVE');
CREATE TYPE registry.driver_status AS ENUM ('ACTIVE', 'SUSPENDED');

CREATE TABLE registry.vehicles (
	id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
	plate TEXT NOT NULL UNIQUE,
	status registry.vehicle_status NOT NULL
);

CREATE INDEX idx_vehicles_status ON registry.vehicles(status);
CREATE INDEX idx_vehicles_plate ON registry.vehicles(plate);

CREATE TRIGGER trg_vehicles_updated BEFORE UPDATE ON registry.vehicles
	FOR EACH ROW EXECUTE FUNCTION common.set_updated_at();


CREATE TABLE registry.drivers (
	id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
	name TEXT NOT NULL,
	status registry.driver_status NOT NULL
);
```
