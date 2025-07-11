package com.erzip.webpolish.util;

public class CursorStyleBuilder {

    // 内部 Record 类型定义光标配置
    public record CursorConfig(
        String normalCursorUrl,      // 普通光标完整URL
        String helpCursorUrl,        // 帮助光标完整URL
        String textCursorUrl,        // 文本光标完整URL
        String handwritingCursorUrl  // 手写光标完整URL
    ) {
        // 提供默认配置
        public static CursorConfig defaultConfig() {
            return new CursorConfig(
                "https://erzip.com/upload/1-Normal-Select.cur",
                "https://erzip.com/upload/1-Help-Select.cur",
                "https://erzip.com/upload/1-Text-Select.cur",
                "https://erzip.com/upload/1-Handwriting.cur"
            );
        }

        // 验证配置有效性
        public boolean isValid() {
            return normalCursorUrl != null && !normalCursorUrl.isBlank() &&
                helpCursorUrl != null && !helpCursorUrl.isBlank() &&
                textCursorUrl != null && !textCursorUrl.isBlank() &&
                handwritingCursorUrl != null && !handwritingCursorUrl.isBlank();
        }
    }

    // 私有构造方法防止实例化
    private CursorStyleBuilder() {}

    // 1. 使用默认配置生成CSS
    public static String buildDefaultStyle() {
        return buildStyle(CursorConfig.defaultConfig());
    }

    // 2. 根据配置生成CSS
    public static String buildStyle(CursorConfig config) {
        if (config == null || !config.isValid()) {
            throw new IllegalArgumentException("Invalid cursor configuration");
        }

        return String.format("""
            <style>
              body {
                cursor: url(%s), auto;
              }
              button, a:hover {
                cursor: url(%s), pointer;
              }
              input {
                cursor: url(%s), text;
              }
              textarea, input:focus {
                cursor: url(%s), text;
              }
            </style>
            """,
            config.normalCursorUrl(),
            config.helpCursorUrl(),
            config.textCursorUrl(),
            config.handwritingCursorUrl()
        );
    }

    // 3. 直接使用URL生成CSS（无需创建配置对象）
    public static String buildStyle(
        String normalCursorUrl,
        String helpCursorUrl,
        String textCursorUrl,
        String handwritingCursorUrl
    ) {
        return buildStyle(new CursorConfig(
            normalCursorUrl,
            helpCursorUrl,
            textCursorUrl,
            handwritingCursorUrl
        ));
    }
}