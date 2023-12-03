package library.lib.backend.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import library.lib.backend.models.ReturnModel;
import library.lib.backend.services.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(path="/api/users")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping(value="/register")
    public ResponseEntity<String> register(@RequestParam String username, @RequestParam String password, @RequestParam String email) {
        try {
            log.info("Registering user");
            log.info(username+" "+ password+" "+ email);
            ReturnModel response = memberService.register(username, password, email);
            return  ResponseEntity.status(response.code).body(response.object == null ? response.message : response.object.toJson());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping(value="/login")
    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String password) {

        ReturnModel member = memberService.login(email, password);

        try {
            return ResponseEntity.status(member.code).body(member.object == null ? member.message : member.object.toJson());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
