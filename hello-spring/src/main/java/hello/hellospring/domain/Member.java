package hello.hellospring.domain;

public class Member {

    private Long id; // 시스템이 데이터를 구분하기 위해 저장하는 임의의 값. 사용자 id 아님
    private String name;

    
    // getter, setter 만들려면 -> alt + insert 눌러서 getter and setter 선택
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
