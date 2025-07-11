package com.erzip.webpolish.process.impl;

import com.erzip.webpolish.record.WebPolishSettings;
import com.erzip.webpolish.record.CursorSetting;
import com.erzip.webpolish.process.FeatureStrategy;
import com.erzip.webpolish.util.CursorStyleBuilder;
import org.thymeleaf.model.IModel;
import org.thymeleaf.model.IModelFactory;
import org.springframework.stereotype.Component;

@Component
public class CursorStrategy implements FeatureStrategy {

    @Override
    public void apply(IModel model, IModelFactory modelFactory, WebPolishSettings settings) {
        CursorSetting cursorStyle = settings.cursorSetting();
        if (!cursorStyle.cursorEnabled()) {
            return;
        }

        String cursorCss = CursorStyleBuilder.buildStyle(
            cursorStyle.normalCursorUrl(),
            cursorStyle.helpCursorUrl(),
            cursorStyle.textCursorUrl(),
            cursorStyle.handwritingCursorUrl()
        );

        if (cursorCss != null && !cursorCss.isBlank()) {
            model.add(modelFactory.createText(cursorCss));
        }
    }
}