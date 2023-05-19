package com.rktirtho.emart.order;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class OrderServiceApplicationTests {

	@Test
	void contextLoads() {
		byte[] bs = {
				(byte) 0b00010110,
				(byte) 0b00000001
		};
		int actual = readLong(bs);
		assertEquals(150, actual);
	}

	private int readLong(byte[] bytes){
		return bytes[0]+ ((bytes[1] & 0xFF)<<7);
	}

}
