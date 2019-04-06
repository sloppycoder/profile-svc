package cloudbank.svc.profilesvc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ProfileController {
    @Value("${svc.message}")
    String message;

    @RequestMapping("/")
    Map<String, String> showMessage() {
        HashMap<String, String> result = new HashMap<>();
        result.put("message", "hello");
        result.put("pod", ManagementFactory.getRuntimeMXBean().getName());
        return result;
    }
}