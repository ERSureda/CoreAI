# Puntos a Revisar y Mejorar (Arquitectura)

## 1. Renombrar el módulo "Dispatch" en el Core
**Sugerencia:** Renombrar el módulo `dispatch` del Core a `routing` o `webhooks`.
**Motivo:** Evitar que el equipo (o nosotros mismos) se confunda al hablar de "el problema está en el dispatch", ya que TaxAI (el vertical) también tiene un concepto muy fuerte de "dispatch" para la asignación de flotas físicas.

---

## 2. Separación de responsabilidades: Core vs TaxAI (Manejo de Planes)
Es fundamental entender y mantener clara la barrera de responsabilidades entre los dos mundos.

*Imagina que le vendes tu software a "Taxis Girona":*

### Lo que controla el CORE (Planes de Consumo AI)
Al Core **solo le importa el gasto de servidor y telecomunicaciones**. 
Su plan dice: *"Taxis Girona tiene un límite de 2.000 minutos de voz al mes, 1 número de teléfono y 3 agentes concurrentes"*. Si se pasan, el Core les cobra exceso de minutos. **Al Core no le importa cuántos coches físicos tiene la empresa.**

### Lo que controla TAXAI (Planes de Negocio/SaaS)
A TaxAI le da igual cuántos minutos hable la IA. Lo que TaxAI necesita saber es: *"¿Taxis Girona ha pagado la licencia 'Premium' que le permite registrar 50 vehículos, tener 3 operadores en el panel web y usar el módulo de facturación B2B?"*

En la API de TaxAI (el vertical), **no necesitas meter la pasarela de pagos, ni el precio en euros de los planes, ni lidiar con facturas**. Solo necesitas gestionar los **"Entitlements"** (Derechos o Límites de uso) asociados al `tenant_id`.

---

## 3. Flujo de Trabajo y Sinergia (Modelo Shopify/Atlassian)
El flujo perfecto de comunicación entre ambos contextos es el siguiente:

1. **La Compra:** 
   El dueño de Taxis Girona entra a su panel de control y dice: *"Quiero el Plan PRO de Taxis (que incluye 50 coches y 1.000 minutos de IA)"*.
2. **El Cobro (Stripe):** 
   Stripe le cobra 150€ al mes.
3. **El Reparto de Información (Webhooks internos):**
   - El sistema de cobros le avisa al **Core**: *"Oye, ponle a este Tenant el límite de 1.000 minutos"*.
   - El sistema de cobros manda un evento (vía Kafka o Webhook) a **TaxAI**: *"Oye, actualiza los `tenant_entitlements` de este Tenant al plan PRO (50 coches)"*.
4. **La Ejecución:**
   - Cuando el recepcionista de Taxis Girona intenta añadir el coche número 51 en el panel web, el backend de TaxAI mira su tabla local `tenant_entitlements`, ve que el límite es 50, y le devuelve un error `403 Forbidden: Upgrade your plan`. 
   - El Core ni se entera de este bloqueo físico de negocio.
