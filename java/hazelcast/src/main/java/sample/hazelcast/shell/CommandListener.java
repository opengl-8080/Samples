package sample.hazelcast.shell;

import java.util.Optional;

public interface CommandListener {

    /**
     * 現在のインスタンスで入力された情報を処理し、パブリッシュするデータを返す.
     * @param command 入力された文字列
     * @return パブリッシュするデータ（空を返した場合はパブリッシュされない）
     */
    Optional<String> commandToMessage(String command);
}
