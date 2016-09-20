package sample.jpa.infrastructure.common;

import sample.jpa.domain.common.Numbers;
import sample.jpa.domain.common.NumbersRepository;
import sample.jpa.domain.common.NumbersType;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;

public class JpaNumbersRepository implements NumbersRepository {
    private EntityManager em;

    public JpaNumbersRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Numbers findByTypeWithLock(NumbersType type) {
        return this.em.find(Numbers.class, NumbersType.ORDER, LockModeType.PESSIMISTIC_WRITE);
    }
}
