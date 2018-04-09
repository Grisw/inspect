package pers.sdulxt.inspect.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.sdulxt.inspect.model.Response;
import pers.sdulxt.inspect.service.ContactService;
import pers.sdulxt.inspect.service.TokenService;
import pers.sdulxt.inspect.util.ValidateUtils;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/contact")
public class ContactController {

    private final ContactService contactService;
    private final TokenService tokenService;

    @Autowired
    public ContactController(ContactService contactService, TokenService tokenService) {
        this.contactService = contactService;
        this.tokenService = tokenService;
    }

    @GetMapping
    public Response<List<Map<String, String>>> getContacts(@RequestHeader("X-INSPECT-PN") String pn, @RequestHeader("X-INSPECT-TOKEN") String token){
        // Validating
        if(ValidateUtils.checkNull(pn, token)){
            return new Response<>(Response.Code.TOKEN_EXPIRED);
        }

        // Check token
        if(tokenService.checkValidity(pn, token))
            return new Response<>(contactService.getContacts(pn));
        else
            return new Response<>(Response.Code.TOKEN_EXPIRED);
    }
}
