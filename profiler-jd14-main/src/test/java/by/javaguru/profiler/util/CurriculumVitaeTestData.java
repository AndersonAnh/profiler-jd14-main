package by.javaguru.profiler.util;

import by.javaguru.profiler.persistence.model.Country;
import by.javaguru.profiler.persistence.model.CurriculumVitae;
import by.javaguru.profiler.persistence.model.CvStatus;
import by.javaguru.profiler.persistence.model.Image;
import by.javaguru.profiler.persistence.model.Position;
import by.javaguru.profiler.persistence.model.User;
import by.javaguru.profiler.usecasses.dto.CurriculumVitaeRequestDto;
import by.javaguru.profiler.usecasses.dto.CurriculumVitaeResponseDto;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Stream;

public class CurriculumVitaeTestData {

    public static final String CVS_URL_TEMPLATE = "/api/v1/cvs";
    public static final String CVS_UUID_URL_TEMPLATE = "/api/v1/cvs/{uuid}";
    public static final String CV_UUID = "0a5a28ca-e960-420c-af53-50e6f6e80bf2";
    public static final String CV_UUID_FROM_DB = "123e4567-e89b-12d3-a456-426614174001";
    public static final String IMAGE_UUID = "95f5f3cf-ac52-4638-aad1-12d1e836fdd1";
    private static final String NAME = "Name";
    private static final String SURNAME = "Surname";
    public static final long POSITION_ID = 1L;
    private static final String POSITION_NAME = "Java developer";
    public static final long COUNTRY_ID = 1L;
    private static final String COUNTRY_NAME = "Afghanistan";
    private static final String CITY = "City";
    private static final boolean IS_READY_TO_RELOCATE = true;
    private static final boolean IS_READY_FOR_REMOTE_WORK = true;
    private static final Map<Long, String> POSITIONS = Collections.singletonMap(POSITION_ID, POSITION_NAME);
    private static final Map<Long, String> COUNTRIES = Collections.singletonMap(COUNTRY_ID, COUNTRY_NAME);
    private static final boolean IS_CONTACTS_EXISTS = false;
    private static final boolean IS_ABOUT_EXISTS = false;
    private static final boolean IS_COMPETENCES_EXISTS = false;
    private static final boolean IS_EXPERIENCE_EXISTS = false;
    private static final boolean IS_EDUCATION_EXISTS = false;
    private static final boolean IS_ADDITIONAL_INFORMATION_EXISTS = false;
    private static final boolean IS_RECOMMENDATIONS_EXISTS = false;
    private static final String STATUS = CvStatus.DRAFT.name();
    private static final String USER_MAIL_COM = "user@mail.com";
    private static final long CV_ID = 1L;

    public static CurriculumVitaeRequestDto.CurriculumVitaeRequestDtoBuilder getValidCvRequestDto() {
        return CurriculumVitaeRequestDto.builder()
                .withImageUuid(IMAGE_UUID)
                .withName(NAME)
                .withSurname(SURNAME)
                .withPositionId(POSITION_ID)
                .withCountryId(COUNTRY_ID)
                .withCity(CITY)
                .withIsReadyToRelocate(IS_READY_TO_RELOCATE)
                .withIsReadyForRemoteWork(IS_READY_FOR_REMOTE_WORK);
    }

    public static CurriculumVitaeResponseDto getCvResponseDtoForExistingUser() {
        return CurriculumVitaeResponseDto.builder()
                .withUuid(CV_UUID_FROM_DB)
                .withImageUuid(null)
                .withName("testName2")
                .withSurname("testSurname2")
                .withPositionId(1L)
                .withPosition(POSITIONS.get(1L))
                .withCountryId(1L)
                .withCountry(COUNTRIES.get(1L))
                .withCity("Gomel")
                .withIsReadyToRelocate(true)
                .withIsReadyForRemoteWork(true)
                .withIsContactsExists(false)
                .withIsAboutExists(false)
                .withIsCompetencesExists(false)
                .withIsExperienceExists(false)
                .withIsEducationsExists(false)
                .withIsAdditionalInformationExists(false)
                .withIsRecommendationsExists(false)
                .withStatus(STATUS)
                .build();
    }

    public static CurriculumVitaeResponseDto.CurriculumVitaeResponseDtoBuilder getCvResponseDtoByCvRequestDto(CurriculumVitaeRequestDto curriculumVitaeRequestDto) {
        return CurriculumVitaeResponseDto.builder()
                .withUuid(CV_UUID)
                .withImageUuid(curriculumVitaeRequestDto.imageUuid())
                .withName(curriculumVitaeRequestDto.name())
                .withSurname(curriculumVitaeRequestDto.surname())
                .withPositionId(curriculumVitaeRequestDto.positionId())
                .withPosition(POSITIONS.get(curriculumVitaeRequestDto.positionId()))
                .withCountryId(curriculumVitaeRequestDto.countryId())
                .withCountry(COUNTRIES.get(curriculumVitaeRequestDto.countryId()))
                .withCity(curriculumVitaeRequestDto.city())
                .withIsReadyToRelocate(curriculumVitaeRequestDto.isReadyToRelocate())
                .withIsReadyForRemoteWork(curriculumVitaeRequestDto.isReadyForRemoteWork())
                .withIsContactsExists(IS_CONTACTS_EXISTS)
                .withIsAboutExists(IS_ABOUT_EXISTS)
                .withIsCompetencesExists(IS_COMPETENCES_EXISTS)
                .withIsExperienceExists(IS_EXPERIENCE_EXISTS)
                .withIsEducationsExists(IS_EDUCATION_EXISTS)
                .withIsAdditionalInformationExists(IS_ADDITIONAL_INFORMATION_EXISTS)
                .withIsRecommendationsExists(IS_RECOMMENDATIONS_EXISTS)
                .withStatus(STATUS);
    }

    public static CurriculumVitaeResponseDto.CurriculumVitaeResponseDtoBuilder getCvResponseDtoByCurriculumVitae(CurriculumVitae curriculumVitae) {
        return CurriculumVitaeResponseDto.builder()
                .withUuid(CV_UUID)
                .withImageUuid(curriculumVitae.getImage().getUuid())
                .withName(curriculumVitae.getName())
                .withSurname(curriculumVitae.getSurname())
                .withPositionId(curriculumVitae.getPosition().getId())
                .withPosition(curriculumVitae.getPosition().getName())
                .withCountryId(curriculumVitae.getCountry().getId())
                .withCountry(curriculumVitae.getCountry().getCountryName())
                .withCity(curriculumVitae.getCity())
                .withIsReadyToRelocate(curriculumVitae.getIsReadyToRelocate())
                .withIsReadyForRemoteWork(curriculumVitae.getIsReadyForRemoteWork())
                .withIsContactsExists(curriculumVitae.getContacts() != null)
                .withIsAboutExists(curriculumVitae.getAbout() != null)
                .withStatus(curriculumVitae.getStatus().toString());
    }

    public static CurriculumVitae getCvByCvRequestDto(CurriculumVitaeRequestDto curriculumVitaeRequestDto) {
        CurriculumVitae curriculumVitae = new CurriculumVitae();
        curriculumVitae.setId(CV_ID);
        curriculumVitae.setUuid(CV_UUID);
        curriculumVitae.setUser(getUser());
        curriculumVitae.setImage(getImageByImageUuid(curriculumVitaeRequestDto.imageUuid()));
        curriculumVitae.setName(curriculumVitaeRequestDto.name());
        curriculumVitae.setSurname(curriculumVitaeRequestDto.surname());
        curriculumVitae.setPosition(getPositionByPositionId(curriculumVitaeRequestDto.positionId()));
        curriculumVitae.setCountry(getCountryByCountyId(curriculumVitaeRequestDto.countryId()));
        curriculumVitae.setCity(curriculumVitaeRequestDto.city());
        curriculumVitae.setIsReadyToRelocate(curriculumVitaeRequestDto.isReadyToRelocate());
        curriculumVitae.setIsReadyForRemoteWork(curriculumVitaeRequestDto.isReadyForRemoteWork());
        curriculumVitae.setStatus(getCvStatusByName());
        return curriculumVitae;
    }

    public static CurriculumVitae getValidCurriculumVitae() {
        CurriculumVitae curriculumVitae = new CurriculumVitae();
        curriculumVitae.setId(CV_ID);
        curriculumVitae.setUuid(CV_UUID);
        curriculumVitae.setUser(getUser());
        curriculumVitae.setImage(getImageByImageUuid(IMAGE_UUID));
        curriculumVitae.setName(NAME);
        curriculumVitae.setSurname(SURNAME);
        curriculumVitae.setPosition(getPositionByPositionId(POSITION_ID));
        curriculumVitae.setCountry(getCountryByCountyId(COUNTRY_ID));
        curriculumVitae.setCity(CITY);
        curriculumVitae.setIsReadyToRelocate(IS_READY_TO_RELOCATE);
        curriculumVitae.setIsReadyForRemoteWork(IS_READY_FOR_REMOTE_WORK);
        curriculumVitae.setStatus(getCvStatusByName());
        return curriculumVitae;
    }

    public static List<CurriculumVitae> getListOfCvsOfUser(int listSize) {
        return Stream
                .generate(CurriculumVitaeTestData::getCvWithRandomFields)
                .limit(listSize)
                .toList();
    }

    public static List<CurriculumVitaeResponseDto> getListOfCvResponseDtoFromCvList(List<CurriculumVitae> curriculumVitaeList) {
        return curriculumVitaeList.stream()
                .map(curriculumVitae -> getCvResponseDtoByCurriculumVitae(curriculumVitae).build())
                .toList();
    }

    public static Image getImageByImageUuid(String uuid) {
        Image image = new Image();
        image.setUuid(uuid);
        image.setUser(getUser());
        return image;
    }

    public static User getUser() {
        User user = new User();
        user.setEmail(USER_MAIL_COM);
        return user;
    }

    public static Position getPositionByPositionId(Long id) {
        Position position = new Position();
        position.setId(id);
        position.setName(POSITIONS.get(id));
        return position;
    }

    public static Country getCountryByCountyId(Long id) {
        Country country = new Country();
        country.setId(id);
        country.setCountryName(COUNTRIES.get(id));
        return country;
    }

    private static CvStatus getCvStatusByName() {
        return CvStatus.valueOf(CurriculumVitaeTestData.STATUS);
    }

    private static CurriculumVitae getCvWithRandomFields() {
        CurriculumVitae curriculumVitae = new CurriculumVitae();
        curriculumVitae.setId(new Random().nextLong(1, 50));
        curriculumVitae.setUuid(UUID.randomUUID().toString());
        curriculumVitae.setUser(getUser());
        curriculumVitae.setImage(getImageByImageUuid(UUID.randomUUID().toString()));
        curriculumVitae.setName(getRandomAlphabeticString());
        curriculumVitae.setSurname(getRandomAlphabeticString());
        curriculumVitae.setPosition(getPositionByPositionId(POSITION_ID));
        curriculumVitae.setCountry(getCountryByCountyId(COUNTRY_ID));
        curriculumVitae.setCity(getRandomAlphabeticString());
        curriculumVitae.setIsReadyToRelocate(IS_READY_TO_RELOCATE);
        curriculumVitae.setIsReadyForRemoteWork(IS_READY_FOR_REMOTE_WORK);
        curriculumVitae.setStatus(getCvStatusByName());
        return curriculumVitae;
    }

    private static String getRandomAlphabeticString() {
        int leftLimit = 97;
        int rightLimit = 123;
        int maxSize = 10;
        return new Random().ints(leftLimit, rightLimit)
                .limit(maxSize)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
