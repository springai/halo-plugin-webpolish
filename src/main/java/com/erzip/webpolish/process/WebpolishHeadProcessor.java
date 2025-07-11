package com.erzip.webpolish.process;


import com.erzip.webpolish.service.FeatureProcessorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IModel;
import org.thymeleaf.processor.element.IElementModelStructureHandler;
import reactor.core.publisher.Mono;
import run.halo.app.theme.dialect.TemplateHeadProcessor;

@Component
@RequiredArgsConstructor
public class WebpolishHeadProcessor implements TemplateHeadProcessor {
    private final FeatureProcessorService featureProcessorService;

    @Override
    public Mono<Void> process(ITemplateContext context, IModel model,
        IElementModelStructureHandler structureHandler) {
        return featureProcessorService.processFeatures(context, model);
    }
}