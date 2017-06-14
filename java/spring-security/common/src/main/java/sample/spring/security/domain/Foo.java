package sample.spring.security.domain;

public class Foo {
    private final long id;

    public Foo(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Foo{id=" + id + '}';
    }
}
