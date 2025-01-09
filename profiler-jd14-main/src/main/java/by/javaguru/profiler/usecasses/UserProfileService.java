package by.javaguru.profiler.usecasses;


import by.javaguru.profiler.usecasses.dto.UserProfileDto;
import by.javaguru.profiler.usecasses.dto.UserProfileResponseDto;

import java.util.Optional;

public interface UserProfileService {

    UserProfileResponseDto save(UserProfileDto userProfileDto);

    UserProfileResponseDto getUserProfile();

    Optional<UserProfileResponseDto> update(UserProfileDto userProfileDto);
}
