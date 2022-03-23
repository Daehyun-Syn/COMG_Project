package kopo.poly.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class CjoinController {

    @GetMapping(value="COMGjoin")
    public String ComgJoin() {

        return "/COMGjoin/COMGjoin";
    }

}
