package com.taxai.api.fleet.infrastructure.adapter.in.web;

public class OperatorController {
}

/**
 * @RequiredArgsConstructor
 * @RestController
 * @RequestMapping("/api/v1/admin/users")
 * @Tag(name = "Admin User Management", description = "API for administrators: manage users.")
 * public class AdminUserController {
 *
 *     private final AdminUserWebMapper mapper;
 *
 *     private final AdminForceResetUserUseCase adminForceResetUserUseCase;
 *
 *     @PostMapping("/force-reset/{userId}")
 *     @Operation(
 *             summary = "Force password reset",
 *             description = "Forces an immediate password reset for the specified user and deletes all active sessions. The user will be required to set a new password upon their next login."
 *     )
 *     public ResponseEntity<TenantInfoResult> forceResetUserPassword(
 *             @Validate @RequestBody RegisterUserHttpRequest register
 *     ) {
 *         adminForceResetUserUseCase.execute(mapper.toAdminForceResetUserCommand(userId));
 *         return null;
 *     }
 * }
 */