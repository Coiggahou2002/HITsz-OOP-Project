# 安卓飞机大战 服务端

## 技术框架

+ 服务端 Spring Boot，Controller-Service-Mapper 层次结构
+ 数据持久层 Mybatis Plus + MySQL
+ NoSQL 缓存 Redis
+ 阿里云服务器部署服务端、数据库和缓存

## 数据建模

+ 实体类
  + 用户
  + 道具
  + 得分记录
  + 各种 DTO
+ 表
  + user 表
  + prop 表
  + record 表
+ 主要接口
  + UserController
    + register 注册
    + login 登录
  + PropController
    + getAllProps 获取道具列表
    + buyProp 购买道具
  + GameController
    + createNewRoom 创建游戏房间
    + joinGameRoom 加入房间
    + getRoomInfo 获取房间信息
    + getPendingRooms 获取等待中的房间列表
    + updateGameInfo 更新对局信息

## 对战匹配实现思路

因为 Http 是无状态协议，所以本质上是需要一块类似 Session 性质的东西来存储会话信息

核心是利用 Redis 中的 Hash 数据结构存储 $[对局ID,对局信息]$ 这样的键值对

其中对局信息使用 Hash 存储

| Key                                     | Hash key     | Hash value |
| --------------------------------------- | ------------ | ---------- |
| GameID_bb285caa0de0439991f84ab96639deaf | Status       | 0          |
|                                         | PlayerIdA    | 132132131  |
|                                         | PlayerNameA  | John       |
|                                         | PlayerScoreA | 1233       |
|                                         | PlayerIdB    | 1120990409 |
|                                         | PlayerNameB  | Coiggahou  |
|                                         | PlayerScoreB | 292        |

对战匹配过程：

+ 玩家 A 创建房间，然后向服务器轮询，有人加入就开始游戏
+ 玩家 B 点击加入房间，选择等待中的房间加入，加入成功就开始游戏
+ 各自加入成功后开始游戏，两端各自轮询服务器，上传自己的最新得分，同时下载对手的最新得分，绘制到画布上

## 其他要点

+ lombok 简化开发
+ 封装 Api 统一返回结构体 ApiResponse（使用 ApiResponseStatus 枚举所有状态码和提示信息）
+ 自定义 GlobalExceptionHandler
+ 切面增强 ApiResponseHandlerAdvice（自动包装返回体）
