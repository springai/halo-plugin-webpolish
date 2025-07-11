package com.erzip.webpolish.service.impl;

import com.erzip.webpolish.record.WebPolishSettings;
import com.erzip.webpolish.process.FeatureStrategy;
import com.erzip.webpolish.service.FeatureProcessorService;
import com.erzip.webpolish.service.SettingFetcherGetter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IModel;
import org.thymeleaf.model.IModelFactory;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class FeatureProcessorServiceImpl implements FeatureProcessorService {
    private final SettingFetcherGetter settingFetcherGetter;
    private final List<FeatureStrategy> featureStrategies;

    /**
     * 处理并添加所有WebPolish功能
     */
    public Mono<Void> processFeatures(ITemplateContext context, IModel model) {
        return addWebPolishFeatures(context, model);
    }

    /**
     * 添加所有WebPolish功能
     */
    private Mono<Void> addWebPolishFeatures(ITemplateContext context, IModel model) {
        return Mono.zip(
                settingFetcherGetter.getBubbleConfig(),
                settingFetcherGetter.getCursorConfig()
            )
            .doOnError(e -> log.error("[WebPolish] Failed to load settings", e))
            .onErrorResume(e -> Mono.empty())
            .flatMap(tuple -> {
                final IModelFactory modelFactory = context.getModelFactory();
                WebPolishSettings settings = new WebPolishSettings(tuple.getT1(), tuple.getT2());
                applyAllStrategies(model, modelFactory, settings);
                return Mono.empty();
            });
    }

    /**
     * 应用所有功能策略
     */
    private void applyAllStrategies(IModel model,
        IModelFactory modelFactory,
        WebPolishSettings settings) {
        for (FeatureStrategy strategy : featureStrategies) {
            try {
                strategy.apply(model, modelFactory, settings);
            } catch (Exception e) {
                handleStrategyError(strategy, model, modelFactory, e);
            }
        }
    }

    /**
     * 处理策略执行错误
     */
    private void handleStrategyError(FeatureStrategy strategy,
        IModel model,
        IModelFactory modelFactory,
        Exception e) {
        String strategyName = strategy.getClass().getSimpleName();
        String errorMsg = String.format(
            "[WebPolish] Feature strategy '%s' failed: %s",
            strategyName,
            e.getMessage()
        );

        log.error(errorMsg, e);

        if (log.isDebugEnabled()) {
            String errorComment = String.format(
                "<!-- [WebPolish] Feature strategy '%s' failed: %s -->",
                strategyName,
                e.getMessage().replace("--", "")
            );
            model.add(modelFactory.createText(errorComment));
        }
    }
}