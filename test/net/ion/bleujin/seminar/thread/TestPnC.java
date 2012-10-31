package net.ion.bleujin.seminar.thread;

import java.util.Random;

import net.ion.framework.util.InfinityThread;
import junit.framework.TestCase;

public class TestPnC extends TestCase {
	
	public void testPnCPattern() throws Exception {
		Table table = new Table(3); // 
		new MakerThread("MakerThread-1", table, 31415).start();
		new MakerThread("MakerThread-2", table, 92653).start();
		new MakerThread("MakerThread-3", table, 58979).start();

		new EaterThread("EaterThread-1", table, 32384).start();
		new EaterThread("EaterThread-2", table, 62643).start();
		new EaterThread("EaterThread-3", table, 38327).start();
		
		new InfinityThread().startNJoin() ;
	}
}


class EaterThread extends Thread {
    private final Random random;
    private final Table table;
   
    public EaterThread(String name, Table table, long seed) {
        super(name);
        this.table = table;
        random = new Random(seed);
    }
   
    @Override
    public void run() {
        try {
            while (true) {
                @SuppressWarnings("unused")
                String cake = table.take();
                Thread.sleep(random.nextInt(1000)); // ����ũ �Դ� �ð�.
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class MakerThread extends Thread {
    private final Random random;
    private final Table table;
    private static int id; // ����ũ ��ȣ
   
    public MakerThread(String name, Table table, long seed) {
        super(name);
        this.table = table;
        this.random = new Random(seed);
    }
   
    @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep(random.nextInt(1000)); // ����ũ ����� �ð�
                String cake = "[ Cake No." + nextId() + " by " +
                        getName() + " ] ";
                table.put(cake);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
   
    private static synchronized int nextId() {
        return id++;
    }
}

class Table {
    private final String[] buffer;
    private int count;
    private int head;
    private int tail;
   
    public Table(int count) {
        this.buffer = new String[count];
        this.head = 0;
        this.tail = 0;
        this.count = 0;
    }
   
    // ����ũ�� ���̺? �÷��д�.
    public synchronized void put(String cake) throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " puts " + cake);
   
        while (count >= buffer.length) {
            wait();
        }
       
        buffer[tail] = cake;
        tail = (tail + 1) % buffer.length;
        count++;
        notifyAll();
    }
   
    // ����ũ�� ���̺?�� ��������.
    public synchronized String take() throws InterruptedException {
        while (count <= 0) {
            wait();
        }
       
        String cake = buffer[head];
        head = (head + 1) % buffer.length;
        count--;
        notifyAll();
        System.out.println(Thread.currentThread().getName() + " takes " + cake);
        return cake;
    }
}