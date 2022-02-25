package br.com.gels.seletivo.itau.api.auth;

import br.com.gels.seletivo.itau.api.adapter.dto.UsuarioAutenticacaoDTO;
import br.com.gels.seletivo.itau.api.exception.APIException;
import com.google.gson.Gson;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Base64;

@Service
public class AuthenticationValidation {

    private final MessageSource messageSource;

    @Autowired
    public AuthenticationValidation(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public UsuarioAutenticacaoDTO validarToken(HttpServletRequest request) throws APIException {
        String token = request.getHeader("Authorization");

        if (token == null || token.isEmpty())
            throw new APIException(messageSource.getMessage("token.invalido", null,
                    LocaleContextHolder.getLocale()), HttpStatus.UNAUTHORIZED);

        String[] chunks = token.split("\\.");
        Base64.Decoder decoder = Base64.getDecoder();
        JSONObject objTokenJwt;
        try {
            objTokenJwt = new JSONObject(new String(decoder.decode(chunks[1])));
        } catch (Exception e) {
            throw new APIException(messageSource.getMessage("token.invalido", null,
                    LocaleContextHolder.getLocale()), HttpStatus.UNAUTHORIZED);
        }

        if (Timestamp.valueOf(LocalDateTime.now())
                .after(new Timestamp(objTokenJwt.getLong("exp") * 1000)))
            throw new APIException(messageSource.getMessage("token.expirado", null,
                    LocaleContextHolder.getLocale()), HttpStatus.UNAUTHORIZED);

        UsuarioAutenticacaoDTO user;
        try {
            user = new Gson().fromJson(objTokenJwt.toString(), UsuarioAutenticacaoDTO.class);
            user.setUsername(objTokenJwt.getString("user_name"));
        } catch (Exception e) {
            throw new APIException(messageSource.getMessage("usuario.nao-encontrado", null,
                    LocaleContextHolder.getLocale()), HttpStatus.UNAUTHORIZED, null, e);
        }

        return user;
    }
}