package com.folksdev.account.util;

import java.util.UUID;

public class CustomUUIDGenerator {
    public static String generatePartitionUUID() {
        return UUID.randomUUID().toString().split("-")[0];
    }
}
