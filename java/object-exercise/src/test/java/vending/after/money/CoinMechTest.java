package vending.after.money;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

import mockit.Mocked;
import mockit.NonStrictExpectations;
import mockit.Verifications;

public class CoinMechTest {
    
    @Mocked
    private CashBox cashBox;
    @Mocked
    private Payment payment;
    
    @Test
    public void インスタンスを生成すると金庫に100円が10枚投入される() {
        // exercise
        new CoinMech();
        
        // verify
        new Verifications() {{
            cashBox.add(Coin.ONE_HUNDRED); times = 10;
        }};
    }
    
    @Test
    public void 支払いが釣り銭を必要としていて_かつ金庫に釣り銭切れの場合_釣り銭なしとは判定されない() throws Exception {
        // setup
        new NonStrictExpectations() {{
            payment.needChange(); result = true;
            cashBox.doesNotHaveChange(); result = true;
        }};

        // exercise
        assertDoesNotHaveChange(true);
    }
    
    @Test
    public void 支払いが釣り銭を必要としていて_かつ金庫に釣り銭切れでない場合_釣り銭なしと判定される() throws Exception {
        // setup
        new NonStrictExpectations() {{
            payment.needChange(); result = true;
            cashBox.doesNotHaveChange(); result = false;
        }};
        
        // exercise
        assertDoesNotHaveChange(false);
    }
    
    @Test
    public void 支払いが釣り銭を必要としていなくて_かつ金庫が釣り銭切れの場合_釣り銭なしとは判定されない() throws Exception {
        // setup
        new NonStrictExpectations() {{
            payment.needChange(); result = false;
            cashBox.doesNotHaveChange(); result = true;
        }};
        
        // exercise
        assertDoesNotHaveChange(false);
    }
    
    @Test
    public void 支払いが釣り銭を必要としていなくて_かつ金庫が釣り銭切れでない場合_釣り銭なしとは判定されない() throws Exception {
        // setup
        new NonStrictExpectations() {{
            payment.needChange(); result = false;
            cashBox.doesNotHaveChange(); result = false;
        }};
        
        // exercise
        assertDoesNotHaveChange(false);
    }
    
    private static void assertDoesNotHaveChange(boolean expected) {
        CoinMech coinMech = new CoinMech();
        coinMech.put(Coin.ONE_HUNDRED);
        
        // exercise
        boolean actual = coinMech.doesNotHaveChange();
        
        // verify
        assertThat(actual).isEqualTo(expected);
    }
    
    @Test
    public void 釣り銭は支払いオブジェクトが返したお釣りをそのまま返す() throws Exception {
        // setup
        final Change change = new Change();
        new NonStrictExpectations() {{
            payment.refund(); result = change;
        }};
        
        CoinMech coinMech = new CoinMech();
        coinMech.put(Coin.ONE_HUNDRED);
        
        // exercise
        Change actual = coinMech.refund();
        
        // verify
        assertThat(actual).isSameAs(change);
    }
    
    @Test
    public void コミットすると_支払いオブジェクトのコミットが実行される() throws Exception {
        // setup
        CoinMech coinMech = new CoinMech();
        coinMech.put(Coin.ONE_HUNDRED);
        
        // exercise
        coinMech.commit();
        
        // verify
        new Verifications() {{
            payment.commit((CashBox) any); times = 1;
        }};
    }
}
