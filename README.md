# VIDEO

<div align="center">
    <img src="https://ws4.sinaimg.cn/large/006tNc79ly1fluug8kpmnj30gl07hweq.jpg" alt="SSM项目">
</div>

## 项目说明
> 一个主打分享的个人视频播放网站后端代码，主要用于项目练手。
    
## 功能介绍
- 本地资源视频文件上传在线播放
- 在线资源链接上传（ed2k、迅雷、等资源）下载
- 会员授权管理
- 第三方支付/一键生成秘钥
- danmuplayer在线播放
- 视频播放弹幕系统
- 第三方分享功能
- 评分系统
- 评论系统
- 后台管理
- geetest极验图像验证
    
## 技术介绍
1. SSM(Spring+SpringMVC+MyBatis)
2. redis缓存
3. Solr缓存
4. JSP页面
5. H5
6. javaScript
    
## 整改计划
### 后端 
- [x] SSM框架-->SpringBoot
- [ ] 邮件功能完善
- [ ] 文件服务器搭建（预计会自己搭建一个Hadoop平台）
- [ ] 支付系统搭建（借助支付宝平台）
- [ ] VIP破解，VIP视频免费观看
- [ ] 推荐算法
- [ ] 添加删除分类功能
- [ ] 后端代码优化（持续）
    
### 前端
- [ ] 页面整改美化
- [x] 前后端分离（VUE）
- [ ] 文件上传插件更换
    <div>
        前端VUE框架已经搭建完毕，具体整改进度见<a href="https://github.com/Asimple-L/vue-video">video-vue</a>
    </div>

### 代码/接口优化
- [x] 导航栏信息可以单独写成一个接口，主要传输数据包括分类信息，用户信息
