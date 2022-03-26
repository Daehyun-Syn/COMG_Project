package kopo.poly.controller;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class CmainController {

    @GetMapping(value = "COMGmain")
    public String ComgMain(){


        return "/COMGmain/COMGmain";
    }
}
