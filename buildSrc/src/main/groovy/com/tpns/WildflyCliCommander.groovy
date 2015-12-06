package com.tpns

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.jboss.as.cli.scriptsupport.CLI

class WildflyCliCommander extends DefaultTask{

	def host
	def port
	def user
	def pass
	def cmd

	@TaskAction
	def foo(){
		def cli = CLI.newInstance()
		//connect(host, port, user, pass.toCharArray()) 
		cli.connect()
		def result = cli.cmd(cmd)
		def response = result.getResponse() 
		def serverstate = response.get("result")
		        
		println "Current server state: $serverstate" 
		        
		cli.disconnect()    
	}        

}