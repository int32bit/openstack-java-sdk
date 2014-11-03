What's openstack ?
==================

OpenStack是一个云平台管理的项目，由几个主要的组件组合起来,旨在为公共及私有云的建设与管理提供平台。它是由 Rackspace 和 NASA 共同开发的云计算平台，帮助服务商和企业内部实现类似于 Amazon EC2 和 S3 的云基础架构服务(Infrastructure as a Service, IaaS),具体查看[openstack官网](http://www.openstack.org/)

About this project
==================

这个项目旨在写一个方便调用、方便扩展的openstack开发包。它是根据[openstack api](http://developer.openstack.org/api-ref.html)封装的java开发库，最初应实验室需求而开发。目前已经花了陆陆续续近3个月的时间，仅仅实现了基本功能，还有很多bug，以及功能尚未实现。

How to use ?
==========

我学习了openstack官方python库的设计架构，尽力做到调用简单，方便.以下是demo：
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

Join me !
=========

由于时间有限，加上本人代码功底尚浅，需要完美完成所有功能实在是感觉力不从心。希望有更多的大牛能够加入一起完成，并且指出我目前工作的不足，甚至重构整个代码，我真诚的感谢每一位热心的朋友！

下面讲讲我目前如何扩展新功能：

Step 1:创建model
----------------
model就是实体对应的java bean，接收请求过来的数据一般是json，即要把json表示的对象转化成的java对象。你可以完全由自己来定义自己的bean，我的所有bean都继承AbstractEntity,属性由Property注解，构造方法传递一个JSONObject对象，一般直接调用基类的构造方法就能完成JSONObject到java bean的转化。以下是model demo：
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
	public JSONObject getMetadata() {
		return metadata;
	}
}
```
Step 2:创建bean管理接口
---------------------

管理接口即对bean定义操作，这些操作可能根据api的版本不同有不同的实现。在设计接口时，我没有使用共同接口，也尚未使用接口继承，原因是很多实体bean对应的操作差异性很大，即使是相同的操作，参数也不完全相同。下面是一个管理接口demo：
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
Step 3: 实现管理接口
------------------

接下来就是具体实现接口，可能会根据api版本不同而有不同的实现，实际应用时可以根据配置文件指定需要使用的版本。下面是一个实现Demo：
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
Step 4: 注册新功能
----------------

注册新功能就是把相应的管理接口添加到client下（比如Nova， Glance等），目前我直接硬编码，实际操作应该根据配置文件选择api版本由工厂负责创建，以下是demo：
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
How to extend new features?
==========================

如何为已经实现的接口增加新功能，比如在请求前记录日志，或者使用cache。只需要增加装饰器即可!以下是Demo：
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

1. 本项目的所有代码任何人都可以下载使用并且修改和重构!
2. 任何人所做的修改必须继续公开到github，并且及时通知我！
3. 对我的代码有任何意见，均可以通过邮箱告诉我！
