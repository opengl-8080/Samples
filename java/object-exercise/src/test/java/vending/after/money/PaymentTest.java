package vending.after.money;

import static org.assertj.core.api.Assertions.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.bechte.junit.runners.context.HierarchicalContextRunner;

@RunWith(HierarchicalContextRunner.class)
public class PaymentTest {
    
    private CashBox cashBox = new CashBox();
    private Payment pay100Yen = new Payment(Coin.ONE_HUNDRED);
    private Payment pay500Yen = new Payment(Coin.FIVE_HUNDRED);
    
    @Before
    public void setup() {
        for (int i=0; i<10; i++) {
            cashBox.add(Coin.ONE_HUNDRED);
        }
    }
    
    public class _100円を投入した場合 {
        
        @Test
        public void お釣り不要() throws Exception {
            // exercise
            boolean actual = pay100Yen.needChange();
            
            // verify
            assertThat(actual).isFalse();
        }
        
        @Test
        public void コミットしていない場合は_投入した金額がそのままお釣りとなる() throws Exception {
            // exercise
            Change change = pay100Yen.refund();
            
            // verify
            String actual = change.getAmount().toString();
            String expected = Coin.ONE_HUNDRED.toMoney().toString();
            
            assertThat(actual).isEqualTo(expected);
        }
        
        @Test
        public void コミットしている場合_お釣りは０円() throws Exception {
            // setup
            pay100Yen.commit(cashBox);
            
            // exercise
            Change change = pay100Yen.refund();
            
            // verify
            String actual = change.getAmount().toString();
            String expected = new Money(0).toString();
            
            assertThat(actual).isEqualTo(expected);
        }
    }
    
    public class _500円を投入した場合 {

        @Test
        public void お釣り必要() throws Exception {
            // exercise
            boolean actual = pay500Yen.needChange();
            
            // verify
            assertThat(actual).isTrue();
        }
        
        @Test
        public void コミットしていない場合は_投入した金額がそのままお釣りとなる() throws Exception {
            // exercise
            Change change = pay500Yen.refund();
            
            // verify
            String actual = change.getAmount().toString();
            String expected = Coin.FIVE_HUNDRED.toMoney().toString();
            
            assertThat(actual).isEqualTo(expected);
        }
        
        @Test
        public void コミットしている場合_お釣りは４００円() throws Exception {
            // setup
            pay500Yen.commit(cashBox);
            
            // exercise
            Change change = pay500Yen.refund();
            
            // verify
            String actual = change.getAmount().toString();
            String expected = new Money(400).toString();
            
            assertThat(actual).isEqualTo(expected);
        }
    }
    
    @Test
    public void コミット前なら_未コミット判定はtrue() {
        // exercise
        boolean actual = pay100Yen.doesNotCommitted();
        
        // verify
        assertThat(actual).isTrue();
    }
    
    @Test
    public void コミット後なら_未コミット判定はtrue() {
        // setup
        pay100Yen.commit(cashBox);
        
        // exercise
        boolean actual = pay100Yen.doesNotCommitted();
        
        // verify
        assertThat(actual).isFalse();
    }
}
