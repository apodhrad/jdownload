package org.apodhrad.jdownload.manager;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.apodhrad.jdownload.manager.util.UnpackUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class UnpackUtilsTest {

	private static final String JETTY_RESOURCE_BASE = System.getProperty("jetty.resourceBase");
	private static final String TEST_RESOURCE = "gradle-wrapper.jar";
	private static final String RESOURCE_DIR = UnpackUtilsTest.class.getResource("/").getPath();
	private static final File TEST_DIR = new File(RESOURCE_DIR, "test");

	@BeforeClass
	public static void checkProperties() {
		assertNotNull("System property jetty.resourceBase is not set", JETTY_RESOURCE_BASE);
	}

	@Before
	@After
	public void deleteTestDir() throws IOException {
		UnpackUtils.deleteDirectory(TEST_DIR);
	}

	@Test
	public void unpackJarTest() throws Exception {
		UnpackUtils.unpack(new File(JETTY_RESOURCE_BASE, TEST_RESOURCE), TEST_DIR);

		assertTrue(new File(TEST_DIR, "META-INF").exists());
		assertTrue(new File(TEST_DIR, "META-INF").isDirectory());
	}

	@Test
	public void copyTest() throws Exception {
		UnpackUtils.copyFileToDirectory(new File(JETTY_RESOURCE_BASE, TEST_RESOURCE), TEST_DIR);

		assertTrue(new File(TEST_DIR, TEST_RESOURCE).exists());
		assertTrue(new File(TEST_DIR, TEST_RESOURCE).isFile());
	}

	@Test
	public void copyWithSameNameTest() throws Exception {
		UnpackUtils.copyFile(new File(JETTY_RESOURCE_BASE, TEST_RESOURCE), new File(TEST_DIR, TEST_RESOURCE));

		assertTrue(new File(TEST_DIR, TEST_RESOURCE).exists());
		assertTrue(new File(TEST_DIR, TEST_RESOURCE).isFile());
	}

	@Test
	public void copyWithDifferentNameTest() throws Exception {
		UnpackUtils.copyFile(new File(JETTY_RESOURCE_BASE, TEST_RESOURCE), new File(TEST_DIR, "test.jar"));

		assertFalse(new File(TEST_DIR, TEST_RESOURCE).exists());
		assertTrue(new File(TEST_DIR, "test.jar").exists());
		assertTrue(new File(TEST_DIR, "test.jar").isFile());
	}

	@Test
	public void createDirTest() {
		UnpackUtils.createDir(TEST_DIR);

		assertTrue(TEST_DIR.exists());
		assertTrue(TEST_DIR.isDirectory());
	}

	@Test
	public void createExistingDirTest() {
		UnpackUtils.createDir(TEST_DIR);
		UnpackUtils.createDir(TEST_DIR);

		assertTrue(TEST_DIR.exists());
		assertTrue(TEST_DIR.isDirectory());
	}

}
