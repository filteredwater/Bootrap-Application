package GUI;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ControllerTest {

    @Test
    public void isStringValidNumericalInputShouldReturnTrueForPositiveNumber(){
        Controller controller = new Controller();

        assertTrue(controller.isStringValidNumericalInput("0.9"));
        assertTrue(controller.isStringValidNumericalInput("2"));
    }

    @Test
    public void isStringValidNumericalInputShouldReturnTrueFor0(){
        Controller controller = new Controller();

        assertTrue(controller.isStringValidNumericalInput("0"));
        assertTrue(controller.isStringValidNumericalInput("00"));
    }

    @Test
    public void isStringValidNumericalInputShouldReturnTrueForNegativeNumber(){
        Controller controller = new Controller();

        assertTrue(controller.isStringValidNumericalInput("-0.9"));
        assertTrue(controller.isStringValidNumericalInput("-2"));
    }

    @Test
    public void isStringValidNumericalInputShouldReturnFalseForNonnumeric(){
        Controller controller = new Controller();

        assertFalse(controller.isStringValidNumericalInput("-abc"));
        assertFalse(controller.isStringValidNumericalInput("abc"));
        assertFalse(controller.isStringValidNumericalInput("*"));
        assertFalse(controller.isStringValidNumericalInput(" abc"));
    }

    @Test
    public void isStringValidNumericalInputShouldReturnTrueWhen0StandsBeforeNumber(){
        Controller controller = new Controller();

        assertTrue(controller.isStringValidNumericalInput("02"));
        assertTrue(controller.isStringValidNumericalInput("03.9"));
        assertTrue(controller.isStringValidNumericalInput("-02"));
    }

    @Test
    public void isStringValidNumericalInputShouldReturnTrueForEmptyString(){
        Controller controller = new Controller();

        assertTrue(controller.isStringValidNumericalInput(""));
    }

    @Test
    public void isStringValidNumericalInputShouldReturnTrueWhenDotStandsBeforeNumber(){
        Controller controller = new Controller();

        assertTrue(controller.isStringValidNumericalInput(".3"));
        assertTrue(controller.isStringValidNumericalInput("-.8"));
    }

    @Test
    public void isStringValidNumericalInputWithoutValueShouldReturnTrueWhenItReallyShould(){
        Controller controller = new Controller();

        assertTrue(controller.isStringValidNumericalInputWithoutValue(""));
        assertTrue(controller.isStringValidNumericalInputWithoutValue("."));
    }

    @Test
    public void isStringValidNumericalInputWithoutValueShouldReturnFalseForValidValue(){
        Controller controller = new Controller();

        assertFalse(controller.isStringValidNumericalInputWithoutValue("5"));
        assertFalse(controller.isStringValidNumericalInputWithoutValue(".5"));
        assertFalse(controller.isStringValidNumericalInputWithoutValue("-5"));
    }


}