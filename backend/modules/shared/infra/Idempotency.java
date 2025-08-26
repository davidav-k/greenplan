package com.greenplan.infra;

import java.util.UUID;

public final class Idempotency {
    public static String newKey() {
        return UUID.randomUUID().toString();
    }
}