# TaxAI API — Controllers, Métodos, Requests y Results (v2 — Results consolidados)

Convención de nombrado:
- Controller: `{Recurso}Controller`
- Método: verbo en camelCase
- Request: `{nombreMetodo}HttpRequest` — **uno por método**, no se fusionan (cada input es distinto por diseño)
- Result: `{Recurso}Result` — **uno por agregado**, reutilizado por todos los métodos que devuelven el estado actual de ese recurso
- Excepciones a la fusión: listados de alto volumen (`{Recurso}Item` más ligero), operaciones cuya salida no es un snapshot del recurso (históricos, matching, streams), y respuestas `204` sin contenido
- Tipo: `HTTP` | `WEBSOCKET` | `SSE`

---

## MÓDULO `fleet`

### `TenantController` — `/v1/fleet/tenants`

| Endpoint | Método | Tipo | Request | Result |
|---|---|---|---|---|
| `POST /tenants` | `createTenant` | HTTP | `CreateTenantHttpRequest { name, taxId, dataRegion, defaultLanguage }` | `TenantResult` |
| `GET /tenants/{id}` | `getTenant` | HTTP | `GetTenantHttpRequest { id }` | `TenantResult` |
| `PATCH /tenants/{id}` | `updateTenant` | HTTP | `UpdateTenantHttpRequest { id, name?, isActive? }` | `TenantResult` |

**`TenantResult`**: `{ id, name, taxId, isActive, createdAt, updatedAt }`

### `OperatorController` — `/v1/fleet/operators`

| Endpoint | Método | Tipo | Request | Result |
|---|---|---|---|---|
| `POST /operators` | `createOperator` | HTTP | `CreateOperatorHttpRequest { tenantId, userId, fullName, role }` | `OperatorResult` |
| `GET /operators/{id}` | `getOperator` | HTTP | `GetOperatorHttpRequest { id }` | `OperatorResult` |
| `GET /operators?tenantId=` | `listOperators` | HTTP | `ListOperatorsHttpRequest { tenantId, cursor?, limit? }` | `ListOperatorsResult` |
| `PATCH /operators/{id}` | `updateOperator` | HTTP | `UpdateOperatorHttpRequest { id, role?, isActive? }` | `OperatorResult` |

**`OperatorResult`**: `{ id, tenantId, fullName, role, isActive, createdAt, updatedAt }`
**`ListOperatorsResult`**: `{ items: [OperatorResult], nextCursor }`

### `VehicleController` — `/v1/fleet/vehicles`

| Endpoint | Método | Tipo | Request | Result |
|---|---|---|---|---|
| `POST /vehicles` | `createVehicle` | HTTP | `CreateVehicleHttpRequest { tenantId, plate, make, model, type, passengerSeats, wheelchairAccessible, allowsPets }` | `VehicleResult` |
| `GET /vehicles/{id}` | `getVehicle` | HTTP | `GetVehicleHttpRequest { id }` | `VehicleResult` |
| `GET /vehicles?tenantId=&type=&status=` | `listVehicles` | HTTP | `ListVehiclesHttpRequest { tenantId, type?, status?, cursor?, limit? }` | `ListVehiclesResult` |
| `PATCH /vehicles/{id}` | `updateVehicle` | HTTP | `UpdateVehicleHttpRequest { id, make?, model?, passengerSeats?, wheelchairAccessible?, allowsPets? }` | `VehicleResult` |
| `PATCH /vehicles/{id}/status` | `updateVehicleStatus` | HTTP | `UpdateVehicleStatusHttpRequest { id, status }` | `VehicleResult` |

**`VehicleResult`**: `{ id, tenantId, plate, make, model, type, status, passengerSeats, wheelchairAccessible, allowsPets, createdAt, updatedAt }`
**`ListVehiclesResult`**: `{ items: [VehicleResult], nextCursor }`

### `DriverController` — `/v1/fleet/drivers`

| Endpoint | Método | Tipo | Request | Result |
|---|---|---|---|---|
| `POST /drivers` | `createDriver` | HTTP | `CreateDriverHttpRequest { tenantId, userId?, employeeCode, fullName, phone }` | `DriverResult` |
| `GET /drivers/{id}` | `getDriver` | HTTP | `GetDriverHttpRequest { id }` | `DriverResult` |
| `GET /drivers?tenantId=&status=` | `listDrivers` | HTTP | `ListDriversHttpRequest { tenantId, status?, cursor?, limit? }` | `ListDriversResult` |
| `PATCH /drivers/{id}` | `updateDriver` | HTTP | `UpdateDriverHttpRequest { id, fullName?, phone? }` | `DriverResult` |
| `PATCH /drivers/{id}/status` | `updateDriverStatus` | HTTP | `UpdateDriverStatusHttpRequest { id, adminStatus }` | `DriverResult` |

**`DriverResult`**: `{ id, tenantId, employeeCode, fullName, phone, adminStatus, defaultVehicleId, createdAt, updatedAt }`
**`ListDriversResult`**: `{ items: [DriverResult], nextCursor }`

### `DriverVehicleAssignmentController` — `/v1/fleet`

| Endpoint | Método | Tipo | Request | Result |
|---|---|---|---|---|
| `POST /drivers/{id}/vehicle-assignments` | `createVehicleAssignment` | HTTP | `CreateVehicleAssignmentHttpRequest { driverId, vehicleId, createdBy }` | `VehicleAssignmentResult` |
| `PATCH /vehicle-assignments/{id}/end` | `endVehicleAssignment` | HTTP | `EndVehicleAssignmentHttpRequest { id }` | `VehicleAssignmentResult` |
| `GET /drivers/{id}/vehicle-assignments/current` | `getCurrentVehicleAssignment` | HTTP | `GetCurrentVehicleAssignmentHttpRequest { driverId }` | `VehicleAssignmentResult` |

**`VehicleAssignmentResult`**: `{ id, driverId, vehicleId, validFrom, validTo }`

### `DriverPresenceController` — `/v1/fleet/drivers`

| Endpoint | Método | Tipo | Request | Result |
|---|---|---|---|---|
| `POST /drivers/{id}/presence` | `updateDriverPresence` | HTTP | `UpdateDriverPresenceHttpRequest { driverId, status, zoneId? }` | `DriverPresenceResult` |

**`DriverPresenceResult`**: `{ driverId, status, zoneId, updatedAt }`

---

## MÓDULO `booking`

### `BookingController` — `/v1/bookings`

| Endpoint | Método | Tipo | Request | Result |
|---|---|---|---|---|
| `POST /bookings` | `createBooking` | HTTP | `CreateBookingHttpRequest { tenantId, channel, type, passengerPhone, scheduledPickupAt?, recurrenceRule?, passengerCount, vehicleTypeRequired?, needsChildSeat?, wheelchairRequired?, notes?, sourceConversationId?, idempotencyKey, luggage?, pets? }` | `BookingResult` |
| `GET /bookings/{id}` | `getBooking` | HTTP | `GetBookingHttpRequest { id }` | `BookingResult` |
| `GET /bookings/by-locator/{locator}` | `getBookingByLocator` | HTTP | `GetBookingByLocatorHttpRequest { locator }` | `BookingResult` |
| `GET /bookings?tenantId=&status=&passengerId=` | `listBookings` | HTTP | `ListBookingsHttpRequest { tenantId, status?, passengerId?, cursor?, limit? }` | `ListBookingsResult` |
| `PATCH /bookings/{id}` | `updateBooking` | HTTP | `UpdateBookingHttpRequest { id, scheduledPickupAt?, vehicleTypeRequired?, notes? }` | `BookingResult` |
| `POST /bookings/{id}/confirm` | `confirmBooking` | HTTP | `ConfirmBookingHttpRequest { id }` | `BookingResult` |
| `POST /bookings/{id}/cancel` | `cancelBooking` | HTTP | `CancelBookingHttpRequest { id, reason? }` | `BookingResult` |
| `POST /bookings/{id}/fulfill` | `fulfillBooking` | HTTP | `FulfillBookingHttpRequest { id }` | `BookingResult` |
| `POST /bookings/{id}/luggage` | `addLuggage` | HTTP | `AddLuggageHttpRequest { bookingId, type, quantity, notes? }` | `LuggageResult` |
| `DELETE /bookings/{id}/luggage/{luggageId}` | `removeLuggage` | HTTP | `RemoveLuggageHttpRequest { bookingId, luggageId }` | — *(204, sin contenido)* |
| `POST /bookings/{id}/pets` | `addPet` | HTTP | `AddPetHttpRequest { bookingId, type, quantity, inCarrier, notes? }` | `PetResult` |
| `DELETE /bookings/{id}/pets/{petId}` | `removePet` | HTTP | `RemovePetHttpRequest { bookingId, petId }` | — *(204, sin contenido)* |

**`BookingResult`**: `{ id, locator, tenantId, passengerId, channel, type, status, scheduledPickupAt, passengerCount, vehicleTypeRequired, needsChildSeat, wheelchairRequired, notes, sourceConversationId, luggage: [LuggageResult], pets: [PetResult], createdAt, updatedAt }`
*(en `getBookingByLocator` puede devolverse una vista reducida del mismo tipo, con campos internos omitidos según el rol del actor — mismo DTO, serialización condicionada, no un tipo nuevo)*
**`ListBookingsResult`**: `{ items: [BookingItem{id, locator, status, type, createdAt}], nextCursor }`
**`LuggageResult`**: `{ id, type, quantity, notes }`
**`PetResult`**: `{ id, type, quantity, inCarrier, notes }`

---

## MÓDULO `trip`

### `TripController` — `/v1/trips`

| Endpoint | Método | Tipo | Request | Result |
|---|---|---|---|---|
| `POST /trips` | `createTrip` | HTTP | `CreateTripHttpRequest { tenantId, bookingId, passengerId }` | `TripResult` |
| `GET /trips/{id}` | `getTrip` | HTTP | `GetTripHttpRequest { id }` | `TripResult` |
| `GET /trips?tenantId=&status=&driverId=` | `listTrips` | HTTP | `ListTripsHttpRequest { tenantId, status?, driverId?, cursor?, limit? }` | `ListTripsResult` |
| `GET /trips/driver/{driverId}/current` | `getCurrentTripByDriver` | HTTP | `GetCurrentTripByDriverHttpRequest { driverId }` | `TripResult` |
| `POST /trips/{id}/search` | `startTripSearch` | HTTP | `StartTripSearchHttpRequest { id }` | `TripResult` |
| `POST /trips/{id}/assign` | `assignTrip` | HTTP | `AssignTripHttpRequest { id, driverId, vehicleId }` | `TripResult` |
| `POST /trips/{id}/accept` | `acceptTrip` | HTTP | `AcceptTripHttpRequest { id }` | `TripResult` |
| `POST /trips/{id}/arriving` | `markTripArriving` | HTTP | `MarkTripArrivingHttpRequest { id }` | `TripResult` |
| `POST /trips/{id}/arrived` | `markTripArrived` | HTTP | `MarkTripArrivedHttpRequest { id }` | `TripResult` |
| `POST /trips/{id}/board` | `boardTrip` | HTTP | `BoardTripHttpRequest { id }` | `TripResult` |
| `POST /trips/{id}/complete` | `completeTrip` | HTTP | `CompleteTripHttpRequest { id, actualDistanceM?, actualDurationS? }` | `TripResult` |
| `POST /trips/{id}/cancel` | `cancelTrip` | HTTP | `CancelTripHttpRequest { id, cancelledBy, cancelReason, cancelNote? }` | `TripResult` |
| `POST /trips/{id}/fail` | `failTrip` | HTTP | `FailTripHttpRequest { id, reason }` | `TripResult` |
| `GET /trips/{id}/status-history` | `getTripStatusHistory` | HTTP | `GetTripStatusHistoryHttpRequest { id }` | `TripStatusHistoryResult` |

**`TripResult`**: `{ id, tenantId, bookingId, passengerId, driverId, vehicleId, status, scheduledPickupAt, searchStartedAt, searchExpiresAt, assignedAt, acceptedAt, arrivingStartedAt, waitingStartedAt, waitDeadlineAt, boardedAt, completedAt, cancelledAt, cancelledBy, cancelReason, cancelNote, estimatedDistanceM, estimatedDurationS, actualDistanceM, actualDurationS, createdAt, updatedAt }`
**`ListTripsResult`**: `{ items: [TripItem{id, status, driverId, createdAt}], nextCursor }`
**`TripStatusHistoryResult`**: `{ items: [StatusHistoryItem{fromStatus, toStatus, actorType, actorId, reason, occurredAt}] }`

### `TripStopController` — `/v1/trips/{id}/stops`

| Endpoint | Método | Tipo | Request | Result |
|---|---|---|---|---|
| `POST /trips/{id}/stops` | `addTripStop` | HTTP | `AddTripStopHttpRequest { tripId, seq, type, locationId?, addressSnapshot, contactName?, contactPhone? }` | `TripStopResult` |
| `GET /trips/{id}/stops` | `listTripStops` | HTTP | `ListTripStopsHttpRequest { tripId }` | `ListTripStopsResult` |
| `PATCH /trips/{id}/stops/{stopId}` | `updateTripStop` | HTTP | `UpdateTripStopHttpRequest { tripId, stopId, status }` | `TripStopResult` |

**`TripStopResult`**: `{ id, tripId, seq, type, status, locationId, addressSnapshot, contactName, contactPhone, etaAt, arrivedAt, departedAt, notes }`
**`ListTripStopsResult`**: `{ items: [TripStopResult] }`

### `TripRouteController` — `/v1/trips/{id}/routes`

| Endpoint | Método | Tipo | Request | Result |
|---|---|---|---|---|
| `POST /trips/{id}/routes` | `addTripRoute` | HTTP | `AddTripRouteHttpRequest { tripId, reason, provider, encodedPolyline, distanceM, durationS, durationTrafficS?, waypoints? }` | `TripRouteResult` |
| `GET /trips/{id}/routes/active` | `getActiveTripRoute` | HTTP | `GetActiveTripRouteHttpRequest { tripId }` | `TripRouteResult` |

**`TripRouteResult`**: `{ id, tripId, routeVersion, reason, provider, encodedPolyline, distanceM, durationS, durationTrafficS, waypoints, computedAt }`

### `TripLiveController` — streaming

| Endpoint | Método | Tipo | Request | Result |
|---|---|---|---|---|
| `GET /trips/{id}/live` | `streamTripLive` | SSE | `StreamTripLiveHttpRequest { tripId }` | `TripLiveEventResult` *(stream)* |
| `GET /tenants/{id}/trips/live` | `streamTenantTripsLive` | SSE | `StreamTenantTripsLiveHttpRequest { tenantId }` | `TenantTripsLiveEventResult` *(stream)* |

**`TripLiveEventResult`**: `{ type, status?, etaMin?, lat?, lng? }`
**`TenantTripsLiveEventResult`**: `{ tripId, status, driverId, updatedAt }`

---

## MÓDULO `dispatch`

### `DispatchController` — `/v1/dispatch/trips`

| Endpoint | Método | Tipo | Request | Result |
|---|---|---|---|---|
| `POST /trips/{tripId}/match` | `matchTrip` | HTTP | `MatchTripHttpRequest { tripId }` | `MatchTripResult` |
| `POST /trips/{tripId}/assign-nearest` | `assignNearestDriver` | HTTP | `AssignNearestDriverHttpRequest { tripId, zoneId? }` | `OfferResult` |

**`MatchTripResult`**: `{ tripId, wave, offersCreated: [OfferResult] }`

### `DispatchOfferController` — `/v1/dispatch`

| Endpoint | Método | Tipo | Request | Result |
|---|---|---|---|---|
| `GET /trips/{tripId}/offers?wave=` | `listTripOffers` | HTTP | `ListTripOffersHttpRequest { tripId, wave? }` | `ListOffersResult` |
| `GET /drivers/{driverId}/offers/pending` | `listPendingDriverOffers` | HTTP | `ListPendingDriverOffersHttpRequest { driverId }` | `ListOffersResult` |
| `POST /offers/{id}/accept` | `acceptOffer` | HTTP | `AcceptOfferHttpRequest { offerId }` | `OfferResult` |
| `POST /offers/{id}/reject` | `rejectOffer` | HTTP | `RejectOfferHttpRequest { offerId }` | `OfferResult` |

**`OfferResult`**: `{ id, tripId, driverId, vehicleId, rank, distanceM, etaS, status, offeredAt, expiresAt, respondedAt }`
**`ListOffersResult`**: `{ items: [OfferResult], nextCursor }`

### `DriverChannelHandler` — `/v1/dispatch/drivers/{id}/channel`

| Evento | Método | Tipo | Request | Result |
|---|---|---|---|---|
| Cliente → servidor (aceptar) | `acceptOfferViaChannel` | WEBSOCKET | `AcceptOfferViaChannelHttpRequest { driverId, offerId }` | `OfferResult` |
| Cliente → servidor (rechazar) | `rejectOfferViaChannel` | WEBSOCKET | `RejectOfferViaChannelHttpRequest { driverId, offerId }` | `OfferResult` |
| Cliente → servidor (ubicación) | `updateDriverLocation` | WEBSOCKET | `UpdateDriverLocationHttpRequest { driverId, lat, lng }` | `DriverLocationResult` |
| Servidor → cliente (push oferta) | `pushOfferToDriver` | WEBSOCKET | *(interno, sin invocación de cliente)* | `OfferResult` |

**`DriverLocationResult`**: `{ driverId, receivedAt }`

---

## MÓDULO `pricing`

### `FareEstimateController` — `/v1/pricing`

| Endpoint | Método | Tipo | Request | Result |
|---|---|---|---|---|
| `POST /estimates` | `createFareEstimate` | HTTP | `CreateFareEstimateHttpRequest { tenantId, bookingId?, origin, destination, vehicleType }` | `FareEstimateResult` |
| `GET /bookings/{bookingId}/estimates` | `listBookingFareEstimates` | HTTP | `ListBookingFareEstimatesHttpRequest { bookingId }` | `ListFareEstimatesResult` |

**`FareEstimateResult`**: `{ id, tenantId, bookingId, estimatedMin, estimatedMax, currency, tariffId, calculatedAt }`
**`ListFareEstimatesResult`**: `{ items: [FareEstimateResult] }`

### `TripReceiptController` — `/v1/pricing`

| Endpoint | Método | Tipo | Request | Result |
|---|---|---|---|---|
| `POST /trips/{id}/receipt` | `createTripReceipt` | HTTP | `CreateTripReceiptHttpRequest { tripId, paymentMethod, totalAmount, currency, driverNotes? }` | `ReceiptResult` |
| `GET /trips/{id}/receipt` | `getTripReceipt` | HTTP | `GetTripReceiptHttpRequest { tripId }` | `ReceiptResult` |
| `GET /receipts?tenantId=&from=&to=` | `listReceipts` | HTTP | `ListReceiptsHttpRequest { tenantId, from, to, cursor?, limit? }` | `ListReceiptsResult` |

**`ReceiptResult`**: `{ id, tripId, paymentMethod, totalAmount, currency, driverNotes, recordedAt }`
**`ListReceiptsResult`**: `{ items: [ReceiptItem{tripId, paymentMethod, totalAmount, recordedAt}], nextCursor }`

### `TariffController` — `/v1/pricing/tariffs`

| Endpoint | Método | Tipo | Request | Result |
|---|---|---|---|---|
| `POST /tariffs` | `createTariff` | HTTP | `CreateTariffHttpRequest { name, zoneId?, vehicleType?, baseFare, perKm, perMin, minFare, waitingPerMin?, nightSurchargePct?, airportSurcharge?, validFrom, validTo? }` | `TariffResult` |
| `GET /tariffs/{id}` | `getTariff` | HTTP | `GetTariffHttpRequest { id }` | `TariffResult` |
| `GET /tariffs?zoneId=&vehicleType=` | `listTariffs` | HTTP | `ListTariffsHttpRequest { zoneId?, vehicleType?, cursor?, limit? }` | `ListTariffsResult` |
| `PATCH /tariffs/{id}` | `updateTariff` | HTTP | `UpdateTariffHttpRequest { id, baseFare?, perKm?, perMin?, active? }` | `TariffResult` |
| `DELETE /tariffs/{id}` | `deleteTariff` | HTTP | `DeleteTariffHttpRequest { id }` | — *(204, sin contenido)* |

**`TariffResult`**: `{ id, name, zoneId, vehicleType, baseFare, perKm, perMin, minFare, waitingPerMin, nightSurchargePct, airportSurcharge, validFrom, validTo, active }`
**`ListTariffsResult`**: `{ items: [TariffResult], nextCursor }`

---

## MÓDULO `notification`

### `NotificationTemplateController` — `/v1/notifications/templates`

| Endpoint | Método | Tipo | Request | Result |
|---|---|---|---|---|
| `POST /templates` | `createTemplate` | HTTP | `CreateTemplateHttpRequest { code, channel, language, subject?, body }` | `TemplateResult` |
| `GET /templates?code=&channel=&language=` | `listTemplates` | HTTP | `ListTemplatesHttpRequest { code?, channel?, language? }` | `ListTemplatesResult` |
| `PATCH /templates/{id}` | `updateTemplate` | HTTP | `UpdateTemplateHttpRequest { id, body?, active? }` | `TemplateResult` |

**`TemplateResult`**: `{ id, code, channel, language, subject, body, version, active }`
**`ListTemplatesResult`**: `{ items: [TemplateResult] }`

### `NotificationPreferenceController` — `/v1/notifications/preferences`

| Endpoint | Método | Tipo | Request | Result |
|---|---|---|---|---|
| `GET /preferences?ownerType=&ownerId=` | `getPreferences` | HTTP | `GetPreferencesHttpRequest { ownerType, ownerId }` | `ListPreferencesResult` |
| `PUT /preferences` | `updatePreferences` | HTTP | `UpdatePreferencesHttpRequest { ownerType, ownerId, channel, enabled, quietFrom?, quietTo?, timezone }` | `PreferenceResult` |

**`PreferenceResult`**: `{ ownerType, ownerId, channel, enabled, quietFrom, quietTo, timezone }`
**`ListPreferencesResult`**: `{ items: [PreferenceResult] }`

---

## MÓDULO `support`

### `IncidentController` — `/v1/support/incidents`

| Endpoint | Método | Tipo | Request | Result |
|---|---|---|---|---|
| `POST /incidents` | `createIncident` | HTTP | `CreateIncidentHttpRequest { tripId?, type, priority?, reportedBy, reporterId?, title, description? }` | `IncidentResult` |
| `GET /incidents/{id}` | `getIncident` | HTTP | `GetIncidentHttpRequest { id }` | `IncidentResult` |
| `GET /incidents?status=&priority=&tripId=` | `listIncidents` | HTTP | `ListIncidentsHttpRequest { status?, priority?, tripId?, cursor?, limit? }` | `ListIncidentsResult` |
| `PATCH /incidents/{id}` | `updateIncident` | HTTP | `UpdateIncidentHttpRequest { id, description?, priority? }` | `IncidentResult` |
| `POST /incidents/{id}/assign` | `assignIncident` | HTTP | `AssignIncidentHttpRequest { id, assigneeId }` | `IncidentResult` |
| `POST /incidents/{id}/resolve` | `resolveIncident` | HTTP | `ResolveIncidentHttpRequest { id, resolution }` | `IncidentResult` |
| `POST /incidents/{id}/close` | `closeIncident` | HTTP | `CloseIncidentHttpRequest { id }` | `IncidentResult` |

**`IncidentResult`**: `{ id, tripId, type, status, priority, reportedBy, reporterId, assigneeId, title, description, resolution, createdAt, resolvedAt, updatedAt }`
**`ListIncidentsResult`**: `{ items: [IncidentItem{id, type, status, priority, createdAt}], nextCursor }`

### `LostItemController` — `/v1/support/lost-items`

| Endpoint | Método | Tipo | Request | Result |
|---|---|---|---|---|
| `POST /lost-items` | `createLostItem` | HTTP | `CreateLostItemHttpRequest { tripId, incidentId?, description, storageLocation?, contactPhone? }` | `LostItemResult` |
| `GET /lost-items/{id}` | `getLostItem` | HTTP | `GetLostItemHttpRequest { id }` | `LostItemResult` |
| `GET /lost-items?tripId=&status=` | `listLostItems` | HTTP | `ListLostItemsHttpRequest { tripId?, status?, cursor?, limit? }` | `ListLostItemsResult` |
| `PATCH /lost-items/{id}/status` | `updateLostItemStatus` | HTTP | `UpdateLostItemStatusHttpRequest { id, status }` | `LostItemResult` |

**`LostItemResult`**: `{ id, tripId, incidentId, description, status, storageLocation, contactPhone, foundAt, returnedAt, createdAt }`
**`ListLostItemsResult`**: `{ items: [LostItemItem{id, description, status, createdAt}], nextCursor }`

---

## MÓDULO `audit`

### `AuditLogController` — `/v1/audit/logs`

| Endpoint | Método | Tipo | Request | Result |
|---|---|---|---|---|
| `GET /logs?aggregateType=&aggregateId=&actorType=&from=&to=` | `listAuditLogs` | HTTP | `ListAuditLogsHttpRequest { aggregateType?, aggregateId?, actorType?, from?, to?, cursor?, limit? }` | `ListAuditLogsResult` |
| `GET /logs/{id}` | `getAuditLog` | HTTP | `GetAuditLogHttpRequest { id }` | `AuditLogResult` |

**`AuditLogResult`**: `{ id, occurredAt, aggregateType, aggregateId, action, actorType, actorId, source, eventId }`
**`ListAuditLogsResult`**: `{ items: [AuditLogResult], nextCursor }`
