# Implementation of os classical algorithm

os经典算法实现

## 项目结构说明

下面以测试目录的页面置换算法为例 , 说明本项目的结构

```
└─页面置换算法(测试)
    ├─golang
    │  ├─.idea
    │  └─lru
    ├─Java
    │  └─com
    │      └─dhx
    │          ├─algorithms
    │          │  └─lru
    │          └─sample
    └─Python
        └─mx
            ├─algorithm
            └─sample
```

第一级目录为某方面算法的名称 , 比如置换算法、调度算法等。

第二级目录为某方面算法的**具体语言实现** ，例如页面置换算法(测试)

```

└─页面置换算法(测试)
    ├─golang
    ├─Java
    └─Python
```

## 贡献

### 代码规范

#### Java

1. **命名**: 标识符的命名力求做到统一、达意和简洁，Java请使用驼峰法命名
2. **格式**: 请确保您的代码每个缩进为4个空格，源文件使用**utf-8**编码，行宽不要超过120。
3. **注释**: 请确保您的类、域和方法上面有相应的 Java Doc , 单行注释使用`//` 多行时用`/* ..*/`，较短的代码块用空行表示注释作用域，较长的代码块要用`/*------ start: ------*/`和`/*-------- end: -------*/`包围，可以考虑使用大括号来表示注释范围。

> Java编程规范（第三版）百度云下载（.pdf）：https://pan.baidu.com/s/1Di5VN-FfFPate-_fBNiXqA

### 贡献流程

理想的贡献工作流程概述如下：

1. fork当前代码仓库
2. 克隆项目仓库到本地
3. 更新本地分支代码，需要确认本地分支的代码是新的
4. 开始您的代码，请确保您的代码满足基本的代码规范，如变量名、排版以及必要的注释等
5. commit 和 push，请确保您的commit message 满足规范，详见https://www.conventionalcommits.org/zh-hans/v1.0.0/
6. pull reqeust，在pull request之前请将您的分支仓库与远程仓库同步，以确保您的 PR 优雅、简洁

## 注意事项

### 运行golang代码

如果您需要直接运行golang代码，请将工作目录切换为golang文件夹，以确保代码可以正确被编译器识别

```
└─页面置换算法(测试)
    ├─golang
```

![示意图](http://oss.dhx.icu/dhx/image-20230329122912749.png)
