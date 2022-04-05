package kopo.poly.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller

public class CloginController {

    @GetMapping(value = "COMGlogin")
    public String ComgLogin(){

        return "/COMGlogin/COMGlogin";
    }

    @GetMapping(value = "COMG/PwSearch")
    public String PwSearch(){

        return "/COMGlogin/COMGpwSearch";
    }

    @GetMapping(value = "COMG/IdSearch")
    public String IdSearch(){

        return "/COMGlogin/COMGidSearch";
    }
}
