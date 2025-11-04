package com.sitionix.bffssox.it.infra.utils.wiremock.verify;

import com.sitionix.bffssox.it.infra.utils.wiremock.WireMockJournalClient;
import com.sitionix.bffssox.it.infra.utils.wiremock.domain.check.WireMockCheck;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.IntSupplier;

import static com.sitionix.bffssox.it.infra.utils.comparator.JsonMatcher.jsonEqualsIgnore;
import static java.util.Objects.nonNull;

@Component
@RequiredArgsConstructor
public class WireMockValidator {

    private final WireMockJournalClient journalClient;

    public void validate(final WireMockCheck check) {
        final IntSupplier liveCount = () -> this.journalClient.findBodiesByUrl(check.endpoint()).size();
        try {
            if (check.atLeastTimes() <= 0) {
                throw new IllegalArgumentException("atLeastTimes must be greater than zero");
            }
            verifyExactTimesWithBackoff(liveCount, check.atLeastTimes());
            if (nonNull(check.expectedJson())) {
                final String[] fieldsForIgnore = check.ignoreFields().toArray(new String[0]);
                final var actualJsons = this.journalClient.findBodiesByUrl(check.endpoint());
                final boolean anyMatch = actualJsons.stream().anyMatch(jsonEqualsIgnore(check.expectedJson(), fieldsForIgnore));
                if (!anyMatch) {
                    throw new AssertionError("No matching JSON found for endpoint: " + check.endpoint());
                }
            }
        } catch (final Exception e) {
            throw new AssertionError(e);
        } finally {
            if (check.cleanAfterVerify()) {
                this.journalClient.deleteMapping(check.id());
            }
        }
    }

    private void verifyExactTimesWithBackoff(final IntSupplier actual, final int expected) {
        final int count = actual.getAsInt();
        if (count != expected) {
            throw new AssertionError("Request count is " + count + " but expected " + expected);
        }
    }
}