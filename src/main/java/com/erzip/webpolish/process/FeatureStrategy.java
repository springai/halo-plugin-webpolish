package com.erzip.webpolish.process;

import org.thymeleaf.model.IModel;
import org.thymeleaf.model.IModelFactory;
import com.erzip.webpolish.record.WebPolishSettings;

@FunctionalInterface
public interface FeatureStrategy {
    void apply(IModel model, IModelFactory modelFactory, WebPolishSettings settings);
}