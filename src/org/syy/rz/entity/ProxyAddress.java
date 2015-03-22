package org.syy.rz.entity;

/** 
 * @ClassName: ProxyIp 
 * @PackageName: com.syy.winxincrawl.proxy
 * @ProjectName: winxinCrawl
 *
 * @Description: TODO
 *
 * @author syy
 * @date 2014-10-9 下午10:43:44 
 * @version 1.0
 */
public class ProxyAddress {

	private int id;
	private String ip;
	private int port;
	
	public ProxyAddress() {
		super();
	}

	public ProxyAddress(String ip, int port) {
		super();
		this.setIp(ip);
		this.setPort(port);
	}

	/**
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * @param ip the ip to set
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @param port the port to set
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	
}
