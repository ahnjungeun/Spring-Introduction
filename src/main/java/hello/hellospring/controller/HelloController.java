package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @GetMapping("hello")
    public String hello(Model model) {
        model.addAttribute("data", "JJeong!!");
        return "hello";
    }

    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam("key") String name, Model model) {
        model.addAttribute("key", name);
        return "hello-template";
    }

    @GetMapping("hello-string")
    @ResponseBody
    public String helloString(@RequestParam("str") String name) {
        return "안녕하세요, " + name;
    }

    @GetMapping("hello-api")
    @ResponseBody
    public Hello HelloApi(@RequestParam("name") String username) {
        Hello hello = new Hello();
        hello.setName(username);
        return hello;
    }

    static class Hello {
        private String name;

        // generate로 간편하게 생성! (alt + insert)
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
