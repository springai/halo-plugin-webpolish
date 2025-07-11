package com.erzip.webpolish.record;

public record CursorSetting(
    Boolean cursorEnabled,       // 是否启用光标
    String normalCursorUrl,      // 普通光标完整URL
    String helpCursorUrl,        // 帮助光标完整URL
    String textCursorUrl,        // 文本光标完整URL
    String handwritingCursorUrl  // 手写光标完整URL
) {
    public static final String GROUP = "style_setting_cursor";
}
