package sample.findbugs;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

@Immutable
public class Main {

    private String name;

    @Nonnull
    public String method() {
        return null;
    }
}
