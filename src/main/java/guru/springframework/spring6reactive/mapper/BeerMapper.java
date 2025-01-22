package guru.springframework.spring6reactive.mapper;

import guru.springframework.spring6reactive.domain.Beer;
import guru.springframework.spring6reactive.model.BeerDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

import static org.mapstruct.NullValueMappingStrategy.RETURN_DEFAULT;

@Mapper
public interface BeerMapper {

    BeerDTO toDTO(final Beer beer);

    Beer toDomain(final BeerDTO beerDTO);

    @BeanMapping(ignoreByDefault = true, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "beerName", source = "beerName")
    @Mapping(target = "beerStyle", source = "beerStyle")
    @Mapping(target = "upc", source = "upc")
    @Mapping(target = "quantityOnHand", source = "quantityOnHand")
    @Mapping(target = " price", source = "price")
    Beer merge(@MappingTarget Beer beer, final BeerDTO beerDTO);
}
