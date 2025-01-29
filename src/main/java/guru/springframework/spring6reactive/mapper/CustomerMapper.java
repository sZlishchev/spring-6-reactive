package guru.springframework.spring6reactive.mapper;

import guru.springframework.spring6reactive.domain.Customer;
import guru.springframework.spring6reactive.model.CustomerDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;


@Mapper
public interface CustomerMapper {

    CustomerDTO toDto(final Customer customer);

    Customer toDomain(final CustomerDTO customerDTO);

    @BeanMapping(ignoreByDefault = true, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "userName", source = "userName")
    Customer merge(@MappingTarget final Customer customer, final CustomerDTO customerDTO);

    @BeanMapping(ignoreByDefault = true, nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
    @Mapping(target = "userName", source = "userName")
    Customer update(@MappingTarget final Customer customer, final CustomerDTO customerDTO);
}
