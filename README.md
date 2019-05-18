![Language](https://img.shields.io/badge/Language-Java-green.svg)
![License](https://img.shields.io/badge/License-MIT-green.svg)
[![Build Status](https://travis-ci.com/ShenJiahuan/SE228.svg?token=hcmob6QewZpKr9F3oQHK&branch=dev)](https://travis-ci.com/ShenJiahuan/SE228)

# 上海交通大学 SE228 Web开发技术大作业 eBook在线书店
## 技术栈
### 前端
- Vue.js  

### 后端
- Spring Boot
- Hibernate
- MySQL

## 已知问题
- Server端Session过期后，Client未作出相应检测，此时访问需登录的内容时会返回401
- Server端未对当前可禁用用户做出限制
- ~~同一用户1秒内重复下单，会导致主键重复，无法完成订单~~ （已修复）
