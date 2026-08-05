---
name: java_mapstruct_mapper_generator
description: "ACTIVAR CUANDO: Se solicite generar interfaces de mapeo MapStruct en Java tanto para la capa de entrada Web (Web Mappers DTO -> Command) como para la capa de salida de Persistencia (Persistence Mappers Domain <-> Entity) a partir de la especificación de API, entidades o contexto del proyecto. HACE: Parsea los controladores, DTOs, comandos y entidades de persistencia para generar interfaces @Mapper de MapStruct en infrastructure/adapter/in/web/mapper/ o infrastructure/adapter/out/persistence/<tech>/mapper/."
version: 2.0.0
---

# SKILL: JAVA_MAPSTRUCT_MAPPER_GENERATOR

## 1. ACTIVACIÓN Y ALCANCE
- **Scope / Directorios**:
    - **Origen (Read-Only)**: Especificación de API en Markdown, Controllers Web, HttpRequest DTOs, Commands, Modelos de Dominio o Entidades de Persistencia (JPA/DynamoDB).
    - **Destino (Write)**: 
        - Web Mappers: `src/main/java/com/taxai/api/<module>/infrastructure/adapter/in/web/mapper/<Feature>WebMapper.java`
        - Persistence Mappers: `src/main/java/com/taxai/api/<module>/infrastructure/adapter/out/persistence/<tech>/mapper/<Entity>Mapper.java`
- **Inputs válidos**: Peticiones para crear mappers entre adaptadores web y comandos, o entre modelos de dominio y entidades de base de datos.
- **Output Contract**: Generar interfaces MapStruct en Java 17+ configuradas con Spring `@Mapper`, manejando políticas de atributos no mapeados y métodos `default` según el tipo de mapper.

## 2. REGLAS INVARIABLES (STRICT CONSTRAINTS)
> ⚠️ **Atención IA**: Las siguientes reglas de configuración MapStruct, paquetes y patrones de conversión son de cumplimiento incondicional.

### A. Categorías de Mappers y Ubicación Hexagonal
- **[MUST-01] Mappers Web (Entrada)**:
    - Ruta: `src/main/java/com/taxai/api/<module>/infrastructure/adapter/in/web/mapper/<Feature>WebMapper.java`
    - Paquete: `package com.taxai.api.<module>.infrastructure.adapter.in.web.mapper;`
    - Configuración @Mapper:
      ```java
      @Mapper(
              componentModel = MappingConstants.ComponentModel.SPRING,
              unmappedTargetPolicy = ReportingPolicy.IGNORE
      )
      ```
- **[MUST-02] Mappers de Persistencia (Salida)**:
    - Ruta: `src/main/java/com/taxai/api/<module>/infrastructure/adapter/out/persistence/<tech>/mapper/<Entity>Mapper.java` (donde `<tech>` puede ser `postgres`, `dynamodb`, etc.)
    - Paquete: `package com.taxai.api.<module>.infrastructure.adapter.out.persistence.<tech>.mapper;`
    - Configuración @Mapper:
      ```java
      @Mapper(
              componentModel = MappingConstants.ComponentModel.SPRING,
              unmappedTargetPolicy = ReportingPolicy.ERROR
      )
      ```

### B. Reglas de Mapeo Web (`WebMapper`)
- **[MUST-03]**: Los métodos de `WebMapper` transforman peticiones HTTP a Comandos de Aplicación:
    - Firma estándar: `<Action><Resource>Command to<Action><Resource>Command(<Action><Resource>HttpRequest request);`
    - Peticiones combinadas (Path ID + Request Body): `<Action><Resource>Command to<Action><Resource>Command(UUID <id>, <Action><Resource>HttpRequest request);`
- **[NEVER-01]**: NUNCA usar `ReportingPolicy.ERROR` en `WebMapper`. Los HttpRequest y Commands pueden tener firmas intencionalmente dispares.

### C. Reglas de Mapeo de Persistencia (`PersistenceMapper`)
- **[MUST-04]**: Los `PersistenceMapper` convierten entre el Modelo de Dominio y la Entidad de Persistencia (`toEntity` y `toDomain`).
- **[MUST-05]**: Al reconstruir objetos de dominio encapsulados, el mapper DEBE utilizar métodos `default` en la interfaz invocando el método de fábrica estático del dominio (ej. `DomainModel.reconstruct(...)`), gestionando explícitamente conversiones de tipos (ej. String $\leftrightarrow$ Enum, fechas, nombres de atributos mapeados manualmente).
- **[MUST-06]**: Todos los métodos `default` DEBEN incluir comprobaciones explícitas de nulos (`if (domain == null) return null;`).
- **[NEVER-02]**: NUNCA instanciar modelos de dominio mediante constructores públicos `new DomainModel(...)` si el modelo utiliza métodos de reconstrucción de fábrica (`.reconstruct(...)` o `.of(...)`).

## 3. ALGORITMO DE EJECUCIÓN
Ejecuta la tarea en esta secuencia estricta:

1. **Identify Mapper Type & Context**:
    - Determinar si se solicita un **WebMapper** (dto $\rightarrow$ command) o un **PersistenceMapper** (domain $\leftrightarrow$ entity).
    - Identificar el módulo (`<module>`) y la tecnología de persistencia (`postgres`, `dynamodb`, etc.) si aplica.
2. **WebMapper Generation Flow**:
    - Inspeccionar los Controllers y los `HttpRequest` / `Command` implicados.
    - Generar la interfaz anotada con `@Mapper` usando `ReportingPolicy.IGNORE`.
    - Crear los métodos `to<Command>Name(...)` resolviendo firmas simples o compuestas (UUID + DTO).
3. **PersistenceMapper Generation Flow**:
    - Inspeccionar el objeto de Dominio y la Entidad de Persistencia.
    - Generar la interfaz anotada con `@Mapper` usando `ReportingPolicy.ERROR`.
    - Implementar métodos `default` para `toEntity(...)` y `toDomain(...)` realizando verificaciones de nulo e invocando `Domain.reconstruct(...)`.
4. **Emit File**:
    - Escribir el archivo `.java` en UTF-8 en el directorio correspondiente.

## 4. EDGE CASES & FALLBACKS
- **Si el WebMapper recibe solo un valor simple (ej. token o ID)**: Generar el comando directamente: `VerifyUserCommand toVerifyUserCommand(String token);`.
- **Si la Entidad de BD almacena Enums como String**: Realizar la conversión en los métodos `default`:
    - Dominio $\rightarrow$ Entidad: `domain.getType() != null ? domain.getType().name() : null`
    - Entidad $\rightarrow$ Dominio: `entity.getType() != null ? EnumType.valueOf(entity.getType()) : null`
- **Si el mapping MapStruct es directo y sin conversiones manuales**: Se pueden usar métodos abstractos directos en lugar de `default`.

## 5. CHECKLIST DE AUTO-VERIFICACIÓN (Pre-Flight Checks)
Verifica internamente antes de emitir los mappers Java:

- [ ] ¿Los WebMappers usan `unmappedTargetPolicy = ReportingPolicy.IGNORE` (`[MUST-01]`)?
- [ ] ¿Los PersistenceMappers usan `unmappedTargetPolicy = ReportingPolicy.ERROR` (`[MUST-02]`)?
- [ ] ¿Los mappers se ubican en la ruta exacta Hexagonal (`web/mapper` u `out/persistence/<tech>/mapper`) (`[MUST-01]`, `[MUST-02]`)?
- [ ] ¿Los métodos `default` en persistencia manejan nulos e invocan `Domain.reconstruct(...)` (`[MUST-05]`, `[MUST-06]`)?

---

## 6. FEW-SHOT EXAMPLES

### ❌ CÓDIGO GENERADO INCORRECTO

```java
package com.taxai.api.identity.mapper; // ❌ ERROR: Paquete no sigue la convención Hexagonal

import org.mapstruct.Mapper;

@Mapper // ❌ ERROR: Faltan componentModel = SPRING y unmappedTargetPolicy
public interface UserMapper {
    UserEntity toEntity(User domain); // ❌ ERROR: Mapeo directo sin usar .reconstruct() ni manejar disconformidades de tipos
}
```

---

### ✅ CÓDIGO GENERADO CORRECTO (Web Mapper)

**Salida Java** (`src/main/java/com/taxai/api/identity/infrastructure/adapter/in/web/mapper/AuthWebMapper.java`):

```java
package com.taxai.api.identity.infrastructure.adapter.in.web.mapper;

import com.taxai.api.identity.application.command.*;
import com.taxai.api.identity.infrastructure.adapter.in.web.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface AuthWebMapper {

    LoginUserCommand toLoginUserCommand(LoginUserHttpRequest request);
    RegisterUserCommand toRegisterUserCommand(RegisterUserHttpRequest request);
    VerifyUserCommand toVerifyUserCommand(String token);
    ForgotPasswordUserCommand toForgotPasswordUserCommand(ForgotPasswordUserHttpRequest request);
    ResetPasswordUserCommand toResetPasswordUserCommand(ResetPasswordUserHttpRequest request);
    UpdatePasswordUserCommand toUpdatePasswordUserCommand(UpdatePasswordUserHttpRequest request);
}
```

---

### ✅ CÓDIGO GENERADO CORRECTO (Persistence Mapper)

**Salida Java** (`src/main/java/com/taxai/api/identity/infrastructure/adapter/out/persistence/postgres/mapper/VerificationTokenMapper.java`):

```java
package com.taxai.api.identity.infrastructure.adapter.out.persistence.postgres.mapper;

import com.taxai.api.identity.domain.model.VerificationToken;
import com.taxai.api.identity.domain.model.enums.VerificationTokenType;
import com.taxai.api.identity.infrastructure.adapter.out.persistence.postgres.entity.VerificationTokenEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface VerificationTokenMapper {

    default VerificationTokenEntity toEntity(VerificationToken domain) {
        if (domain == null) {
            return null;
        }
        VerificationTokenEntity entity = new VerificationTokenEntity();
        entity.setId(domain.getToken());
        entity.setUserId(domain.getUserId());
        entity.setType(domain.getType() != null ? domain.getType().name() : null);
        entity.setExpiresAt(domain.getExpiryDate());
        return entity;
    }

    default VerificationToken toDomain(VerificationTokenEntity entity) {
        if (entity == null) {
            return null;
        }
        return VerificationToken.reconstruct(
                entity.getId(),
                entity.getUserId(),
                entity.getType() != null ? VerificationTokenType.valueOf(entity.getType()) : null,
                entity.getExpiresAt()
        );
    }
}
```