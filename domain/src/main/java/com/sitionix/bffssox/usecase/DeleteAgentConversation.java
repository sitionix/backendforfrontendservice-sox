package com.sitionix.bffssox.usecase;

import java.util.UUID;

/**
 * Use case for soft deleting one direct conversation.
 */
public interface DeleteAgentConversation {

    /**
     * Soft deletes one direct conversation.
     *
     * @param conversationId conversation identifier.
     */
    void execute(UUID conversationId);
}
