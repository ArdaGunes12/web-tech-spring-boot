package de.htwberlin.webtech.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
public class MessageController {

    @GetMapping("/api/message")
    public Map<String, String> getMessage() {
        return Map.of("message", "Hallo vom Backend 🎉");
    }

}
