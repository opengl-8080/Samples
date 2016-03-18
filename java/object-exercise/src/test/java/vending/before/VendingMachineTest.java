package vending.before;

import static org.assertj.core.api.Assertions.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.bechte.junit.runners.context.HierarchicalContextRunner;
import vending.before.Drink;
import vending.before.VendingMachine;

@RunWith(HierarchicalContextRunner.class)
public class VendingMachineTest {
    
    private VendingMachine vm = new VendingMachine();
    
    public class _100でも500でも無いお金を入れた場合 {
        
        private Drink drink;
        private final int money = 99;
        
        @Before
        public void setup() {
            drink = vm.buy(money, Drink.COKE);
        }

        @Test
        public void 飲み物はnullが返される() {
            // verify
            assertThat(drink).isNull();
        }
        
        @Test
        public void お釣りとして投入金額を取得できる() {
            // exercise
            int actual = vm.refund();
            
            // verify
            assertThat(actual).isEqualTo(money);
        }
    }
    
    public class コーラを100円で購入した場合 {
        
        private Drink drink;
        private final int money = 100;
        
        @Before
        public void setup() {
            drink = vm.buy(money, Drink.COKE);
        }

        @Test
        public void コーラが返される() {
            // verify
            assertThat(drink.getKind()).isEqualTo(Drink.COKE);
        }
        
        @Test
        public void お釣りは0円() {
            // exercise
            int actual = vm.refund();
            
            // verify
            assertThat(actual).isEqualTo(0);
        }
    }
    
    public class コーラを500円で購入した場合 {
        
        @Test
        public void お釣りは400円() {
            // setup
            vm.buy(500, Drink.COKE);
            
            // exercise
            int actual = vm.refund();
            
            // verify
            assertThat(actual).isEqualTo(400);
        }
    }
    
    public class コーラを５回購入した場合 {

        @Test
        public void コーラが購入できること() {
            // setup
            vm.buy(100, Drink.COKE);
            vm.buy(100, Drink.COKE);
            vm.buy(100, Drink.COKE);
            vm.buy(100, Drink.COKE);
            
            // exercise
            Drink drink = vm.buy(100, Drink.COKE);
            
            // verify
            assertThat(drink.getKind()).isEqualTo(Drink.COKE);
        }
    }
    
    public class コーラを６回購入しようとした場合 {

        private final int money = 100;
        
        @Before
        public void setup() {
            vm.buy(money, Drink.COKE);
            vm.buy(money, Drink.COKE);
            vm.buy(money, Drink.COKE);
            vm.buy(money, Drink.COKE);
            vm.buy(money, Drink.COKE);
        }

        @Test
        public void _6回目は在庫切れのためnullを返す() {
            // exercise
            Drink actual = vm.buy(money, Drink.COKE);
            
            // verify
            assertThat(actual).isNull();
        }

        @Test
        public void _6回目に投入したお金はお釣りとして取得できる() {
            // setup
            vm.buy(money, Drink.COKE);
            
            // exercise
            int actual = vm.refund();
            
            // verify
            assertThat(actual).isEqualTo(money);
        }
    }

}
