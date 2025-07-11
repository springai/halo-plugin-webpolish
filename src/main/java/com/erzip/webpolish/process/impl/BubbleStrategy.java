package com.erzip.webpolish.process.impl;

import com.erzip.webpolish.record.WebPolishSettings;
import com.erzip.webpolish.record.BubbleSetting;
import com.erzip.webpolish.process.FeatureStrategy;
import com.erzip.webpolish.util.BubbleBackgroundBuilder;
import com.erzip.webpolish.util.HexToRgbConverter;
import org.thymeleaf.model.IModel;
import org.thymeleaf.model.IModelFactory;
import org.springframework.stereotype.Component;

import static com.erzip.webpolish.util.BubbleBackgroundBuilder.buildFullScript;

@Component
public class BubbleStrategy implements FeatureStrategy {

    @Override
    public void apply(IModel model, IModelFactory modelFactory, WebPolishSettings settings) {
        BubbleSetting bubbleStyle = settings.bubbleSetting();
        if (!bubbleStyle.bubbleEnabled()) {
            return;
        }

        String colorRgb = HexToRgbConverter.safeHexToRgb(
            bubbleStyle.color(),
            "rgb(255,255,255)" // 默认白色
        );

        BubbleBackgroundBuilder.BubbleConfig config = new BubbleBackgroundBuilder.BubbleConfig(
            colorRgb,
            bubbleStyle.radius(),
            bubbleStyle.density(),
            bubbleStyle.clearOffset(),
            bubbleStyle.minSpeed(),
            bubbleStyle.maxSpeed(),
            bubbleStyle.minScale(),
            bubbleStyle.maxScale()
        );

        String script = buildFullScript(config);
        if (script != null && !script.isBlank()) {
            model.add(modelFactory.createText(script));
        }
    }
}