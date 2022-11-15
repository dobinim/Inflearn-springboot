package hello.hellospring.controller;

public class MemberForm {

    private String name;
    // createMemberForm.html에서 id = name 인 애랑 매칭되도록 같은 이름으로 선언

    // getter, setter -> alt + insert
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
