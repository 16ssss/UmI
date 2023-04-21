package YOUmI.domain.Authentication.controller;

import YOUmI.common.exception.BadRequestException;
import YOUmI.domain.Authentication.model.dto.AuthDto;
import YOUmI.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final SecurityUtil securityUtil;

    @PostMapping("/token")
    public String getAccessToken(@RequestBody AuthDto credential) {

        String token = "";

        if(credential != null && StringUtils.isNoneBlank(credential.getId(),credential.getPassword())) {
            token = securityUtil.createToken(credential);
        } else {
            throw new BadRequestException("credential을 확인해주세요.");
        }

        return token;

    }

}
