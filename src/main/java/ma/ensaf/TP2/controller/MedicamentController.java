package ma.ensaf.TP2.controller;



import ma.ensaf.TP2.repository.MedicamentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MedicamentController {

    private final MedicamentRepository medicamentRepository;

    public MedicamentController(
            MedicamentRepository medicamentRepository) {
        this.medicamentRepository = medicamentRepository;
    }

    @GetMapping("/medicaments")
    public String listeMedicaments(Model model) {

        model.addAttribute(
                "medicaments",
                medicamentRepository.findAll()
        );

        return "medicaments";
    }
}