---
name: java_http_request_record_generator
description: "ACTIVAR CUANDO: Se solicite generar los DTOs de entrada (HttpRequest) en Java con validaciones Jakarta y anotaciones OpenAPI/Swagger a partir de la especificación Markdown del contrato de API (ej. 'genera los HttpRequest en Java del módulo fleet', 'crea los records de request para los controllers'). HACE: Parsea los DTOs {nombreMetodo}HttpRequest del contrato en Markdown, infiere los tipos de datos Java, genera descripciones/ejemplos en @Schema y aplica restricciones de Jakarta Validation (@NotBlank, @NotNull, @Size, etc.) con códigos de error estandarizados en la carpeta infrastructure/adapter/in/web/dto/."
version: 2.0.0
---

# SKILL: JAVA_HTTP_REQUEST_RECORD_GENERATOR

## 1. ACTIVACIÓN Y ALCANCE
- **Scope / Directorios**:
    - **Origen (Read-Only)**: Especificación de API en Markdown (ej. `docs/api/api_specification.md` o contexto de la conversación).
    - **Destino (Write)**: `src/main/java/com/taxai/api/<module>/infrastructure/adapter/in/web/dto/<HttpRequestName>.java`
- **Inputs válidos**: Documentos Markdown con la especificación de controladores/endpoints o firmas de Requests (ej. `CreateVehicleHttpRequest { tenantId, plate, make, model }`).
- **Output Contract**: Generar archivos `.java` independientes en UTF-8 para cada `HttpRequest` dentro de la capa Web Adapter de arquitectura hexagonal (`infrastructure/adapter/in/web/dto`), utilizando **Java Records (Java 17+)** enriquecidos con anotaciones de Jakarta Validation y OpenAPI 3.

## 2. REGLAS INVARIABLES (STRICT CONSTRAINTS)
> ⚠️ **Atención IA**: Las siguientes reglas de arquitectura, validación, inferencia de tipos y formato visual son de cumplimiento incondicional.

### A. Ubicación y Paquetes (Arquitectura Hexagonal)
- **[MUST-01]**: Cada archivo generado DEBE guardarse estrictamente en la ruta `src/main/java/com/taxai/api/<module>/infrastructure/adapter/in/web/dto/<HttpRequestName>.java`.
- **[MUST-02]**: La declaración del paquete DEBE seguir la convención Hexagonal: `package com.taxai.api.<module>.infrastructure.adapter.in.web.dto;`.

### B. Anotaciones de Validación y OpenAPI
- **[MUST-03]**: Todo atributo DEBE incluir la anotación `@Schema(description = "...", example = "...")` con textos y ejemplos realistas e inherentes al contexto del dominio.
- **[MUST-04]**: Todos los campos obligatorios (sin el sufijo `?` en el especificado Markdown) DEBEN incluir anotaciones de **Jakarta Validation**:
    - Cadenas de texto obligatorias ➔ `@NotBlank(message = "PARAM_NAME_REQUIRED")`
    - Objeto, UUID, Números, Fechas u Enums obligatorios ➔ `@NotNull(message = "PARAM_NAME_REQUIRED")`
    - Códigos o formatos restringidos (ej. ISO country, UUID, rangos) ➔ `@Size`, `@Min`, `@Max`, `@Pattern` con mensaje `PARAM_NAME_INVALID`.
- **[MUST-05]**: Los mensajes de validación DEBEN escribirse en **MAYÚSCULAS_CON_GUIONES** con el sufijo `_REQUIRED` o `_INVALID` (ej. `PLATE_REQUIRED`, `COUNTRY-CODE_INVALID`).

### C. Estructura y Formato Visual Estricto
- **[MUST-06]**: Todos los DTOs DEBEN implementarse usando la sintaxis nativa de **Java Record** (`public record <HttpRequestName>(...) {}`).
- **[MUST-07]**: El espaciado vertical DEBE incluir **exactamente 1 salto de línea en blanco** entre la declaración de cada parámetro/campo para garantizar máxima legibilidad visual entre bloques de anotaciones.
- **[NEVER-01]**: NUNCA usar clases tradicionales (`class`), ni Lombok (`@Data`, `@AllArgsConstructor`), ni omitir validaciones en campos requeridos.

## 3. ALGORITMO DE EJECUCIÓN
Ejecuta la tarea en esta secuencia estricta:

1. **Parse Request Signatures**:
    - Escanear el Markdown fuente bajo cada controlador/módulo.
    - Extraer el nombre del `HttpRequest` (ej. `CreateTenantHttpRequest`) y sus parámetros listados.
2. **Type Mapping & Validation Strategy**:
    - Identificar si un parámetro es opcional (`?`) o requerido.
    - Mapear tipos Java: `UUID` (`id`), `Instant` (`*At`), `BigDecimal` (`amount`/`fare`), `Integer`/`Long` (`count`/`limit`), `Boolean` (`is*`/`has*`), `List<T>` (`items`), o `String`.
    - Generar el código de error para el atributo (ej. `addressLine1` ➔ `ADDRESS-LINE1_REQUIRED`).
3. **Import Resolution**:
    - Resolver imports de Swagger: `io.swagger.v3.oas.annotations.media.Schema`.
    - Resolver imports de Jakarta: `jakarta.validation.constraints.*` (`@NotBlank`, `@NotNull`, `@Size`, `@Min`, `@Max`, etc.).
4. **Directory Creation & Code Generation**:
    - Crear el directorio `mkdir -p src/main/java/com/taxai/api/<module>/infrastructure/adapter/in/web/dto/`.
    - Escribir el código Java con codificación UTF-8 aplicando rigurosamente el espaciado `[MUST-07]`.

## 4. EDGE CASES & FALLBACKS
- **Si el campo es opcional (`?` en Markdown)**: Omitir anotaciones `@NotBlank` / `@NotNull`, pero mantener siempre la anotación `@Schema(description = "Optional ...", example = "...")`.
- **Si el campo representa un ID de recurso**: Usar el tipo `UUID` anotado con `@NotNull(message = "RESOURCE-ID_REQUIRED")`.
- **Si la petición no tiene body (ej. GET por ID o DELETE sin payload)**: Si se define un DTO de query params o path, incluir las anotaciones correspondientes de formato/validación.

## 5. CHECKLIST DE AUTO-VERIFICACIÓN (Pre-Flight Checks)
Verifica internamente antes de dar la tarea por completada:

- [ ] ¿El archivo se ubica en `<module>/infrastructure/adapter/in/web/dto/` (`[MUST-01]`)?
- [ ] ¿El paquete asignado es `com.taxai.api.<module>.infrastructure.adapter.in.web.dto` (`[MUST-02]`)?
- [ ] ¿Todos los atributos tienen su anotación `@Schema` con `description` y `example` (`[MUST-03]`)?
- [ ] ¿Los campos requeridos incluyen `@NotBlank` / `@NotNull` con mensaje en formato `PARAM_REQUIRED` (`[MUST-04]`, `[MUST-05]`)?
- [ ] ¿Existe exactamente 1 salto de línea en blanco entre parámetros dentro del record (`[MUST-07]`)?

---

## 6. FEW-SHOT EXAMPLES

### ❌ CÓDIGO GENERADO INCORRECTO

```java
package com.taxai.api.fleet.dto; // ❌ ERROR: Paquete incorrecto (debe incluir infrastructure.adapter.in.web.dto)

public class CreateVehicleHttpRequest { // ❌ ERROR: Uso de class en vez de record y sin OpenAPI/Validation
    private String plate;
    private String make;
}
```

---

### ✅ CÓDIGO GENERADO CORRECTO

**Entrada Markdown**:
`POST /tenants` | `CreateTenantHttpRequest { name, taxId, dataRegion?, defaultLanguage }`

**Salida Java** (`src/main/java/com/taxai/api/fleet/infrastructure/adapter/in/web/dto/CreateTenantHttpRequest.java`):

```java
package com.taxai.api.fleet.infrastructure.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateTenantHttpRequest(
        @Schema(description = "The registered legal name of the tenant enterprise.", example = "Taxi Central Corp")
        @NotBlank(message = "NAME_REQUIRED")
        String name,

        @Schema(description = "The tax identification number or CIF.", example = "B12345678")
        @NotBlank(message = "TAX-ID_REQUIRED")
        String taxId,

        @Schema(description = "Optional cloud region assigned for tenant data isolation.", example = "eu-west-1")
        String dataRegion,

        @Schema(description = "The default ISO 2-letter language code for tenant operation.", example = "ES")
        @NotBlank(message = "DEFAULT-LANGUAGE_REQUIRED")
        @Size(min = 2, max = 2, message = "DEFAULT-LANGUAGE_INVALID")
        String defaultLanguage
) {}
```