package com.erzip.webpolish.service.impl;

import com.erzip.webpolish.record.BubbleSetting;
import com.erzip.webpolish.record.CursorSetting;
import com.erzip.webpolish.service.SettingFetcherGetter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import run.halo.app.plugin.ReactiveSettingFetcher;

@Slf4j
@Component
@RequiredArgsConstructor
public class SettingFetcherGetterImpl implements SettingFetcherGetter {
    private final ReactiveSettingFetcher settingFetcher;

    // 默认气泡配置（禁用状态）
    private static final BubbleSetting DEFAULT_BUBBLE_SETTING = new BubbleSetting(
        false, "#FFFFFF", 0, 0D, 0D, 0D, 0D, 0D, 0D
    );

    // 默认光标配置（禁用状态）
    private static final CursorSetting DEFAULT_CURSOR_SETTING = new CursorSetting(
        false, "", "", "", ""
    );

    @Override
    public Mono<BubbleSetting> getBubbleConfig() {
        return settingFetcher.fetch(BubbleSetting.GROUP, BubbleSetting.class)
            .onErrorResume(e -> {
                log.error("[WebPolish] Failed to load bubble configuration. Using default settings", e);
                return Mono.just(DEFAULT_BUBBLE_SETTING);
            })
            .defaultIfEmpty(DEFAULT_BUBBLE_SETTING);
    }

    @Override
    public Mono<CursorSetting> getCursorConfig() {
        return settingFetcher.fetch(CursorSetting.GROUP, CursorSetting.class)
            .onErrorResume(e -> {
                log.error("[WebPolish] Failed to load cursor configuration. Using default settings", e);
                return Mono.just(DEFAULT_CURSOR_SETTING);
            })
            .defaultIfEmpty(DEFAULT_CURSOR_SETTING);
    }
}