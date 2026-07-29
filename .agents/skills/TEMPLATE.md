---
name: <nombre_skill_en_snake_case>
description: "ACTIVAR CUANDO: <condiciones exactas, ej: el usuario edite archivos .sql en db/schema/, pida crear migraciones de Flyway o use el comando /format-schema>. HACE: <efecto final directo>."
version: 2.0.0
---

# SKILL: <NOMBRE_DE_LA_SKILL>

## 1. ACTIVACIÓN Y ALCANCE
- **Scope / Directorios**: `<ruta/a/archivos/**/*>`
- **Inputs válidos**: `<archivos de origen, código raw o comandos del usuario>`
- **Output Contract**: `<Describir exactamente qué se escribe: ej. Solo archivos .sql en UTF-8 en la ruta X. Cero texto conversacional adicional.>`

## 2. REGLAS INVARIABLES (STRICT CONSTRAINTS)
> ⚠️ **Atención IA**: Las siguientes reglas tienen prioridad máxima. No debes violar ninguna bajo ningún concepto.

- **[MUST-01]**: <Regla obligatoria clara y atómica>
- **[MUST-02]**: <Regla de sintaxis, formato o naming convention>
- **[NEVER-01]**: <Acción estrictamente prohibida (ej: Modificar ficheros fuente en schema/)>
- **[NEVER-02]**: <Otra prohibición crítica>

## 3. ALGORITMO DE EJECUCIÓN
Ejecuta los siguientes pasos en secuencia estricta:

1. **Parse & Map**:
    - Analizar el input identificando <entidades / dependencias / módulos>.
    - Determinar el orden de ejecución basado en <criterio de dependencias>.
2. **Transform**:
    - Aplicar transformación siguiendo las reglas `[MUST-0X]`.
3. **Emit & File Structure**:
    - Crear directorios implícitos: `mkdir -p <ruta>`
    - Escribir archivos finales en `<ruta_destino>` respetando el Output Contract.

## 4. EDGE CASES & FALLBACKS
- **Si ocurre <Problema A>**: <Solución A explícita>.
- **Si falta <Información B>**: <Acción: ej. Asumir default X sin preguntar / Detener y pedir dato Y>.
- **Si hay conflicto de dependencias**: <Estrategia de resolución de conflictos>.

## 5. CHECKLIST DE AUTO-VERIFICACIÓN (Pre-Flight Checks)
Antes de dar la tarea por completada, la IA DEBE verificar internamente cada punto:

- [ ] ¿He aplicado el formato exacto sin violar ningún `[MUST-0X]`?
- [ ] ¿He respetado la prohibición `[NEVER-01]` y no he tocado archivos protegidos?
- [ ] ¿Los nombres de archivo y rutas coinciden exactamente con la convención?
- [ ] ¿El archivo resultado está libre de bloques truncados o marcas de formato erróneas?

## 6. FEW-SHOT EXAMPLES

### ❌ INPUT / OUTPUT INCORRECTO
```<lenguaje>
// Muestra el fallo típico (ej: saltos de línea incorrectos, minúsculas en enums)
```

### ✅ INPUT / OUTPUT CORRECTO
```<lenguaje>
// Muestra el resultado impecable esperado
```