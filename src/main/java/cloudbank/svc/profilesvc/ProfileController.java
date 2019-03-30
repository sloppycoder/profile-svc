package cloudbank.svc.profilesvc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileController {
    @Value("${svc.message}")
    String message;

    @RequestMapping("/")
    String showMessage() {
        return message;
    }
}
