package vn.edu.hcmut.cse.adsoftweng.lab.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ApplicationController {
    @GetMapping
    public String index(Model model) {
        return "redirect:/students";
    }
}
