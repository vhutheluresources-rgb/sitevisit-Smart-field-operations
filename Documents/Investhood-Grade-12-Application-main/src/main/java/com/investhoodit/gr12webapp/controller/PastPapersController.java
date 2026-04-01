package com.investhoodit.gr12webapp.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pastpapers")
public class PastPapersController {

	@GetMapping("/Accounting")
	public String getMathPapers() {
		return "htmlSubjects/Accounting";
	}

	@GetMapping("/Afrikaans")
	public String getMathLitPapers() {
		return "htmlSubjects/Afrikaans";
	}

	@GetMapping("/Agricultural Science")
	public String getAccPapers() {
		return "htmlSubjects/AgriculturalScience";
	}

	@GetMapping("/Business Studies")
	public String getAfirkaansPapers() {
		return "htmlSubjects/BusinessStudies";
	}

	@GetMapping("/Computer Applications Technology")
	public String getAgricPapers() {
		return "htmlSubjects/ComputerAppTech";
	}

	@GetMapping("/Economics")
	public String getBusinessPapers() {
		return "htmlSubjects/Economics";
	}

	@GetMapping("/English")
	public String getCatPapers() {
		return "htmlSubjects/English";
	}


	@GetMapping("/Geography")
	public String getEconomicsPapers() {
		return "htmlSubjects/Geography";
	}

	@GetMapping("/History")
	public String getHistoryPapers() {
		return "htmlSubjects/History";
	}

	@GetMapping("/IsiNdebele")
	public String getGeographyPapers() {
		return "htmlSubjects/IsiNdebele";
	}

	@GetMapping("/IsiXhosa")
	public String getEnglishPapers() {
		return "htmlSubjects/IsiXhosa";
	}
	

	@GetMapping("/IsiZulu")
	public String getNdebelePapers() {
		return "htmlSubjects/IsiZulu";
	}

	@GetMapping("/Life Orientation")
	public String getZuluPapers() {
		return "htmlSubjects/LifeOrientation";
	}

	@GetMapping("/Life Sciences")
	public String getLifeOrientationPapers() {
		return "htmlSubjects/LifeSciences";
	}

	@GetMapping("/Mathematics")
	public String getLifeSciencePapers() {
		return "htmlSubjects/Mathematics";
	}

	@GetMapping("/Mathematical Literacy")
	public String getSetswanaPapers() {
		return "htmlSubjects/MathematicalLiteracy";
	}

	@GetMapping("/Physical Sciences")
	public String getSesothoPapers() {
		return "htmlSubjects/PhysicalSciences";
	}

	@GetMapping("/Sepedi")
	public String getPhysicsPapers() {
		return "htmlSubjects/Sepedi";
	}

	@GetMapping("/Sesotho")
	public String getSepediPapers() {
		return "htmlSubjects/Sesotho";
	}
	

	@GetMapping("/Setswana")
	public String getSiswatiPapers() {
		return "htmlSubjects/Setswana";
	}

	@GetMapping("/Siswati")
	public String getTechnicalMathPapers() {
		return "htmlSubjects/Siswati";
	}

	@GetMapping("/Technical Mathematics")
	public String getTourismPapers() {
		return "htmlSubjects/TechnicalMathematics";
	}

	@GetMapping("/Tourism")
	public String getTshivendaPapers() {
		return "htmlSubjects/Tourism";
	}
	
	@GetMapping("/Tshivenda")
	public String getXitsongaPapers() {
		return "htmlSubjects/Tshivenda";
	}
	
	@GetMapping("/Xitsonga")
	public String getXhosaPapers() {
		return "htmlSubjects/Xitsonga";
	}
	
	@GetMapping("/pastpapers/{subject}")
	public String getPastPapers(@PathVariable String subject, Model model) {
	    model.addAttribute("subject", subject);
	    return "htmlSubjects/" + subject;
	}


	// Fix for PDF Viewing
    @GetMapping("/view/{filename}")
    public ResponseEntity<Resource> viewPdf(@PathVariable String filename) {
        try {
            // Load the file from resources/pastpapers/
            Resource resource = new ClassPathResource("pastpapers/" + filename);

            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

}
