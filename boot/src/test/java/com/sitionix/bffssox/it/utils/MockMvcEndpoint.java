package com.sitionix.bffssox.it.utils;

import com.app_afesox.bffssox.api_first.dto.EmailVerificationDTO;
import com.app_afesox.bffssox.api_first.dto.EmailVerificationResponseDTO;
import com.app_afesox.bffssox.api_first.dto.AgentConversationDetailsDTO;
import com.app_afesox.bffssox.api_first.dto.AgentConversationsResponseDTO;
import com.app_afesox.bffssox.api_first.dto.AgentProjectDTO;
import com.app_afesox.bffssox.api_first.dto.AgentProjectsPageResponseDTO;
import com.app_afesox.bffssox.api_first.dto.ChatAgentRequestDTO;
import com.app_afesox.bffssox.api_first.dto.CreateAgentProjectRequestDTO;
import com.app_afesox.bffssox.api_first.dto.SubmitChatExecutionResponseDTO;
import com.app_afesox.bffssox.api_first.dto.CreateAgentRuleRequestDTO;
import com.app_afesox.bffssox.api_first.dto.CreateAgentRequestDTO;
import com.app_afesox.bffssox.api_first.dto.DeleteAgentRuleResponseDTO;
import com.app_afesox.bffssox.api_first.dto.CreateSiteRequestDTO;
import com.app_afesox.bffssox.api_first.dto.CreateSiteResponseDTO;
import com.app_afesox.bffssox.api_first.dto.LoginRequestDTO;
import com.app_afesox.bffssox.api_first.dto.LoginResponseDTO;
import com.app_afesox.bffssox.api_first.dto.AgentDTO;
import com.app_afesox.bffssox.api_first.dto.AgentRuleDTO;
import com.app_afesox.bffssox.api_first.dto.AgentRulesResponseDTO;
import com.app_afesox.bffssox.api_first.dto.PatchAgentRequestDTO;
import com.app_afesox.bffssox.api_first.dto.PatchAgentRuleRequestDTO;
import com.app_afesox.bffssox.api_first.dto.SiteOverviewDTO;
import com.app_afesox.bffssox.api_first.dto.RefreshAccessTokenRequestDTO;
import com.app_afesox.bffssox.api_first.dto.RefreshAccessTokenResponseDTO;
import com.app_afesox.bffssox.api_first.dto.ResendEmailVerificationResponseDTO;
import com.app_afesox.bffssox.api_first.dto.RegisterUserDTO;
import com.app_afesox.bffssox.api_first.dto.ResponseRegisterUserDTO;
import com.app_afesox.bffssox.api_first.dto.WorkspaceSitesResponseDTO;
import com.sitionix.forgeit.domain.endpoint.Endpoint;
import com.sitionix.forgeit.domain.endpoint.HttpMethod;
import com.sitionix.forgeit.domain.endpoint.mockmvc.MockmvcDefault;
import org.springframework.http.HttpStatus;

public class MockMvcEndpoint {

    public static final Endpoint<LoginRequestDTO, LoginResponseDTO> POST_LOGIN_USER =
            Endpoint.createContract("/api/v1/auth/login",
                    HttpMethod.POST,
                    LoginRequestDTO.class,
                    LoginResponseDTO.class,
                    (MockmvcDefault) context -> context.expectStatus(HttpStatus.OK.value())
                            .withRequest("requestDefaultLoginUserWithHappyPath.json")
                            .expectResponse("responseDefaultLoginUserWithHappyPath.json"));

    public static final Endpoint<EmailVerificationDTO, EmailVerificationResponseDTO> POST_VERIFY_EMAIL =
            Endpoint.createContract("/api/v1/auth/email/verify",
                    HttpMethod.POST,
                    EmailVerificationDTO.class,
                    EmailVerificationResponseDTO.class,
                    (MockmvcDefault) context -> context.expectStatus(HttpStatus.OK.value())
                            .withRequest("requestDefaultVerifyEmailWithHappyPath.json")
                            .expectResponse("responseDefaultVerifyEmailWithHappyPath.json"));

    public static final Endpoint<RefreshAccessTokenRequestDTO, RefreshAccessTokenResponseDTO> POST_REFRESH_ACCESS_TOKEN =
            Endpoint.createContract("/api/v1/auth/refresh",
                    HttpMethod.POST,
                    RefreshAccessTokenRequestDTO.class,
                    RefreshAccessTokenResponseDTO.class,
                    (MockmvcDefault) context -> context.expectStatus(HttpStatus.OK.value())
                            .cookie("__Host-refresh_token", "dGhpc0lzUmVmcmVzaA==")
                            .withRequest("requestDefaultRefreshAccessTokenWithHappyPath.json")
                            .expectResponse("responseDefaultRefreshAccessTokenWithHappyPath.json"));

    public static final Endpoint<Object, ResendEmailVerificationResponseDTO> POST_RESEND_EMAIL_VERIFICATION =
            Endpoint.createContract("/api/v1/auth/email/verify/resend",
                    HttpMethod.POST,
                    Object.class,
                    ResendEmailVerificationResponseDTO.class,
                    (MockmvcDefault) context -> context.expectStatus(HttpStatus.ACCEPTED.value())
                            .withRequest("requestDefaultResendEmailVerificationWithHappyPath.json")
                            .expectResponse("responseDefaultResendEmailVerificationWithHappyPath.json"),
                    ItUserTokens.USER_JWT);

    public static final Endpoint<RegisterUserDTO, ResponseRegisterUserDTO> POST_REGISTER_USER =
            Endpoint.createContract("/api/v1/users",
                    HttpMethod.POST,
                    RegisterUserDTO.class,
                    ResponseRegisterUserDTO.class,
                    (MockmvcDefault) context -> context.expectStatus(HttpStatus.OK.value())
                            .withRequest("requestDefaultRegisterUserWithHappyPath.json")
                            .expectResponse("responseDefaultRegisterUserWithHappyPath.json"));

    public static final Endpoint<CreateSiteRequestDTO, CreateSiteResponseDTO> POST_CREATE_SITE =
            Endpoint.createContract("/api/v1/sites",
                    HttpMethod.POST,
                    CreateSiteRequestDTO.class,
                    CreateSiteResponseDTO.class,
                    (MockmvcDefault) context -> context.expectStatus(HttpStatus.CREATED.value())
                            .withRequest("requestDefaultCreateSiteWithHappyPath.json")
                            .expectResponse("responseDefaultCreateSiteWithHappyPath.json"),
                    ItUserTokens.USER_JWT);

    public static final Endpoint<Void, WorkspaceSitesResponseDTO> GET_SITES =
            Endpoint.createContract("/api/v1/sites",
                    HttpMethod.GET,
                    Void.class,
                    WorkspaceSitesResponseDTO.class,
                    (MockmvcDefault) context -> context.expectStatus(HttpStatus.OK.value()),
                    ItUserTokens.USER_JWT);

    public static final Endpoint<Void, SiteOverviewDTO> GET_SITE_OVERVIEW =
            Endpoint.createContract("/api/v1/sites/{siteId}/overview",
                    HttpMethod.GET,
                    Void.class,
                    SiteOverviewDTO.class,
                    (MockmvcDefault) context -> context.expectStatus(HttpStatus.OK.value()),
                    ItUserTokens.USER_JWT);

    public static final Endpoint<Void, AgentDTO> POST_ACTIVATE_AGENT =
            Endpoint.createContract("/api/v1/agents/{agentId}/activate",
                    HttpMethod.POST,
                    Void.class,
                    AgentDTO.class,
                    (MockmvcDefault) context -> context.expectStatus(HttpStatus.OK.value())
                            .expectResponse("responseDefaultActivateAgentWithHappyPath.json"),
                    ItUserTokens.USER_JWT);

    public static final Endpoint<Void, AgentDTO> POST_ARCHIVE_AGENT =
            Endpoint.createContract("/api/v1/agents/{agentId}/archive",
                    HttpMethod.POST,
                    Void.class,
                    AgentDTO.class,
                    (MockmvcDefault) context -> context.expectStatus(HttpStatus.OK.value())
                            .expectResponse("responseDefaultArchiveAgentWithHappyPath.json"),
                    ItUserTokens.USER_JWT);

    public static final Endpoint<Void, AgentDTO> POST_RESTORE_AGENT =
            Endpoint.createContract("/api/v1/agents/{agentId}/restore",
                    HttpMethod.POST,
                    Void.class,
                    AgentDTO.class,
                    (MockmvcDefault) context -> context.expectStatus(HttpStatus.OK.value())
                            .expectResponse("responseDefaultRestoreAgentWithHappyPath.json"),
                    ItUserTokens.USER_JWT);

    public static final Endpoint<Void, AgentDTO> DELETE_AGENT =
            Endpoint.createContract("/api/v1/agents/{agentId}",
                    HttpMethod.DELETE,
                    Void.class,
                    AgentDTO.class,
                    (MockmvcDefault) context -> context.expectStatus(HttpStatus.OK.value())
                            .expectResponse("responseDefaultDeleteAgentWithHappyPath.json"),
                    ItUserTokens.USER_JWT);

    public static final Endpoint<CreateAgentRequestDTO, AgentDTO> POST_CREATE_AGENT =
            Endpoint.createContract("/api/v1/agents",
                    HttpMethod.POST,
                    CreateAgentRequestDTO.class,
                    AgentDTO.class,
                    (MockmvcDefault) context -> context.expectStatus(HttpStatus.CREATED.value())
                            .withRequest("requestDefaultCreateAgentWithNameOnly.json")
                            .expectResponse("responseDefaultCreateAgentWithNameOnly.json"),
                    ItUserTokens.USER_JWT);

    public static final Endpoint<CreateAgentProjectRequestDTO, AgentProjectDTO> POST_CREATE_AGENT_PROJECT =
            Endpoint.createContract("/api/v1/agent-projects",
                    HttpMethod.POST,
                    CreateAgentProjectRequestDTO.class,
                    AgentProjectDTO.class,
                    (MockmvcDefault) context -> context.expectStatus(HttpStatus.CREATED.value())
                            .withRequest("requestDefaultCreateAgentProject.json")
                            .expectResponse("responseDefaultCreateAgentProject.json"),
                    ItUserTokens.USER_JWT);

    public static final Endpoint<Void, AgentProjectsPageResponseDTO> GET_AGENT_PROJECTS =
            Endpoint.createContract("/api/v1/agent-projects",
                    HttpMethod.GET,
                    Void.class,
                    AgentProjectsPageResponseDTO.class,
                    (MockmvcDefault) context -> context.expectStatus(HttpStatus.OK.value())
                            .expectResponse("responseDefaultGetAgentProjects.json"),
                    ItUserTokens.USER_JWT);

    public static final Endpoint<Void, AgentDTO> GET_AGENT =
            Endpoint.createContract("/api/v1/agents/{agentId}",
                    HttpMethod.GET,
                    Void.class,
                    AgentDTO.class,
                    (MockmvcDefault) context -> context.expectStatus(HttpStatus.OK.value())
                            .expectResponse("responseDefaultGetAgentWithInstruction.json"),
                    ItUserTokens.USER_JWT);

    public static final Endpoint<Void, AgentConversationsResponseDTO> GET_AGENT_CONVERSATIONS =
            Endpoint.createContract("/api/v1/agents/{agentId}/conversations",
                    HttpMethod.GET,
                    Void.class,
                    AgentConversationsResponseDTO.class,
                    (MockmvcDefault) context -> context.expectStatus(HttpStatus.OK.value())
                            .expectResponse("responseDefaultGetAgentConversationsWithHappyPath.json"),
                    ItUserTokens.USER_JWT);

    public static final Endpoint<Void, AgentConversationDetailsDTO> GET_AGENT_CONVERSATION =
            Endpoint.createContract("/api/v1/conversations/{conversationId}",
                    HttpMethod.GET,
                    Void.class,
                    AgentConversationDetailsDTO.class,
                    (MockmvcDefault) context -> context.expectStatus(HttpStatus.OK.value())
                            .expectResponse("responseDefaultGetAgentConversationWithHappyPath.json"),
                    ItUserTokens.USER_JWT);

    public static final Endpoint<Void, Void> DELETE_AGENT_CONVERSATION =
            Endpoint.createContract("/api/v1/conversations/{conversationId}",
                    HttpMethod.DELETE,
                    Void.class,
                    Void.class,
                    (MockmvcDefault) context -> context.expectStatus(HttpStatus.NO_CONTENT.value()),
                    ItUserTokens.USER_JWT);

    public static final Endpoint<ChatAgentRequestDTO, SubmitChatExecutionResponseDTO> POST_CHAT_AGENT =
            Endpoint.createContract("/api/v1/agents/{agentId}/chat",
                    HttpMethod.POST,
                    ChatAgentRequestDTO.class,
                    SubmitChatExecutionResponseDTO.class,
                    (MockmvcDefault) context -> context.expectStatus(HttpStatus.ACCEPTED.value())
                            .withRequest("requestDefaultChatAgentNewConversation.json")
                            .expectResponse("responseDefaultSubmitChatExecutionNewConversation.json"),
                    ItUserTokens.USER_JWT);

    public static final Endpoint<PatchAgentRequestDTO, AgentDTO> PATCH_AGENT =
            Endpoint.createContract("/api/v1/agents/{agentId}",
                    HttpMethod.PATCH,
                    PatchAgentRequestDTO.class,
                    AgentDTO.class,
                    (MockmvcDefault) context -> context.expectStatus(HttpStatus.OK.value())
                            .withRequest("requestDefaultPatchAgentInstructionOnly.json")
                            .expectResponse("responseDefaultPatchAgentInstructionOnly.json"),
                    ItUserTokens.USER_JWT);

    public static final Endpoint<Void, AgentRulesResponseDTO> GET_AGENT_RULES =
            Endpoint.createContract("/api/v1/agents/{agentId}/rules",
                    HttpMethod.GET,
                    Void.class,
                    AgentRulesResponseDTO.class,
                    (MockmvcDefault) context -> context.expectStatus(HttpStatus.OK.value())
                            .expectResponse("responseDefaultGetAgentRulesWithHappyPath.json"),
                    ItUserTokens.USER_JWT);

    public static final Endpoint<CreateAgentRuleRequestDTO, AgentRuleDTO> POST_CREATE_AGENT_RULE =
            Endpoint.createContract("/api/v1/agents/{agentId}/rules",
                    HttpMethod.POST,
                    CreateAgentRuleRequestDTO.class,
                    AgentRuleDTO.class,
                    (MockmvcDefault) context -> context.expectStatus(HttpStatus.CREATED.value())
                            .withRequest("requestDefaultCreateAgentRuleWithHappyPath.json")
                            .expectResponse("responseDefaultCreateAgentRuleWithHappyPath.json"),
                    ItUserTokens.USER_JWT);

    public static final Endpoint<PatchAgentRuleRequestDTO, AgentRuleDTO> PATCH_AGENT_RULE =
            Endpoint.createContract("/api/v1/agents/{agentId}/rules/{ruleId}",
                    HttpMethod.PATCH,
                    PatchAgentRuleRequestDTO.class,
                    AgentRuleDTO.class,
                    (MockmvcDefault) context -> context.expectStatus(HttpStatus.OK.value())
                            .withRequest("requestDefaultPatchAgentRuleWithHappyPath.json")
                            .expectResponse("responseDefaultPatchAgentRuleWithHappyPath.json"),
                    ItUserTokens.USER_JWT);

    public static final Endpoint<Void, DeleteAgentRuleResponseDTO> DELETE_AGENT_RULE =
            Endpoint.createContract("/api/v1/agents/{agentId}/rules/{ruleId}",
                    HttpMethod.DELETE,
                    Void.class,
                    DeleteAgentRuleResponseDTO.class,
                    (MockmvcDefault) context -> context.expectStatus(HttpStatus.OK.value())
                            .expectResponse("responseDefaultDeleteAgentRuleWithHappyPath.json"),
                    ItUserTokens.USER_JWT);
}
