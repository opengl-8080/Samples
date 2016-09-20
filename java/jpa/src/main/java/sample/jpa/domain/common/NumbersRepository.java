package sample.jpa.domain.common;

public interface NumbersRepository {
    Numbers findByTypeWithLock(NumbersType type);
}
