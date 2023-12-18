package com.zugverwaltung.Zugverwaltung.Controller;

import com.zugverwaltung.Zugverwaltung.entity.info;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.zugverwaltung.Zugverwaltung.reporstory.VerwalterReository;

import java.util.List;

@RestController
@RequestMapping("/info")
public class VerwalterController {

    private VerwalterReository verwalterReository;

    public VerwalterController(VerwalterReository verwalterReository)
    {
        this.verwalterReository = verwalterReository;
    }
    
    @GetMapping("")
    public List<info> index() {
        return verwalterReository.findAll();

    }
}
