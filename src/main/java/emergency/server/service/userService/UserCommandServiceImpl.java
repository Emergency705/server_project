package emergency.server.service.userService;

import emergency.server.convertor.UserConvertor;
import emergency.server.domain.Region;
import emergency.server.domain.User;
import emergency.server.dtos.UserRequestDto;
import emergency.server.repository.RegionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import emergency.server.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserCommandServiceImpl implements UserCommandService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RegionRepository regionRepository;


    @Override
    @Transactional
    public User joinUser(UserRequestDto.JoinDto request) {
        Region region = regionRepository.findById(request.getRegionId())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 지역입니다."));


        User newUser = UserConvertor.toUser(request, region);

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        newUser.encodePassword(encodedPassword);

        return userRepository.save(newUser);
    }
}



