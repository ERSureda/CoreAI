package com.taxai.api.fleet.infrastructure.adapter.in.web.mapper;

public class DriverWebMapper {
}

/**
 * @Mapper(
 *         componentModel = MappingConstants.ComponentModel.SPRING,
 *         unmappedTargetPolicy = ReportingPolicy.IGNORE
 * )
 * public interface ProfileWebMapper {
 *
 *     UpdateProfileCommand toUpdateProfileCommand(UpdateProfileHttpRequest request); // NO ESTA
 *     CreateAddressCommand toCreateAddressCommand(CreateAddressHttpRequest request); // NO ESTA
 *
 *     @Mapping(target = "addressId", source = "addressId")
 *     UpdateAddressCommand toUpdateAddressCommand(UUID addressId, UpdateAddressHttpRequest request);
 *
 *     DeleteAddressCommand toDeleteAddressCommand(UUID addressId);
 * }
 */