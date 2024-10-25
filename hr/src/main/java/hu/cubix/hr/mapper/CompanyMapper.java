package hu.cubix.hr.mapper;

import hu.cubix.hr.dto.CompanyDto;
import hu.cubix.hr.model.Company;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CompanyMapper {
    CompanyDto companyToDto(Company company);

    List<CompanyDto> companiesToDtos(List<Company> company);

    Company dtoToCompany(CompanyDto companyDto);

    List<Company> dtosToCompanies(List<CompanyDto> companyDtos);

    @IterableMapping(qualifiedByName = "WithoutEmployees")
    List<CompanyDto> companiesToDtosWithoutEmployees(List<Company> companies);

    @Mapping(target = "employees", ignore = true)
    @Named("WithoutEmployees")
    CompanyDto companyToDtoWithoutEmployees(Company company);

}
