package sample.jpa.domain.order;

import sample.jpa.domain.common.Numbers;
import sample.jpa.domain.common.NumbersRepository;
import sample.jpa.domain.common.NumbersType;

public class GenerateOrderNumberService {
    private NumbersRepository numbersRepository;

    public GenerateOrderNumberService(NumbersRepository numbersRepository) {
        this.numbersRepository = numbersRepository;
    }

    public OrderNumber generateNextNumber() {
        Numbers orderNumber = this.numbersRepository.findByTypeWithLock(NumbersType.ORDER);
        orderNumber.countUp();
        return OrderNumber.create(orderNumber);
    }
}
