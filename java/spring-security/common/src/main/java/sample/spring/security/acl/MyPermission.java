package sample.spring.security.acl;

import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.model.Permission;

public class MyPermission extends BasePermission {
    public static final Permission HOGE = new MyPermission(0b100000, 'H');

    private MyPermission(int mask, char code) {
        super(mask, code);
    }
}
