package bupt.openstack.nova.api;

import java.util.List;
import java.util.Map;

import bupt.openstack.common.OperationException;
import bupt.openstack.nova.model.Console;
import bupt.openstack.nova.model.SecurityGroup;
import bupt.openstack.nova.model.Server;

public interface ServerManager {
	public enum BackupType {
		daily, weekly
	}
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
	 * Add an IP address on a network.
	 * @param id The ID of Server to add an IP to.
	 * @param networkId The ID of the network the IP should be on.
	 * @throws OperationException
	 */
	void addFixedIp(String id, String networkId) throws OperationException;
	/**
	 * Remove an IP address.
	 * @param id The ID of Server to remove an IP from.
	 * @param address The IP address to remove.
	 * @throws OperationException
	 */
	void removeFixedIp(String id, String address) throws OperationException;
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
	  * Reboot a server.
	  * @param id The ID of server to reboot.
	  * @param hard if true, for a vertual power cycle hard reboot. if false, for a software-lever reboot.
	  */
	 void reboot(String id, boolean hard) throws OperationException;
	 /**
	  * Reboot a server, for a software-lever reboot
	  * @param id The ID of server to reboot.
	  * @see reboot(String id, boolean hard)
	  */
	 void reboot(String id) throws OperationException;
	 /**
	  * Get a VNC console url for an instance, novnc only, xvpvnc not support.
	  * @param id The ID of server to get console from.
	  * @throws OperationException
	  */
	 Console getConsole(String id) throws OperationException;
	 /**
	  * Get text console log output from Server.
	  * @param id The ID of Server whose console output you would like to retrieve.
	  * @return The Text console log.
	  * @throws OperationException
	  */
	 String getLog(String id) throws OperationException;
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
	 /**
	  * Get password for instance
	  * Required that openssl is installed and in the path
	  * @param id the id of instance to get password
	  * @param privateKey The private key to decrypt password
	  * @return
	  * @throws OperationException
	  */
	 String getPassword(String id, String privateKey) throws OperationException;
	 /**
	  * Clear password for an i	nstance
	  * @param id The id of instance to clear password
	  * @throws OperationException
	  */
	 void clearPassword(String id) throws OperationException;
	 /**
	  * Force delete the server.
	  * @param id The id of server to delete.
	  * @throws OperationException
	  */
	 void forceDelete(String id) throws OperationException;
	 /**
	  * Restore soft-deleted server
	  * @param id the id of server
	  * @throws OperationException
	  */
	 void restore(String id) throws OperationException;
	 /**
	  * Pause the server
	  * @param id the id of server
	  * @throws OperationException
	  */
	 void pause(String id) throws OperationException;
	 /**
	  * Unpause the server
	  * @param id the id of server
	  * @throws OperationException
	  */
	 void unpause(String id) throws OperationException;
	 /**
	  * Lock the Server
	  * @param id
	  * @throws OperationException
	  */
	 void lock(String id) throws OperationException;
	 /**
	  * Unlock the server
	  * @param id
	  * @throws OperationException
	  */
	 void unlock(String id) throws OperationException;
	 /**
	  * Resume the suspended server.
	  * @param id
	  * @throws OperationException
	  */
	 void resume(String id) throws OperationException;
	 /**
	  * Suspend the server
	  * @param id
	  * @throws OperationException
	  */
	 void suspend(String id) throws OperationException;
	 /**
	  * Rescue the server
	  * @param id
	  * @throws OperationException
	  */
	 void rescue(String id) throws OperationException;
	 /**
	  * Unrescue the server.
	  * @param id
	  * @throws OperationException
	  */
	 void unrescue(String id) throws OperationException;
	 /**
	  * Retrieve server diagnostics.
	  * @param id The id of server
	  * @return The Map contains key-value of diagnostics.
	  * @throws OperationException
	  */
	 Map<String, String> diagnostics(String id) throws OperationException;
	 /**
	  * Rebuild -- shut down and then re-image -- a server.
	  * @param id The id of server to rebuild.
	  * @param imageRef The id of image to re-image with.
	  * @throws OperationException
	  */
	 void rebuild(String id, String imageRef) throws OperationException;
	 /**
	  * Migrate a server to a new host.
	  * @param id The id of server to migrate.
	  * @throws OperationException
	  */
	 void migrate(String id) throws OperationException;
	 /**
	  * Resize a server's resources.
	  * Until a resize event is confirmed with confirmResize, the old
	  * server will be kept around and you'll be able to roll back to the
	  * old flavor quickly with revertResize. All resizes are automatically
	  * confirmed after 24 hours.
	  * @param id
	  * @param flavorRef
	  * @throws OperationException
	  */
	 void resize(String id, String flavorRef) throws OperationException;
	 /**
	  * Confirm that the resized worked, thus removing the original server.
	  * @param id The id of server.
	  * @throws OperationException
	  */
	 void confirmResize(String id) throws OperationException;
	 /**
	  * Revert a previous resize, switching back to the old server.
	  * @param id the id of server.
	  * @throws OperationException
	  */
	 void revertResize(String id) throws OperationException;
	 /**
	  * Snapshot a server.
	  * @param id the id of server to snapshot.
	  * @param name the name of snapshot for this server.
	  * @return the uuid of snapshot of the server.
	  * @throws OperationException
	  */
	 void snapshot(String id, String name) throws OperationException;
	 /**
	  * Backup a server.
	  * @param id the id of server to backup.
	  * @param name Name of the backup image.
	  * @param type The backup type, like 'daily' or 'weekly'.
	  * @param rotation int parameter representing how many backups to keep around.
	  * @throws OperationException
	  */
	 void backup(String id, String name, BackupType type, int rotation) throws OperationException;
	 /**
	  * Set a servers metadata.
	  * @param id the id of server to set.
	  * @param key the key of metadata.
	  * @param value the value of metedata.
	  * @throws OperationException
	  */
	 void setMeta(String id, String key, String value) throws OperationException;
	 /** 
	  * Update a servers metadata.
	  * @param id the id of server
	  * @param key the key of metadta
	  * @param value the new value of the metadata.
	  * @throws OperationException
	  */
	 void updateMeta(String id, String key, String value) throws OperationException;
	 /**
	  * Delete metadata from an server.
	  * @param id the id of server.
	  * @param key The key to delete from the server
	  * @throws OperationException
	  */
	 void removeMeta(String id, String key) throws OperationException;
	 /**
	  * Migrates a running instance to a new machine.
	  * @param id the id of server
	  * @param hostname destination host name
	  * @param blockMigration if True, do block_migration
	  * @param diskOverCommit if True, Allow overcommit
	  * @throws OperationException
	  */
	 void liveMigrate(String id, String hostname, boolean blockMigration, boolean diskOverCommit) throws OperationException;
	 /**
	  * Migrates a running instance to a new machine.
	  * @param id  the id of server
	  * @param hostname destination host name
	  * @throws OperationException
	  * @see {@link #liveMigrate(String, String, boolean, boolean)}
	  */
	 void liveMigrate(String id, String hostname) throws OperationException;
	 /**
	  * Reset the state of an instance to active or error.
	  * @param id Id of instance to reset the state of.
	  * @param state Desired state; either 'active' or 'error'.
	  * @throws OperationException
	  */
	 void resetState(String id, String state) throws OperationException;
	 /**
	  * Reset network of an instance.
	  * @param id Id of the instance to reset the network of.
	  * @throws OperationException
	  */
	 void resetNetwork(String id) throws OperationException;
	 /**
	  * Add a Security Group to an instance.
	  * @param id The id of the instance.
	  * @param securityGroup The name of security group to add.
	  * @throws OperationException
	  */
	 void addSecurityGroup(String id, String securityGroup) throws OperationException;
	 /**
	  * Remove a Security Group from an instance.
	  * @param id The id of the instance.
	  * @param securityGroup The name of security group to remove.
	  * @throws OperationException
	  */
	 void removeSecurityGroup(String id, String securityGroup) throws OperationException;
	 /**
	  * List Security Group(s) of an instance
	  * @param id The id of instance.
	  * @return List of SecurityGroup.
	  * @throws OperationException
	  */
	 List<SecurityGroup> listSecurityGroup(String id) throws OperationException;
	 /**
	  * Evacuate a server instance.
	  * @param id The id to share onto.
	  * @param hostname Name of the target host.
	  * @throws OperationException
	  */
	 void evacuate(String id, String hostname) throws OperationException;
}
