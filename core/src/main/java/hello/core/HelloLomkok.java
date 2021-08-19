package hello.core;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class HelloLomkok {

    private String name;
    private int age;

    public static void main(String[] args) {
        HelloLomkok helloLomkok = new HelloLomkok();
        helloLomkok.setName("asd");
        helloLomkok.setAge(5);

        System.out.println("helloLombok = " + helloLomkok);
    }
}
