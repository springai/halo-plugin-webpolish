package com.erzip.webpolish.util;

public class HexToRgbConverter {

    private HexToRgbConverter(){}

    // 默认颜色值
    private static final String DEFAULT_RGB = "rgb(160, 228, 124)";

    public static String safeHexToRgb(String hex, String defaultValue) {
        try {
            return hexToRgb(hex);
        } catch (IllegalArgumentException e) {
            return defaultValue;
        }
    }

    public static String hexToRgb(String hex) {
        // 清理输入
        String cleanHex = hex.replace("#", "").trim();

        // 处理3位简写格式
        if (cleanHex.length() == 3) {
            cleanHex = new String(new char[] {
                cleanHex.charAt(0), cleanHex.charAt(0),
                cleanHex.charAt(1), cleanHex.charAt(1),
                cleanHex.charAt(2), cleanHex.charAt(2)
            });
        }

        // 验证长度
        if (cleanHex.length() != 6) {
            return DEFAULT_RGB;
        }

        // 解析颜色分量
        try {
            int r = Integer.parseInt(cleanHex.substring(0, 2), 16);
            int g = Integer.parseInt(cleanHex.substring(2, 4), 16);
            int b = Integer.parseInt(cleanHex.substring(4, 6), 16);
            return "rgb(" + r + ", " + g + ", " + b + ")";
        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
            // 捕获所有可能的解析异常
            return DEFAULT_RGB;
        }
    }
}