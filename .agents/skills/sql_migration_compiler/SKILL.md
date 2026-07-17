---
name: sql_migration_compiler
description: Guidelines and strict rules for compiling and concatenating individual PostgreSQL schema files from domain directories into unified Flyway migration scripts.
---

# SQL Migration Compiler Guidelines

You are acting as the `sql_migration_compiler`. Your primary responsibility is to safely read modular, domain-driven SQL schema files and compile them into a single, valid Flyway migration script. 

## 1. Directory Structure and Scope
- **Source of Truth:** The individual, modular SQL files reside in `src/main/resources/db/schema/<domain>/<module>/` (e.g., `schema/taxai/iam/V1__create_iam.sql` or `schema/core/tenancy/V1__create_tenancy.sql`).
- **Target Destination:** The compiled migration script MUST ALWAYS be saved in `src/main/resources/db/migration/`.
- **Target Naming Convention:** Name the output file following Flyway versioning, typically `V<X>__create_<domain>.sql` (e.g., `V1__create_taxai.sql`, `V3__create_core.sql`), where `<X>` is the next available logical version.

## 2. Strict Non-Destructive Policy
- **DO NOT** delete, rename, or modify the source SQL files inside the `schema/` folder.
- Your job is strictly to **READ** from the `schema/` folders and **WRITE** to the `migration/` folder. 

## 3. Dependency Resolution and Ordering
The order in which the modular SQL files are concatenated is **CRITICAL** for the database to execute the script successfully.
- You must analyze the foreign keys, custom types, and functions to determine the correct dependency graph.
- **Base Modules First:** Modules providing cross-cutting concerns (e.g., `common`), shared utilities, or base tables (e.g., `tenancy.tenants`) MUST be placed at the very top of the compiled file.
- **Dependent Modules Later:** Schemas that reference other schemas (e.g., `iam` referencing `common.set_updated_at()`, or `dispatch` referencing `platform.create_month_partition()`) must be appended AFTER their dependencies.
- **DO Blocks and Scripts:** Ensure that scripts containing `DO` blocks (for RLS, triggers, or partition setups) that rely on external schema functions are ordered logically after those functions are created.

## 4. Concatenation Rules
- Use safe reading tools (like `view_file` or robust scripts) to extract the content. Avoid blind `cat` shell commands that might corrupt encodings or truncate large files.
- Ensure the final output is encoded in **UTF-8**.
- Add a safe newline separator between files when joining them to prevent syntax collision at the boundaries.
- Maintain the visual separation between modules. Retain any existing header comments (e.g., `-- Module: tenancy`) to make the final unified file readable and traceable.
- **Do not** add extra arbitrary blank lines inside the SQL blocks themselves; respect the spacing that the files inherently provide.

## 5. Handling User Prompts
The user might ask for:
1. **Full Domain Compilation:** Concatenating all modules under a domain like `taxai/` or `core/`. You must find all `.sql` files within that tree and order them.
2. **Specific Folder Compilation:** Concatenating only a specific subset (e.g., just `taxai/booking/` and its children). You must isolate your search to the requested path and compile only those files.
