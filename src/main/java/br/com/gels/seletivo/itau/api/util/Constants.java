package br.com.gels.seletivo.itau.api.util;

public class Constants {
    public static final String TITLE_API = "RESTFUL API de gerenciamento de tarefas (TODO-LIST API)";
    public static final String TITLE_VERSION = "Version:";
    public static final String MAIN_PACKAGE = "br.com.gels.seletivo.itau.api";
    public static final String VERSION = " 1.0";
    public static final String TERMS_OF_SERVICE = "Terms of Service";
    public static final String AUTHOR = "Angelica Ribeiro";
    public static final String URL_AUTOR = "https://github.com/gels-estudo";
    public static final String EMAIL_AUTOR = "gels.estudo@gmail.com";
    public static final String LICENSE = "Apache License Version 2.0";
    public static final String LICENSE_LINK = "https://www.apache.org/licesen.html";

    public static final String MESSAGE_CODE200 = "Requisição realizada com sucesso";
    public static final String MESSAGE_CODE201 = "Criado com sucesso";
    public static final String MESSAGE_CODE204 = "Sem conteúdo";
    public static final String MESSAGE_CODE401 = "Requisição não autorizada";
    public static final String MESSAGE_CODE403 = "Requisição não permitida";
    public static final String MESSAGE_CODE404 = "Requisição inválida";
    public static final String MESSAGE_CODE500 = "Erro interno";

    public static final String HTTP_CONTENT_TYPE = "application/json";

    public static final String STATUS_CODE_FORMAT = "%s - %s";
    public static final String INTERNAL_SERVER_ERROR_MESSAGE = "Ocorreu um erro interno no servidor";
    public static final String SERVER_UNAVALIABLE = "Não foi possível atender sua solicitação no momento. Tente novamente mais tarde!";
    public static final String PARAMETERS_INVALID_ERROR_MSG = "Falha ao converter parâmetros recebidos";

    public static final String SCOPE_READ = "read";
    public static final String SCOPE_WRITE = "write";
    public static final String GRANT_TYPE = "password";
    public static final String TOKEN_KEY_ACCESS = "permitAll()";
    public static final String CHECK_TOKEN_ACCESS = "isAuthenticated()";

    public static final String ENDPOINT_POST_INCLUIR_TAREFA = "Adiciona uma nova tarefa";
    public static final String ENDPOINT_PUT_ATUALIZAR_TAREFA = "Atualiza uma tarefa cadastradas";
    public static final String ENDPOINT_GET_CONSULTAR_TAREFAS = "Consulta as tarefas cadastradas";
    public static final String ENDPOINT_EXCLUIR_TAREFA = "Exclui uma tarefa cadastrada";
}