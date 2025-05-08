package com.switchfully.apps.betterparkshark.service.mapper;

import com.switchfully.apps.betterparkshark.domain.Address;
import com.switchfully.apps.betterparkshark.webapi.dto.AddressDtoInput;
import com.switchfully.apps.betterparkshark.webapi.dto.AddressDtoOutput;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {

    public Address inputToAddress(AddressDtoInput addressDtoInput) {
        return new Address(addressDtoInput.getStreet(),
                addressDtoInput.getStreetNumber(),
                addressDtoInput.getPostalCode(),
                addressDtoInput.getCity(),
                addressDtoInput.getCountry());
    }

    public AddressDtoOutput addressToOutput(Address address) {
        return new AddressDtoOutput(address.getId(),
                address.getStreet(),
                address.getStreetNumber(),
                address.getPostalCode(),
                address.getCity(),
                address.getCountry());
    }
}
