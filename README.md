What's openstack ?
==================

OpenStack是一个IaaS层基础资源管理平台，由Nova、Cinder、Neutron等主要的组件组合起来，提供计算服务、存储服务、网络服务等云服务，为公共及私有云的建设与管理提供一种开源实现方案。它最初是由 Rackspace 和 NASA 共同开发帮助服务商和企业内部实现类似于 Amazon EC2 和 S3 的云基础架构服务(Infrastructure as a Service, IaaS),更多信息科参考[openstack官网](http://www.openstack.org/)。

关于此项目
==================

Openstack提供了Python接口以及RESTFul API，但却一直没有Java版本的SDK，这个项目的最终目的就是要实现一个便于调用、易于扩展的Openstack Java SDK包，为Java开发人员提供直接访问和管理Openstack平台的接口，弥补Openstack API的不足。它是基于[Openstack RESTFul](http://developer.openstack.org/api-ref.html)的二次开发。最初应实验室需求而开展工作，目前已经花了陆陆续续近1个月的时间，实现了基本功能。当然存在很多bug以及许多尚未实现的功能。

如何使用
==========

本项目使用Maven构建，因此首先使用`mvn`命令构建本项目：

```bash
mvn package
```

如果需要把依赖包一块打进一个包，可以使用以下命令实现：

```
mvn assembly:assembly
```

项目运行时需要配置文件，配置文件路径由系统变量`OPENSTACK_CONF_PATH`指定，默认为`/etc/openstack`,参考[配置文件说明](etc)
该项目设计模式参考了Openstack官方Python库的设计理念，尽力做到调用简单、利于扩展并可实现热插拔.以下是一个简单的使用例子：

```java
	OpenstackSession session = OpenstackSession.getSession("username", "password");// get session
	Nova nova = session.getNovaClient(); // get nova client
	// get a flavor list, print their name
	for (Flavor flavor : nova.flavors.list()) {
		System.out.println(flavor.getName());
	}
	// create a new server
	Server server = new Server();
	server.setName("demo");
	server.setImageRef("imageId");
	server.setFlavorRef("flavorId");
	// some other config 
	nova.servers.create(server); // call create method to execute.
```

加入开发
=========

由于时间有限，此时此刻正忙于写毕设论文，短期内要完成所有功能实在有难度。因此希望有更多的大牛加入，共同推进完善。也欢迎指出目前代码的不足之处，甚至重构整个代码。

下面介绍如何扩展新接口的步骤：

Step 1:创建model
----------------

model就是实体对应的Java Bean，接收请求过来的数据一般是Json，要把Json表示的对象转化成的Java对象。你完全可以自定义Bean，或者继承AbstractEntity,属性使用Property注解，构造方法需要传递一个JSONObject对象，一般直接调用基类的构造方法就能完成JSONObject到Java Bean的转化。以下是Model实例：

```java
@Entity("volume_type")
public class VolumeType extends AbstractEntity {
	private static final long serialVersionUID = -6539238104579991330L;
	@Property("extra_specs")
	private JSONObject metadata;
	/*
	@Property("name")
	private String name;
	@Property("id")
	private String id;
	*/
	public VolumeType() {
		super();
	}
	public VolumeType(JSONObject jsonObj) {
		super(jsonObj);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	// some other getter and setter here
}
```

Step 2:创建Bean Manager接口
---------------------

Manager接口即对Bean定义可实现的操作，这些操作可能因API的版本不同有不同的实现。在设计接口时，没有使用共同接口，也尚未使用接口继承，原因是很多实体Bean对应的操作差异性很大，即使是相同的操作，参数也不完全相同。下面是一个Manager接口实例：

```java
public interface ServerManager {
	/**
	 * Get a server.
	 * @param id ID of the Server to get.
	 * @return Server
	 * @throws OperationException
	 */
	Server get(String id) throws OperationException;
	/**
	 * Get a list of servers.
	 * @return List of server
	 * @throws OperationException
	 */
	List<Server> list() throws OperationException;
	/**
	 * Stop(power off) the server
	 * @param id The id of server to stop.
	 * @throws OperationException
	 */
	void stop(String id) throws OperationException;
	/**
	 * Start(power on) the server,
	 * @param id The id of server to start.
	 * @throws OperationException
	 */
	 void start(String id) throws OperationException;
	 /**
	  * Reboot a server, for a software-lever reboot
	  * @param id The ID of server to reboot.
	  * @see reboot(String id, boolean hard)
	  */
	 void reboot(String id) throws OperationException;
	 /**
	  * update the name for a Server
	  * @param id the id of server to rename
	  * @param name the new name of the server.
	  * @throws OperationException
	  */
	 void rename(String id, String name) throws OperationException;
	 /**
	  * Create (boot) a new Server.<br/>
	  * <i>Remember</i> : You must set name, imageRef, flavorRef !
	  * @param instance The new server you have created.
	  * @return
	  * @throws OperationException
	  */
	 Server create(Server instance) throws OperationException;
	 /**
	  * Delete (i.e shut down and delete the image) this server.
	  * @param id The ID of server to delete.
	  * @throws OperationException
	  */
	 void delete(String id) throws OperationException;
}
```

Step 3: 实现Manager接口
------------------

接下来就是接口的具体实现，可能会因API版本不同而有不同的实现，实际应用时可以根据配置文件指定需要使用的版本。

```java
public class Flavors extends AbstractManager<Flavor> implements FlavorManager{
	private final String PREFIX = "/flavors";
	public Flavors(Authenticated credentical) {
		super(credentical, Flavor.class);
	}
	/**
	 * Get a list of all flavors
	 * @return A List , which holds flavors
	 * @throws OperationException
	 */
	@Override
	public List<Flavor> list() throws OperationException {
		return _list(PREFIX + "/detail");
	}
	/**
	 * Get a specific Flavor.
	 * @param id The ID of Flavor to get. 
	 * @return Flavor
	 * @throws OperationException
	 */
	@Override
	public Flavor get(String id) throws OperationException {
		return _get(PREFIX + "/" + id);
	}
	/**
	 * Delete a specific Flavor.
	 * @param id The ID of Flavor to delete.
	 * @throws OperationException
	 */
	@Override
	public void delete(String id) throws OperationException {
		_delete("/flavors/" + id);
	}
	/**
	 * create a new flavor for a tenant
	 * @param flavor The flavor to create
	 * @return The new flavor
	 * @throws OperationException
	 */
	@Override
	public Flavor create(Flavor flavor) throws OperationException {
		return _create("/flavors", flavor);
	}
}

```
Step 4: 注册新接口
----------------

注册新功能就是把相应的Manager接口添加到Client下（比如Nova， Glance等），目前我直接硬编码，实际操作应该根据配置文件选择api版本由工厂负责创建，以下是实例：

```java
public class Nova {
	public final FlavorManager flavors;
	public final HypervisorManager hypervisors;
	public final ServerManager servers;
	public final KeyPairManager keypairs;
	public Nova(Authenticated credentical) {
		// bad work, don't do that!!
		flavors = new Flavors(credentical);
		hypervisors = new Hypervisors(credentical);
		servers = new Servers(credentical);
		keypairs = new KeyPairs(credentical);
	}
```
如何扩展新功能
==========================

如何为已经实现的接口增加新功能，比如在请求前记录日志，或者使用Cache。只需要增加装饰器即可!以下是一个简单的实例：

```java
public class FlavorCachedManager implements FlavorManager {
	private FlavorManager flavors;
	public FlavorCachedManager(FlavorManager flavors) {
		this.flavors = flavors;
	}
	@Override
	public Flavor get(String id) throws OperationException {
		Flavor flavor = getFromCache(id);
		if (flavor == null) {
			flavor = flavors.get(id);
		}
		addToCache(flavor);
		return flavor;
	}

}
```
Copyright
=========

The MIT License (MIT)
