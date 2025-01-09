package by.javaguru.profiler.usecasses.mapper;

import by.javaguru.profiler.persistence.model.Country;
import by.javaguru.profiler.persistence.model.PhoneCode;
import by.javaguru.profiler.usecasses.dto.CountryDto;
import by.javaguru.profiler.usecasses.dto.PhoneCodeDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Objects;

import static by.javaguru.profiler.util.PhoneCodeTestData.createPhoneCode;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class PhoneCodeMapperTest {
    @InjectMocks
    private PhoneCodeMapperImpl phoneCodeMapper;

    @Mock(answer = Answers.CALLS_REAL_METHODS)
    private CountryMapperImpl countryMapper;

    @Test
    void shouldMapCorrectlyAllFieldWhenInvokeFromEntityToDto() {
        List<PhoneCode> phoneCodeList = List.of(createPhoneCode());
        List<PhoneCodeDto> phoneCodeDtoList = phoneCodeMapper.toDto(phoneCodeList);

        assertEquals(phoneCodeList.size(), phoneCodeDtoList.size());
        assertThat(phoneCodeDtoList, hasSize(1));
        assertEquals(phoneCodeList.get(0).getId(), phoneCodeDtoList.get(0).id());
        assertEquals(phoneCodeList.get(0).getCode(), phoneCodeDtoList.get(0).code());
        assertTrue(isTheSameCountry(phoneCodeList.get(0).getCountry(), phoneCodeDtoList.get(0).country()));
    }

    private boolean isTheSameCountry(Country country, CountryDto dto) {
        return Objects.equals(country.getId(), dto.id()) && Objects.equals(country.getCountryName(), dto.countryName());
    }
}