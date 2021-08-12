package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @GetMapping("hello")
    public String hello(Model model){
        model.addAttribute("data","hello!!");
        return "hello";
    }

    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam("name") String name,Model model){
        model.addAttribute("name",name);
        return "hello-template";
    }

    @GetMapping("hello-string")
    @ResponseBody   //바디부분에 내용을 직접 넣어주겠다.
    public String helloString(@RequestParam("name") String name){
        return "hello " + name; //View가 없이 이 내용이 그대로 출력됨
    }

    @GetMapping("hello-api")
    @ResponseBody
    public Hello HelloApi(@RequestParam("name") String name){
        Hello hello = new Hello();
        hello.setName(name);
        return hello;   //객체타입 Json 형태로 출력
    }

    static class Hello{
        private String name;
        //alt + insert 통해 자동생성
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}



