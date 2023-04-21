package YOUmI.domain.User.controller;

import YOUmI.common.exception.BadRequestException;
import YOUmI.domain.Authentication.model.dto.AuthDto;
import YOUmI.domain.User.repository.CustomerRepository;
import YOUmI.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
public class CustomerController {

    private final SecurityUtil securityUtil;

    @Autowired
    private CustomerRepository customerRepository;

//    @GetMapping("/test/{username}")
//    @ResponseBody
//    public Map testHandler(@PathVariable String username){
//
//        String jwt = securityUtil.createToken(username);
//
//        log.error(jwt);
//        Map<String,Object> map = new HashMap<>();
//        map.put("abc","tees");
//        //response.setHeader(HttpHeaders.AUTHORIZATION, "Bearer "+jwt);
//        return map;
////        log.error(username);
////        Customer customer = customerRepository.findByUsername(username);
////        log.error(customer.toString());
////        return ResponseEntity.ok().body(Response.builder().result(SUCCESS.SUCCESS).resultObject(customer).build());
//
//    }

    @PostMapping("/login")
    public String login(@RequestBody AuthDto credential) {

        String token = "";

        //TODO
        //로그인 작업 필요

        if(credential != null && StringUtils.isNoneBlank(credential.getId(),credential.getPassword())) {
            token = securityUtil.createToken(credential);
        } else {
            throw new BadRequestException("credential을 확인해주세요.");
        }

        return token;

    }

}
