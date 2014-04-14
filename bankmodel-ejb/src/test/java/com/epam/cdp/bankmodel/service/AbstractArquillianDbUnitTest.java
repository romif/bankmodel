/**
 * 
 */
package com.epam.cdp.bankmodel.service;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.map.HashedMap;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class AbstractArquillianDbUnitTest.
 * @author Roman_Konovalov
 */
@RunWith(Arquillian.class)
public abstract class AbstractArquillianDbUnitTest {

	/**
	 * thrown.
	 */
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	/**
	 * CORE_PACKAGE.
	 */
	private static final String CORE_PACKAGE = "com.epam.cdp";

	/** The Byte array constant. */
	protected static final byte[] TEST_BYTE_ARRAY = {49, 50, 51, 52};

	/** The String constant. */
	protected static final String TEST_STRING = "testString";

	/** The Email String constant. */
	protected static final String TEST_EMAIL_STRING = "test@test.com";

	/** The Fail String constant. */
	protected static final String FAIL_TEST_STRING = "FailTestString";

	/** The int constant. */
	protected static final int TEST_INT = 1111;

	/** The long constant. */
	protected static final Long TEST_LONG = 1111L;

	/** The Date constant. */
	protected static final Date TEST_DATE = new GregorianCalendar(2014, 3, 27).getTime();

	/** The Boolean constant. */
	protected static final boolean TEST_BOOLEAN = true;

	/** The Logger. */
	private static final Logger LOG = LoggerFactory.getLogger(AbstractArquillianDbUnitTest.class);

	/**
	 * TypeMap.
	 */
	@SuppressWarnings("unchecked")
	private static Map<Class<?>, Object> typeMap = new HashedMap();

	/**
	 * Constructor.
	 */
	public AbstractArquillianDbUnitTest() {
		typeMap.put(String.class, TEST_STRING);
		typeMap.put(int.class, TEST_INT);
		typeMap.put(Integer.class, TEST_INT);
		typeMap.put(Long.class, TEST_LONG);
		typeMap.put(long.class, TEST_LONG);
		typeMap.put(byte[].class, TEST_BYTE_ARRAY);
		typeMap.put(Date.class, TEST_DATE);
		typeMap.put(boolean.class, TEST_BOOLEAN);
	}

	/**
	 * @param targetObject
	 *            the targetObject
	 */
	protected void setObjectFields(final Object targetObject) {
		Field[] fields = targetObject.getClass().getDeclaredFields();

		for (Field field : fields) {
			try {
				field.setAccessible(true);
				field.set(targetObject, typeMap.get(field.getType()));
			} catch (IllegalArgumentException e) {
				LOG.debug("TypeMap doesn't contain {} class.\n{}", field.getType(), e.getMessage());
			} catch (IllegalAccessException e) {
				LOG.debug("Can not set final field.\n{}", e.getMessage());
			}
		}

	}

	/**
	 * getInstance.
	 * @param clazz
	 * @return Instance
	 */
	protected <T> T getFilledInstance(final Class<T> clazz) {
		try {
			T instance = (T) clazz.newInstance();
			setObjectFields(instance);
			return instance;
		} catch (InstantiationException e) {
			LOG.debug("Cannot instantiate new object {}.\n{}", clazz.getSimpleName(), e.getMessage());
			return null;
		} catch (IllegalAccessException e) {
			LOG.debug("Cannot instantiate new object {}.\n{}", clazz.getSimpleName(), e.getMessage());
			return null;
		}
	}

	/**
	 * testPojo.
	 * @param targetObject targetObject
	 * @param ignoreFields ignoreMethods
	 */
	protected void testEntity(final Object targetObject, final String[] ignoreFields) {
		try {
			BeanInfo pojoInfo = Introspector.getBeanInfo(targetObject.getClass());
			Set<String> ignoreSet = new HashSet<String>(Arrays.asList(ignoreFields));
			for (PropertyDescriptor propertyDescriptor : pojoInfo.getPropertyDescriptors()) {
				if (!ignoreSet.contains(propertyDescriptor.getName())) {
					testProperty(targetObject, propertyDescriptor);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("something went wrong during mocking object values", e);
		}
	}

	/**
	 * testProperty.
	 * @param targetObject pojo
	 * @param propertyDescriptor propertyDescriptor
	 */
	private void testProperty(final Object targetObject, final PropertyDescriptor propertyDescriptor) {
		try {
			Class<?> propertyType = propertyDescriptor.getPropertyType();
			Object testValue = typeMap.get(propertyType);
			if (testValue == null) {
				return;
			}
			Method readMethod = propertyDescriptor.getReadMethod();
			if (readMethod != null) {
				if (propertyType.isArray()) {
					Assert.assertArrayEquals("Column " + propertyDescriptor.getName(), (Object[]) testValue,
							(Object[]) readMethod.invoke(targetObject));
				} else {
					Assert.assertEquals("Column " + propertyDescriptor.getName(), testValue, readMethod.invoke(targetObject));
				}
			}
		} catch (Exception e) {
			// ignore
		}
	}

	/**
	 * createTestArchive.
	 * @return Archive
	 */
	@Deployment
	public static Archive<?> createTestArchive() {
		return ShrinkWrap.create(WebArchive.class, "test.war").addPackages(true, CORE_PACKAGE)
				.addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
				.addAsWebInfResource("META-INF/test-ds.xml", "test-ds.xml")
				.addAsResource("test-import.sql", "import.sql");
	}

	/**
	 * The Class CauseMatcher.
	 */
	protected static class CauseMatcher extends TypeSafeMatcher<Throwable> {

		/**
		 * type.
		 */
		private final Class<? extends Throwable> type;

		/**
		 * @param type
		 *            type
		 */
		public CauseMatcher(final Class<? extends Throwable> type) {
			this.type = type;
		}

		@Override
		protected boolean matchesSafely(final Throwable item) {
			return item.getClass().isAssignableFrom(type);
		}

		@Override
		public void describeTo(final Description description) {
			description.appendText("expects type ").appendValue(type);
		}
	}

}
