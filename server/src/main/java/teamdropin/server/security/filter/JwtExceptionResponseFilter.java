package teamdropin.server.security.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;
import teamdropin.server.security.utils.ErrorResponder;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtExceptionResponseFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(request.getAttribute("exception") != null) {
            Exception exception = (Exception) request.getAttribute("exception");
            ErrorResponder.sendErrorResponse(response, HttpStatus.UNAUTHORIZED);

            log.info("Jwt Error message = {}", request);
        } else {
            filterChain.doFilter(request,response);
        }
    }
}
