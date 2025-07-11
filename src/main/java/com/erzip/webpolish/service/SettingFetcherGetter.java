package com.erzip.webpolish.service;

import com.erzip.webpolish.record.BubbleSetting;
import com.erzip.webpolish.record.CursorSetting;
import reactor.core.publisher.Mono;

public interface SettingFetcherGetter {

    //获取气泡上升配置信息
    Mono<BubbleSetting> getBubbleConfig();

    //获取鼠标配置信息
    Mono<CursorSetting> getCursorConfig();
}
