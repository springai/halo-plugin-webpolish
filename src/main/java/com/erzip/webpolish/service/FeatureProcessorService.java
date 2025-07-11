package com.erzip.webpolish.service;

import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IModel;
import reactor.core.publisher.Mono;

public interface FeatureProcessorService {
    /**
     * 处理并添加所有WebPolish功能
     */
    Mono<Void> processFeatures(ITemplateContext context, IModel model);


}
