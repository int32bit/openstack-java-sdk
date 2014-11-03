关于配置文件
===========

1. 项目运行需要配置文件，配置文件路径由系统变量OPENSTACK_CONF_PATH指定，默认为/etc/openstack
2. 配置文件包括日志配置文件，使用[log4j 2](http://logging.apache.org/log4j/2.x/)版本。
3. openstack.conf配置一些参数,主要包括以下选项：
  - AuthURL: 配置认证地址
  - TenantName: 默认租户名
  - 数据库地址
  - 其他参数
4. 配置文件读取由bupt.openstack.common.conf处理，可以针对应用修改
