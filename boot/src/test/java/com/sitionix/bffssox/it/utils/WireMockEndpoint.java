package com.sitionix.bffssox.it.utils;

import com.app_afesox.athssox.client.dto.EmailVerificationDTO;
import com.app_afesox.athssox.client.dto.EmailVerificationResponseDTO;
import com.app_afesox.athssox.client.dto.LoginRequestDTO;
import com.app_afesox.athssox.client.dto.LoginResponseDTO;
import com.app_afesox.atmssox.client.dto.AgentConversationDetailsDTO;
import com.app_afesox.atmssox.client.dto.AgentConversationsResponseDTO;
import com.app_afesox.atmssox.client.dto.AgentDTO;
import com.app_afesox.atmssox.client.dto.AgentRuleDTO;
import com.app_afesox.atmssox.client.dto.AgentRulesResponseDTO;
import com.app_afesox.atmssox.client.dto.ChatAgentRequestDTO;
import com.app_afesox.atmssox.client.dto.SubmitChatExecutionResponseDTO;
import com.app_afesox.atmssox.client.dto.CreateAgentRuleRequestDTO;
import com.app_afesox.atmssox.client.dto.CreateAgentRequestDTO;
import com.app_afesox.atmssox.client.dto.DeleteAgentRuleResponseDTO;
import com.app_afesox.atmssox.client.dto.PatchAgentRuleRequestDTO;
import com.app_afesox.atmssox.client.dto.PatchAgentRequestDTO;
import com.app_afesox.athssox.client.dto.RefreshAccessTokenRequestDTO;
import com.app_afesox.athssox.client.dto.RefreshAccessTokenResponseDTO;
import com.app_afesox.athssox.client.dto.ResendEmailVerificationResponseDTO;
import com.app_afesox.athssox.client.dto.RegisterUserDTO;
import com.app_afesox.athssox.client.dto.ResponseRegisterUserDTO;
import com.app_afesox.wagssox.client.dto.SiteOverviewDTO;
import com.app_afesox.stsssox.client.dto.CreateSiteRequestDTO;
import com.app_afesox.stsssox.client.dto.CreateSiteResponseDTO;
import com.app_afesox.wagssox.client.dto.WorkspaceSitesPageDTO;
import com.sitionix.forgeit.domain.endpoint.Endpoint;
import com.sitionix.forgeit.domain.endpoint.HttpMethod;
import com.sitionix.forgeit.domain.endpoint.wiremock.WiremockDefault;
import com.sitionix.forgeit.wiremock.api.Parameter;

public class WireMockEndpoint {

    public static final Endpoint<LoginRequestDTO, LoginResponseDTO> POST_LOGIN_USER =
            Endpoint.createContract("/authsox/api/v1/auth/login",
                    HttpMethod.POST,
                    LoginRequestDTO.class,
                    LoginResponseDTO.class,
                    (WiremockDefault) context -> {
                        context.matchesJson("requestDefaultMappingLoginUserWithHappyPath.json")
                                .responseBody("responseDefaultMappingLoginUserWithHappyPath.json")
                                .plainUrl()
                                .responseStatus(200);
                    });

    public static final Endpoint<EmailVerificationDTO, EmailVerificationResponseDTO> POST_VERIFY_EMAIL =
            Endpoint.createContract("/authsox/api/v1/auth/email/verify",
                    HttpMethod.POST,
                    EmailVerificationDTO.class,
                    EmailVerificationResponseDTO.class,
                    (WiremockDefault) context -> {
                        context.matchesJson("requestDefaultMappingVerifyEmailWithHappyPath.json")
                                .responseBody("responseDefaultMappingVerifyEmailWithHappyPath.json")
                                .plainUrl()
                                .responseStatus(200);
                    });

    public static final Endpoint<RefreshAccessTokenRequestDTO, RefreshAccessTokenResponseDTO> POST_REFRESH_ACCESS_TOKEN =
            Endpoint.createContract("/authsox/api/v1/auth/refresh",
                    HttpMethod.POST,
                    RefreshAccessTokenRequestDTO.class,
                    RefreshAccessTokenResponseDTO.class,
                    (WiremockDefault) context -> {
                        context.matchesJson("requestDefaultMappingRefreshAccessTokenWithHappyPath.json")
                                .responseBody("responseDefaultMappingRefreshAccessTokenWithHappyPath.json")
                                .plainUrl()
                                .responseStatus(200);
                    });

    public static final Endpoint<Object, ResendEmailVerificationResponseDTO> POST_RESEND_EMAIL_VERIFICATION =
            Endpoint.createContract("/authsox/api/v1/auth/email/verify/resend",
                    HttpMethod.POST,
                    Object.class,
                    ResendEmailVerificationResponseDTO.class,
                    (WiremockDefault) context -> {
                        context.matchesJson("requestDefaultMappingResendEmailVerificationWithHappyPath.json")
                                .header("X-Forge-User-Sub", Parameter.equalTo("it-user-123"))
                                .header("Authorization", Parameter.matches("Bearer\\s+.+"))
                                .responseBody("responseDefaultMappingResendEmailVerificationWithHappyPath.json")
                                .plainUrl()
                                .responseStatus(202);
                    });

    public static final Endpoint<Object, Object> GET_JWKS =
            Endpoint.createContract("/authsox/.well-known/jwks.json",
                    HttpMethod.GET,
                    Object.class,
                    Object.class,
                    (WiremockDefault) context -> {
                        context.responseBody("responseDefaultMappingJwks.json")
                                .plainUrl()
                                .responseStatus(200);
                    });

    public static final Endpoint<RegisterUserDTO, ResponseRegisterUserDTO> POST_REGISTER_USER =
            Endpoint.createContract("/authsox/api/v1/users",
                    HttpMethod.POST,
                    RegisterUserDTO.class,
                    ResponseRegisterUserDTO.class,
                    (WiremockDefault) context -> {
                        context.matchesJson("requestDefaultMappingRegisterUserWithHappyPath.json")
                                .responseBody("responseDefaultMappingRegisterUserWithHappyPath.json")
                                .plainUrl()
                                .responseStatus(200);
                    });

    public static final Endpoint<CreateSiteRequestDTO, CreateSiteResponseDTO> POST_CREATE_SITE =
            Endpoint.createContract("/stsssox/api/v1/sites",
                    HttpMethod.POST,
                    CreateSiteRequestDTO.class,
                    CreateSiteResponseDTO.class,
                    (WiremockDefault) context -> {
                        context.matchesJson("requestDefaultMappingCreateSiteWithHappyPath.json")
                                .header("X-Forge-User-Sub", Parameter.equalTo("it-user-123"))
                                .header("Authorization", Parameter.matches("Bearer\\s+.+"))
                                .responseBody("responseDefaultMappingCreateSiteWithHappyPath.json")
                                .plainUrl()
                                .responseStatus(201);
                    });

    public static final Endpoint<Void, WorkspaceSitesPageDTO> GET_SITES =
            Endpoint.createContract("/wagssox/api/v1/sites",
                    HttpMethod.GET,
                    Void.class,
                    WorkspaceSitesPageDTO.class,
                    (WiremockDefault) context -> {
                        context.header("X-Forge-User-Sub", Parameter.equalTo("it-user-123"))
                                .header("Authorization", Parameter.matches("Bearer\\s+.+"))
                                .responseBody("responseDefaultMappingGetSitesFirstPageWithHappyPath.json")
                                .plainUrl()
                                .responseStatus(200);
                    });

    public static final Endpoint<Void, SiteOverviewDTO> GET_SITE_OVERVIEW =
            Endpoint.createContract("/wagssox/api/v1/sites/{siteId}/overview",
                    HttpMethod.GET,
                    Void.class,
                    SiteOverviewDTO.class,
                    (WiremockDefault) context -> {
                        context.header("X-Forge-User-Sub", Parameter.equalTo("it-user-123"))
                                .header("Authorization", Parameter.matches("Bearer\\s+.+"))
                                .responseBody("responseDefaultMappingGetSiteOverviewWithHappyPath.json")
                                .responseStatus(200);
                    });

    public static final Endpoint<Void, AgentDTO> POST_ACTIVATE_AGENT =
            Endpoint.createContract("/atmssox/api/v1/agents/{agentId}/activate",
                    HttpMethod.POST,
                    Void.class,
                    AgentDTO.class,
                    (WiremockDefault) context -> {
                        context.header("X-Forge-User-Sub", Parameter.equalTo("it-user-123"))
                                .header("Authorization", Parameter.matches("Bearer\\s+.+"))
                                .responseBody("responseDefaultMappingActivateAgentWithHappyPath.json")
                                .responseStatus(200);
                    });

    public static final Endpoint<Void, AgentDTO> POST_ARCHIVE_AGENT =
            Endpoint.createContract("/atmssox/api/v1/agents/{agentId}/archive",
                    HttpMethod.POST,
                    Void.class,
                    AgentDTO.class,
                    (WiremockDefault) context -> {
                        context.header("X-Forge-User-Sub", Parameter.equalTo("it-user-123"))
                                .header("Authorization", Parameter.matches("Bearer\\s+.+"))
                                .responseBody("responseDefaultMappingArchiveAgentWithHappyPath.json")
                                .responseStatus(200);
                    });

    public static final Endpoint<Void, AgentDTO> POST_RESTORE_AGENT =
            Endpoint.createContract("/atmssox/api/v1/agents/{agentId}/restore",
                    HttpMethod.POST,
                    Void.class,
                    AgentDTO.class,
                    (WiremockDefault) context -> {
                        context.header("X-Forge-User-Sub", Parameter.equalTo("it-user-123"))
                                .header("Authorization", Parameter.matches("Bearer\\s+.+"))
                                .responseBody("responseDefaultMappingRestoreAgentWithHappyPath.json")
                                .responseStatus(200);
                    });

    public static final Endpoint<Void, AgentDTO> DELETE_AGENT =
            Endpoint.createContract("/atmssox/api/v1/agents/{agentId}",
                    HttpMethod.DELETE,
                    Void.class,
                    AgentDTO.class,
                    (WiremockDefault) context -> {
                        context.header("X-Forge-User-Sub", Parameter.equalTo("it-user-123"))
                                .header("Authorization", Parameter.matches("Bearer\\s+.+"))
                                .responseBody("responseDefaultMappingDeleteAgentWithHappyPath.json")
                                .responseStatus(200);
                    });

    public static final Endpoint<CreateAgentRequestDTO, AgentDTO> POST_CREATE_AGENT =
            Endpoint.createContract("/atmssox/api/v1/agents",
                    HttpMethod.POST,
                    CreateAgentRequestDTO.class,
                    AgentDTO.class,
                    (WiremockDefault) context -> {
                        context.responseBody("responseDefaultMappingCreateAgentWithNameOnly.json")
                                .plainUrl()
                                .responseStatus(201);
                    });

    public static final Endpoint<Void, AgentDTO> GET_AGENT =
            Endpoint.createContract("/atmssox/api/v1/agents/{agentId}",
                    HttpMethod.GET,
                    Void.class,
                    AgentDTO.class,
                    (WiremockDefault) context -> {
                        context.responseBody("responseDefaultMappingGetAgentWithInstruction.json")
                                .responseStatus(200);
                    });

    public static final Endpoint<Void, AgentConversationsResponseDTO> GET_AGENT_CONVERSATIONS =
            Endpoint.createContract("/atmssox/api/v1/agents/{agentId}/conversations",
                    HttpMethod.GET,
                    Void.class,
                    AgentConversationsResponseDTO.class,
                    (WiremockDefault) context -> {
                        context.header("X-Forge-User-Sub", Parameter.equalTo("it-user-123"))
                                .header("Authorization", Parameter.matches("Bearer\\s+.+"))
                                .responseBody("responseDefaultMappingGetAgentConversationsWithHappyPath.json")
                                .responseStatus(200);
                    });

    public static final Endpoint<Void, AgentConversationDetailsDTO> GET_AGENT_CONVERSATION =
            Endpoint.createContract("/atmssox/api/v1/conversations/{conversationId}",
                    HttpMethod.GET,
                    Void.class,
                    AgentConversationDetailsDTO.class,
                    (WiremockDefault) context -> {
                        context.header("X-Forge-User-Sub", Parameter.equalTo("it-user-123"))
                                .header("Authorization", Parameter.matches("Bearer\\s+.+"))
                                .responseBody("responseDefaultMappingGetAgentConversationWithHappyPath.json")
                                .responseStatus(200);
                    });

    public static final Endpoint<ChatAgentRequestDTO, SubmitChatExecutionResponseDTO> POST_CHAT_AGENT =
            Endpoint.createContract("/atmssox/api/v1/agents/{agentId}/chat/executions",
                    HttpMethod.POST,
                    ChatAgentRequestDTO.class,
                    SubmitChatExecutionResponseDTO.class,
                    (WiremockDefault) context -> {
                        context.header("X-Forge-User-Sub", Parameter.equalTo("it-user-123"))
                                .header("Authorization", Parameter.matches("Bearer\\s+.+"))
                                .responseBody("responseDefaultMappingSubmitChatExecutionNewConversation.json")
                                .responseStatus(202);
                    });

    public static final Endpoint<PatchAgentRequestDTO, AgentDTO> PATCH_AGENT =
            Endpoint.createContract("/atmssox/api/v1/agents/{agentId}",
                    HttpMethod.PATCH,
                    PatchAgentRequestDTO.class,
                    AgentDTO.class,
                    (WiremockDefault) context -> {
                        context.responseBody("responseDefaultMappingPatchAgentInstructionOnly.json")
                                .responseStatus(200);
                    });

    public static final Endpoint<Void, AgentRulesResponseDTO> GET_AGENT_RULES =
            Endpoint.createContract("/atmssox/api/v1/agents/{agentId}/rules",
                    HttpMethod.GET,
                    Void.class,
                    AgentRulesResponseDTO.class,
                    (WiremockDefault) context -> {
                        context.responseBody("responseDefaultMappingGetAgentRulesWithHappyPath.json")
                                .responseStatus(200);
                    });

    public static final Endpoint<CreateAgentRuleRequestDTO, AgentRuleDTO> POST_CREATE_AGENT_RULE =
            Endpoint.createContract("/atmssox/api/v1/agents/{agentId}/rules",
                    HttpMethod.POST,
                    CreateAgentRuleRequestDTO.class,
                    AgentRuleDTO.class,
                    (WiremockDefault) context -> {
                        context.matchesJson("requestDefaultMappingCreateAgentRuleWithHappyPath.json")
                                .responseBody("responseDefaultMappingCreateAgentRuleWithHappyPath.json")
                                .responseStatus(201);
                    });

    public static final Endpoint<PatchAgentRuleRequestDTO, AgentRuleDTO> PATCH_AGENT_RULE =
            Endpoint.createContract("/atmssox/api/v1/agents/{agentId}/rules/{ruleId}",
                    HttpMethod.PATCH,
                    PatchAgentRuleRequestDTO.class,
                    AgentRuleDTO.class,
                    (WiremockDefault) context -> {
                        context.matchesJson("requestDefaultMappingPatchAgentRuleWithHappyPath.json")
                                .responseBody("responseDefaultMappingPatchAgentRuleWithHappyPath.json")
                                .responseStatus(200);
                    });

    public static final Endpoint<Void, DeleteAgentRuleResponseDTO> DELETE_AGENT_RULE =
            Endpoint.createContract("/atmssox/api/v1/agents/{agentId}/rules/{ruleId}",
                    HttpMethod.DELETE,
                    Void.class,
                    DeleteAgentRuleResponseDTO.class,
                    (WiremockDefault) context -> {
                        context.responseBody("responseDefaultMappingDeleteAgentRuleWithHappyPath.json")
                                .responseStatus(200);
                    });
}
