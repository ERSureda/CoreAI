---
name: java_result_record_generator
description: "ACTIVAR CUANDO: Se solicite generar los DTOs de salida (Result) en Java a partir de la especificación Markdown del contrato de API (ej. 'genera los Results en Java del módulo fleet', 'crea los records para los Result del contrato API'). HACE: Parsea las definiciones **XResult**: { ... } del contrato en Markdown, infiere los tipos de datos Java más eficientes y fuertemente tipados (UUID, Instant, BigDecimal, List, etc.), y genera autónomamente los archivos .java usando la sintaxis exacta de Java Record en la ruta <module>/application/result/."
version: 2.0.0
---

# SKILL: JAVA_RESULT_RECORD_GENERATOR

## 1. ACTIVACIÓN Y ALCANCE
- **Scope / Directorios**:
    - **Origen (Read-Only)**: Especificación de API en Markdown (ej. `docs/api/api_specification.md` o contexto de la conversación).
    - **Destino (Write)**: `src/main/java/com/taxai/api/<module>/application/result/<ResultName>.java`
- **Inputs válidos**: Documentos Markdown con la especificación de controladores/endpoints o bloques de definición de tipos DTO (ej. `**TenantResult**: { id, name, isActive, createdAt }`).
- **Output Contract**: Generar archivos `.java` independientes en UTF-8 para cada `Result` dentro de la carpeta `application/result` del módulo correspondiente, utilizando estrictamente la sintaxis de **Java Record (Java 17+)**.

## 2. REGLAS INVARIABLES (STRICT CONSTRAINTS)
> ⚠️ **Atención IA**: Las siguientes reglas de arquitectura, inferencia de tipos y formato de código son de cumplimiento incondicional.

### A. Ubicación y Paquetes
- **[MUST-01]**: Cada archivo generado DEBE guardarse estrictamente en la ruta `src/main/java/com/taxai/api/<module>/application/result/<ResultName>.java`.
- **[MUST-02]**: La declaración del paquete DEBE seguir el patrón `package com.taxai.api.<module>.application.result;`.

### B. Estructura y Formato de Código
- **[MUST-03]**: Todos los DTOs Result DEBEN implementarse usando la sintaxis nativa de **Java Record** (`public record <ResultName>(...) {}`).
- **[MUST-04]**: El formato visual y la indentación DEBEN ser exactamente idénticos al estándar de la arquitectura:
    - Nombre del record y apertura del paréntesis en la primera línea.
    - Cada parámetro en su propia línea con una tabulación (`\t` o 4 espacios).
    - Coma al final de cada parámetro, **excepto en el último**.
    - Cierre en una sola línea `) {}`.
- **[NEVER-01]**: NUNCA usar clases Java tradicionales (`class`), ni anotaciones de Lombok (`@Data`, `@Value`, `@Builder`), ni anotaciones de Jackson a menos que sea estrictamente indispensable.

### C. Inferencia Estricta de Tipos Java
- **[MUST-05]**: La IA DEBE inferir el tipo de dato Java más preciso según el nombre del campo:
    - `id`, `*Id` (ej. `tenantId`, `bookingId`, `driverId`) ➔ `UUID`
    - `createdAt`, `updatedAt`, `*At` (ej. `scheduledPickupAt`, `validFrom`, `etaAt`) ➔ `Instant`
    - `isActive`, `active`, `needs*`, `allows*`, `wheelchair*` ➔ `Boolean`
    - `passengerCount`, `passengerSeats`, `quantity`, `seq`, `rank`, `durationS`, `distanceM` ➔ `Integer` o `Long`
    - `totalAmount`, `baseFare`, `perKm`, `perMin`, `minFare`, `estimatedMin` ➔ `BigDecimal`
    - Colecciones o arrays (ej. `items: [X]`, `luggage: [Y]`) ➔ `List<X>`
    - Cualquier otro texto genérico ➔ `String`

## 3. ALGORITMO DE EJECUCIÓN
Ejecuta la tarea en esta secuencia estricta:

1. **Parse & Module Resolution**:
    - Escanear el Markdown fuente e identificar las definiciones DTO bajo cada `## MÓDULO <module>`.
    - Extraer el nombre del Result (ej. `DriverResult`) y la lista de sus atributos.
2. **Type Mapping & Imports**:
    - Aplicar las reglas de inferencia `[MUST-05]` a cada atributo del Result.
    - Construir el bloque de `import` necesario según los tipos detectados (`java.util.UUID`, `java.time.Instant`, `java.math.BigDecimal`, `java.util.List`).
3. **Directory Creation & Code Generation**:
    - Crear la carpeta de destino si no existe: `mkdir -p src/main/java/com/taxai/api/<module>/application/result/`.
    - Generar el código Java aplicando el formato estricto `[MUST-03]` y `[MUST-04]`.
4. **Emit Files**:
    - Escribir cada archivo `<ResultName>.java` en UTF-8 garantizando que no existan líneas en blanco excesivas al final del archivo.

## 4. EDGE CASES & FALLBACKS
- **Si un Result referencia a otros Result o DTOs internos**: (Ej. `luggage: [LuggageResult]`). Importar o incluir la referencia relativa `List<LuggageResult>` manteniendo la consistencia de tipos.
- **Si el tipo es ambiguo**: Preferir tipos envolventes (`UUID`, `Integer`, `Boolean`) sobre primitivos (`int`, `boolean`) para dar soporte nativo a valores nulos.
- **Si un Result no pertenece a un módulo explícito**: Ubicarlo en `src/main/java/com/taxai/api/shared/application/result/`.

## 5. CHECKLIST DE AUTO-VERIFICACIÓN (Pre-Flight Checks)
Verifica internamente antes de emitir los archivos Java:

- [ ] ¿El archivo se ubica dentro de `<module>/application/result/` (`[MUST-01]`)?
- [ ] ¿El paquete asignado es `package com.taxai.api.<module>.application.result;` (`[MUST-02]`)?
- [ ] ¿Se ha utilizado únicamente la sintaxis de `public record` sin Lombok ni `class` (`[MUST-03]`, `[NEVER-01]`)?
- [ ] ¿Los campos `id` usan `UUID`, las fechas `Instant` y los montos `BigDecimal` (`[MUST-05]`)?
- [ ] ¿El cierre del record sigue exactamente la estructura `) {}` (`[MUST-04]`)?

---

## 6. FEW-SHOT EXAMPLES

### ❌ CÓDIGO GENERADO INCORRECTO

```java
package com.taxai.api.fleet.dto; // ❌ ERROR: Paquete incorrecto (debe ser application.result)

import lombok.Data; // ❌ ERROR: Uso de Lombok

@Data
public class TenantResult { // ❌ ERROR: Uso de class en vez de record
    private String id; // ❌ ERROR: String en lugar de UUID
    private String name;
    private boolean isActive;
    private String createdAt; // ❌ ERROR: String en lugar de Instant
}
```

Aquí tienes la especificación completa para la nueva Skill **`java_result_record_generator`**, diseñada siguiendo el estándar exacto, las restricciones incondicionales, el formato estricto de Java Records y la ubicación por módulo solicitada (`application/result`).

---

### ✅ CÓDIGO GENERADO CORRECTO

**Entrada Markdown**:
`**TenantResult**: { id, name, taxId, isActive, createdAt, updatedAt }`

**Salida Java** (`src/main/java/com/taxai/api/fleet/application/result/TenantResult.java`):

```java
package com.taxai.api.fleet.application.result;

import java.time.Instant;
import java.util.UUID;

public record TenantResult(
        UUID id,
        String name,
        String taxId,
        Boolean isActive,
        Instant createdAt,
        Instant updatedAt
) {}
```