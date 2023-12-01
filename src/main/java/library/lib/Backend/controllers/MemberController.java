package library.lib.Backend.controllers;

import com.google.gson.JsonObject;
import library.lib.Backend.models.Member;
import library.lib.Backend.models.ReturnModel;
import library.lib.Backend.services.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path="/api/users")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @RequestMapping(value="/register", method=RequestMethod.POST)
    public ResponseEntity<String> register(@RequestParam String username, @RequestParam String password, @RequestParam String email) {
        System.out.println("Registering user");
        System.out.println(username+" "+ password+" "+ email);
        ReturnModel response = memberService.register(username, password, email);

        return  ResponseEntity.status(response.code).body(response.object == null ? response.message : response.object.toJson());
    }

    @RequestMapping(value="/login", method=RequestMethod.POST)
    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String password) {

        ReturnModel member = memberService.login(email, password);

        return ResponseEntity.status(member.code).body(member.object == null ? member.message : member.object.toJson());
    }
}
