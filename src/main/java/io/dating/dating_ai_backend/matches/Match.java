package io.dating.dating_ai_backend.matches;

import io.dating.dating_ai_backend.profiles.Profile;

public record Match(String id, Profile profile, String conversationId) {
}