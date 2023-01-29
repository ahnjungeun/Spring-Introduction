package hello.hellospring.controller;

public class MemberForm {
    private String name; // html의 name의 "name"이랑 매칭

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
