package co.id.jss.jssrestapi.config.filter;

import co.id.jss.jssrestapi.common.misc.Constants;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

/**
 * Spring Web filter for logging request and response.
 *
 * @author Hidetake Iwata
 * @see org.springframework.web.filter.AbstractRequestLoggingFilter
 * @see ContentCachingRequestWrapper
 * @see ContentCachingResponseWrapper
 */

@Slf4j
public class HttpLoggingFilter extends OncePerRequestFilter {

    private static final Logger LOG = LoggerFactory.getLogger(HttpLoggingFilter.class);

    private static final List<MediaType> VISIBLE_TYPES = Arrays.asList(
            MediaType.valueOf("text/*"),
            MediaType.APPLICATION_FORM_URLENCODED,
            MediaType.APPLICATION_JSON,
            MediaType.APPLICATION_XML,
            MediaType.valueOf("application/*+json"),
            MediaType.valueOf("application/*+xml"),
            MediaType.MULTIPART_FORM_DATA
    );

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {

        if (isAsyncDispatch(request)) {
            filterChain.doFilter(request, response);
        } else {
            doFilterWrapped(wrapRequest(request), wrapResponse(response), filterChain);
        }
    }

    private void doFilterWrapped(ContentCachingRequestWrapper request, ContentCachingResponseWrapper response, FilterChain filterChain) throws ServletException, IOException {

        try {
            beforeRequest(request, response);
            filterChain.doFilter(request, response);
        }
        finally {
            afterRequest(request, response);
            response.copyBodyToResponse();
        }
    }

    private void beforeRequest(ContentCachingRequestWrapper request, ContentCachingResponseWrapper response) {

        System.out.println("================> START REQUEST");

        if (LOG.isInfoEnabled()) {
            logRequestHeader(request, request.getRemoteAddr() + "|>");
        }
    }

    private void afterRequest(ContentCachingRequestWrapper request, ContentCachingResponseWrapper response) {

        boolean isLogin = request.getRequestURI().contains("login");

        if (LOG.isInfoEnabled()) {

            if(isLogin){
                System.out.println("================> LOG REQUEST BODY NO AVAILABLE FOR LOGIN USER");
            }
            else {
                logRequestBody(request, request.getRemoteAddr() + "|>");
            }

            logResponse(response, request.getRemoteAddr() + "|<", isLogin);
        }

        System.out.println("================> END REQUEST");
    }

    private static void logRequestHeader(ContentCachingRequestWrapper request, String prefix) {

        System.out.println("================> THIS IS LOG REQUEST HEADER");

        String queryString = request.getQueryString();
        if (queryString == null) {
            LOG.info("{} {} {}", prefix, request.getMethod(), request.getRequestURI());
        } else {
            LOG.info("{} {} {}?{}", prefix, request.getMethod(), request.getRequestURI(), queryString);
        }
        Collections.list(request.getHeaderNames()).forEach(headerName ->
                Collections.list(request.getHeaders(headerName)).forEach(headerValue ->
                        LOG.info("{} {}: {}", prefix, headerName, headerName.equalsIgnoreCase(Constants.JWT_AUTHORIZATION) ? "***secret***" : headerValue)));
        LOG.info("{}", prefix);
    }

    private static void logRequestBody(ContentCachingRequestWrapper request, String prefix) {

        System.out.println("================> THIS IS LOG REQUEST BODY");

        byte[] content = request.getContentAsByteArray();

        if (content.length > 0) {

            logContent(content, request.getContentType(), request.getCharacterEncoding(), prefix);
        }
    }

    private static void logResponse(ContentCachingResponseWrapper response, String prefix, boolean isLogin) {

        System.out.println("================> THIS IS LOG RESPONSE");

        int status = response.getStatus();
        LOG.info("{} {} {}", prefix, status, HttpStatus.valueOf(status).getReasonPhrase());
        response.getHeaderNames().forEach(headerName ->
                response.getHeaders(headerName).forEach(headerValue ->
                        LOG.info("{} {}: {}", prefix, headerName, headerValue)));
        LOG.info("{}", prefix);

        if(isLogin){
            System.out.println("================> LOG RESPONSE BODY NO AVAILABLE FOR LOGIN USER");
        }
        else {

            byte[] content = response.getContentAsByteArray();
            if (content.length > 0) {
                logContent(content, response.getContentType(), response.getCharacterEncoding(), prefix);
            }
        }
    }

    private static void logContent(byte[] content, String contentType, String contentEncoding, String prefix) {

        System.out.println("================> THIS IS LOG CONTENT");

        MediaType mediaType = MediaType.valueOf(contentType);
        boolean visible = VISIBLE_TYPES.stream().anyMatch(visibleType -> visibleType.includes(mediaType));
        if (visible) {
            try {
                String contentString = new String(content, contentEncoding);
                Stream.of(contentString.split("\r\n|\r|\n")).forEach(line -> LOG.info("{} {}", prefix, line));
            } catch (UnsupportedEncodingException e) {
                LOG.info("{} [{} bytes content]", prefix, content.length);
            }
        } else {
            LOG.info("{} [{} bytes content]", prefix, content.length);
        }
    }

    private static ContentCachingRequestWrapper wrapRequest(HttpServletRequest request) {

        if (request instanceof ContentCachingRequestWrapper) {
            return (ContentCachingRequestWrapper) request;
        } else {
            return new ContentCachingRequestWrapper(request);
        }
    }

    private static ContentCachingResponseWrapper wrapResponse(HttpServletResponse response) {

        if (response instanceof ContentCachingResponseWrapper) {
            return (ContentCachingResponseWrapper) response;
        } else {
            return new ContentCachingResponseWrapper(response);
        }
    }
}
