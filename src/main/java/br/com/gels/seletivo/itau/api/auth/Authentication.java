package br.com.gels.seletivo.itau.api.auth;

import br.com.gels.seletivo.itau.api.adapter.dto.UsuarioAutenticacaoDTO;
import br.com.gels.seletivo.itau.api.domain.entity.Usuario;
import br.com.gels.seletivo.itau.api.exception.APIException;
import br.com.gels.seletivo.itau.api.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;

@Service
public class Authentication implements UserDetailsService {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private MessageSource messageSource;

    @Value("${jwt.expiration}")
    private Long exp;

    @Override
    public UserDetails loadUserByUsername(String token) throws APIException, UsernameNotFoundException {
        UsuarioAutenticacaoDTO usuarioAutenticacaoDTO = null;
        try {
            usuarioAutenticacaoDTO = decryptToken(token);
        } catch (Exception e) {
            new APIException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        UserModel user = new UserModel();
        user.setUsername(usuarioAutenticacaoDTO.getUsername());
        user.setPassword(usuarioAutenticacaoDTO.getPassword());
        user.setUser_id(usuarioAutenticacaoDTO.getUser_id());
        user.setClient_user(usuarioAutenticacaoDTO.getClient_user());
        user.setClient_key(usuarioAutenticacaoDTO.getClient_key());

        return user;
    }

    public UsuarioAutenticacaoDTO decryptToken(String token) throws APIException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String password = request.getParameter("password");

        Usuario usuario = usuarioService.geUsuario(token,
                new String(Base64.getEncoder().encode(password.getBytes())));

        String authHeader = request.getHeader("Authorization");

        Base64.Decoder decoder = Base64.getDecoder();

        if (authHeader != null && authHeader.startsWith("Basic")) {
            String encodedUsernamePassword = authHeader.substring("Basic ".length()).trim();
            String usernamePassword = new String(decoder.decode(encodedUsernamePassword));

            int seperatorIndex = usernamePassword.indexOf(':');

            String acesskey = usernamePassword.substring(0, seperatorIndex);
            String secretKey = usernamePassword.substring(seperatorIndex + 1);

            return UsuarioAutenticacaoDTO.builder()
                    .token_login(token)
                    .password(passwordEncoder.encode(usuario.getSenha()))
                    .client_user(acesskey)
                    .client_key(secretKey)
                    .user_id(usuario.getId())
                    .username(usuario.getLogin())
                    .exp(exp)
                    .admin(usuario.isAdmin())
                    .build();
        } else {
            throw new APIException(messageSource.getMessage("header.auth",
                    null, LocaleContextHolder.getLocale()));
        }
    }
}