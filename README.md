# 逍客车机工具 (QashqaiCarTool)

日产 / 启辰T70 车机（Android 4.4.3，航盛 HSAE 平台）的实用工具箱：日志抓取、方控/物理按键监听、热点/蓝牙/主题开关，以及本机系统参数查看。

> 本仓库 UI 已按车规级座舱标准重做：深色玻璃拟态（glassmorphism）风格、昼夜双主题、大按钮驾驶友好交互，并严格遵循车机参数手册的硬件约束。

## 设计参考

UI 与交互参考了以下公开项目的设计语言（仅借鉴视觉与交互思路，未复制其代码）：

- [enhanced-carplay-dashboard](https://github.com/kadirgecit/enhanced-carplay-dashboard) — 深色玻璃拟态、彩色编码数据、大号触控目标
- [ReDrive](https://github.com/iUnreallx/ReDrive) — 简洁的 telemetry 卡片、行车不分散注意力
- [Easycontrol_For_Car](https://github.com/xunzhi2010/Easycontrol_For_Car) — 深色磨砂玻璃、夜间驾驶安全优先

## 主要功能

| 模块 | 说明 |
|------|------|
| 日志读取 | 实时抓取系统日志，支持关键词过滤、自动滚动、开始/停止/清空 |
| 按键监听 | 通过无障碍服务监听车机方控/物理按键，实时显示按键名、动作、时间 |
| 日产工具 | 开机自动热点、蓝牙自动重启、大灯联动主题（夜间模式）开关 |
| 车辆参数 | 直接展示车机参数手册中的系统/显示/内存/车型/内置应用/数据接口规格 |
| 系统设置 | 关于、外观主题切换、跳转车辆参数 |

## 车机参数手册合规

界面与适配严格满足手册要求：

- **系统版本**：Android 4.4.3 (API 19)，`minSdk/targetSdk = 19`
- **横屏锁定**：所有 Activity `screenOrientation="landscape"`
- **主屏 1920×1080**：尺寸以 dp 为单位，自动适配 1280×720 / 1024×768
- **深色主题 + 昼夜模式**：基于 `Theme.AppCompat.DayNight`，默认夜间，可在任意页一键切换；「大灯联动主题」开关直接驱动夜间模式
- **大按钮 / 驾驶场景**：主操作按钮 `minHeight=66dp`，关键触控目标 ≥72dp
- **内存管理**：最大堆 384MB；全程零位图资源，仅用矢量 drawable 与 shape 渐变，避免 OOM

## 构建

```bash
git clone https://github.com/ybshayu/QashqaiCarTool.git
cd QashqaiCarTool
./gradlew assembleDebug
```

依赖：AndroidX AppCompat / RecyclerView / CardView / Material 1.0.0，编译 SDK 28。需在车载设备或同 API 19 的模拟器上运行。

## 许可证

仅供个人车机折腾使用。
