package bupt.openstack.nova.api.v2;

import java.util.List;

import bupt.openstack.common.OperationException;
import bupt.openstack.nova.model.SecurityGroup;
import bupt.openstack.nova.model.SecurityGroup.Rule;

public interface SecurityGroupManager {
	List<SecurityGroup> list() throws OperationException;
	SecurityGroup get(String id) throws OperationException;
	SecurityGroup create(String name, String description) throws OperationException;
	SecurityGroup update(SecurityGroup securityGroup) throws OperationException;
	void delete(String id) throws OperationException;
	/**
	 * Create a security group rule
	 * @param id the id of security group
	 * @param rule the rule to add.
	 * @return rule.
	 * @throws OperationException
	 */
	Rule addRule(String id, Rule rule) throws OperationException;
	/**
	 * remove a security group rule
	 * @param ruleId The id of rule to remove.
	 * @throws OperationException
	 */
	void removeRule(String ruleId) throws OperationException;
}
