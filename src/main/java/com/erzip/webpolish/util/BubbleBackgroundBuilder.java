package com.erzip.webpolish.util;

import java.util.Map;
import java.util.HashMap;

public class BubbleBackgroundBuilder {

    private BubbleBackgroundBuilder(){}
    public static record BubbleConfig(
        String color,          // 气泡颜色
        int radius,            // 气泡最大半径 (建议范围: 5-30)
        double density,        // 气泡密度 (建议范围: 0.005-0.05)
        double clearOffset,    // 透明度随机范围 (建议范围: 0.1-1.0)
        double minSpeed,       // 最小上升速度 (建议范围: 0.1-1.0)
        double maxSpeed,       // 最大上升速度 (建议范围: 0.5-3.0)
        double minScale,       // 最小缩放比例 (建议范围: 0.05-0.3)
        double maxScale        // 最大缩放比例 (建议范围: 0.2-1.0)
    ) {
        // 默认配置和验证逻辑
        public BubbleConfig {
            if (color == null) color = "rgb(225, 82, 215)";
            if (radius <= 0) radius = 10;
            if (density <= 0) density = 0.01;
            if (clearOffset <= 0) clearOffset = 0.7;
            if (minSpeed <= 0) minSpeed = 0.2;
            if (maxSpeed <= minSpeed) maxSpeed = 1.2;
            if (minScale <= 0) minScale = 0.1;
            if (maxScale <= minScale) maxScale = 0.4;
        }
    }

    // 构建气泡背景的完整脚本
    public static String buildFullScript(BubbleConfig config) {
        if (config == null) {
            config = new BubbleConfig(null, 0, 0D, 0D, 0D, 0D, 0D, 0D);
        }

        // 构建设置对象
        String settings = String.format(
            "{\n" +
                "  color: '%s',\n" +
                "  radius: %d,\n" +
                "  density: %.4f,\n" +
                "  clearOffset: %.4f,\n" +
                "  minSpeed: %.4f,\n" +
                "  maxSpeed: %.4f,\n" +
                "  minScale: %.4f,\n" +
                "  maxScale: %.4f\n" +
                "}",
            config.color(),
            config.radius(),
            config.density(),
            config.clearOffset(),
            config.minSpeed(),
            config.maxSpeed(),
            config.minScale(),
            config.maxScale()
        );

        // 构建完整脚本
        return String.format(
            "<script>\n" +
                "document.addEventListener('DOMContentLoaded', function() {\n" +
                "  // 创建气泡容器\n" +
                "  const container = document.createElement('div');\n" +
                "  container.id = 'bubbles-background';\n" +
                "  container.style.position = 'fixed';\n" +
                "  container.style.top = '0';\n" +
                "  container.style.left = '0';\n" +
                "  container.style.width = '100vw';\n" +
                "  container.style.height = '100vh';\n" +
                "  container.style.zIndex = '-1';\n" +
                "  container.style.pointerEvents = 'none';\n" +
                "  container.style.overflow = 'hidden';\n" +
                "  document.body.appendChild(container);\n" +
                "\n" +
                "  // 初始化画布\n" +
                "  const canvas = document.createElement('canvas');\n" +
                "  canvas.id = 'bubbles-canvas';\n" +
                "  canvas.style.position = 'absolute';\n" +
                "  canvas.style.top = '0';\n" +
                "  canvas.style.left = '0';\n" +
                "  canvas.style.width = '100%%';\n" +
                "  canvas.style.height = '100%%';\n" +
                "  container.appendChild(canvas);\n" +
                "\n" +
                "  // 启动动画\n" +
                "  circleMagic();\n" +
                "\n" +
                "  function circleMagic(options) {\n" +
                "    // 气泡效果设置\n" +
                "    const settings = options || %s;\n" +
                "\n" +
                "    let width = window.innerWidth;\n" +
                "    let height = window.innerHeight;\n" +
                "    const ctx = canvas.getContext('2d');\n" +
                "    const circles = [];\n" +
                "    let animationRunning = true;\n" +
                "\n" +
                "    // 设置画布尺寸\n" +
                "    function resizeCanvas() {\n" +
                "      width = window.innerWidth;\n" +
                "      height = window.innerHeight;\n" +
                "      canvas.width = width;\n" +
                "      canvas.height = height;\n" +
                "      initCircles();\n" +
                "    }\n" +
                "\n" +
                "    // 初始化粒子\n" +
                "    function initCircles() {\n" +
                "      circles.length = 0;\n" +
                "      const particleCount = Math.floor(width * height * settings.density / 800);\n" +
                "      for (let i = 0; i < particleCount; i++) {\n" +
                "        circles.push(new Circle());\n" +
                "      }\n" +
                "    }\n" +
                "\n" +
                "    // 粒子类\n" +
                "    class Circle {\n" +
                "      constructor() {\n" +
                "        this.pos = { x: 0, y: 0 };\n" +
                "        this.init();\n" +
                "      }\n" +
                "\n" +
                "      init() {\n" +
                "        this.pos.x = Math.random() * width;\n" +
                "        this.pos.y = height + Math.random() * 200;\n" +
                "        this.alpha = 0.3 + Math.random() * settings.clearOffset;\n" +
                "        this.scale = settings.minScale + Math.random() * (settings.maxScale - settings.minScale);\n" +
                "        this.speed = settings.minSpeed + Math.random() * (settings.maxSpeed - settings.minSpeed);\n" +
                "        this.color = settings.color === 'random' ? this.randomColor() : settings.color;\n" +
                "      }\n" +
                "\n" +
                "      update() {\n" +
                "        this.pos.y -= this.speed;\n" +
                "        this.alpha -= 0.0003;\n" +
                "        \n" +
                "        if (this.alpha <= 0 || this.pos.y < -50) {\n" +
                "          this.init();\n" +
                "        }\n" +
                "      }\n" +
                "\n" +
                "      draw() {\n" +
                "        ctx.beginPath();\n" +
                "        ctx.arc(this.pos.x, this.pos.y, this.scale * settings.radius, 0, Math.PI * 2);\n" +
                "        \n" +
                "        if (this.color.startsWith('rgb')) {\n" +
                "          const rgba = this.color.replace(')', `, ${this.alpha})`).replace('rgb', 'rgba');\n" +
                "          ctx.fillStyle = rgba;\n" +
                "        } else {\n" +
                "          ctx.fillStyle = `rgba(225, 82, 215, ${this.alpha})`;\n" +
                "        }\n" +
                "        \n" +
                "        ctx.fill();\n" +
                "      }\n" +
                "\n" +
                "      randomColor() {\n" +
                "        const r = Math.floor(Math.random() * 255);\n" +
                "        const g = Math.floor(Math.random() * 255);\n" +
                "        const b = Math.floor(Math.random() * 255);\n" +
                "        return `rgb(${r}, ${g}, ${b})`;\n" +
                "      }\n" +
                "    }\n" +
                "\n" +
                "    // 动画循环\n" +
                "    function animate() {\n" +
                "      if (animationRunning) {\n" +
                "        ctx.clearRect(0, 0, width, height);\n" +
                "        circles.forEach(circle => {\n" +
                "          circle.update();\n" +
                "          circle.draw();\n" +
                "        });\n" +
                "      }\n" +
                "      requestAnimationFrame(animate);\n" +
                "    }\n" +
                "\n" +
                "    // 处理窗口大小变化\n" +
                "    let resizeTimeout;\n" +
                "    function handleResize() {\n" +
                "      clearTimeout(resizeTimeout);\n" +
                "      resizeTimeout = setTimeout(() => {\n" +
                "        resizeCanvas();\n" +
                "      }, 200);\n" +
                "    }\n" +
                "\n" +
                "    // 初始化系统\n" +
                "    resizeCanvas();\n" +
                "    animate();\n" +
                "    \n" +
                "    // 事件监听\n" +
                "    window.addEventListener('resize', handleResize);\n" +
                "    \n" +
                "    // 提供控制接口\n" +
                "    window.bubbleBackground = {\n" +
                "      start: function() {\n" +
                "        animationRunning = true;\n" +
                "      },\n" +
                "      stop: function() {\n" +
                "        animationRunning = false;\n" +
                "      },\n" +
                "      updateSettings: function(newSettings) {\n" +
                "        Object.assign(settings, newSettings);\n" +
                "        initCircles();\n" +
                "      },\n" +
                "      destroy: function() {\n" +
                "        window.removeEventListener('resize', handleResize);\n" +
                "        if (container.parentNode) {\n" +
                "          container.parentNode.removeChild(container);\n" +
                "        }\n" +
                "      }\n" +
                "    };\n" +
                "  }\n" +
                "});\n" +
                "</script>",
            settings
        );
    }

    // 示例：使用默认配置生成脚本
    public static void main(String[] args) {
        BubbleConfig config = new BubbleConfig(
            "rgb(100, 200, 255)", // 自定义颜色
            15,                    // 增大半径
            0.02,                  // 增加密度
            0.8,                   // 增加透明度范围
            0.3,                   // 提高最小速度
            1.5,                   // 提高最大速度
            0.2,                   // 最小缩放
            0.5                    // 最大缩放
        );

        String script = buildFullScript(config);
        System.out.println(script);
    }
}