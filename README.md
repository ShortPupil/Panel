# MyDrawPanel

[TOC]

## 运行

解压，在Eclipse里打开，右键main.MainFrame运行即可



## 分包

- beautytool 美化工具
- configure 配置，曲线画图
- drawpanel 画板
- fileoperation 文件操作
- graph 图形的数据结构
- identify 识别的算法
- main 主界面与运行

## 识别算法

### 要求

只能按同一时针画图

### 基本运算

**边数**和**曲点**共同作为识别标准

曲点由**曲率的峰值**决定

![](/home/songzi/文档/my_github/Panel/image/曲率.png)



| 曲率               | 几何特征 |
| ------------------ | -------- |
| 连续零曲率         | 直线段   |
| 连续非零曲率       | 弧线段   |
| 局部最大曲率绝对值 | 角点     |
| 局部最大曲率正值   | 凸角点   |

## 标注信息

### 两部分信息

### 形状

由图片颜色与图片上显示的文字表示，可在”确认标注“前修改

### 标注

图片上显示的文字，可在”确认标注“前修改



##打开保存操作

###两种保存方式

###简单保存

将面板中的对象序列化写入文本文件中

###另存为图像

将面板中的数据保存为jpg格式文件