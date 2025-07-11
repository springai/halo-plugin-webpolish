package com.erzip.webpolish.record;

public record BubbleSetting(
    Boolean bubbleEnabled,
    String color,          // 气泡颜色
    Integer radius,            // 气泡最大半径
    Double density,        // 气泡密度
    Double clearOffset,    // 透明度随机范围
    Double minSpeed,       // 最小上升速度
    Double maxSpeed,       // 最大上升速度
    Double minScale,       // 最小缩放比例
    Double maxScale        // 最大缩放比例
) {
    public static final String GROUP = "style_setting_bubbleBackground";
}
