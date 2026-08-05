---
name: java_use_case_and_command_generator
description: "ACTIVAR CUANDO: Se solicite generar los puertos de entrada (Use Cases) y comandos de aplicación (Commands) en Java a partir de la especificación Markdown del contrato de API o contexto de controladores (ej. 'genera los UseCases y Commands del módulo profile'). HACE: Genera las interfaces UseCase en application/port/in/ y los records Command en application/command/ aplicando las reglas exactas de CommandValidator según el tipo de dato."
version: 2.0.0
---

# SKILL: JAVA_USE_CASE_AND_COMMAND_GENERATOR

## 1. ACTIVACIÓN Y ALCANCE
- **Scope / Directorios**:
    - **Origen (Read-Only)**: Especificación de API en Markdown (ej. `docs/api/api_specification.md`), controladores Web o contexto de la conversación.
    - **Destino (Write)**: 
        - Interfaz UseCase: `src/main/java/com/taxai/api/<module>/application/port/in/<Action><Resource>UseCase.java`
        - Record Command: `src/main/java/com/taxai/api/<module>/application/command/<Action><Resource>Command.java`
- **Inputs válidos**: Documentos Markdown con tablas de endpoints, métodos de controladores o peticiones explícitas para generar casos de uso y comandos de dominio.
- **Output Contract**: Generar pares de archivos `.java` en UTF-8 (Interfaz de puerto de entrada + Record de comando validado) respetando la arquitectura hexagonal y el patrón de validación fluido `CommandValidator`.

## 2. REGLAS INVARIABLES (STRICT CONSTRAINTS)
> ⚠️ **Atención IA**: Las siguientes reglas de arquitectura hexagonal, asignación de métodos de validación y mensajes son de cumplimiento incondicional.

### A. Ubicación y Paquetes (Arquitectura Hexagonal)
- **[MUST-01]**: Las interfaces de UseCase DEBEN guardarse en `src/main/java/com/taxai/api/<module>/application/port/in/<Action><Resource>UseCase.java` con el paquete `package com.taxai.api.<module>.application.port.in;`.
- **[MUST-02]**: Los records de Command DEBEN guardarse en `src/main/java/com/taxai/api/<module>/application/command/<Action><Resource>Command.java` con el paquete `package com.taxai.api.<module>.application.command;`.

### B. Interfaz UseCase
- **[MUST-03]**: La interfaz de UseCase DEBE declarar un único método `execute(...)`:
    - Operaciones con mutación o parámetros ➔ `<ResultType> execute(<Action><Resource>Command command);` (o `void execute(...)` si no hay retorno).
    - Consultas sin parámetros ➔ `<ResultType> execute();`.

### C. Matriz de Mapeo de `CommandValidator` (MÁXIMA PRIORIDAD)
- **[MUST-04]**: Dentro del constructor compacto del Command (`public <Action><Resource>Command { ... }`), la IA DEBE seleccionar el método de `CommandValidator` según el tipo de dato exacto:

| Tipo de Dato | Condición / Atributo | Método `CommandValidator` a usar | Ejemplo de Código |
|---|---|---|---|
| `String` | Obligatorio (texto general, nombres, códigos) | `.rejectIfBlank(value, code, msg)` | `.rejectIfBlank(name, "NAME_REQUIRED", "Name is required.")` |
| `String` | Formato UUID en texto | `.rejectIfInvalidUuid(value, code, msg)` | `.rejectIfInvalidUuid(idStr, "ID_INVALID", "ID format is invalid.")` |
| `UUID` | Obligatorio (identificadores fuertemente tipados) | `.rejectIfNull(value, code, msg)` | `.rejectIfNull(addressId, "ADDRESS_ID_REQUIRED", "Address ID is required.")` |
| `Instant` / `LocalDate` | Obligatorio (fechas y marcas de tiempo) | `.rejectIfNull(value, code, msg)` | `.rejectIfNull(scheduledAt, "SCHEDULED_AT_REQUIRED", "Scheduled time is required.")` |
| `BigDecimal` / `Integer` / `Long` | Obligatorio (montos, contadores, cantidades) | `.rejectIfNull(value, code, msg)` | `.rejectIfNull(amount, "AMOUNT_REQUIRED", "Amount is required.")` |
| `Boolean` | Obligatorio (flags o estados booleanos) | `.rejectIfNull(value, code, msg)` | `.rejectIfNull(isActive, "IS_ACTIVE_REQUIRED", "Is active flag is required.")` |
| `Enum` / Objetos | Obligatorio (tipos enumerados u objetos embebidos) | `.rejectIfNull(value, code, msg)` | `.rejectIfNull(status, "STATUS_REQUIRED", "Status is required.")` |
| Cualquier tipo | Reglas compuestas, rangos o patrones (`min/max`, etc.) | `.rejectIf(condition, code, msg)` | `.rejectIf(age < 18, "AGE_INVALID", "Age must be at least 18.")` |

- **[MUST-05]**: Para **campos opcionales**, NUNCA usar `rejectIfBlank` ni `rejectIfNull`. Si el campo opcional requiere validar formato si está presente, usar `.rejectIf(val != null && ..., "CODE", "Msg")` o `.rejectIfInvalidUuid(val, "CODE", "Msg")`.

### D. Nomenclatura y Restricciones Generales
- **[NEVER-01]**: NUNCA recrear la clase `CommandValidator.java`. Se asume que existe de forma transversal en `com.taxai.api.shared.application.validation.CommandValidator`.
- **[NEVER-02]**: NUNCA utilizar Lombok ni anotaciones de Jakarta Validation (`@NotNull`, `@NotBlank`) dentro de la capa de aplicación/comandos.
- **[MUST-06]**: Los códigos de error (`code`) DEBEN estar en `MAYÚSCULAS_CON_GUIONES_BAJOS` con el sufijo `_REQUIRED` o `_INVALID` (ej. `ADDRESS_ID_REQUIRED`).
- **[MUST-07]**: Los mensajes de error (`message`) DEBEN seguir la plantilla estándar en inglés terminada en punto:
    - Obligatorio ➔ `"<Parameter Name> is required."`
    - Inválido ➔ `"<Parameter Name> is invalid."`

## 3. ALGORITMO DE EJECUCIÓN
Ejecuta la tarea en esta secuencia estricta:

1. **Parse & Map Operation**:
    - Identificar el método del controlador y sus parámetros de entrada.
    - Deducir los nombres del UseCase y Command.
2. **Generate UseCase Interface**:
    - Declarar la interfaz en `com.taxai.api.<module>.application.port.in` con la firma `execute(...)`.
3. **Generate Command Record & Build Validations**:
    - Crear el Record en `com.taxai.api.<module>.application.command`.
    - Iterar cada parámetro del Command y aplicar la **Matriz de Mapeo `[MUST-04]`**:
        - Si es `String` obligatorio ➔ `rejectIfBlank`.
        - Si es `UUID`, `Instant`, `BigDecimal`, `Integer`, `Boolean` o `Enum` obligatorio ➔ `rejectIfNull`.
        - Si es un String que representa UUID ➔ `rejectIfInvalidUuid`.
        - Si requiere validación de rango o lógica personalizada ➔ `rejectIf`.
4. **Emit Files**:
    - Escribir los archivos `.java` en UTF-8 garantizando la invocación final a `.validate(<Command>.class.getSimpleName())`.

## 4. EDGE CASES & FALLBACKS
- **Si el UseCase no requiere parámetros**: Omitir la creación del Command y definir la firma sin argumentos: `ProfileResult execute();`.
- **Si un Command combina String obligatorio y UUID obligatorio**:
```java
  CommandValidator.start()
          .rejectIfBlank(name, "NAME_REQUIRED", "Name is required.") // String ➔ rejectIfBlank
          .rejectIfNull(tenantId, "TENANT_ID_REQUIRED", "Tenant ID is required.") // UUID ➔ rejectIfNull
          .validate(CreateTenantCommand.class.getSimpleName());
```

## 5. CHECKLIST DE AUTO-VERIFICACIÓN (Pre-Flight Checks)

Verifica internamente antes de dar la tarea por completada:

* [ ] ¿Los campos `String` usan `rejectIfBlank` y los campos `UUID`/`Instant`/`BigDecimal` usan `rejectIfNull` (`[MUST-04]`)?
* [ ] ¿Los formatos UUID en texto usan `rejectIfInvalidUuid` (`[MUST-04]`)?
* [ ] ¿El Command finaliza la cadena con `.validate(CommandName.class.getSimpleName())` (`[MUST-04]`)?
* [ ] ¿Los códigos usan `UPPER_SNAKE_CASE` y los mensajes siguen la plantilla `"<Param> is required."` (`[MUST-06]`, `[MUST-07]`)?

---

## 6. FEW-SHOT EXAMPLES

### ❌ CÓDIGO GENERADO INCORRECTO

```java
public record CreateVehicleCommand(
        UUID tenantId,
        String plate,
        Integer seats
) {
    public CreateVehicleCommand {
        CommandValidator.start()
                .rejectIfNull(plate, "PLATE_REQUIRED", "Plate is required.") // ❌ ERROR: Usó rejectIfNull para un String (debe ser rejectIfBlank)
                .rejectIfBlank(tenantId.toString(), "TENANT_REQUIRED", "Tenant required") // ❌ ERROR: Transformó UUID a String para usar rejectIfBlank
                .validate("CreateVehicleCommand");
    }
}
```

---

### ✅ CÓDIGO GENERADO CORRECTO

```java
package com.taxai.api.fleet.application.command;

import com.taxai.api.shared.application.validation.CommandValidator;

import java.util.UUID;

public record CreateVehicleCommand(
        UUID tenantId,
        String plate,
        Integer passengerSeats
) {
    public CreateVehicleCommand {
        CommandValidator.start()
                .rejectIfNull(tenantId, "TENANT_ID_REQUIRED", "Tenant ID is required.")
                .rejectIfBlank(plate, "PLATE_REQUIRED", "Plate is required.")
                .rejectIfNull(passengerSeats, "PASSENGER_SEATS_REQUIRED", "Passenger seats is required.")
                .validate(CreateVehicleCommand.class.getSimpleName());
    }
}
```