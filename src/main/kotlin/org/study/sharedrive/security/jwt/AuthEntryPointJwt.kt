package org.study.sharedrive.security.jwt

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Component
class AuthEntryPointJwt: AuthenticationEntryPoint {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(AuthEntryPointJwt::class.java)
    }

    override fun commence(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authException: AuthenticationException?
    ) {
        logger.error("Unauthorized error: {}", authException?.message);
        response!!.contentType = MediaType.APPLICATION_JSON_VALUE
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        val body: MutableMap<String, Any> = HashMap()
        body["status"] = HttpServletResponse.SC_UNAUTHORIZED
        body["error"] = "Unauthorized"
        body["message"] = authException?.message!!
        body["path"] = request!!.servletPath
        val mapper = ObjectMapper()
        mapper.writeValue(response.outputStream, body)
    }

}