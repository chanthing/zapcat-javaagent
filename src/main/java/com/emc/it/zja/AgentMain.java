package com.emc.it.zja;

import java.lang.instrument.Instrumentation;
import java.util.concurrent.TimeUnit;

import org.kjkoster.zapcat.Trapper;
import org.kjkoster.zapcat.zabbix.ZabbixTrapper;

/**
 * This class is responsible for bootstrapping zorka agent.
 *
 */
public class AgentMain {

    private static String zabbixServerIP = "192.168.1.240";
    private static String defaultAppName = "TrapTest";

    /**
     * This is entry method of java agent.
     *
     * @param args            arguments (supplied via -javaagent:/path/to/agent.jar=arguments)
     * @param instrumentation reference to JVM instrumentation interface
     */
    public static void premain(String args, Instrumentation instrumentation) throws Exception {
	Trapper trapper = null;
        trapper = new ZabbixTrapper(zabbixServerIP, defaultAppName);

	/*    trapper.send("java.version", System.getProperty("java.version"));*/
	
	    trapper.every(60, TimeUnit.SECONDS, "heap_used", "java.lang:type=Memory", "HeapMemoryUsage.used");
	    trapper.every(60, TimeUnit.SECONDS, "num_threads", "java.lang:type=Threading", "ThreadCount");
	    trapper.every(60, TimeUnit.SECONDS, "peak_threads", "java.lang:type=Threading", "PeakThreadCount");
	    trapper.every(60, TimeUnit.SECONDS, "gc.count", "java.lang:type=GarbageCollector,name=PS Scavenge", "CollectionCount");
     }

}
