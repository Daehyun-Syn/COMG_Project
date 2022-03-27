package kopo.poly.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class CassignmentController {

    @GetMapping(value = "COMGassignment")
    public String assignment() {

        return "COMGassignment/Status";
    }

}
