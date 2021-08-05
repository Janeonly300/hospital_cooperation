# hospital_cooperation
阜外医院项目


# 规则状态约定
## doctor
- stat： 
  - 0:管理员
  - 1:协作医生
  - 2:普通医师
  - 3:协作医师停用
  - 4:普通医师停用

- pass
  - 0:未激活
  - 1:激活 
  
## 诊断表
* 状态
    * 1 创建申请(默认，申请时创建)
    * 2 管理员回退申请
    * 3 已分配
    * 4 驳回协助医师的报告 （不满意协作医师、返回给协作者）
    * 5 反馈诊断报告（满意协作医师、返回给上传者）
    * 6 协作医师完成诊断


 
# profile(配置文件)
- 指定文件上传路径
![image](https://user-images.githubusercontent.com/54475097/127613709-b4cd075f-f3f6-4b4c-8189-2cacae92ab84.png)
