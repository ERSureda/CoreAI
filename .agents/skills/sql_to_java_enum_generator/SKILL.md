---
name: sql_to_java_enum_generator
description: "ACTIVAR CUANDO: Se solicite analizar esquemas SQL en db/schema/ para generar los Enums Java correspondientes en los módulos del dominio (ej. 'genera los enums del módulo booking', 'crea los enums de taxai'). HACE: Parsea los DDL CREATE TYPE ... AS ENUM, consulta expresamente al usuario cuáles son enums globales (Shared Kernel) para colocarlos en shared vs módulo específico, y genera los archivos .java con anotaciones para JPA y Jackson."
version: 2.0.0
---

# SKILL: SQL_TO_JAVA_ENUM_GENERATOR

## 1. ACTIVACIÓN Y ALCANCE
- **Scope / Directorios**:
    - **Origen (Read-Only)**: `src/main/resources/db/schema/<domain>/<module>/*.sql`
    - **Destino (Write)**: 
        - Módulo específico: `src/main/java/com/taxai/api/<module>/domain/model/enums/<EnumName>.java`
        - Shared Kernel: `src/main/java/com/taxai/api/shared/domain/model/enums/<EnumName>.java`
- **Inputs válidos**: Peticiones para generar Enums Java a partir de esquemas SQL (un módulo aislado o todo un dominio como `taxai`).
- **Output Contract**: Generar archivos `.java` en UTF-8 con la definición del enum en Java de forma limpia (únicamente el paquete y las constantes exactas del DDL SQL, sin valor default UNKNOWN ni comentarios).

## 2. REGLAS INVARIABLES (STRICT CONSTRAINTS)
> ⚠️ **Atención IA**: Las siguientes reglas son de estricto cumplimiento y tienen máxima prioridad.

- **[MUST-01] Interacción Previa (Confirmación de Enums Globales)**: ANTES de escribir ningún archivo Java en disco, la IA DEBE analizar todas las declaraciones `CREATE TYPE <schema>.<name> AS ENUM (...)` del input, identificar los enums candidatos a ser compartidos (ej: los que coinciden en nombre en múltiples esquemas como `vehicle_type` o `actor_type`, o los de uso transversal), y **preguntar expresamente al usuario** cuáles de ellos deben ir a `shared/domain/model/enums` y cuáles a `<module>/domain/model/enums`.
- **[MUST-02] Convención de Nombres y Paquetes**:
    - Nombre del paquete del módulo: `com.taxai.api.<module>.domain.model.enums`
    - Nombre del paquete compartido: `com.taxai.api.shared.domain.model.enums`
    - Transformación de nombre SQL a Java: `snake_case` a `PascalCase` en singular (ej. `vehicle_type` ➔ `VehicleType`, `offer_status` ➔ `OfferStatus`).
- **[MUST-03] Formato Limpio Estricto (Sin Defaults, Comentarios ni Salto de Línea Final)**:
    - Generar únicamente las constantes exactas extraídas del DDL SQL.
    - NO añadir constante fallback `UNKNOWN`, NO incluir la anotación `@JsonEnumDefaultValue` ni comentarios JavaDoc.
    - El contenido del archivo DEBE terminar exactamente en el carácter `}` de cierre en la última línea (sin ningún salto de línea `\n` o línea vacía posterior).
- **[MUST-04] Codificación y Formato**: Generar los archivos exclusivamente en **UTF-8** con sintaxis Java 17+ / Spring Boot 3+.
- **[NEVER-01]**: NUNCA modificar, editar o borrar archivos SQL dentro de `src/main/resources/db/schema/`. La fuente SQL es **estrictamente Read-Only**.
- **[NEVER-02]**: NUNCA asumir arbitrariamente la ubicación de enums ambiguos sin preguntar primero al usuario (`[MUST-01]`).

## 3. ALGORITMO DE EJECUCIÓN
Ejecuta la tarea en esta secuencia estricta:

1. **Parse & Extract Enums**:
    - Inspeccionar los archivos `.sql` en `src/main/resources/db/schema/<domain>/<module>/`.
    - Extraer todas las declaraciones de enum: `CREATE TYPE <schema>.<enum_name> AS ENUM ('VAL1', 'VAL2', ...);`.
2. **Global Enum Detection & User Consultation**:
    - Listar los enums encontrados organizados por esquema.
    - Identificar coincidencias de nombres entre diferentes submódulos (ej. `vehicle_type` en `booking`, `fleet` y `pricing`).
    - **Detener la ejecución y preguntar al usuario** qué enums deben ser creados en `shared` como Enums Globales (Shared Kernel) y cuáles deben ir al paquete del módulo local.
3. **Wait for User Input**:
    - Esperar la respuesta explícita del usuario indicando la distribución de los enums.
4. **Code Generation & File Writing**:
    - Para cada enum confirmado, construir la clase Enum en Java aplicando las reglas `[MUST-02]` y `[MUST-03]`.
    - Crear los directorios de destino si no existen (`mkdir -p`).
    - Escribir los archivos `.java` finales garantizando que la escritura se realice exactamente hasta el carácter `}` sin inyectar saltos de línea `\n` posteriores.

## 4. EDGE CASES & FALLBACKS
- **Si un enum ya existe en Java**: Verificar si ya tiene el paquete correcto y la anotación `@JsonEnumDefaultValue`. Si requiere cambios, informar antes de sobrescribir.
- **Si el enum en SQL ya contiene un valor `UNKNOWN`**: No duplicarlo; simplemente colocarle la anotación `@JsonEnumDefaultValue`.
- **Si el usuario decide que NINGÚN enum va a `shared`**: Respetar su decisión y crear cada enum de forma independiente dentro del paquete `com.taxai.api.<module>.domain.model.enums`.

## 5. CHECKLIST DE AUTO-VERIFICACIÓN (Pre-Flight Checks)
Verifica internamente antes de dar la tarea por completada:

- [ ] ¿He preguntado al usuario antes de escribir código cuáles enums van a `shared` (`[MUST-01]`)?
- [ ] ¿No he alterado ningún archivo SQL original (`[NEVER-01]`)?
- [ ] ¿Cada enum Java incluye `UNKNOWN` con `@JsonEnumDefaultValue` (`[MUST-03]`)?
- [ ] ¿Los nombres de clase y paquetes respetan la convención en singular `PascalCase` (`[MUST-02]`)?

## 6. FEW-SHOT EXAMPLES

### ❌ CÓDIGO JAVA GENERADO INCORRECTO
```java
package com.taxai.api.booking; // ❌ ERROR: Paquete incorrecto

public enum vehicle_type { // ❌ ERROR: Nombre no sigue PascalCase en singular
    SEDAN,
    ESTATE,
    MINIVAN_6
    // ❌ ERROR: Falta UNKNOWN y @JsonEnumDefaultValue
}
```

### ✅ CÓDIGO JAVA GENERADO CORRECTO
```java
package com.taxai.api.booking.domain.model.enums;

public enum VehicleType {
    SEDAN,
    ESTATE,
    MINIVAN_6,
    VAN_9,
    LUXURY,
    EV,
    WHEELCHAIR_ACCESSIBLE
}
```
