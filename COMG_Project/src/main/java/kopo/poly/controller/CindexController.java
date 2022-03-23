package kopo.poly.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class CindexController {

    @GetMapping(value = "COMG")
    public String ComgIndex() {

        return "/COMGindex/COMGindex";
    }


}
