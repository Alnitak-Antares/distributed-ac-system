package app.controller;

import java.util.concurrent.atomic.AtomicLong;

import app.dto.Greeting;
import org.springframework.web.bind.annotation.*;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @PostMapping("/greeting1")
    public Greeting greeting1(@RequestParam(value="name", defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(),
             String.format(template, name));
    }

    @GetMapping("/greeting2")
    public Greeting greeting2(@RequestParam(value="name", defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(),
                String.format(template, name));
    }

}