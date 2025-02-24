package com.jmquijano.ejemplos.models;

import com.jmquijano.ejemplos.exceptions.NotEnoughMoneyException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.*;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class CuentaTest {

    Cuenta cuenta;

    @BeforeEach
    void initMetodoTest() {
        this.cuenta = new Cuenta("Juan", new BigDecimal("1000.12345"));
        System.out.println("Starting Method");
    }

    @AfterEach
    void tearDown() {
        System.out.println("Finishing Method");
    }

    @BeforeAll
    static void beforeAll() {
        System.out.println("Starting TestClass");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("Finishing TestClass");
    }

    @Test
    @DisplayName("Prueba de Nombre, que no sea null, que coincida con el valor esperado")
    void testNombreCuenta() {
        assertNotNull(cuenta.getPersona(), "La cuenta no puede ser nula");
        assertEquals("Juan", cuenta.getPersona(), "El nombre de la cuenta no es el que se esperaba");
        assertTrue(cuenta.getPersona().equals("Juan"), "Nombre Cuenta esperada debe ser igual al valor esperado");
    }

    @Test
    @DisplayName("Probando el saldo de la cuenta corriente, que no sea null, mayor que cero, valor esperado.")
    void testSaldoCuenta() {
        assertEquals(1000.12345, cuenta.getSaldo().doubleValue());
        assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0);
        assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
    }

    @Test
    @DisplayName("Testeando referencias que sean iguales, con el método equals.")
    void testReferenciaCuenta() {
        cuenta = new Cuenta("John Doe", new BigDecimal("8900.9997"));
        Cuenta cuenta2 = new Cuenta("John Doe", new BigDecimal("8900.9997"));

        assertEquals(cuenta2, cuenta);
    }

    @Test
    @DisplayName("Testeando Debito Sobre cuenta")
    void testDebitoCuenta() {
        cuenta.debito(new BigDecimal(100));
        assertNotNull(cuenta.getSaldo());
        assertEquals(900, cuenta.getSaldo().intValue());
        assertEquals("900.12345", cuenta.getSaldo().toPlainString());
    }

    @Test
    @DisplayName("Testeando Credito sobre cuenta")
    void testCreditoCuenta() {
        cuenta.credito(new BigDecimal(100));
        assertNotNull(cuenta.getSaldo());
        assertEquals(1100, cuenta.getSaldo().intValue());
        assertEquals("1100.12345", cuenta.getSaldo().toPlainString());
    }

    @Test
    @DisplayName("Probando que la excepción se arroje")
    void testNotEnoughMoneyException() {
        Exception exception = assertThrows(NotEnoughMoneyException.class, () -> {
            cuenta.debito(new BigDecimal(1500));
        });
        assertEquals("Dinero Insuficiente", exception.getMessage());
    }

    @Test
    @Disabled
    void testRelacionBancoCuenta() {
        Cuenta cuenta1 = new Cuenta("John Doe", new BigDecimal("2500"));
        Cuenta cuenta2 = new Cuenta("Juan", new BigDecimal("700"));

        Banco banco = new Banco();
        banco.addCuenta(cuenta1);
        banco.addCuenta(cuenta2);

        banco.setNombre("BBVA");
        banco.transferir(cuenta1, cuenta2, new BigDecimal(500));

        assertAll(() -> {
            assertEquals("1200", cuenta2.getSaldo().toPlainString());
        }, () -> {
            assertEquals("2000", cuenta1.getSaldo().toPlainString());
        }, () -> {
            assertEquals(2, banco.getCuentas().size());
        }, () -> {
            assertEquals("BBVA", cuenta1.getBanco().getNombre());
        }, () -> {
            assertEquals("Juan", banco.getCuentas().stream().filter(c -> c.getPersona().equals("Juan"))
                    .findFirst().get().getPersona());
        }, () -> {
            assertTrue(banco.getCuentas().stream().anyMatch(c -> c.getPersona().equals("John Doe")));
        });
    }

    @Test
    @EnabledOnOs(OS.WINDOWS)
    void testSoloWindows() {

    }

    @Test
    @EnabledOnOs({OS.LINUX, OS.MAC})
    void testSoloLinuxMac() {

    }

    @Test
    @DisabledOnOs(OS.WINDOWS)
    void testNoWindows() {

    }

    @Test
    @EnabledOnJre(JRE.JAVA_8)
    void soloJDK8() {

    }

    @Test
    @DisabledOnJre(JRE.JAVA_15)
    void testNoJDK15() {

    }

    @Test
    void imprimirSystemProperties() {
        Properties properties = System.getProperties();
        properties.forEach((k, v) -> System.out.println(k + ": " + v));
    }

    @Test
    @EnabledIfSystemProperty(named = "java.version", matches = "21.0.5")
    void testJavaVersion() {

    }

    @Test
    @DisabledIfSystemProperty(named = "os.arch", matches = ".*32.*")
    void disableOn64Arch() {

    }

    @Test
    @EnabledIfSystemProperty(named = "os.arch", matches = ".*32.*")
    void testNO64() {

    }

    @Test
    @EnabledIfSystemProperty(named = "user.name", matches = "EM2024007497")
    void testUsername() {

    }

    @Test
    @EnabledIfSystemProperty(named = "ENV", matches = "dev")
    void testDev() {
    }

    @Test
    void imprimirVariablesAmbiente() {
        Map<String, String> getenv = System.getenv();
        getenv.forEach((k, v) -> System.out.println(k + " = " + v));
    }

    @Test
    @EnabledIfEnvironmentVariable(named = "JAVA_HOME", matches = ".*jdk-21")
    void testJavaHome() {

    }

    @Test
    @EnabledIfEnvironmentVariable(named = "NUMBER_OF_PROCESSORS", matches = "12")
    void testProcesadores() {

    }

    @Test
    @EnabledIfEnvironmentVariable(named = "ENVIRONMENT", matches = "DEV")
    void testEnv() {

    }

    @Test
    @DisabledIfEnvironmentVariable(named = "ENVIRONMENT", matches = "prod")
    void testEnvProdDisabled() {
    }
}