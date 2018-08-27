# Live
## 功能
- 用户可以通过手机号创建账号、密码进行登录
- 现阶段，所有用户都可以创建Live，使用Live，没有任何约束。
- 用户可以编辑自己的个人资料
- 可以通过文字及语音形式通信
## 详情
### 图片
- 上传至七牛
- 上传的图片进行了压缩
### 音频
- 使用AudioRecord录制音频
- 使用AudioTrack播放
- 使用云通信IMSDK进行传输
- 不足之处：音频未压缩。尝试过采用Speex框架，压缩的语音也正常传输，奈何播放时候有噪音需要C++进行降噪。。。
### 界面
- 观察学习了知乎Live还有Tim的部分界面设计

![演示1](https://github.com/weibinhwb/ChongyouLive/tree/master/app/gif/one.gif)
![演示2](https://github.com/weibinhwb/ChongyouLive/tree/master/app/gif/two.gif)
![演示3](https://github.com/weibinhwb/ChongyouLive/tree/master/app/gif/three.gif)
