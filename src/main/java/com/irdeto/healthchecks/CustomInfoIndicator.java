package com.irdeto.healthchecks;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

@Component
public class CustomInfoIndicator implements InfoContributor {
    @Override
    public void contribute(Info.Builder builder) {
        builder.withDetail("MyKey", 123).build();
    }
}
