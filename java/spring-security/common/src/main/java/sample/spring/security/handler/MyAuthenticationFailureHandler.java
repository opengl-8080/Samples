package sample.spring.security.handler;

import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {
    
    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
                throws IOException, ServletException {
        String message;
        if (e instanceof BadCredentialsException) {
            message = "ユーザー名またはパスワードが間違っています";
        } else if (e instanceof LockedException) {
            message = "アカウントがロックされています";
        } else if (e instanceof DisabledException) {
            message = "アカウントが無効化されています";
        } else if (e instanceof AccountExpiredException) {
            message = "アカウントの利用期限が切れています";
        } else if (e instanceof CredentialsExpiredException) {
            message = "パスワードの有効期限が切れています";
        } else if (e instanceof SessionAuthenticationException) {
            message = "セッションが上限数に達しています";
        } else {
            message = "ログインに失敗しました";
        }

        System.out.println(message);
        
        DefaultRedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
        redirectStrategy.sendRedirect(request, response, "/login");
    }
}
