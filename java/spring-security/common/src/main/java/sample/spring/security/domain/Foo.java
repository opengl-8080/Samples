package sample.spring.security.domain;

public class Foo {
    private long id;

    public Foo(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Foo{" +
                "id=" + id +
                '}';
    }
}
