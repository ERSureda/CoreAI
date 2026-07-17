---
name: sql_schema_formatter
description: Guidelines and strict rules for formatting and generating PostgreSQL schema (.sql) files in the Taxai project.
---

# SQL Schema Formatting Rules

When creating, refactoring, or updating SQL schema files for this project, you **MUST** strictly adhere to the following formatting and structural rules. These rules dictate the architecture, syntax, indentation, and vertical spacing of the SQL files.

## 1. Modularization & File Structure
- Schemas are divided into domain modules (e.g., `common`, `registry`, `booking`, `billing`, `support`, `audit`).
- Create a specific folder for the schema (e.g., `src/main/resources/db/schema/taxai/`).
- Inside that folder, create a subfolder for each module.
- Name the migration scripts following Flyway convention with the module name appended: `V1__create_<module_name>.sql`.

## 2. Preamble & Enums Placement
- **Descriptive Header**: Each file MUST start with a descriptive header comment block that synthesizes the goal and main responsibilities of the module. Follow this exact format:
  ```sql
  -- ========================================================
  --  V1
  --  Module: <module_name> (<Brief Title>)
  --  Goal: <A clear explanation of what this schema achieves>:
  --      - <Key responsibility 1>
  --      - <Key responsibility 2>
  --      - <Key responsibility 3>
  -- ========================================================
  ```
- **Schema Creation**: Immediately after the header, place the `CREATE SCHEMA IF NOT EXISTS <module_name>;` statement.
- **Types and ENUMs**: ALL `CREATE TYPE ... AS ENUM` statements belonging to a schema must be placed at the very top of their respective module file, right after the schema creation and extensions.
- **Enum Definitions and Values**: All ENUM definitions and their inner string values MUST be in **UPPERCASE**. 
- **Strict Enum Usage**: NEVER use a `CHECK (column IN ('A', 'B'))` constraint to substitute or emulate an enum. You must ALWAYS create and use a real PostgreSQL `CREATE TYPE ... AS ENUM` for these cases.
- **Data Types Capitalization**: All PostgreSQL native data types inside tables, functions, or casts (e.g., `UUID`, `TEXT`, `BOOLEAN`, `TIMESTAMPTZ`, `SMALLINT`, `JSONB`) must be completely in **UPPERCASE**.

## 3. Indentation & Padding
- **Tabs, not spaces**: The indentation for column definitions inside a `CREATE TABLE` block must be done using exactly **one tab character (`\t`)**.
- **No padding**: Do NOT use multiple spaces to vertically align column data types or constraints. Ensure there is only a single space between the column name and the column type, and remove all leading spaces replaced by the single tab.

## 4. Strict Vertical Spacing (Line Breaks)
The vertical spacing ("intros" or blank lines) between SQL statements is strictly defined:

1. **First Table spacing**: There must be exactly **one blank line** (one intro) between the initial setup block (enums/extensions/schema) and the first `CREATE TABLE` statement in the file.
2. **Intra-block spacing**: 
   - A logical "table block" consists of the table definition, its indices, and its triggers.
   - Between the end of a `CREATE TABLE` and its first `CREATE INDEX` or `CREATE TRIGGER`, there must be exactly **one blank line**.
   - Multiple `CREATE INDEX` statements for the same table must be grouped together with **NO blank lines** (zero intros) between them.
   - Between the indices and the `CREATE TRIGGER` statement, there must be exactly **one blank line**.
3. **Inter-block spacing (Between Tables)**:
   - Between the end of one logical table block (i.e., after its last index or trigger) and the start of the next `CREATE TABLE` statement, there must be exactly **two blank lines** (two intros).

## 5. Execution Workflow for Processing Raw SQL
When the user provides a raw monolithic SQL file and asks you to apply this skill (or format it), you MUST proactively do the following:
1. Parse the entire input SQL to identify the logical modules (schemas).
2. Autonomously create the required directory structure on the filesystem (e.g., `src/main/resources/db/schema/taxai/<module_name>/`).
3. Generate the separated `V1__create_<module_name>.sql` files inside their respective folders.
4. Apply ALL the formatting rules defined above (Enums placement, Tabs, Caps, and Vertical spacing) when writing the files. Do not ask for intermediate confirmation to create the folders; just create them and write the properly formatted SQL files.

---
**Example of valid formatting:**
```sql
CREATE SCHEMA IF NOT EXISTS registry;

CREATE TYPE registry.vehicle_status AS ENUM ('ACTIVE','INACTIVE');
CREATE TYPE registry.driver_status AS ENUM ('ACTIVE','SUSPENDED');

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
