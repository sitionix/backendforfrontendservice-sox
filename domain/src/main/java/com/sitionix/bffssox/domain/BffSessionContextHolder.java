package com.sitionix.bffssox.domain;

public final class BffSessionContextHolder {

    private static final ThreadLocal<BffResolvedSession> HOLDER = new ThreadLocal<>();

    private BffSessionContextHolder() {
    }

    public static void set(final BffResolvedSession session) {
        HOLDER.set(session);
    }

    public static BffResolvedSession get() {
        return HOLDER.get();
    }

    public static void clear() {
        HOLDER.remove();
    }
}
