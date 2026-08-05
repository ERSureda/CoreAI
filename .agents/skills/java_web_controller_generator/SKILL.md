---
name: java_web_controller_generator
description: "ACTIVAR CUANDO: Se solicite generar la capa de adaptadores web de entrada (Controllers Spring Boot REST) en Java a partir de la especificación Markdown del contrato de API (ej. 'genera los controllers en Java del módulo fleet', 'crea el VehicleController a partir del contrato API'). HACE: Genera ÚNICAMENTE la clase Controller (.java) en infrastructure/adapter/in/web/. Asume expresamente que los Use Cases (application.port.in.*), WebMapper, Commands y DTOs ya están definidos o serán creados por otras skills."
version: 2.0.0
---

# SKILL: JAVA_WEB_CONTROLLER_GENERATOR

## 1. ACTIVACIÓN Y ALCANCE
- **Scope / Directorios**:
    - **Origen (Read-Only)**: Especificación de API en Markdown (ej. `docs/api/api_specification.md` o contexto de la conversación).
    - **Destino (Write)**: `src/main/java/com/taxai/api/<module>/infrastructure/adapter/in/web/<ControllerName>.java`
- **Inputs válidos**: Documentos Markdown con tablas de controladores/endpoints, tipos de petición/respuesta y rutas base.
- **Output Contract**: Generar EXCLUSIVAMENTE el archivo `.java` del Controller correspondiente dentro de la capa Web Adapter de arquitectura hexagonal (`infrastructure/adapter/in/web`). Queda prohibida la creación de cualquier otro archivo secundario.

## 2. REGLAS INVARIABLES (STRICT CONSTRAINTS)
> ⚠️ **Atención IA**: Las siguientes reglas de arquitectura, inyección de dependencias, alcance y respuestas HTTP son de cumplimiento incondicional.

### A. Ubicación y Paquetes (Arquitectura Hexagonal)
- **[MUST-01]**: Cada archivo generado DEBE guardarse estrictamente en la ruta `src/main/java/com/taxai/api/<module>/infrastructure/adapter/in/web/<ControllerName>.java`.
- **[MUST-02]**: La declaración del paquete DEBE seguir la convención Hexagonal: `package com.taxai.api.<module>.infrastructure.adapter.in.web;`.

### B. Anotaciones de Clase e Inyección
- **[MUST-03]**: Toda clase Controller DEBE incluir las anotaciones de nivel de clase:
    - `@RequiredArgsConstructor` (Lombok)
    - `@RestController` (Spring)
    - `@RequestMapping("/api/v1/<module>/<resource>")`
    - `@Tag(name = "<Resource> Management", description = "API for <resource> operations.")` (OpenAPI)
- **[MUST-04]**: Las dependencias inyectadas DEBEN ser declaradas como `private final`:
    - `private final <Module>WebMapper mapper;`
    - Un puerto de entrada (`<Action><Resource>UseCase`) por cada operación del controlador.

### C. Límites de Generación (Strict Scope Bounds)
- **[NEVER-01]**: NUNCA incluir anotaciones `@ApiResponses` ni `@ApiResponse`. Esta documentación queda excluida expresamente por diseño.
- **[NEVER-02]**: NUNCA generar, crear o modificar archivos para Use Cases (`*UseCase.java`), Commands (`*Command.java`), Mappers (`*WebMapper.java`) ni DTOs (`*HttpRequest.java`, `*Result.java`). La IA DEBE asumir que estas interfaces y clases ya existen o serán creadas de forma independiente.

### D. Métodos, Mapeo y Respuestas
- **[MUST-05]**: Cada método DEBE incluir únicamente la anotación de documentación `@Operation(summary = "...", description = "...")` especificando textos claros sobre la acción realizada.
- **[MUST-06]**: Los parámetros de entrada DEBEN usar las anotaciones correspondientes de Spring y Jakarta:
    - `@Valid @RequestBody <HttpRequestName> request` para cuerpos JSON.
    - `@PathVariable UUID <id>` para variables de ruta.
    - `@RequestParam` para parámetros de consulta.
- **[MUST-07]**: La respuesta HTTP DEBE construirse siempre mediante `ResponseEntity`:
    - Endpoints con retorno de datos ➔ `return ResponseEntity.status(200).body(<useCase>.execute(...));`
    - Endpoints de creación/actualización/borrado sin cuerpo ➔ `return ResponseEntity.status(<code >).build();` (donde `<code >` es `201` para creación y `200` para modificación/borrado).

## 3. ALGORITMO DE EJECUCIÓN
Ejecuta la tarea en esta secuencia estricta:

1. **Parse Controller Specification**:
    - Analizar la tabla del controlador en el Markdown de origen (Ruta base, Verbo HTTP, Método, Request DTO y Result DTO).
    - Identificar el módulo (ej. `fleet`, `profile`) y deducir los nombres de los casos de uso (`<Action><Resource>UseCase`) que deben inyectarse.
2. **Imports Resolution (Assuming Existing Ports)**:
    - Importar la interfaz de los casos de uso desde `com.taxai.api.<module>.application.port.in.*`.
    - Importar los Result DTOs desde `com.taxai.api.<module>.application.result.*`.
    - Importar los HttpRequest DTOs desde `com.taxai.api.<module>.infrastructure.adapter.in.web.dto.*`.
    - Importar el mapper desde `com.taxai.api.<module>.infrastructure.adapter.in.web.mapper.<Module>WebMapper`.
3. **Directory Creation & Code Generation**:
    - Crear el directorio `mkdir -p src/main/java/com/taxai/api/<module>/infrastructure/adapter/in/web/`.
    - Escribir únicamente el archivo `<ControllerName>.java` aplicando Lombok, Spring Web y Swagger OpenAPI.
4. **Emit File**:
    - Guardar el archivo del Controller en UTF-8 garantizando la separación limpia mediante saltos de línea entre métodos.

## 4. EDGE CASES & FALLBACKS
- **Si el UseCase no está creado aún en el proyecto**: Inyectar e invocar el UseCase en la firma del Controller asumiendo la convención de nombres `<Action><Resource>UseCase`, sin intentar generar el archivo `.java` de la interfaz.
- **Si el método realiza una mutación que requiere Command**: Asumir que el mapper posee el método `mapper.to<Action>Command(...)` y pasarlo a `useCase.execute(...)`.
- **Si el método devuelve un Result**: Devolver `ResponseEntity<ResultType>` invocando `useCase.execute(...)`.

## 5. CHECKLIST DE AUTO-VERIFICACIÓN (Pre-Flight Checks)
Verifica internamente antes de dar la tarea por completada:

- [ ] ¿Se ha generado EXCLUSIVAMENTE el archivo del Controller sin crear UseCases, Mappers, Commands ni DTOs (`[NEVER-02]`)?
- [ ] ¿El archivo se ubica en `<module>/infrastructure/adapter/in/web/` (`[MUST-01]`)?
- [ ] ¿El paquete asignado es `com.taxai.api.<module>.infrastructure.adapter.in.web` (`[MUST-02]`)?
- [ ] ¿No se incluye NINGUNA anotación `@ApiResponses` ni `@ApiResponse` (`[NEVER-01]`)?
- [ ] ¿Se utiliza `@Operation` en cada método con `summary` y `description` (`[MUST-05]`)?
- [ ] ¿Las mutaciones devuelven `ResponseEntity<Void>` llamando al mapper y al UseCase (`[MUST-07]`)?

---

## 6. FEW-SHOT EXAMPLES

### ❌ CÓDIGO GENERADO INCORRECTO

```java
package com.taxai.api.profile.controller; // ❌ ERROR: Paquete incorrecto

import io.swagger.v3.oas.annotations.responses.ApiResponse; // ❌ ERROR: Import no permitido

@RestController
public class ProfileController { // ❌ ERROR: Falta @RequiredArgsConstructor, @RequestMapping y @Tag

    @ApiResponses({ // ❌ ERROR: Uso de @ApiResponses expresamente prohibido
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @GetMapping("/me")
    public ProfileResult getProfile() { // ❌ ERROR: Debe devolver ResponseEntity<ProfileResult>
        return null;
    }
}
```

---

### ✅ CÓDIGO GENERADO CORRECTO

**Entrada Markdown**:
`PUT /me/addresses/{addressId}` | `updateMyAddress` | `HTTP` | `UpdateAddressHttpRequest` | `Void`

**Salida Java** (`src/main/java/com/taxai/api/profile/infrastructure/adapter/in/web/ProfileController.java`):

```java
package com.taxai.api.profile.infrastructure.adapter.in.web;

import com.taxai.api.profile.application.port.in.*;
import com.taxai.api.profile.application.result.ProfileResult;
import com.taxai.api.profile.infrastructure.adapter.in.web.dto.*;
import com.taxai.api.profile.infrastructure.adapter.in.web.mapper.ProfileWebMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/profiles")
@Tag(name = "Profile Management", description = "API for profile and address management.")
public class ProfileController {

    private final ProfileWebMapper mapper;

    private final GetProfileUseCase getProfileUseCase;
    private final UpdateAddressUseCase updateAddressUseCase;

    @GetMapping("/me")
    @Operation(
            summary = "Get authenticated user profile",
            description = "Retrieves the profile details and active address of the authenticated user."
    )
    public ResponseEntity<ProfileResult> getMyProfile() {
        return ResponseEntity
                .status(200)
                .body(getProfileUseCase.execute());
    }

    @PutMapping("/me/addresses/{addressId}")
    @Operation(
            summary = "Update authenticated user address",
            description = "Updates an existing address within the authenticated user's profile."
    )
    public ResponseEntity<Void> updateMyAddress(
            @PathVariable UUID addressId,
            @Valid @RequestBody UpdateAddressHttpRequest request
    ) {
        updateAddressUseCase.execute(mapper.toUpdateAddressCommand(addressId, request));
        return ResponseEntity
                .status(200)
                .build();
    }
}
```