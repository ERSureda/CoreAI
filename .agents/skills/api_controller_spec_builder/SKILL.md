---
name: api_controller_spec_builder
description: "ACTIVAR CUANDO: Se solicite crear, actualizar, estructurar o refactorizar la especificación de endpoints, controllers, requests y results de una API en Markdown (ej. 'documenta los controllers de fleet', 'genera el contrato de la API a partir de estos requisitos', 'crea el md de endpoints v2'). HACE: Lee los requisitos de negocio, entidades o contexto de la conversación, consolida los Result por agregado, individualiza los Requests por método y genera la documentación técnica completa en Markdown siguiendo el formato estandarizado."
version: 2.0.0
---

# SKILL: API_CONTROLLER_SPEC_BUILDER

## 1. ACTIVACIÓN Y ALCANCE
- **Scope / Directorios**:
    - **Origen (Read-Only)**: Requisitos del usuario, contexto de la conversación, modelos de dominio o esquemas DDL.
    - **Destino (Write)**: `docs/api/api_specification.md` (o generación directa de Markdown en respuesta al usuario).
- **Inputs válidos**: Listado informal de endpoints, contratos DTO raw, historias de usuario o esquemas de base de datos a transformar en especificación técnica de API.
- **Output Contract**: Documento Markdown rigurosamente formateado en UTF-8 con cabecera de convenciones, separación por módulos y controladores, tablas de endpoints, definiciones de DTOs consolidadas y tabla resumen final.

## 2. REGLAS INVARIABLES (STRICT CONSTRAINTS)
> ⚠️ **Atención IA**: Las siguientes reglas de arquitectura, convenciones y formato Markdown son de cumplimiento estricto.

### A. Convenciones de Nombrado
- **[MUST-01]**: Los componentes DEBEN seguir estrictamente las siguientes convenciones:
    - **Controller**: `{Recurso}Controller` (ej. `VehicleController`)
    - **Método Java/Código**: verbo en `camelCase` (ej. `createVehicle`, `listDrivers`)
    - **Request DTO**: `{nombreMetodo}HttpRequest` (ej. `CreateVehicleHttpRequest`)
    - **Result DTO**: `{Recurso}Result` para el agregado principal (ej. `VehicleResult`)
- **[NEVER-01]**: NUNCA fusionar o reutilizar `HttpRequest` entre diferentes métodos. Cada endpoint/método DEBE tener su propio Request dedicado (1 a 1), ya que cada input difiere por diseño.

### B. Consolidación y Excepciones de Result
- **[MUST-02]**: Los `Result` DEBEN consolidarse por agregado. Todos los métodos que devuelven el estado actual del mismo recurso deben reutilizar `{Recurso}Result`.
- **[MUST-03]**: Solo se permite romper la consolidación de `Result` en las siguientes excepciones explícitas:
    1. **Listados masivos**: Utilizar `{Recurso}Item` como DTO ligero si el listado omite campos pesados.
    2. **Respuestas 204**: Indicar explícitamente `— *(204, sin contenido)*`.
    3. **Operaciones sin snapshot de recurso**: Históricos (`TripStatusHistoryResult`), matching (`MatchTripResult`), acks simples o eventos de streaming (`TripLiveEventResult`).

### C. Formato y Estructura Markdown
- **[MUST-04]**: Todo documento generado DEBE iniciar con el bloque explicativo de **Convención de nombrado** y el listado de reglas base.
- **[MUST-05]**: Cada controlador DEBE estar estructurado bajo una cabecera de Nivel 3 (`### {ControllerName} — {base_path}`) e incluir una tabla Markdown con el formato exacto:
  `| Endpoint | Método | Tipo | Request | Result |`
  donde **Tipo** solo acepta: `HTTP` | `WEBSOCKET` | `SSE`. En la celda `Request`, los atributos clave deben incluirse inline entre llaves: `` `MethodHttpRequest { param1, param2? }` ``.
- **[MUST-06]**: Inmediatamente debajo de la tabla del controlador, DEBEN definirse los esquemas de los `Result`, `ItemResult` y `ListResult` introducidos o utilizados en esa sección usando negritas y llaves:
    - Objeto: `**VehicleResult**: { id, tenantId, plate, status }`
    - Colección: `**ListVehiclesResult**: { items: [VehicleResult], nextCursor }`
- **[MUST-07]**: El documento DEBE finalizar con una sección `## Resumen` que contabilice la reducción o el total de DTOs Result por módulo.

## 3. ALGORITMO DE EJECUCIÓN
Ejecuta la tarea en esta secuencia estricta:

1. **Parse & Resource Mapping**:
    - Analizar la información de entrada e identificar los módulos (ej. `fleet`, `booking`, `trip`), recursos y acciones solicitadas.
    - Mapear cada acción a un verbo HTTP (`POST`, `GET`, `PATCH`, `DELETE`) o protocolo (`WEBSOCKET`, `SSE`).
2. **DTO & Path Strategy**:
    - Asignar a cada método su `HttpRequest` individualizado (`{nombreMetodo}HttpRequest`).
    - Determinar el DTO `Result` consolidado del agregado o aplicar las excepciones autorizadas `[MUST-03]`.
3. **Markdown Construction**:
    - Generar el título principal y la cabecera de convenciones `[MUST-04]`.
    - Iterar por Módulo (`## MÓDULO <modulo>`) y por Controller (`### <Controller> — <path>`).
    - Construir las tablas de endpoints `[MUST-05]` y los bloques de definición DTO `[MUST-06]`.
4. **Summary & Verification**:
    - Generar la tabla comparativa o resumen final por módulo `[MUST-07]`.
    - Ejecutar el checklist de pre-verificación pre-flight antes de emitir la respuesta.

## 4. EDGE CASES & FALLBACKS
- **Si el método es DELETE o actualización void**: Asignar `— *(204, sin contenido)*` en la columna Result.
- **Si un campo DTO es opcional**: Añadir el sufijo `?` al nombre del campo en la definición del JSON schema (ej. `notes?`, `quietFrom?`).
- **Si no se proporciona el módulo**: Agrupar los controladores bajo un módulo genérico denominado `core` o deducirlo del path base del recurso.
- **Si hay endpoints WebSocket o SSE**: Especificar el protocolo en la columna `Tipo` y documentar si el payload es un evento de streaming o de canal bidireccional.

## 5. CHECKLIST DE AUTO-VERIFICACIÓN (Pre-Flight Checks)
Verifica internamente antes de emitir la documentación:

- [ ] ¿Cada método de API tiene su propio `HttpRequest` exclusivo sin reutilizar (`[NEVER-01]`)?
- [ ] ¿Los `Result` están consolidados por recurso/agregado evitando duplicidad de DTOs de salida (`[MUST-02]`)?
- [ ] ¿Todas las tablas contienen exactamente las columnas `| Endpoint | Método | Tipo | Request | Result |` (`[MUST-05]`)?
- [ ] ¿Los esquemas DTO están documentados tras cada tabla usando el formato `**NameResult**: { ... }` (`[MUST-06]`)?
- [ ] ¿Se ha incluido la tabla o sección final de resumen (`[MUST-07]`)?

---

## 6. FEW-SHOT EXAMPLES

### ❌ FORMATO INCORRECTO

```markdown
## Endpoints de Vehículos

* POST /vehicles: crea un vehículo usando VehicleDTO y devuelve VehicleDTO.
* GET /vehicles/{id}: devuelve GetVehicleResponse.
* PATCH /vehicles/{id}: edita el vehículo usando VehicleDTO.

### DTOs
VehicleDTO: id, plate, make
GetVehicleResponse: id, plate, make
```

*Errores: Falta tabla Markdown, falta nombrado estándar (VehicleDTO / GetVehicleResponse), falta tipo de transporte, falta separación de HttpRequest por método y consolidación formal.*

---

### ✅ FORMATO CORRECTO

```markdown
# TaxAI API — Controllers, Métodos, Requests y Results

Convención de nombrado:
- Controller: `{Recurso}Controller`
- Método: verbo en camelCase
- Request: `{nombreMetodo}HttpRequest` — **uno por método**, no se fusionan
- Result: `{Recurso}Result` — **uno por agregado**, reutilizado por todos los métodos que devuelven el estado actual
- Tipo: `HTTP` | `WEBSOCKET` | `SSE`

---

## MÓDULO `fleet`

### `VehicleController` — `/v1/fleet/vehicles`

| Endpoint | Método | Tipo | Request | Result |
|---|---|---|---|---|
| `POST /vehicles` | `createVehicle` | HTTP | `CreateVehicleHttpRequest { tenantId, plate, make, model }` | `VehicleResult` |
| `GET /vehicles/{id}` | `getVehicle` | HTTP | `GetVehicleHttpRequest { id }` | `VehicleResult` |
| `GET /vehicles?tenantId=` | `listVehicles` | HTTP | `ListVehiclesHttpRequest { tenantId, cursor?, limit? }` | `ListVehiclesResult` |
| `PATCH /vehicles/{id}` | `updateVehicle` | HTTP | `UpdateVehicleHttpRequest { id, make?, model? }` | `VehicleResult` |
| `DELETE /vehicles/{id}` | `deleteVehicle` | HTTP | `DeleteVehicleHttpRequest { id }` | — *(204, sin contenido)* |

**`VehicleResult`**: `{ id, tenantId, plate, make, model, createdAt, updatedAt }`
**`ListVehiclesResult`**: `{ items: [VehicleResult], nextCursor }`
```