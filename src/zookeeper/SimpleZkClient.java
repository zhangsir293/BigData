package zookeeper;

import java.io.IOException;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

public class SimpleZkClient {

	private static final String connectString = "192.168.0.101:2181,192.168.0.103:2181,192.168.0.105:2181";
	private static final int sessionTimeout = 2000;

	ZooKeeper zkClient = null;

	@Before
	public void init() throws Exception {
		zkClient = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
			@Override
			public void process(WatchedEvent event) {
				// �յ��¼�֪ͨ��Ļص�������Ӧ���������Լ����¼������߼���
				System.out.println(event.getType() + "---" + event.getPath());
				try {
					zkClient.getChildren("/", true);
				} catch (Exception e) {
				}
			}
		});

	}

	/**
	 * ���ݵ���ɾ�Ĳ�
	 * 
	 * @throws InterruptedException
	 * @throws KeeperException
	 */

	// �������ݽڵ㵽zk��
	@Test
	public void testCreate() throws KeeperException, InterruptedException {
		// ����1��Ҫ�����Ľڵ��·�� ����2���ڵ������ ����3���ڵ��Ȩ�� ����4���ڵ������
		String nodeCreated = zkClient.create("/eclipse", "hellozk".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		//�ϴ������ݿ������κ����ͣ�����Ҫת��byte[]
	}

	//�ж�znode�Ƿ����
	@Test	
	public void testExist() throws Exception{
		Stat stat = zkClient.exists("/eclipse", false);
		System.out.println(stat==null?"not exist":"exist");
		
		
	}
	
	// ��ȡ�ӽڵ�
	@Test
	public void getChildren() throws Exception {
		List<String> children = zkClient.getChildren("/", true);
		for (String child : children) {
			System.out.println(child);
		}
		Thread.sleep(Long.MAX_VALUE);
	}

	//��ȡznode������
	@Test
	public void getData() throws Exception {
		
		byte[] data = zkClient.getData("/eclipse", false, null);
		System.out.println(new String(data));
		
	}
	
	//ɾ��znode
	@Test
	public void deleteZnode() throws Exception {
		
		//����2��ָ��Ҫɾ���İ汾��-1��ʾɾ�����а汾
		zkClient.delete("/eclipse", -1);
		
		
	}
	//ɾ��znode
	@Test
	public void setData() throws Exception {
		
		zkClient.setData("/app1", "imissyou angelababy".getBytes(), -1);
		
		byte[] data = zkClient.getData("/app1", false, null);
		System.out.println(new String(data));
		
	}
	
	
}
