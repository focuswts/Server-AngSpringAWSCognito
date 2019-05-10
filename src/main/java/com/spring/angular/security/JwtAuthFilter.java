package com.spring.angular.security;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.source.RemoteJWKSet;
import com.nimbusds.jose.proc.BadJOSEException;
import com.nimbusds.jose.proc.JWSKeySelector;
import com.nimbusds.jose.proc.JWSVerificationKeySelector;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class JwtAuthFilter extends OncePerRequestFilter  {

    private static final String AUTH_HEADER_STRING = "Authorization";
    private static final String AUTH_BEARER_STRING = "Bearer";
    // should cache keys
    RemoteJWKSet remoteJWKSet;
    private String ISSUER = "https://cognito-idp.us-west-2.amazonaws.com/us-west-2_gzPV3UIk5";
    private String KEY_STORE_PATH = "/.well-known/jwks.json";


    public JwtAuthFilter() throws MalformedURLException {
        URL JWKUrl = new URL(ISSUER + KEY_STORE_PATH);
        this.remoteJWKSet = new RemoteJWKSet(JWKUrl);
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest req,
            HttpServletResponse res,
            FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(AUTH_HEADER_STRING).replace(AUTH_BEARER_STRING, "");

        System.out.println("Path " + req.getServletPath());
        if ("/v2/api-docs".equals(req.getServletPath())) {
         //   shouldNotFilter(req);
        }else{

        }



        logger.info(header);

        try {


            JWT jwt = JWTParser.parse(header);

            String iss = jwt.getJWTClaimsSet().getIssuer();
            logger.info(iss);

            // check if issuer is our user pool
            try {
                if (ISSUER.equals(jwt.getJWTClaimsSet().getIssuer())) {

                    JWSKeySelector keySelector = new JWSVerificationKeySelector(JWSAlgorithm.RS256, remoteJWKSet);

                    ConfigurableJWTProcessor jwtProcessor = new DefaultJWTProcessor();
                    jwtProcessor.setJWSKeySelector(keySelector);

                    // check token
                    JWTClaimsSet claimsSet = jwtProcessor.process(jwt, null);

                    // process roles (groups in cognito)
                    List<String> groups = (List<String>) claimsSet.getClaim("cognito:groups");

                    List<GrantedAuthority> authorities = new ArrayList<>();
                    authorities.add(new SimpleGrantedAuthority("users"));

                    groups.forEach(s -> {
                        logger.info(s);
                        switch (s) {
                            case "users": {
                                authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
                            }
                            break;
                        }
                    });

                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            claimsSet,
                            null,
                            authorities
                    );

                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }

        } catch (java.text.ParseException e) {
            e.printStackTrace();
        } catch (JOSEException e) {
            e.printStackTrace();
        } catch (BadJOSEException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            // in case that header is null
            e.printStackTrace();
        }

        chain.doFilter(req, res);
    }

}
