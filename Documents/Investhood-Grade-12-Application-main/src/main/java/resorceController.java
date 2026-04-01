import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class resorceController {

	@GetMapping("/resources")
    public String resourcesPage() {
        return "resources"; // Loads resources.html from the templates folder
    }

    @GetMapping("/resources/studyMaterials")
    public String studyMaterialsPage() {
        return "studyMaterials"; // Loads study-materials.html from the templates folder
    }

}
