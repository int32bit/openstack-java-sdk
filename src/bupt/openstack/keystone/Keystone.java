package bupt.openstack.keystone;

import bupt.openstack.authentication.Authenticated;
import bupt.openstack.common.OperationException;
import bupt.openstack.keystone.api.EndpointManager;
import bupt.openstack.keystone.api.RoleManager;
import bupt.openstack.keystone.api.ServiceManager;
import bupt.openstack.keystone.api.TenantManager;
import bupt.openstack.keystone.api.TokenManager;
import bupt.openstack.keystone.api.UserManager;
import bupt.openstack.keystone.api.v2.Endpoints;
import bupt.openstack.keystone.api.v2.Roles;
import bupt.openstack.keystone.api.v2.Services;
import bupt.openstack.keystone.api.v2.Tenants;
import bupt.openstack.keystone.api.v2.Tokens;
import bupt.openstack.keystone.api.v2.Users;
import bupt.openstack.keystone.model.Secret;

public class Keystone {
	public final UserManager users;
	public final RoleManager roles;
	public final TokenManager tokens;
	public final ServiceManager services;
	public final TenantManager tenants;
	public final EndpointManager endpoints;
	public Keystone(Authenticated credentical) {
		this.users = new Users(credentical);
		this.roles = new Roles(credentical);
		this.tokens = new Tokens(credentical);
		this.services = new Services(credentical);
		this.tenants = new Tenants(credentical);
		this.endpoints = new Endpoints(credentical);
	}
	public static Authenticated authenticate(Secret secret) throws OperationException {
		TokenManager authenticator = new Tokens();
		return authenticator.authenticate(secret);
	}
}
