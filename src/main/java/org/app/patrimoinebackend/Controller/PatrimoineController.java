package org.app.patrimoinebackend.Controller;

import org.app.patrimoinebackend.Model.Patrimoine;
import org.app.patrimoinebackend.Service.PatrimoineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@CrossOrigin
@RestController
@RequestMapping("/")
public class PatrimoineController {

    @Autowired
    private  PatrimoineService patrimoineService;

    @GetMapping("/Patrimoine/{id}")
    public ResponseEntity<Patrimoine> getAllPatrimoine(@PathVariable int id) throws Exception{
        try {
            Patrimoine patrimoines = patrimoineService.getPatrimoine(id);
            if(patrimoines != null) {
                return ResponseEntity.ok(patrimoines);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
         return  ResponseEntity.notFound().build();
    }

    @PutMapping("/Patrimoine/{id}")
    public ResponseEntity<Patrimoine> updateOrCreatePatrimoine(@PathVariable int id, @RequestBody Patrimoine patrimoine) {
        try {
            Patrimoine updatedPatrimoine = patrimoineService.updatePatrimoine(id, patrimoine);
            HttpStatus status = (id < patrimoineService.readPatrimoinesFromFile().size()) ? HttpStatus.OK : HttpStatus.CREATED;
            return new ResponseEntity<>(updatedPatrimoine, status);
        } catch (IOException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public String HomePage() throws Exception{
        return "Bienvenue sur mon patrimoine !";
    }
}
