package sample.junit5;

import static org.junit.gen5.api.Assertions.*;

import org.junit.gen5.api.Test;

public class Junit5Test {
    
    @Test
    public void trueであることの検証() {
        assertTrue(false);
    }

    @Test
    public void trueであることの検証_ラムダ式も渡せる() {
        assertTrue(() -> false);
    }

    @Test
    public void trueであることの検証_末尾に任意のメッセージをセットできる() {
        assertTrue(false, "true を期待したのに false だった");
    }

    @Test
    public void trueであることの検証_末尾に任意のメッセージをラムダ式でセットできる() {
        assertTrue(false, () -> "true を期待したのに false だった");
    }

    @Test
    public void falseであることの検証() {
        assertFalse(true);
    }

    @Test
    public void equalsで比較して同じことを検証() {
        assertEquals("hoge", "fuga");
    }

    @Test
    public void equalsで比較して異なることを検証() {
        assertNotEquals("hoge", "hoge");
    }

    @Test
    public void nullでないことを検証() {
        assertNotNull(null);
    }

    @Test
    public void nullであることを検証() {
        assertNull("not null");
    }

    @Test
    public void 等号で比較して同じインスタンスであることを検証() {
        assertSame(new String("hoge"), new String("hoge"));
    }

    @Test
    public void 指定した例外がスローされることを検証() {
        assertThrows(IllegalArgumentException.class, () -> {throw new NullPointerException();});
    }

    @Test
    public void スローされた例外を検証したうえで_その例外を取得する() {
        IllegalArgumentException e = expectThrows(IllegalArgumentException.class, () -> {
            throw new IllegalArgumentException("テストです");
        });
        
        assertEquals("test です", e.getMessage());
    }

    @Test
    public void 複数のアサーションをまとめて実行() {
        assertAll(
            () -> assertTrue(false, "true と信じてたら false だった"),
            () -> assertEquals("hoge", "fuga", "hoge と信じてる")
        );
    }
}