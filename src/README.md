代码结构
=======

1. 顶层包
---------
- bupt.openstack.keystone 实现keystone组件接口实现，包括认证，用户、组、租户、服务等管理。
- bupt.openstack.glance	实现glance组件接口实现，包括查看镜像、上传镜像等。
- bupt.openstack.nova	实现nova组件接口实现，包括Flavor、Server、ip管理。
- bupt.openstack.cinder	实现cinder组件接口实现，包括磁盘管理、磁盘挂载等。
- bupt.openstack.common 这是所有组件依赖的一些接口实现、包括http请求、数据库连接、配置读取和json处理等。

2. 子包
-------

每个组件包结构基本一致，下面以bupt.openstack.nova为例：
- bupt.openstack.nova.model 定义实体bean，即json对应的java类
- bupt.openstack.nova.api 定义了所有model下实体的操作接口，子包由根据api版本的实现。
- bupt.openstack.nova.Nova nova客户端，组合了所有nova组件的操作功能，调用此接口实现完成功能。


下面是bupt.opensatck.common的内容：
- bupt.openstack.common.conf 负责配置文件读取。
- bupt.openstack.common.request 负责向openstack服务器发送http请求。
- bupt.openstack.common.annotation 定义Entity、Property注解以及注解处理器。
- bupt.openstack.common.cache 负责缓存管理，尚未实现。
- bupt.openstack.comon.tools 一些辅助类，比如数据库连接、json数据、字符串处理等。
- bupt.openstack.common.model 一些共享实体类，比如AbstractEntity。
- bupt.openstack.common.OpenstackSession 一个认证辅助类，它会调用keystone完成认证，并且返回client
- bupt.openstack.common.Manager 管理器的基类，负责发送和接收http请求，相互转化json和java bean。

代码原则
=======
